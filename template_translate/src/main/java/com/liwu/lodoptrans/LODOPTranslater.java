package com.liwu.lodoptrans;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LODOPTranslater {

    public static final String PRINTER_TYPE_KM_202BT = "202BT";
    public static final String PRINTER_TYPE_QR_488BT = "488BT";
    public static final String PRINTER_TYPE_QR_486BT = "486BT";
    public static final String PRINTER_TYPE_HM_D45 = "D45";
    private final String IGNORE = "IGNORE";
    private int textCount = -1;
    private String fontSize = "0";
    /**
     * 上一个元素是否是打印Text
     */
    private boolean lastElementIsText = false;
    /**
     * 上一个元素是否是打印条形码
     */
    private boolean lastElementIsBarcode = false;
    private HashMap<Pattern, String> dependMaps = new HashMap<>();
    private boolean printStackTrace;
    private int templateHeight = 0;
    //是否在后台对\r\n做处理，拆分字符串
    private boolean splitInBackPlatform = false;
    /**
     * 一般的key，value为null时代表不替换，不为null代表替换为对应的值；
     *      如果key是logo,为null代表不打印logo，其他表示打印logo
     */
    private HashMap<String, String> replaceMaps;
    private String printerBrand;
    private String printerType;

    /**
     * 是否是小的打印机
     * @return 是否
     */
    private boolean isSmallTemplate() {
        if (printerType == null) {
            return true;
        }
        if (printerType.equals(PRINTER_TYPE_HM_D45)
                || printerType.equals(PRINTER_TYPE_KM_202BT)
                || printerType.equals(PRINTER_TYPE_QR_488BT)
                || printerType.equals(PRINTER_TYPE_QR_486BT)) {
            return false;
        } else {
            return true;
        }
    }

    public String translate(String source, HashMap<String, String> replaceMaps, String printerBrand, String printerType) {
        return translate(source, replaceMaps, printerBrand, printerType, false);
    }

    public String translate(String source, boolean justTranslate, HashMap<String, String> replaceMaps, String printerBrand, String printerType) {
        return translate(source, justTranslate, replaceMaps, printerBrand, printerType, false);
    }

    public String translate(String source, HashMap<String, String> replaceMaps, String printerBrand, String printerType, boolean printStackTrace) {
        return translate(source, false, replaceMaps, printerBrand, printerType, printStackTrace);
    }

    /**
     * 转换的方法
     * @param source 原始的LODOP字符串
     * @param justTranslate 是否是仅仅用于转换，如果是是，将忽略replaceMaps里面的值
     * @param replaceMaps 用于进行二次替换，在仅仅用于转换时为null
     * @param printerBrand 打印机品牌
     * @param printerType 打印机型号，打印机会影响纸的宽高，及位置的计算
     * @param printStackTrace 是否打印日志
     * @return 转换好的蓝牙模板
     */
    public String translate(String source, boolean justTranslate, HashMap<String, String> replaceMaps, String printerBrand, String printerType, boolean printStackTrace) {
        this.printerBrand = printerBrand;
        this.printerType = printerType;
        this.printStackTrace = printStackTrace;
        //这个处理的原因参照对应replaceMaps属性的说明
        if (justTranslate) {
            this.replaceMaps = new HashMap<>();
            this.replaceMaps.put("logo", "");
        } else {
            if (replaceMaps == null) {
                this.replaceMaps = new HashMap<>();
            } else {
                this.replaceMaps = replaceMaps;
            }
        }
        initDependMap();
        textCount = -1;
        fontSize = "0";
        lastElementIsText = false;
        lastElementIsBarcode = false;
        try {
            //临时存储读取到的内容，决定下一步的转换如何执行
            StringBuilder temp = new StringBuilder();
            //存储读取到的一行有效的LODOP代码
            StringBuilder oneLine = new StringBuilder();
            //代表要转换的蓝牙模板
            StringBuilder allContent = new StringBuilder();
            int index = 0;
            int sourceLengthTotal = source.length();
            //检测到“LODOP”字符则将该值置为true,开始往oneLine里面写入内容，LODOP的指令总是以“LODOP”开头
            boolean startAdd = false;
            //开头，蓝牙打印要先设置纸的宽高，LODOP中无对应设置
            allContent.append("[");
            allContent.append("\r\n");
            allContent.append("\"PAGE#" + (isSmallTemplate() ? "576" : getBigMachineWidth()) + ",-HEIGHT-\",");
            allContent.append("\r\n");
            //是否已经取完所有字符
            while (index < sourceLengthTotal) {
                //取字符
                char c = source.charAt(index);
                index++;
                if (!startAdd) {
                    temp.append(c);
                } else {
                    oneLine.append(c);
                }
                if (temp.toString().contains("LODOP")) {
                    startAdd = true;
                    temp = new StringBuilder();
                    oneLine.append("LODOP");
                }
                //读取到;代表一句LODOP指令结束，可以开始转换了
                if (c == ';') {

                    String oneLineStr = oneLine.toString();
                    //用于存储一句LODOP转换出的所有蓝牙代码
                    ArrayList<String> appTemplateStringLists = new ArrayList<>();
                    //LODOP打印语句的参数，在“()”之间
                    String keyWordInLODOP = oneLineStr.substring(oneLineStr.indexOf("(") + 1, oneLineStr.indexOf(")"));
                    //参数以“,”间隔，
                    String[] paramsStringArrayInLODOP = keyWordInLODOP.split(",");
                    boolean correct = false;
                    if (oneLineStr.startsWith("LODOP.ADD_PRINT_IMAGE")) {
                        correct = doPic(appTemplateStringLists, paramsStringArrayInLODOP);
                    } else if (oneLineStr.startsWith("LODOP.ADD_PRINT_TEXT")) {
                        correct = doText(allContent, appTemplateStringLists, paramsStringArrayInLODOP);
                    } else if (oneLineStr.startsWith("LODOP.SET_PRINT_STYLEA")) {
                        correct = doTextParam(allContent, appTemplateStringLists, paramsStringArrayInLODOP);
                    } else if (oneLineStr.startsWith("LODOP.ADD_PRINT_BARCODE")) {
                        correct = doBarcodeAndQRCode(appTemplateStringLists, paramsStringArrayInLODOP);
                    } else if (oneLineStr.startsWith("LODOP.ADD_PRINT_LINE")) {
                        correct = doLine(appTemplateStringLists, paramsStringArrayInLODOP);
                    } else if (oneLineStr.startsWith("LODOP.ADD_PRINT_RECT")) {
                        correct = doLineByRect(appTemplateStringLists, paramsStringArrayInLODOP);
                    } else {
                        correct = true;
                    }
                    if (!correct) {
                        return null;
                    }
                    //写入allContent
                    for (String tem : appTemplateStringLists) {
                        if (!tem.equals(IGNORE)) {
                            allContent.append(tem);
                            allContent.append("\r\n");
                        }
                    }

                    startAdd = false;
                    appTemplateStringLists.clear();
                    oneLine = new StringBuilder();
                }
            }
            //调整尺寸
            adjustLastFontYPosition(allContent);
            //结尾
            allContent.append("]");
            allContent.append("\r\n");
            //去掉最后一个逗号
            int last = allContent.lastIndexOf(",");
            allContent.replace(last, last + 1, "");

            String contentString = allContent.toString();
            //替换中文冒号
            contentString = contentString.replaceAll("：", ":");
            //设置模板高度
            int heightMM = templateHeight / 8;
            if (templateHeight % 8 != 0) {
                heightMM++;
            }
            if (isSmallTemplate()) {
                heightMM *= 8;
            }
            contentString = contentString.replace("-HEIGHT-", heightMM + "");
            return contentString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getBigMachineWidth() {
        if (printerType.equals(PRINTER_TYPE_QR_488BT) || printerType.equals(PRINTER_TYPE_QR_486BT)) {
            return "95";
        } else {
            return "98";
        }
    }

    private boolean doLineByRect(ArrayList<String> appTemplateStringLists, String[] paramsStringArrayInLODOP) {
        //绘制矩形，等于四条线
        try {
            String startY = paramsStringArrayInLODOP[0];
            String startX = paramsStringArrayInLODOP[1];
            String width = paramsStringArrayInLODOP[2];
            String height = paramsStringArrayInLODOP[3];
            String lineType = paramsStringArrayInLODOP[4];
            String lineWidth = paramsStringArrayInLODOP[5];
            startX = scaleHorizontal(startX);
            startY = scaleVertical(startY);
            width = scaleHorizontal(width);
            height = scaleVertical(height);
            lineWidth = scaleHorizontal(lineWidth);
            int right = twoStringAdd(startX, width);
            int bottom = twoStringAdd(startY, height);
            String appTemplateString1 = "\"LINE#" + startX + "," + startY + "," + right + "," + startY + "," + lineWidth + "\",";
            appTemplateStringLists.add(appTemplateString1);
            String appTemplateString2 = "\"LINE#" + startX + "," + startY + "," + startX + "," + bottom + "," + lineWidth + "\",";
            appTemplateStringLists.add(appTemplateString2);
            String appTemplateString3 = "\"LINE#" + right + "," + startY + "," + right + "," + bottom + "," + lineWidth + "\",";
            appTemplateStringLists.add(appTemplateString3);
            String appTemplateString4 = "\"LINE#" + startX + "," + bottom + "," + right + "," + bottom + "," + lineWidth + "\",";
            appTemplateStringLists.add(appTemplateString4);
            setTemplateHeight(startY, height);
            return true;
        } catch (Exception e) {
            if (printStackTrace) {
                e.printStackTrace();
            }
            return false;
        }
    }

    private boolean doLine(ArrayList<String> appTemplateStringLists, String[] paramsStringArrayInLODOP) {
        //画线
        try {
            String startY = paramsStringArrayInLODOP[0];
            String startX = paramsStringArrayInLODOP[1];
            String endY = paramsStringArrayInLODOP[2];
            String endX = paramsStringArrayInLODOP[3];
            String lineType = paramsStringArrayInLODOP[4];
            String lineWidth = paramsStringArrayInLODOP[5];
            if (isSmallTemplate()) {
                int startXInt = (int) (Float.parseFloat(startX) + 0.5);
                int startYInt = (int) (Float.parseFloat(startY) + 0.5);
                int endXInt = (int) (Float.parseFloat(endX) + 0.5);
                int endYInt = (int) (Float.parseFloat(endY) + 0.5);
                //LODOP的位置可能包括线宽，但是蓝牙中不包括
                if (Math.abs(startXInt - endXInt) < 4) {
                    //竖线
                    startX = Math.min(startXInt, endXInt) + "";
                    endX = Math.min(startXInt, endXInt) + "";
                } else if (Math.abs(startYInt - endYInt) < 4) {
                    //横线
                    startY = Math.min(startYInt, endYInt) + "";
                    endY = Math.min(startYInt, endYInt) + "";
                }
            }
            //"LINE#49,283,49,548,2",
            String appTemplateString = "\"LINE#" + scaleHorizontal(startX) + "," + scaleVertical(startY) + "," + scaleHorizontal(endX) + "," + scaleVertical(endY) + "," + scaleHorizontal(lineWidth) + "\",";
            setTemplateHeight(scaleVertical(startY), "1");
            setTemplateHeight(scaleVertical(endY), "1");
            appTemplateStringLists.add(appTemplateString);
            return true;
        } catch (Exception e) {
            if (printStackTrace) {
                e.printStackTrace();
            }
            return false;
        }
    }

    private boolean doBarcodeAndQRCode(ArrayList<String> appTemplateStringLists, String[] paramsStringArrayInLODOP) {
        lastElementIsBarcode = false;
        lastElementIsText = false;
        try {
            //绘制条形码或二维码
            String startY = paramsStringArrayInLODOP[0];
            String startX = paramsStringArrayInLODOP[1];
            String width = paramsStringArrayInLODOP[2];
            String height = paramsStringArrayInLODOP[3];
            String codeType = paramsStringArrayInLODOP[4].replaceAll("\"", "");
            String content = paramsStringArrayInLODOP[5].replaceAll("\"", "");
            String contentChanged = changeToBlueToothTextContent(content);
            if (!isEmpty(contentChanged)) {
                //259,39,325,70,"128B","{快递单号条码}"
                if (codeType.equals("QRCode")) {
                    //二维码
                    String appTemplateString = "\"QRCODE#" + scaleHorizontal(startX) + "," + scaleVertical(startY) + "," + qrCodeScale(scaleHorizontal(width)) + "," + qrCodeScale(scaleVertical(height)) + "," + contentChanged + "\",";
                    setTemplateHeight(scaleVertical(startY), scaleVertical(height));
                    appTemplateStringLists.add(appTemplateString);
                } else {
                    //"BARCODE#290,706,286,40,3.kuaidinum",
                    lastElementIsBarcode = true;
                    int barcodeHeight = Integer.parseInt(scaleVertical(height));
                    barcodeHeight -= (24 + 4);
                    if (isSmallTemplate()) {
                        int xF = (int) Float.parseFloat(startX);
                        int widthF = (int) Float.parseFloat(width);
                        if (xF - widthF / 4 >= 0 && widthF + widthF / 2 <= 576 / 2) {
                            //条形码宽度增加一半
                            startX = (xF - widthF / 4) + "";
                            width = (widthF + widthF / 2) + "";
                        }
                    }
                    String appTemplateString = "\"BARCODE#" + scaleHorizontal(startX) + "," + scaleVertical(startY) + "," + scaleHorizontal(width) + "," + barcodeHeight + "," + contentChanged + "\",";
                    setTemplateHeight(scaleVertical(startY), scaleVertical(height));
                    appTemplateStringLists.add(appTemplateString);
                    //默认下面有数字
                    String bottomTextY = Integer.parseInt(scaleVertical(startY)) + barcodeHeight + 4 + "";
                    String textTemplateString = "\"TEXT#" + scaleHorizontal(startX) + "," + bottomTextY + "," + scaleHorizontal(width) + ",24,24,1,,," + contentChanged + "|6.wider\",";
                    appTemplateStringLists.add(textTemplateString);
                }
            }
            return true;
        } catch (Exception e) {
            if (printStackTrace) {
                e.printStackTrace();
            }
            return false;
        }
    }

    private String smallerBarcodeX(String startX, String width) {
        try {
            int xF = (int) Float.parseFloat(startX);
            int widthF = (int) Float.parseFloat(width);
            xF -= widthF / 4;
            return xF + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return startX;
    }

    private String biggerBarcodeWidth(String width) {
        try {
            int widthF = (int) Float.parseFloat(width);
            widthF += widthF / 2;
            return widthF + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return width;
    }

    private boolean doTextParam(StringBuilder allContent, ArrayList<String> appTemplateStringLists, String[] paramsStringArrayInLODOP) {
        //设置字体属性
        try {
            String useForWhat = paramsStringArrayInLODOP[0];
            String styleName = paramsStringArrayInLODOP[1].replaceAll("\"", "");
            String styleValue = paramsStringArrayInLODOP[2].replaceAll("\"", "");
            String appTemplateString = IGNORE;
            switch (styleName) {
                //设置字体大小
                case "FontSize":
                    String valueReal = beStandard(fScale(styleValue)) + "";
                    fontSize = valueReal;
                    setLastElementParams(allContent, 4, valueReal);
                    break;
                //设置反白
                case "FontColor":
                    if (styleValue.equalsIgnoreCase("#FFFFFF")) {
                        setLastElementParams(allContent, 7, "1");
                    }
                //设置居中
                case "Alignment":
                    if (styleValue.equalsIgnoreCase("2")) {
                        setLastElementParams(allContent, 5, "1");
                    }
                    break;
                //设置加粗
                case "Bold":
                    setLastElementParams(allContent, 6, styleValue);
                    break;
                //是否需要条码下面的文字
                case "ShowBarText":
                    setLastElementParams(allContent, 3, styleValue.equals("0") ? "28" : "0", false);
                    break;
                case "AlignJustify":
                    //x居中
                    setLastElementParams(allContent, 5, styleValue.equals("1") ? "1" : "0");
                    //y居中
                    setLastElementParams(allContent, 1, "center");
                    //添加一个6.wider
                    setLastElementParams(allContent, 8, "6.wider");
                    break;
            }
            appTemplateStringLists.add(appTemplateString);
            return true;
        } catch (Exception e) {
            if (printStackTrace) {
                e.printStackTrace();
            }
            return false;
        }
    }

    private boolean doText(StringBuilder allContent, ArrayList<String> appTemplateStringLists, String[] paramsStringArrayInLODOP) {
        adjustLastFontYPosition(allContent);
        lastElementIsBarcode = false;
        lastElementIsText = false;
        //写字
        try {
            String startY = paramsStringArrayInLODOP[0];
            String startX = paramsStringArrayInLODOP[1];
            String width = paramsStringArrayInLODOP[2];
            String height = paramsStringArrayInLODOP[3];
            String content = paramsStringArrayInLODOP[4].replaceAll("\"", "");
            String[] contentArray;
            if (splitInBackPlatform) {
                contentArray = content.split("\\\\r\\\\n");
            } else {
                //去掉最后一个无效的\r\n
                while (content.endsWith("\\r\\n")) {
                    content = content.substring(0, content.lastIndexOf("\\r\\n"));
                }
                //把有效的\r\n替换为“■■■■”，在蓝牙识别到这个再做换行处理
                String replaceRN = content.replaceAll("\\\\r\\\\n", "■■■■");
                contentArray = new String[]{replaceRN};
            }
            textCount = contentArray.length;
            for (int i = 0; i < contentArray.length; i++) {
                String contentChanged = changeToBlueToothTextContent(contentArray[i]);
                /*if (!splitInBackPlatform) {
                    contentChanged = handleUnCorrectSplit(contentChanged);
                }*/
                if (!isEmpty(contentChanged)) {
                    String fontSize = "24";
                    if (isALotTips(contentChanged)) {
                        fontSize = "16";
                    }
                    lastElementIsText = true;
                    //"TEXT#59,301,,,24,,1,,1.recname|4. |3.recPhone",
                    String appTemplateString = "\"TEXT#" + scaleHorizontal(startX) + "," + scaleVertical(startY) + "," + scaleHorizontal(width) + "," + scaleVertical(height) + "," + fontSize + ",,,," + contentChanged + "\",";
                    setTemplateHeight(scaleVertical(startY), scaleVertical(height));
                    appTemplateStringLists.add(appTemplateString);
                } else {
                    textCount--;
                }
            }
            return true;
        } catch (Exception e) {
            if (printStackTrace) {
                e.printStackTrace();
            }
            return false;
        }
    }

    //是否是说明文字
    private boolean isALotTips(String content) {
        if (content.contains("代表您") && content.contains("包装完好") && content.contains("没有划痕")) {
            return true;
        } else if (content.contains("确认") && content.contains("签收") && content.contains("包裹完好")) {
            return true;
        }
        return false;
    }

    private String handleUnCorrectSplit(String contentChanged) {
        if (!contentChanged.contains("■■■■")) {
            return contentChanged;
        }
        StringBuilder correctContentAppender = new StringBuilder();
        String[] split = contentChanged.split("■■■■");
        if (split.length == 1) {
            return contentChanged;
        }
        for (int i = 0; i < split.length; i++) {
            String eachSplit = split[i];
            if (i == 0) {
                eachSplit = handleTail(eachSplit);
            } else if (i == split.length - 1) {
                eachSplit = handleHead(eachSplit);
            } else {
                eachSplit = handleTail(eachSplit);
                eachSplit = handleHead(eachSplit);
            }
            correctContentAppender.append(eachSplit);
            if (i != split.length - 1) {
                correctContentAppender.append("■■■■");
            }
        }
        return correctContentAppender.toString();
    }

    private String handleHead(String s) {
        if (s.length() > 1) {
            String contentAfterKey = s.substring(0, 1);
            if (contentAfterKey.equals("|")) {
                s = s.substring(1);
            } else {
                s = "4." + s;
            }
        }
        return s;
    }

    private String handleTail(String s) {
        int length = s.length();
        if (length > 3) {
            String contentBeforeKey = s.substring(length - 3, length);
            if (contentBeforeKey.equals("|4.")) {
                s = s.substring(0, length - 3);
            }
        }
        return s;
    }

    private boolean isEmpty(String text) {
        return text == null || text.equals("");
    }

    private boolean doPic(ArrayList<String> appTemplateStringLists, String[] paramsStringArrayInLODOP) {
        //绘图
        try {
            String startY = paramsStringArrayInLODOP[0];
            String startX = paramsStringArrayInLODOP[1];
            String width = paramsStringArrayInLODOP[2];
            String height = paramsStringArrayInLODOP[3];
            String content = paramsStringArrayInLODOP[4];
            //Log.d("LODOP_PIC", "picContent=" + content);
            //代表不打印图片
            String contentChange = "3.contentUnSet";
            if (content.contains("<img src=")) {
                //图片链接
                if (content.contains("http")) {
                    //<img src=\"http://cdn.kuaidi100.com/images/user/history/ent/print/ico_print_1.png?222\"/>
                    String url = content.substring(content.indexOf("http"), content.indexOf("\\\"/>"));
                    String toReplaceLogo = replaceMaps.get("logo");
                    if (url.contains("annengkuaidi") && toReplaceLogo == null) {
                        contentChange = "3.contentUnSet";
                    } else {
                        //“.”在蓝牙打印中有作用，为避免干扰，先将其替换为“{p}”并且encode
                        url = url.replaceAll("\\.", "{p}");
                        //Log.d("LODOP_PIC", "picUrl=" + url);
                        contentChange = "3." + URLEncoder.encode(url, "UTF-8");
                    }
                    // annengkuaidi
                } else {
                    // <br><img src=\"{水印图片}\"/>
                }
            } else {
                //{快递公司logo}
                if (content.contains("{")) {
                    String contentInBracket = content.substring(content.indexOf("{") + 1, content.indexOf("}"));
                    contentChange = "3." + contentInBracket;
                    if (contentInBracket.equals("快递公司logo")) {
                        String toReplaceLogo = replaceMaps.get("logo");
                        if (toReplaceLogo == null) {
                            contentChange = "3.contentUnSet";
                        }
                    }
                }
            }

            //代收货款200*200,缩放到对应的位置
            if (contentChange.contains("img_dshk")) {
                int dshkPicWidth = 200;
                int dshkPicHeight = 200;
                int paramsX = (int) (Float.parseFloat(scaleHorizontal(startX)) + 0.5);
                int paramsY = (int) (Float.parseFloat(scaleVertical(startY)) + 0.5);
                int paramsWidth = (int) (Float.parseFloat(scaleHorizontal(width)) + 0.5);
                int paramsHeight = (int) (Float.parseFloat(scaleVertical(height)) + 0.5);

                //裁剪
                int xIncrease = 0, yIncrease = 0;
                float scale = dshkPicHeight / dshkPicWidth;
                float rectScale = paramsHeight / (float) paramsWidth;
                int fitWidth = paramsWidth;
                int fitHeight = paramsHeight;
                if (scale > rectScale) {
                    fitWidth = (int) (paramsHeight / scale + 0.5);
                    xIncrease = (paramsWidth - fitWidth) / 2;
                    yIncrease = 0;
                } else {
                    fitHeight = (int) (paramsWidth * scale + 0.5);
                    yIncrease = (paramsHeight - fitHeight) / 2;
                    xIncrease = 0;
                }
                paramsX += xIncrease;
                paramsY += yIncrease;

                int pageWidthHM = Integer.parseInt(getBigMachineWidth()) * 8;
                if (paramsX + fitWidth > pageWidthHM) {
                    paramsX = pageWidthHM - fitWidth - 2;
                }
                String appTemplateString = "\"IMG#" + paramsX + "," + paramsY + "," + fitWidth + "," + fitHeight + "," + contentChange + "\",";
                setTemplateHeight(paramsY + "", dshkPicHeight + "");
                appTemplateStringLists.add(appTemplateString);
            } else {
                if (!contentChange.equals("3.contentUnSet")) {
                    String appTemplateString = "\"IMG#" + scaleHorizontal(startX) + "," + scaleVertical(startY) + "," + scaleHorizontal(width) + "," + scaleVertical(height) + "," + contentChange + "\",";
                    setTemplateHeight(scaleVertical(startY), scaleVertical(height));
                    appTemplateStringLists.add(appTemplateString);
                }
            }
            return true;
        } catch (Exception e) {
            if (printStackTrace) {
                e.printStackTrace();
            }
            return false;
        }
    }

    /**
     * 检测模板高度，用于最后替换蓝牙模板的高度
     * @param y 当前的y值
     * @param height 当前的高度
     */
    private void setTemplateHeight(String y, String height) {
        try {
            float yF = Float.parseFloat(y);
            float hF = Float.parseFloat(height);
            templateHeight = Math.max(templateHeight, (int) (yF + hF));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * LODOP中有换行，蓝牙模板对应要从第二行开始增加高度
     * 该方法由于有不可避免的问题，已弃用，在APP中再做处理
     * @param allContent
     */
    private void adjustLastFontYPosition(StringBuilder allContent) {
        if (!splitInBackPlatform) {
            return;
        }
        if (textCount == -1) {
            //Log.d("adjustLastFontYPosition", "第一次");
            return;
        }
        try {
            //Log.d("adjustLastFontYPosition", "textCount=" + textCount);
            String toFind = allContent.toString();
            int startReplaceIndex = allContent.length();
            for (int i = textCount - 1; i >= 1; i--) {
                if (i != textCount - 1) {
                    toFind = allContent.substring(0, startReplaceIndex - 1);
                }
                startReplaceIndex = toFind.lastIndexOf("TEXT#");
                int endReplaceIndex = toFind.lastIndexOf("\"");
                String srcString = toFind.substring(startReplaceIndex, endReplaceIndex);
                String[] split = srcString.split(",");
                float y = Float.parseFloat(split[1]);
                float fontSize = Float.parseFloat(split[4]);
                split[1] = y + fontSize * i + "";
                String replacedString = "";
                for (int j = 0; j < split.length; j++) {
                    replacedString += split[j];
                    if (j != split.length - 1) {
                        replacedString += ",";
                    }
                }
                //Log.d("adjustLastFontYPosition", "替换 " + srcString + " 为 " + replacedString);
                allContent.replace(startReplaceIndex, endReplaceIndex, replacedString);
            }
        } catch (Exception e) {
            if (printStackTrace) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 这些是对应的有依赖关系的元素，
     * 如"件数[:：]"), "5.3count"，
     * 表示如果在蓝牙打印中解析5.3count的值为空，则前面的正则表达式匹配的部分也不打印
     */
    private void initDependMap() {
        dependMaps.put(Pattern.compile("件数[:：]"), "5.3count");
        dependMaps.put(Pattern.compile("\\{数量\\}(件)?"), "5.3count");
        dependMaps.put(Pattern.compile("重量[:：]"), "5.3weight");
        dependMaps.put(Pattern.compile("\\{重量\\}(kg)?"), "5.3weight");
        dependMaps.put(Pattern.compile("快递费用[:：]"), "5.3freight");
        dependMaps.put(Pattern.compile("\\{快递费小写\\}(元)?"), "5.3freight");
        dependMaps.put(Pattern.compile("\\{快递费大写\\}"), "5.3freight");
        dependMaps.put(Pattern.compile("保价费用[:：]"), "5.3valinsPay");
        dependMaps.put(Pattern.compile("\\{保价费用\\}(元)?"), "5.3valinsPay");
        dependMaps.put(Pattern.compile("保价金额[:：]"), "5.3valins");
        dependMaps.put(Pattern.compile("声明价值[:：]"), "5.3valins");
        dependMaps.put(Pattern.compile("\\{保价金额小写\\}(元)?"), "5.3valins");
        dependMaps.put(Pattern.compile("^标准■■■■快递$"), "5.3notCollection");
        dependMaps.put(Pattern.compile("\\{省内件\\}"), "5.3notCollection");
        dependMaps.put(Pattern.compile("代收货款[:：]"), "5.1collection");
        dependMaps.put(Pattern.compile("\\{代收货款小写\\}(元)?"), "5.1collection");
        dependMaps.put(Pattern.compile("邮票号[:：]"), "5.1stampid");
        dependMaps.put(Pattern.compile("\\{邮票编号\\}"), "5.1stampid");
        dependMaps.put(Pattern.compile("\\{需转单\\}"), "5.3anDTB");
        dependMaps.put(Pattern.compile("\\{寄件人电话尾号\\}"), "5.3sendPhone");
        dependMaps.put(Pattern.compile("\\{收件人电话尾号\\}"), "5.3recPhone");
        dependMaps.put(Pattern.compile("\\{备注\\}"), "5.1comment");
        dependMaps.put(Pattern.compile("\\{件数\\}"), "5.3pkgCount");
        dependMaps.put(Pattern.compile("取件员[:：]"), "5.3取件员");
        dependMaps.put(Pattern.compile("\\{取件员\\}"), "5.3取件员");
        dependMaps.put(Pattern.compile("到付[:：]"), "5.3onlyConFreight");
        dependMaps.put(Pattern.compile("\\{到付金额\\}(元)?"), "5.3onlyConFreight");
    }

    public String changeToBlueToothTextContent(String content) {
        //Log.d("pattern", "content=" + content);
        //当前读取到的值
        StringBuilder temp = new StringBuilder();
        //转换出的蓝牙模板的值
        StringBuilder blueTypeSb = new StringBuilder();
        //判断是否依赖,确定依赖值
        MatchResult matchResult = tryToMatch(content);
        //取值
        for (int i = 0; i < content.length(); i++) {
            boolean needAddTemp = false;
            boolean isEnd = false;
            boolean jump = false;

            //判断当前位置是否是依赖起始位置，是则把temp中的值添加到蓝牙模板，并添加一个依赖部分的指针设置
            if (matchResult != null && matchResult.matchCount > 0) {
                for (int j = 0; j < matchResult.matchCount; j++) {
                    int matchIndex = matchResult.matchStartIndex.get(j);
                    //如果当前的index(i)等于依赖起始位置
                    if (matchIndex == i) {

                        //把temp中的值添加到蓝牙模板
                        String toAppend = temp.toString();
                        toAppend = toAppend.replaceAll("\\{", "");
                        toAppend = toAppend.replaceAll("\\}", "");
                        if (!isEmpty(toAppend)) {
                            blueTypeSb.append("4.");
                            blueTypeSb.append(toAppend);
                            temp = new StringBuilder();
                        }

                        if (blueTypeSb.length() != 0 && blueTypeSb.charAt(blueTypeSb.length() - 1) != '|') {
                            blueTypeSb.append("|");
                        }
                        //将依赖部分的指针移到当前位置
                        blueTypeSb.append("6.setDependIndex|");
                    }
                }
            }

            char curC = content.charAt(i);
            if (temp.length() != 0) {
                //是“{”或者“}”代表需要开始将temp添加到蓝牙指令中
                if (curC == '}' || curC == '{') {
                    needAddTemp = true;
                }
            }
            //最后一个字符
            if (i == content.length() - 1) {
                isEnd = true;
            }
            //把当前字符添加到temp中
            temp.append(curC);
            //将temp添加到蓝牙指令中
            if (needAddTemp || isEnd) {
                String toAppend = temp.toString();
                toAppend = toAppend.replaceAll("\\{", "");
                toAppend = toAppend.replaceAll("\\}", "");

                //当前字符为“}”，代表是一个括号中的值需要转换，否则原值直接写入
                if (curC == '}') {
                    jump = tryToAdd(blueTypeSb, toAppend);
                } else {
                    blueTypeSb.append("4.");
                    blueTypeSb.append(toAppend);
                }

                temp = new StringBuilder();
                if (!isEnd && !jump) {
                    blueTypeSb.append("|");
                }
                if (curC == '{') {
                    i--;
                }
            }

            //判断当前位置是否是依赖结束位置，是则把temp中的值添加到蓝牙模板，然后添加一个对应的依赖值
            if (matchResult != null && matchResult.matchCount > 0) {
                for (int j = 0; j < matchResult.matchCount; j++) {
                    int matchIndex = matchResult.matchEndIndex.get(j) - 1;
                    if (matchIndex == i) {

                        //把temp中的值添加到蓝牙模板
                        String toAppend = temp.toString();
                        toAppend = toAppend.replaceAll("\\{", "");
                        toAppend = toAppend.replaceAll("\\}", "");
                        if (!isEmpty(toAppend)) {
                            blueTypeSb.append("4.");
                            blueTypeSb.append(toAppend);
                            temp = new StringBuilder();
                        }

                        if (blueTypeSb.length() != 0 && blueTypeSb.charAt(blueTypeSb.length() - 1) != '|') {
                            blueTypeSb.append("|");
                        }
                        //添加一个对应的依赖值
                        blueTypeSb.append(matchResult.dependValues.get(j));
                        if (!isEnd) {
                            blueTypeSb.append("|");
                        }
                    }
                }
            }
        }
        int length = blueTypeSb.length();
        //去掉最后一个竖线
        if (length != 0 && blueTypeSb.charAt(length - 1) == '|') {
            blueTypeSb.replace(length - 1, length, "");
        }
        //Log.d("changeToBlue", "result=" + blueTypeSb);
        return blueTypeSb.toString();
    }

    private MatchResult tryToMatch(String content) {
        MatchResult matchResult = new MatchResult();
        for (Pattern pa : dependMaps.keySet()) {
            Matcher match = pa.matcher(content);
            boolean find = false;
            int start = -1, end = -1;
            //在所有找到的符合正则表达式的内容中，找到最大最小值，代表起始和终止
            while (match.find()) {
                if (find) {
                    start = Math.min(start, match.start());
                    end = Math.max(end, match.end());
                } else {
                    start = match.start();
                    end = match.end();
                }
                find = true;
            }
            if (find) {
                String value = dependMaps.get(pa);
                int indexInResult = matchResult.dependValues.indexOf(value);
                if (indexInResult == -1) {
                    //本来没有
                    matchResult.dependValues.add(value);
                    matchResult.matchCount++;
                    matchResult.matchStartIndex.add(start);
                    matchResult.matchEndIndex.add(end);
                } else {
                    //已经有一个了
                    int startInResult = matchResult.matchStartIndex.get(indexInResult);
                    matchResult.matchStartIndex.set(indexInResult, Math.min(startInResult, start));

                    int endInResult = matchResult.matchEndIndex.get(indexInResult);
                    matchResult.matchEndIndex.set(indexInResult, Math.max(endInResult, end));
                }
            }
        }
        //Log.d("matchResult", "result=" + matchResult);
        return matchResult;
    }

    /**
     * LODOP括号中的值对应在蓝牙模板中的值
     * @param blueTypeSb 对应在蓝牙模板中的值
     * @param toAppend LODOP括号中的值
     * @return 是否跳过
     */
    private boolean tryToAdd(StringBuilder blueTypeSb, String toAppend) {
        boolean jump = false;
        switch (toAppend) {
            case "进度":
                blueTypeSb.append("3.childOrderPosition");
                break;
            case "始发城市":
                blueTypeSb.append("3.sendCity");
                break;
            case "邮票编号":
                blueTypeSb.append("1.stampid");
                break;
            case "快递公司":
                blueTypeSb.append("3.companyChNameLong");
                break;
            case "快递公司2":
                blueTypeSb.append("3.companyChNameLong2");
                break;
            case "寄件人公司名称":
                appendWithReplace(blueTypeSb, "sendcompany", "1.sendcompany");
                break;
            case "收件人公司名称":
                appendWithReplace(blueTypeSb, "reccompany", "1.reccompany");
                break;
            case "费用类型":
                blueTypeSb.append("3.moneyTypeDes");
                break;
            case "子单号":
                blueTypeSb.append("3.childNumber");
                break;
            case "自定义内容":
                String cusContent = replaceMaps.get("cusprintarea");
                if (cusContent != null) {
                    blueTypeSb.append("4.").append(cusContent);
                } else {
                    jump = true;
                }
                break;
            case "付款方式":
                appendWithReplace(blueTypeSb, "payment", "3.payway");
                break;
            case "需转单":
                blueTypeSb.append("4.需转单");
                break;
            case "代收货款小写":
                appendWithReplace(blueTypeSb, "collection", "1.collection");
                break;
            case "代收货款大写":
                appendWithReplace(blueTypeSb, "collectionDX", "3.collectionBig");
                break;
            case "备注":
                appendWithReplace(blueTypeSb, "comment", "1.comment");
                break;
            case "件数":
                blueTypeSb.append("3.pkgCount");
                break;
            case "寄件人姓名":
                appendWithReplace(blueTypeSb, "sendname", "1.sendname");
                break;
            case "寄件人联系电话":
                appendWithReplace(blueTypeSb, "sendmobile", "1.sendmobile");
                break;
            case "寄件人联系电话2":
                blueTypeSb.append("1.sendtel");
                break;
            case "寄件地址":
                appendWithReplace(blueTypeSb, "sendaddr", "1.sendaddr|4. |1.sendaddrdet");
                break;
            case "大头笔":
            case "大头笔码":
                blueTypeSb.append("2.bulkpen");
                break;
            case "始发地区域编码":
                blueTypeSb.append("2.orgCode");
                break;
            case "目的地区域编码":
                blueTypeSb.append("2.destCode");
                break;
            case "始发地":
                blueTypeSb.append("2.orgName");
                break;
            case "目的地":
                blueTypeSb.append("2.destName");
                break;
            case "始发分拣编码":
                blueTypeSb.append("2.orgSortingCode");
                break;
            case "始发分拣名称":
                blueTypeSb.append("2.orgSortingName");
                break;
            case "目的分拣编码":
                blueTypeSb.append("2.destSortingCode");
                break;
            case "目的分拣名称":
                blueTypeSb.append("2.destSortingName");
                break;
            case "始发其他信息":
                blueTypeSb.append("2.orgExtra");
                break;
            case "目的其他信息":
                blueTypeSb.append("2.destExtra");
                break;
            case "集包编码":
                blueTypeSb.append("2.pkgCode");
                break;
            case "集包地名称":
                blueTypeSb.append("2.pkgName");
                break;
            case "路区":
                blueTypeSb.append("2.road");
                break;
            case "二维码":
                blueTypeSb.append("2.qrCode");
                break;
            case "快递公司订单号":
                blueTypeSb.append("2.kdComOrderNum");
                break;
            case "业务类型编码":
                blueTypeSb.append("2.expressCode");
                break;
            case "业务类型名称":
                blueTypeSb.append("2.expressName");
                break;
            case "收件人姓名":
                appendWithReplace(blueTypeSb, "recname", "1.recname");
                break;
            case "收件人联系电话":
                appendWithReplace(blueTypeSb, "recmobile", "1.recmobile");
                break;
            case "收件人联系电话2":
                blueTypeSb.append("1.rectel");
                break;
            case "收件地址":
                appendWithReplace(blueTypeSb, "recaddr", "1.recaddr|4. |1.recaddrdet");
                break;
            case "寄件日期":
                appendWithReplace(blueTypeSb, "date", "3.date");
                break;
            case "物品名称":
                appendWithReplace(blueTypeSb, "cargo", "3.cargo");
                break;
            case "数量":
                appendWithReplace(blueTypeSb, "count", "3.count");
                break;
            case "重量":
                appendWithReplace(blueTypeSb, "weight", "3.weight2");
                break;
            case "代收":
                blueTypeSb.append("3.rightCollection");
                break;
            case "保价":
                blueTypeSb.append("3.rightValins");
                break;
            case "保价金额小写":
                appendWithReplace(blueTypeSb, "valins", "3.valins");
                break;
            case "保价金额大写":
                appendWithReplace(blueTypeSb, "valinsDX", "3.valinsBig");
                break;
            case "快递单号":
            case "快递单号条码":
                blueTypeSb.append("3.kuaidinum");
                break;
            case "取件员":
                appendWithReplace(blueTypeSb, "gotcouriername", "3.取件员");
                break;
            case "收件人电话尾号":
                blueTypeSb.append("3.收件人电话尾号");
                break;
            case "快递费小写":
                appendWithReplace(blueTypeSb, "freight", "3.freight");
                break;
            case "快递费大写":
                appendWithReplace(blueTypeSb, "freightDX", "3.freightBig");
                break;
            case "保价费用":
                appendWithReplace(blueTypeSb, "valinspay", "3.valinsPay");
                break;
            case "打印时间":
                //原版打印的是时分秒，待定
                blueTypeSb.append("3.hms");
                break;
            case "实名信息":
                blueTypeSb.append("3.身份证encryptcardinfo");
                break;
            case "到付金额":
                blueTypeSb.append("3.onlyConFreight");
                break;
            default:
                jump = true;
                //blueTypeSb.append("4.")/*.append("[").append(toAppend).append("]")*/;
                break;
        }
        return jump;
    }

    /**
     * 若replaceMap中有值，则替换
     * @param blueTypeSb 蓝牙模板sb
     * @param replaceKey key
     * @param srcContent 原始值
     */
    private void appendWithReplace(StringBuilder blueTypeSb, String replaceKey, String srcContent) {
        if (isSmallTemplate()) {
            blueTypeSb.append(srcContent);
            return;
        }
        String toReplaceContent = replaceMaps.get(replaceKey);
        if (toReplaceContent == null) {
            blueTypeSb.append(srcContent);
        } else {
            blueTypeSb.append("4.").append(toReplaceContent);
        }
    }

    private void setLastElementParams(StringBuilder allContent, int index, String styleValue) {
        setLastElementParams(allContent, index, styleValue, true);
    }

    /**
     * 设置上一个元素的属性。在LODOP中有部分属性设置是单独的指令
     * @param allContent 当前蓝牙模板内容
     * @param index 设置的属性的位置
     * @param styleValue 属性名
     * @param paramSetToText 是否是设置字体
     */
    private void setLastElementParams(StringBuilder allContent, int index, String styleValue, boolean paramSetToText) {
        if (!((paramSetToText && lastElementIsText) || (!paramSetToText && lastElementIsBarcode))) {
            return;
        }
        String toFind = allContent.toString();
        int startReplaceIndex = allContent.length();
        //当前textCount固定为1，循环次数为1
        for (int i = 0; i < (paramSetToText ? textCount : 1); i++) {
            //忽略
            if (i != 0) {
                toFind = allContent.substring(0, startReplaceIndex - 1);
            }
            //找到起始替换坐标
            startReplaceIndex = toFind.lastIndexOf(paramSetToText ? "TEXT#" : "BARCODE#");
            //找到终止替换位置
            int endReplaceIndex = -1;
            if (paramSetToText) {
                endReplaceIndex = toFind.lastIndexOf("\"");
            } else {
                //条形码的设置，可能并不紧跟在条形码打印之后，所以需要这个处理
                int lastTextIndex = toFind.lastIndexOf("\"TEXT#");
                endReplaceIndex = toFind.substring(0, lastTextIndex).lastIndexOf("\"");
            }
            //需要设置的部分
            String srcString = toFind.substring(startReplaceIndex, endReplaceIndex);
            //蓝牙切分，获取属性
            String[] srcArray = srcString.split("#");
            String[] paramsArray = srcArray[1].split(",");
            if (paramSetToText) {
                //设置对应的属性值
                if (index == 8) {
                    paramsArray[index] = paramsArray[index] + "|" + styleValue;
                } else if (index == 1 && styleValue.equals("center")) {
                    //居中，手动设置y的值
                    int height = Integer.parseInt(paramsArray[3]);
                    int y = Integer.parseInt(paramsArray[1]);
                    int fontSize = Integer.parseInt(paramsArray[4]);
                    paramsArray[1] = (y + (height - fontSize)) + "";
                } else if (index == 4 && isALotTips(paramsArray[8])) {
                    //设置说明文字的字体大小，固定为16，无需再设置
                    return;
                } else {
                    paramsArray[index] = styleValue;
                }
            } else {
                //设置条码
                int heightAdd = Integer.parseInt(paramsArray[index]);
                //高度增加
                paramsArray[index] = Integer.parseInt(styleValue) + heightAdd + "";
            }
            //拼接
            String replacedString = srcArray[0] + "#";
            for (int j = 0; j < paramsArray.length; j++) {
                replacedString += paramsArray[j];
                if (j != paramsArray.length - 1) {
                    replacedString += ",";
                }
            }
            //替换
            allContent.replace(startReplaceIndex, endReplaceIndex, replacedString);
            //条码
            if (!paramSetToText) {
                //条码增加高度
                int heightAdd = Integer.parseInt(styleValue);
                if (index == 3 && heightAdd > 0) {
                    //去掉默认添加的文字
                    int deleteStart = toFind.lastIndexOf("TEXT#") - 1;
                    int deleteEnd = toFind.lastIndexOf("\"") + 4;
                    allContent.replace(deleteStart, deleteEnd, "");
                }
            }
        }
    }

    public int twoStringAdd(String s1, String s2) {
        try {
            return Integer.parseInt(s1) + Integer.parseInt(s2);
        } catch (Exception e) {
            if (printStackTrace) {
                if (printStackTrace) {
                    e.printStackTrace();
                }
            }
            return 0;
        }
    }

    /**
     * 将LODOP的横向尺寸转为蓝牙打印的横向尺寸，蓝牙打印为像素值
     * @param srcP LODOP尺寸
     * @return 蓝牙尺寸
     */
    private String scaleHorizontal(String srcP) {
        float srcFloat = Float.parseFloat(srcP);
        int scaled = (int) (srcFloat * 2 + 0.5);
        //最小为1
        if (scaled < 1) {
            scaled = 1;
        }
        return scaled + "";
    }

    /**
     * 将LODOP的纵向尺寸转为蓝牙打印的纵向尺寸，蓝牙打印为像素值
     * @param srcP LODOP尺寸
     * @return 蓝牙尺寸
     */
    private String scaleVertical(String srcP) {
        float srcFloat = Float.parseFloat(srcP);
        int scaled = 0;
        if (isSmallTemplate()) {
            scaled = (int) (srcFloat * 2 + 0.5);
        } else {
            if (printerType != null && isKMType()) {
                scaled = (int) (srcFloat * 2 * 1.034 + 0.5);
            } else {
                scaled = (int) (srcFloat * 2 * 1.073 + 0.5);
            }
        }
        //最小为1
        if (scaled < 1) {
            scaled = 1;
        }
        return scaled + "";
    }

    private boolean isKMType() {
        return printerType.equals(PRINTER_TYPE_KM_202BT)
                || printerType.equals(PRINTER_TYPE_QR_488BT)
                || printerType.equals(PRINTER_TYPE_QR_486BT);
    }

    /**
     * 字体转换
     * @param srcF LODOP尺寸
     * @return 蓝牙尺寸
     */
    private String fScale(String srcF) {
        float srcFloat = Float.parseFloat(srcF);
        int scaled = (int) (srcFloat * 25.4f * 8f / 72f + 0.5);
        return scaled + "";
    }

    private boolean toCenter = true;

    /**
     * 转换为蓝牙中的标准字体
     * @param fontsizeStr 原始字体
     * @return 标准字体
     */
    private int beStandard(String fontsizeStr) {
        int fontsizeInt = Integer.parseInt(fontsizeStr);
        if (fontsizeInt < (toCenter ? 20 : 24)) {
            return 16;
        } else if (fontsizeInt < (toCenter ? 28 : 32)) {
            return 24;
        } else if (fontsizeInt < (toCenter ? 40 : 48)) {
            return 32;
        } else if (fontsizeInt < (toCenter ? 56 : 64)) {
            return 48;
        } else if (fontsizeInt < (toCenter ? 68 : 72)) {
            return 64;
        } else if (fontsizeInt < (toCenter ? 84 : 96)) {
            return 72;
        } else {
            return 96;
        }
    }

    /**
     * 二维码大小调整
     * @param src 原始尺寸
     * @return 调整后的尺寸
     */
    private String qrCodeScale(String src) {
        if (isSmallTemplate()) {
            return src;
        } else {
            float srcFloat = Float.parseFloat(src);
            int scaled = (int) (srcFloat * 4 / 5);
            return scaled + "";
        }
    }

    private class MatchResult {
        //有依赖关系的个数
        int matchCount = 0;
        //依赖起始位置
        ArrayList<Integer> matchStartIndex = new ArrayList<>();
        //依赖终止位置
        ArrayList<Integer> matchEndIndex = new ArrayList<>();
        //找到的有依赖关系的对应蓝牙指令的值
        ArrayList<String> dependValues = new ArrayList<>();

        @Override
        public String toString() {
            return "matchCount=" + matchCount + " matchStartIndex=" + matchStartIndex + " matchEndIndex=" + matchEndIndex + " dependValues=" + dependValues;
        }
    }

}
