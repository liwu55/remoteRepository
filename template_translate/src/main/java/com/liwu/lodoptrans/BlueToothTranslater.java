package com.liwu.lodoptrans;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;

public class BlueToothTranslater {

    public String translateToLODOP(String blueToothTemplate) {
        try {
            JSONArray array = new JSONArray(blueToothTemplate);
            StringBuilder lodopTemplate = new StringBuilder();
            for (int i = 0; i < array.length(); i++) {
                String eachRow = array.optString(i);

                String[] typeAndData = eachRow.split("#");
                String type = typeAndData[0];
                String data = typeAndData[1];
                if (type.equals("PAGE")) {
                    //lodop不需要设置宽高
                } else if (type.equals("LINE")) {
                    drawLine(data, lodopTemplate);
                } else if (type.equals("TEXT")) {
                    drawText(data, lodopTemplate);
                } else if (type.equals("BARCODE")) {
                    drawBarcode(data, lodopTemplate);
                } else if (type.equals("IMG")) {
                    drawImage(data, lodopTemplate);
                } else if (type.equals("QRCODE")) {
                    drawQrCode(data, lodopTemplate);
                }
            }
            return lodopTemplate.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void drawQrCode(String data, StringBuilder lodopTemplate) {
        String[] dataArray = data.split(",");
        String x = dataArray[0];
        String y = dataArray[1];
        String width = dataArray[2];
        String height = dataArray[3];
        x = pScale(x);
        y = pScale(y);
        width = pScale(width);
        height = pScale(height);
        String what = dataArray[4];
        String[] picData = what.split("\\.");
        int xInt = getIntValue(x);
        int yInt = getIntValue(y);
        int widthInt = getIntValue(width);
        int heightInt = getIntValue(height);

        lodopTemplate.append("LODOP.ADD_PRINT_BARCODE(");
        lodopTemplate.append(y).append(",");
        lodopTemplate.append(x).append(",");
        lodopTemplate.append(width).append(",");
        lodopTemplate.append(height).append(",\"QRCode\",\"{快递单号条码}\");\r\n");
    }

    private void drawImage(String data, StringBuilder lodopTemplate) {

        String[] dataArray = data.split(",");
        String x = dataArray[0];
        String y = dataArray[1];
        String width = dataArray[2];
        String height = dataArray[3];
        x = pScale(x);
        y = pScale(y);
        width = pScale(width);
        height = pScale(height);
        String bitmapStr = dataArray[4];
        int xInt, yInt, widthInt, heightInt;
        xInt = getIntValue(x);
        yInt = getIntValue(y);
        widthInt = getIntValue(width);
        heightInt = getIntValue(height);
        if (hasMinusOne(xInt, yInt)) {
            return;
        }

        lodopTemplate.append("LODOP.ADD_PRINT_IMAGE(");
        lodopTemplate.append(y).append(",");
        lodopTemplate.append(x).append(",");
        lodopTemplate.append(width).append(",");
        lodopTemplate.append(height).append(",\"{快递公司logo}\");\r\n");
    }

    private void drawBarcode(String data, StringBuilder lodopTemplate) {
        String[] dataArray = data.split(",");
        String x = dataArray[0];
        String y = dataArray[1];
        String width = dataArray[2];
        String height = dataArray[3];
        x = pScale(x);
        y = pScale(y);
        width = pScale(width);
        height = pScale(height);
        String content = dataArray[4];
        int xInt, yInt, widthInt, heightInt;
        xInt = getIntValue(x);
        yInt = getIntValue(y);
        widthInt = getIntValue(width);
        heightInt = getIntValue(height);
        if (hasMinusOne(xInt, yInt, widthInt, heightInt)) {
            return;
        }

        //减小宽度为2/3
        xInt += widthInt / 6;
        widthInt -= widthInt / 3;

        lodopTemplate.append("LODOP.ADD_PRINT_BARCODE(");
        lodopTemplate.append(y).append(",");
        lodopTemplate.append(xInt).append(",");
        lodopTemplate.append(widthInt).append(",");
        lodopTemplate.append(height).append(",\"128Auto\",\"{快递单号条码}\");\r\n");
        //隐藏条码自带的单号
        lodopTemplate.append("LODOP.SET_PRINT_STYLEA(0,\"ShowBarText\",0);\r\n");
    }

    private void drawText(String data, StringBuilder lodopTemplate) {
        String[] dataArray = data.split(",");
        String xStart = dataArray[0];
        String yStart = dataArray[1];
        String width = dataArray[2];
        String height = dataArray[3];
        String fontsize = dataArray[4];
        String center = dataArray[5];
        String bold = dataArray[6];
        String reverse = dataArray[7];
        String content = dataArray[8];

        int xStartInt, yStartInt, widthInt, heightInt, fontsizeInt;
        xStartInt = getIntValue(xStart);
        yStartInt = getIntValue(yStart);
        widthInt = getIntValue(width);
        heightInt = getIntValue(height);
        fontsizeInt = getIntValue(fontsize);

        xStart = pScale(xStart);
        yStart = pScale(yStart);
        width = pScale(width);
        height = pScale(height);
        fontsize = fScale(fontsize);

        if (width.equals("0")) {
            int toRightLineWidth = (576 - xStartInt) / 2;
            width = pScale(toRightLineWidth + "");
            try {
                String[] eachContentArray = content.split("\\.");
                String contentType = eachContentArray[0];
                String contentValue = eachContentArray[1];
                if (contentType.equals("4")) {
                    int leastWidth = (int) (contentValue.getBytes("GBK").length * fontsizeInt / 2 * 1.5 + 0.5);
                    if (toRightLineWidth < leastWidth) {
                        width = pScale(leastWidth + "");
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        if (height.equals("0")) {
            height = pScale(fontsizeInt * 1.5 + "");
        }

        lodopTemplate.append("LODOP.ADD_PRINT_TEXT(");
        lodopTemplate.append(yStart).append(",");
        lodopTemplate.append(xStart).append(",");
        lodopTemplate.append(width).append(",");
        lodopTemplate.append(height).append(",\"");
        String contentInLODOP = parseToLODOPContent(content);
        lodopTemplate
                .append(contentInLODOP)
                .append("\");\r\n");
        //设置字体大小
        lodopTemplate.append("LODOP.SET_PRINT_STYLEA(");
        lodopTemplate.append("0,\"FontSize\",");
        lodopTemplate.append(fontsize).append(");\r\n");
        if (bold.equals("1")) {
            lodopTemplate.append("LODOP.SET_PRINT_STYLEA(");
            lodopTemplate.append("0,\"Bold\",1);\r\n");
        }
        if (center.equals("1")) {
            lodopTemplate.append("LODOP.SET_PRINT_STYLEA(0,\"Alignment\",2);\r\n");
        }
        int lineSpacing = (int) (fontsizeInt / 6f + 0.5);
        lodopTemplate.append("LODOP.SET_PRINT_STYLEA(0,\"LineSpacing\",-").append(lineSpacing).append(");\r\n");
    }

    private String parseToLODOPContent(String content) {
        content = content.replaceAll("：", ":");
        content = content.replaceAll("1.sendaddr\\|4. \\|1.sendaddrdet", "4.{寄件地址}");
        content = content.replaceAll("1.recaddr\\|4. \\|1.recaddrdet", "4.{收件地址}");
        content = content.replaceAll("shunfeng2.destCode@huitongkuaidi2.bulkpen\\|2.destSortingCode@2.bulkpen", "4.{大头笔}");
        String[] contentArray = content.split("\\|");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < contentArray.length; i++) {
            String eachContent = contentArray[i];
            String contentType, contentValue;
            String[] eachContentArray = null;
            if (eachContent.contains(".")) {
                eachContentArray = eachContent.split("\\.");
                contentType = eachContentArray[0];
                contentValue = eachContentArray[1];
            } else {
                contentType = eachContent.substring(0, 1);
                contentValue = eachContent.substring(1);
            }
            switch (contentType) {
                case "1": {
                    switch (contentValue) {
                        case "comment":
                            sb.append("{备注}");
                            break;
                        case "collection":
                            sb.append("{代收货款小写}");
                            break;
                        case "sendcompany":
                            sb.append("{寄件人公司名称}");
                            break;
                        case "reccompany":
                            sb.append("{收件人公司名称}");
                            break;
                        case "stampid":
                            sb.append("{邮票编号}");
                            break;
                        case "sendname":
                            sb.append("{寄件人姓名}");
                            break;
                        case "sendmobile":
                            sb.append("{寄件人联系电话} {寄件人联系电话2}");
                            break;
                        case "recname":
                            sb.append("{收件人姓名}");
                            break;
                        case "recmobile":
                            sb.append("{收件人联系电话} {收件人联系电话2}");
                            break;
                    }
                    break;
                }
                case "2": {
                    switch (contentValue) {
                        case "bulkpen":
                            sb.append("{大头笔}");
                            break;
                        case "orgCode":
                            sb.append("{始发地区域编码}");
                            break;
                        case "destCode":
                            sb.append("{目的地区域编码}");
                            break;
                        case "orgName":
                            sb.append("{始发地}");
                            break;
                        case "destName":
                            sb.append("{目的地}");
                            break;
                        case "orgSortingCode":
                            sb.append("{始发分拣编码}");
                            break;
                        case "orgSortingName":
                            sb.append("{始发分拣名称}");
                            break;
                        case "destSortingCode":
                            sb.append("{目的分拣编码}");
                            break;
                        case "destSortingName":
                            sb.append("{目的分拣名称}");
                            break;
                        case "orgExtra":
                            sb.append("{始发分拣名称}");
                            break;
                        case "destExtra":
                            sb.append("{目的其他信息}");
                            break;
                        case "pkgCode":
                            sb.append("{集包编码}");
                            break;
                        case "pkgName":
                            sb.append("{集包地名称}");
                            break;
                    }
                    break;
                }
                case "3": {
                    String toAppend = "";
                    if (contentValue.startsWith("身份证")) {
                        toAppend = "";
                    } else {
                        switch (contentValue) {
                            case "onlyConFreight":
                                toAppend = "{到付金额}";
                                break;
                            case "hms":
                                toAppend = "{打印时间}";
                                break;
                            case "rightValins":
                                toAppend = "{保价}";
                                break;
                            case "rightCollection":
                                toAppend = "{代收}";
                                break;
                            case "pkgCount":
                                toAppend = "{件数}";
                                break;
                            case "moneyTypeDes":
                                toAppend = "{费用类型}";
                                break;
                            case "companyChNameLong":
                                toAppend = "{快递公司}";
                                break;
                            case "sendCity":
                                toAppend = "{始发城市}";
                                break;
                            case "stampQRCode":
                                toAppend = "{邮票二维码内容}";
                                break;
                            case "orderPosition":
                            case "childOrderPosition":
                                toAppend = "1/1";
                                break;
                            case "stamp":
                                toAppend = "{邮票号}";
                                break;
                            case "collection":
                                toAppend = "{代收货款}";
                                break;
                            case "collectionBig":
                                toAppend = "{大写代收货款}";
                                break;
                            case "putInCodeKuaiDiNum":
                                toAppend = "{入库快递单号}";
                                break;
                            case "putInCodePosition":
                                toAppend = "{货架号}";
                                break;
                            case "com":
                                toAppend = "{快递公司}";
                                break;
                            case "serviceType":
                                toAppend = "{业务类型名称}";
                                break;
                            case "recPhone":
                                toAppend = "{收件人联系电话}";
                                break;
                            case "payway":
                                toAppend = "{付款方式}";
                                break;
                            case "weight":
                                toAppend = "{重量}";
                                break;
                            case "weight2":
                                toAppend = "{重量}";
                                break;
                            case "price":
                                toAppend = "{快递费小写}";
                                break;
                            case "count":
                                toAppend = "{数量}";
                                break;
                            case "time":
                                toAppend = "{打印时间}";
                                break;
                            case "sendPhone":
                                toAppend = "{寄件人联系电话}";
                                break;
                            case "mainOrderKuaidiNum":
                                toAppend = "{主单号}";
                                break;
                            case "kuaidinum":
                            case "childOrderKuaidiNum":
                                toAppend = "{快递单号条码}";
                                break;
                            case "date":
                                toAppend = "{打印时间}";
                                break;
                            case "dateShort":
                                toAppend = "{打印时间}";
                                break;
                            case "cargo":
                                toAppend = "{物品名称}";
                                break;
                            case "valins":
                                toAppend = "{保价金额小写}";
                                break;
                            case "valinsPay":
                                toAppend = "{保价费用}";
                                break;
                            case "保价金额":
                                toAppend = "保价金额：";
                                break;
                            case "保价费用":
                                toAppend = "保价费用：";
                                break;
                            case "kuaidinumSpace":
                                toAppend = "{快递单号条码}";
                                break;
                            case "kuaidinumSpace2":
                                toAppend = "{快递单号条码}";
                                break;
                            case "payaccount":
                                toAppend = "{月结账号}";
                                break;
                            case "到付":
                                toAppend = "到付";
                                break;
                            case "收件人电话尾号":
                                toAppend = "{收件人电话尾号}";
                                break;
                            case "取件员":
                                toAppend = "{取件员}";
                                break;
                            case "freight":
                                toAppend = "{快递费小写}";
                                break;
                            case "onlyFreight":
                                toAppend = "{快递费小写}";
                                break;
                            case "conFreight":
                                toAppend = "{快递费小写}";
                                break;
                            case "stickerTitle":
                                toAppend = "{小贴纸标题}";
                                break;
                            case "stickerName":
                                toAppend = "{小贴纸名字}";
                                break;
                            case "stickerPhone":
                                toAppend = "{小贴纸电话}";
                                break;
                        }
                    }
                    sb.append(toAppend);
                    break;
                }
                case "4":
                case "14":
                    contentValue = contentValue.replaceAll("■■■■", "\\\\" + "r" + "\\\\" + "n");
                    sb.append(contentValue);
                    if (eachContentArray != null && eachContentArray.length > 2) {
                        for (int j = 2; j < eachContentArray.length; j++) {
                            sb.append(".").append(eachContentArray[j]);
                        }
                    }
                    break;
            }
        }
        return sb.toString();
    }

    private void drawLine(String data, StringBuilder lodopTemplate) {
        String[] dataArray = data.split(",");
        String xStart = dataArray[0];
        String yStart = dataArray[1];
        String xEnd = dataArray[2];
        String yEnd = dataArray[3];

        xStart = pScale(xStart);
        yStart = pScale(yStart);
        xEnd = pScale(xEnd);
        yEnd = pScale(yEnd);

        String lineWidth = dataArray[4];
        lineWidth = pScale(lineWidth);

        int xStartInt, yStartInt, xEndInt, yEndInt, lineWidthInt;
        xStartInt = getIntValue(xStart);
        yStartInt = getIntValue(yStart);
        xEndInt = getIntValue(xEnd);
        yEndInt = getIntValue(yEnd);
        lineWidthInt = getIntValue(lineWidth);
        if (hasMinusOne(xStartInt, yStartInt, xEndInt, yEndInt, lineWidthInt)) {
            return;
        }

        if (yStart.equals(yEnd)) {
            yEnd = (yEndInt + lineWidthInt) + "";
        }

        if (xStart.equals(xEnd)) {
            xEnd = (xEndInt + lineWidthInt) + "";
        }

        lodopTemplate.append("LODOP.ADD_PRINT_LINE(");
        lodopTemplate.append(yStart).append(",");
        lodopTemplate.append(xStart).append(",");
        lodopTemplate.append(yEnd).append(",");
        lodopTemplate.append(xEnd).append(",");

        //线条类型
        lodopTemplate.append("0").append(",");
        //线宽
        lodopTemplate.append("1").append(")").append(";").append("\r\n");
    }

    //是否有值是-1
    private boolean hasMinusOne(int... values) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == -1) {
                return true;
            }
        }
        return false;
    }

    //获取Int值，出错为-1
    private int getIntValue(String text) {
        try {
            return (int) Float.parseFloat(text);
        } catch (Exception e) {
            return -1;
        }
    }

    private String pScale(String srcP) {
        float srcFloat;
        try {
            srcFloat = Float.parseFloat(srcP);
        } catch (Exception e) {
            return "0";
        }
        int scaled = (int) (srcFloat / 2 + 0.5);
        return scaled + "";
    }

    private String fScale(String srcF) {
        float srcFloat;
        try {
            srcFloat = Float.parseFloat(srcF);
        } catch (Exception e) {
            srcFloat = 16;
        }
        int scaled = (int) (srcFloat / 25.4f / 8f * 72f + 0.5);
        return scaled + "";
    }
}
