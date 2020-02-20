package com.example.kingdee.something;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.kingdee.something.new_p.NewActivity;
import com.example.kingdee.something.util.ToggleLog;
import com.liwu.lodoptrans.BlueToothTranslater;
import com.liwu.lodoptrans.LODOPTranslater;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatConfig.setDebugEnable(true);
        StatService.trackCustomEvent(this, "onCreate", "");
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Intent service = new Intent(this, CameraService.class);
//        startService(service);
        initViewAndListener();
        List l = new ArrayList();
//        LODOPTranslater.translate();
        //LODOPTranslater.changeToBlueToothTextContent("{物品名称} {数量}件\\r\\n{收件人公司名称}\\r\\n{备注}\\r\\n{自定义内容}\\r\\n{邮票编号}");
        lodopThing();
//        btThing();
        String s="";
        String s1=",";
        String[] sArray = s.split(",");
        String[] s1Array = s1.split(",");
    }

    private void btThing() {
        //优速
        /*String source = "[\n" +
                "\"PAGE#576,800\",\n" +
                "\"LINE#2,0,574,0,2\",\n" +
                "\"TEXT#288,8,288,,48,1,,,4. \",\n" +
                "\"LINE#2,64,574,64,2\",\n" +
                "\"TEXT#2,78,574,,64,1,1,,shunfeng2.destCode@huitongkuaidi2.bulkpen|2.destSortingCode@2.bulkpen\",\n" +
                "\"LINE#2,156,574,156,2\",\n" +
                "\"TEXT#2,164,414,,48,1,,,2.destName\",\n" +
                "\"TEXT#414,176,162,,24,1,,,4.主单\",\n" +
                "\"LINE#2,220,574,220,2\",\n" +
                "\"IMG#10,264,40,48,3.iconYSReceiver\",\n" +
                "\"TEXT#58,252,,,24,,,,1.recname|4. |3.recPhone\",\n" +
                "\"TEXT#58,276,348,72,24,,,,4. |1.recaddr|4. |1.recaddrdet\",\n" +
                "\"LINE#2,356,414,356,2\",\n" +
                "\"IMG#10,380,40,48,3.iconYSSend\",\n" +
                "\"TEXT#58,364,,,16,,,,1.sendname|4. |3.sendPhone\",\n" +
                "\"TEXT#58,380,348,48,16,,,,1.sendaddr|4. |1.sendaddrdet\",\n" +
                "\"LINE#2,452,574,452,2\",\n" +
                "\"TEXT#2,460,574,,24,1,,,3.kuaidinum\",\n" +
                "\"BARCODE#2,492,574,106,3.kuaidinum\",\n" +
                "\"LINE#2,606,574,606,2\",\n" +
                "\"TEXT#10,614,396,64,16,,,,4.快件送达收件人地址，经收件或收件人（寄件人）允许的代收人签字，视为送达，您的签字代表您已经验收包裹，并已确认商品信息无误，包装完好，没有划痕，破损等表面质量问题。\",\n" +
                "\"TEXT#422,614,,,24,,,,4.签收人\",\n" +
                "\"LINE#2,686,574,686,2\",\n" +
                "\"TEXT#10,694,,,24,,,,4.件数：\",\n" +
                "\"TEXT#82,694,,,24,,,,3.count\",\n" +
                "\"TEXT#262,694,,,24,,,,4.重量：\",\n" +
                "\"TEXT#334,694,,,24,,,,3.weight\",\n" +
                "\"TEXT#10,718,,,24,,,,4.品名：\",\n" +
                "\"TEXT#82,718,,,24,,,,3.cargo\",\n" +
                "\"TEXT#422,694,,,48,,,,4.已验视\",\n" +
                "\"LINE#2,750,574,750,2\",\n" +
                "\"TEXT#414,766,,,16,,,,3.time\",\n" +
                "\"LINE#2,798,574,798,2\",\n" +
                "\"LINE#2,0,2,798,2\",\n" +
                "\"LINE#574,0,574,798,2\",\n" +
                "\"LINE#414,156,414,452,2\",\n" +
                "\"LINE#414,606,414,750,2\"\n" +
                "]\n";*/
        String source = "[\n" +
                "\"PAGE#576,1584\",\n" +
                "\"LINE#0,68,576,68,2\",\n" +
                "\"TEXT#296,616,,,24,,,,4.签收人/签收时间\",\n" +
                "\"LINE#0,608,576,608,2\",\n" +
                "\"TEXT#64,496,,,16,,,,1.sendname\",\n" +
                "\"TEXT#296,496,,,16,,,,3.sendPhone\",\n" +
                "\"TEXT#64,528,504,48,16,,,,1.sendaddr|4. |1.sendaddrdet\",\n" +
                "\"LINE#0,488,576,488,2\",\n" +
                "\"TEXT#64,376,,,24,,1,,1.recname\",\n" +
                "\"TEXT#296,376,,,24,,1,,3.recPhone\",\n" +
                "\"TEXT#64,408,504,72,24,,1,,1.recaddr|4. |1.recaddrdet\",\n" +
                "\"LINE#0,368,576,368,2\",\n" +
                "\"TEXT#296,320,,,32,,,,3.date\",\n" +
                "\"TEXT#0,320,288,,32,1,,,2.orgName\",\n" +
                "\"LINE#0,304,576,304,2\",\n" +
                "\"TEXT#0,240,576,,48,1,1,,2.bulkpen\",\n" +
                "\"LINE#0,224,576,224,2\",\n" +
                "\"TEXT#16,404,24,48,24,,,,4.收件\",\n" +
                "\"LINE#56,368,56,760,2\",\n" +
                "\"LINE#288,304,288,368,2\",\n" +
                "\"LINE#288,608,288,760,2\",\n" +
                "\"TEXT#0,168,576,,48,1,,,3.kuaidinumSpace\",\n" +
                "\"BARCODE#0,76,576,84,3.kuaidinum\",\n" +
                "\"TEXT#16,524,24,48,24,,,,4.寄件\",\n" +
                "\"TEXT#16,660,24,48,24,,,,4.服务\",\n" +
                "\"TEXT#64,616,,,16,,,,4.内容品名：\",\n" +
                "\"TEXT#56,640,232,,16,1,,,3.cargo\",\n" +
                "\"TEXT#64,664,,,16,,,,4.计费重量：\",\n" +
                "\"TEXT#144,664,,,16,,,,3.weight\",\n" +
                "\"TEXT#64,688,,,16,,,,4.声明价值：\",\n" +
                "\"TEXT#64,712,,,16,,,,4.代收金额：\",\n" +
                "\"TEXT#504,736,,,24,,,,4.已验视\",\n" +
                "\"LINE#0,904,576,904,2\",\n" +
                "\"BARCODE#0,908,288,32,3.kuaidinum\",\n" +
                "\"TEXT#0,944,288,,24,1,,,3.kuaidinumSpace\",\n" +
                "\"TEXT#296,912,,,24,,,,4.订单号：\",\n" +
                "\"LINE#0,976,576,976,2\",\n" +
                "\"TEXT#8,984,,,16,,,,4.收件方信息：\",\n" +
                "\"TEXT#8,1008,,,16,,,,1.recname|4. |3.recPhone\",\n" +
                "\"TEXT#8,1032,272,48,16,,,,1.recaddr|4. |1.recaddrdet\",\n" +
                "\"TEXT#296,984,,,16,,,,4.寄件方信息：\",\n" +
                "\"TEXT#296,1008,,,16,,,,1.sendname|4. |3.sendPhone\",\n" +
                "\"TEXT#296,1032,272,48,16,,,,1.sendaddr|4. |1.sendaddrdet\",\n" +
                "\"LINE#0,1088,576,1088,2\",\n" +
                "\"TEXT#83,1096,,,16,,,,4.内容品名\",\n" +
                "\"TEXT#0,1136,230,,16,1,,,3.cargo\",\n" +
                "\"LINE#0,1120,576,1120,2\",\n" +
                "\"LINE#288,904,288,1088,2\",\n" +
                "\"LINE#230,1088,230,1168,2\",\n" +
                "\"LINE#346,1088,346,1168,2\",\n" +
                "\"LINE#461,1088,461,1168,2\",\n" +
                "\"TEXT#234,1096,,,16,,,,4.计费重量（kg）\",\n" +
                "\"TEXT#230,1136,116,,16,1,,,3.weight\",\n" +
                "\"TEXT#349,1096,,,16,,,,4.声明价值（￥）\",\n" +
                "\"TEXT#464,1096,,,16,,,,4.代收金额（￥）\",\n" +
                "\"BARCODE#0,1184,288,48,3.kuaidinum\",\n" +
                "\"TEXT#0,1240,288,,24,1,,,3.kuaidinumSpace\",\n" +
                "\"LINE#0,1272,576,1272,2\",\n" +
                "\"TEXT#8,1280,,,16,,,,4.收件方信息：\",\n" +
                "\"TEXT#8,1304,,,16,,,,1.recname|4. |3.recPhone\",\n" +
                "\"TEXT#8,1328,272,48,16,,,,1.recaddr|4. |1.recaddrdet\",\n" +
                "\"TEXT#296,1280,,,16,,,,4.寄件方信息：\",\n" +
                "\"TEXT#296,1304,,,16,,,,1.sendname|4. |3.sendPhone\",\n" +
                "\"TEXT#296,1328,272,48,16,,,,1.sendaddr|4. |1.sendaddrdet\",\n" +
                "\"LINE#0,1384,576,1384,2\",\n" +
                "\"TEXT#26,1392,,,16,,,,4.内容品名\",\n" +
                "\"TEXT#0,1444,115,,16,1,,,3.cargo\",\n" +
                "\"LINE#0,1416,576,1416,2\",\n" +
                "\"LINE#0,1488,576,1488,2\",\n" +
                "\"LINE#288,1168,288,1384,2\",\n" +
                "\"LINE#288,1488,288,1584,2\",\n" +
                "\"LINE#115,1384,115,1488,2\",\n" +
                "\"LINE#230,1384,230,1488,2\",\n" +
                "\"LINE#346,1384,346,1488,2\",\n" +
                "\"LINE#461,1384,461,1488,2\",\n" +
                "\"TEXT#117,1392,,,16,,,,4.计费重量（kg）\",\n" +
                "\"TEXT#115,1444,115,,16,1,,,3.weight\",\n" +
                "\"TEXT#234,1392,,,16,,,,4.声明价值（￥）\",\n" +
                "\"TEXT#349,1392,,,16,,,,4.代收金额（￥）\",\n" +
                "\"TEXT#464,1392,,,16,,,,4.现付运费（￥）\",\n" +
                "\"TEXT#8,1496,,,16,,,,4.打印时间\",\n" +
                "\"TEXT#0,1536,288,,24,1,,,3.date\",\n" +
                "\"TEXT#296,1496,,,16,,,,4.快递员签名/签名时间\"\n" +
                "]";
        String lodopTemplate = new BlueToothTranslater().translateToLODOP(source);
        if(lodopTemplate==null){
            ToggleLog.d("btThing","return null");
            return;
        }
        //全部写入
        BufferedWriter bw = null;
        try {
            String writePath = Environment.getExternalStorageDirectory() + "/template";
            File file = new File(writePath, "LODOPTemplateTrans.txt");
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(lodopTemplate);
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(bw!=null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void lodopThing() {
        String source = "LODOP.ADD_PRINT_IMAGE(447,80,260,115,\"<br><img src=\\\"{水印图片}\\\"/>\");?LODOP.ADD_PRINT_TEXT(209,35,345,20,\"{寄件人姓名}  {寄件人联系电话}  {寄件人联系电话2}\");?LODOP.SET_PRINT_STYLEA(0,\"FontName\",\"黑体\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",8);?LODOP.ADD_PRINT_TEXT(223,35,345,25,\"{物品名称}\");?LODOP.SET_PRINT_STYLEA(0,\"FontName\",\"黑体\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",8);?LODOP.SET_PRINT_STYLEA(0,\"LineSpacing\",-4);?LODOP.ADD_PRINT_TEXT(55,12,364,56,\"{大头笔码}\");?LODOP.SET_PRINT_STYLEA(0,\"FontName\",\"黑体\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",34);?LODOP.SET_PRINT_STYLEA(0,\"Alignment\",2);?LODOP.SET_PRINT_STYLEA(0,\"Bold\",1);?LODOP.ADD_PRINT_TEXT(495,35,274,20,\"{寄件人姓名}  {寄件人联系电话}   {寄件人联系电话2}\\r\\n\");?LODOP.SET_PRINT_STYLEA(0,\"FontName\",\"黑体\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",7);?LODOP.ADD_PRINT_TEXT(507,35,274,24,\"{物品名称}\");?LODOP.SET_PRINT_STYLEA(0,\"FontName\",\"黑体\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",7.5);?LODOP.SET_PRINT_STYLEA(0,\"LineSpacing\",-4);?LODOP.ADD_PRINT_TEXT(456,35,274,20,\"{收件人姓名} {收件人联系电话} {收件人联系电话2}\");?LODOP.SET_PRINT_STYLEA(0,\"FontName\",\"黑体\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",7.5);?LODOP.ADD_PRINT_TEXT(469,35,274,24,\"{收件地址}\");?LODOP.SET_PRINT_STYLEA(0,\"FontName\",\"黑体\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",7.5);?LODOP.SET_PRINT_STYLEA(0,\"LineSpacing\",-4);?LODOP.ADD_PRINT_BARCODE(259,39,325,70,\"128A\",\"{快递单号条码}\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",12);?LODOP.ADD_PRINT_TEXT(670,307,69,15,\"-手机尾号-\");?LODOP.SET_PRINT_STYLEA(0,\"FontName\",\"黑体\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",7);?LODOP.SET_PRINT_STYLEA(0,\"Alignment\",2);?LODOP.ADD_PRINT_TEXT(642,304,74,30,\"{收件人电话尾号}\");?LODOP.SET_PRINT_STYLEA(0,\"FontName\",\"黑体\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",24);?LODOP.SET_PRINT_STYLEA(0,\"Alignment\",2);?LODOP.ADD_PRINT_TEXT(153,35,345,24,\"{收件人姓名} {收件人联系电话} {收件人联系电话2}\");?LODOP.SET_PRINT_STYLEA(0,\"FontName\",\"黑体\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",13);?LODOP.SET_PRINT_STYLEA(0,\"Bold\",1);?LODOP.ADD_PRINT_TEXT(172,35,345,34,\"{收件地址}\");?LODOP.SET_PRINT_STYLEA(0,\"FontName\",\"黑体\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",13);?LODOP.SET_PRINT_STYLEA(0,\"Bold\",1);?LODOP.SET_PRINT_STYLEA(0,\"LineSpacing\",-4);?LODOP.ADD_PRINT_LINE(416,76,339,77,0,1);?LODOP.ADD_PRINT_TEXT(341,6,82,15,\"{寄件日期}\");?LODOP.SET_PRINT_STYLEA(0,\"FontName\",\"黑体\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",8);?LODOP.ADD_PRINT_LINE(528,302,456,303,0,1);?LODOP.ADD_PRINT_TEXT(533,5,375,115,\"{数量}件\\r\\n{收件人公司名称}\\r\\n{备注}\\r\\n{自定义内容}\\r\\n{邮票编号}\");?LODOP.SET_PRINT_STYLEA(0,\"FontName\",\"黑体\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",10);?LODOP.SET_PRINT_STYLEA(0,\"LineSpacing\",-4);?LODOP.ADD_PRINT_TEXT(342,80,220,53,\"快件送达收件地址，经收件人或收件人（寄件人）允许的代收人签字，视为送达。您的签字代表您已验收此包裹，并已确认商品信息无误、包装完好、没有划痕、破损等表面质量问题。\");?LODOP.SET_PRINT_STYLEA(0,\"FontName\",\"黑体\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",7);?LODOP.SET_PRINT_STYLEA(0,\"LineSpacing\",-2);?LODOP.ADD_PRINT_BARCODE(456,307,80,80,\"QRCode\",\"{快递单号条码}\");?LODOP.ADD_PRINT_LINE(113,0,114,375,2,1);?LODOP.ADD_PRINT_LINE(55,0,56,375,2,1);?LODOP.ADD_PRINT_LINE(151,0,152,375,2,1);?LODOP.ADD_PRINT_LINE(246,0,247,375,2,1);?LODOP.ADD_PRINT_LINE(339,1,340,376,2,1);?LODOP.ADD_PRINT_LINE(530,0,531,375,2,1);?LODOP.ADD_PRINT_LINE(492,1,493,298,0,1);?LODOP.ADD_PRINT_LINE(206,0,207,375,2,1);?LODOP.ADD_PRINT_TEXT(404,11,50,17,\"打印时间\");?LODOP.SET_PRINT_STYLEA(0,\"FontName\",\"黑体\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",7);?LODOP.ADD_PRINT_LINE(454,0,454,377,0,1);?LODOP.ADD_PRINT_TEXT(404,238,47,20,\"签收栏\");?LODOP.SET_PRINT_STYLEA(0,\"FontName\",\"黑体\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",8);?LODOP.ADD_PRINT_TEXT(661,6,58,22,\"已验视\");?LODOP.SET_PRINT_STYLEA(0,\"FontName\",\"黑体\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",11);?LODOP.ADD_PRINT_TEXT(114,35,255,37,\"{集包地名称}\");?LODOP.SET_PRINT_STYLEA(0,\"FontName\",\"黑体\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",24);?LODOP.SET_PRINT_STYLEA(0,\"Bold\",1);?LODOP.ADD_PRINT_LINE(453,0,454,375,2,1);?LODOP.ADD_PRINT_BARCODE(420,168,198,32,\"128A\",\"{快递单号条码}\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",8);?LODOP.ADD_PRINT_IMAGE(400,281,15,15,\"<img src=\\\"http://cdn.kuaidi100.com/images/user/history/ent/print/ico_print_1.png?222\\\"/>\");?LODOP.SET_PRINT_STYLEA(0,\"Stretch\",2);?LODOP.ADD_PRINT_IMAGE(401,59,15,15,\"<img src=\\\"http://cdn.kuaidi100.com/images/user/history/ent/print/ico_print_2.png?222\\\"/>\");?LODOP.SET_PRINT_STYLEA(0,\"Stretch\",2);?LODOP.ADD_PRINT_IMAGE(214,3,25,25,\"<img src=\\\"http://cdn.kuaidi100.com/images/user/history/ent/print/ico_print_5.png\\\"/>\");?LODOP.SET_PRINT_STYLEA(0,\"Stretch\",2);?LODOP.ADD_PRINT_IMAGE(396,359,20,20,\"<img src=\\\"http://cdn.kuaidi100.com/images/user/history/ent/print/ico_print_6.png?222\\\"/>\");?LODOP.SET_PRINT_STYLEA(0,\"Stretch\",2);?LODOP.ADD_PRINT_IMAGE(456,0,35,35,\"<img src=\\\"http://cdn.kuaidi100.com/images/user/history/ent/print/ico_print_4.png\\\"/>\\r\\n\");?LODOP.SET_PRINT_STYLEA(0,\"Stretch\",2);?LODOP.ADD_PRINT_IMAGE(500,3,25,25,\"<img src=\\\"http://cdn.kuaidi100.com/images/user/history/ent/print/ico_print_5.png\\\"/>\\r\\n\");?LODOP.SET_PRINT_STYLEA(0,\"Stretch\",2);?LODOP.ADD_PRINT_IMAGE(115,2,35,35,\"<img src=\\\"http://cdn.kuaidi100.com/images/user/history/ent/print/ico_print_3.png\\\"/>\");?LODOP.SET_PRINT_STYLEA(0,\"Stretch\",2);?LODOP.ADD_PRINT_IMAGE(161,0,35,35,\"<img src=\\\"http://cdn.kuaidi100.com/images/user/history/ent/print/ico_print_4.png\\\"/>\");?LODOP.SET_PRINT_STYLEA(0,\"Stretch\",2);?LODOP.ADD_PRINT_TEXT(5,323,53,50,\"标准\\r\\n快递\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",14);?LODOP.SET_PRINT_STYLEA(0,\"Alignment\",2);?LODOP.SET_PRINT_STYLEA(0,\"Bold\",1);?LODOP.ADD_PRINT_LINE(55,321,0,322,0,1);?LODOP.ADD_PRINT_BARCODE(117,276,108,33,\"128A\",\"{集包编码}\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",7);?LODOP.SET_PRINT_STYLEA(0,\"ShowBarText\",0);?LODOP.ADD_PRINT_TEXT(357,5,76,20,\"{打印时间}\");?LODOP.SET_PRINT_STYLEA(0,\"FontName\",\"黑体\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",11);?LODOP.ADD_PRINT_LINE(414,298,339,299,0,1);?LODOP.ADD_PRINT_BARCODE(339,303,80,80,\"QRCode\",\"{快递单号条码}\");?LODOP.ADD_PRINT_RECT(659,5,49,18,0,1);?LODOP.ADD_PRINT_TEXT(663,63,215,20,\"{取件员} {电子面单广告1}\");?LODOP.SET_PRINT_STYLEA(0,\"FontName\",\"黑体\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",10);?LODOP.ADD_PRINT_IMAGE(10,0,115,36,\"{快递公司logo}\");?LODOP.SET_PRINT_STYLEA(0,\"Stretch\",2);?LODOP.ADD_PRINT_IMAGE(422,0,112,27,\"{快递公司logo}\\r\\n\");?LODOP.SET_PRINT_STYLEA(0,\"Stretch\",2);?LODOP.ADD_PRINT_TEXT(376,5,76,20,\"{打印编号}\");?LODOP.SET_PRINT_STYLEA(0,\"FontName\",\"黑体\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",11);?LODOP.SET_PRINT_STYLEA(0,\"Alignment\",2);?LODOP.ADD_PRINT_TEXT(648,63,215,18,\"重量:{重量}kg 快递费用:{快递费小写}元\");?LODOP.SET_PRINT_STYLEA(0,\"FontName\",\"黑体\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",8);?LODOP.ADD_PRINT_IMAGE(0,321,300,57,\"<br><img src=\\\"http://cdn.kuaidi100.com/images/user/history/ent/print/img_dshk.png\\\"/>\");?LODOP.SET_PRINT_STYLEA(0,\"Stretch\",2);?LODOP.ADD_PRINT_TEXT(1,164,161,53,\"代收货款:{代收货款小写}元\\r\\n到付:{到付金额}元\\r\\n保价费用:{保价费用}元\");?LODOP.SET_PRINT_STYLEA(0,\"FontName\",\"黑体\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",12);?LODOP.SET_PRINT_STYLEA(0,\"LineSpacing\",-5);?LODOP.ADD_PRINT_TEXT(5,324,53,50,\"{省内件}\");?LODOP.SET_PRINT_STYLEA(0,\"FontSize\",14);?LODOP.SET_PRINT_STYLEA(0,\"Alignment\",2);?LODOP.SET_PRINT_STYLEA(0,\"Bold\",1);?";
        String blueToothTemplate = new LODOPTranslater().translate(source,getTestReplaceMap(),"","",true);
        if(blueToothTemplate==null){
            ToggleLog.d("lodopThing","return null");
            return;
        }
        //全部写入
        BufferedWriter bw = null;
        try {
            String writePath = Environment.getExternalStorageDirectory() + "/template";
            File file = new File(writePath, "write.txt");
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(blueToothTemplate);
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(bw!=null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private HashMap<String, String> getTestReplaceMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("logo","");
        return map;
    }

    public void initViewAndListener() {
        checkAndSetOnClickListener(findViewById(R.id.tv_new), this);
        checkAndSetOnClickListener(findViewById(R.id.big), this);
        checkAndSetOnClickListener(findViewById(R.id.tv_zrc), this);
        checkAndSetOnClickListener(findViewById(R.id.tv_md), this);
        checkAndSetOnClickListener(findViewById(R.id.tv_smlv), this);
        checkAndSetOnClickListener(findViewById(R.id.tv_th), this);
        checkAndSetOnClickListener(findViewById(R.id.tv_skb), this);
        checkAndSetOnClickListener(findViewById(R.id.tv_dialog), this);
        checkAndSetOnClickListener(findViewById(R.id.tv_wave), this);
        checkAndSetOnClickListener(findViewById(R.id.recycleview), this);
        checkAndSetOnClickListener(findViewById(R.id.threeListView), this);
        checkAndSetOnClickListener(findViewById(R.id.sandglassview), this);
        checkAndSetOnClickListener(findViewById(R.id.sensor), this);
        checkAndSetOnClickListener(findViewById(R.id.speak), this);
        checkAndSetOnClickListener(findViewById(R.id.edit), this);
        checkAndSetOnClickListener(findViewById(R.id.message), this);
        checkAndSetOnClickListener(findViewById(R.id.save), this);
        checkAndSetOnClickListener(findViewById(R.id.reflect), this);
        checkAndSetOnClickListener(findViewById(R.id.fragment), this);
        checkAndSetOnClickListener(findViewById(R.id.barcode), this);
        checkAndSetOnClickListener(findViewById(R.id.scroll), this);
        checkAndSetOnClickListener(findViewById(R.id.multi_point), this);
        checkAndSetOnClickListener(findViewById(R.id.game), this);
        checkAndSetOnClickListener(findViewById(R.id.surface), this);
        checkAndSetOnClickListener(findViewById(R.id.picture), this);
        checkAndSetOnClickListener(findViewById(R.id.cube), this);
        checkAndSetOnClickListener(findViewById(R.id.howmuch), this);
        checkAndSetOnClickListener(findViewById(R.id.ball), this);
        checkAndSetOnClickListener(findViewById(R.id.what), this);
        checkAndSetOnClickListener(findViewById(R.id.matrix), this);
        checkAndSetOnClickListener(findViewById(R.id.myview), this);
        checkAndSetOnClickListener(findViewById(R.id.dragon), this);
        checkAndSetOnClickListener(findViewById(R.id.openGL), this);
        checkAndSetOnClickListener(findViewById(R.id.touchEvent), this);
        checkAndSetOnClickListener(findViewById(R.id.fireWork), this);
        checkAndSetOnClickListener(findViewById(R.id.fireWork2), this);
        checkAndSetOnClickListener(findViewById(R.id.reflectBall), this);
    }

    private void checkAndSetOnClickListener(View view, View.OnClickListener listener) {
        view.setOnClickListener(listener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_new:
                Intent newIntent = new Intent(this, NewActivity.class);
                startActivity(newIntent);
                break;
            case R.id.tv_zrc:
//                Toast.makeText(this,"ZrcListView",Toast.LENGTH_SHORT).show();
                Intent zrcIntent = new Intent(this, ZrcActivity.class);
                startActivity(zrcIntent);
                break;
            case R.id.big:
//                Toast.makeText(this,"MaterialDialog",Toast.LENGTH_SHORT).show();
                Intent bigIntent = new Intent(this, BigActivity.class);
                startActivity(bigIntent);
                break;
            case R.id.tv_md:
//                Toast.makeText(this,"MaterialDialog",Toast.LENGTH_SHORT).show();
                Intent mdIntent = new Intent(this, MaterialDialogActivity.class);
                startActivity(mdIntent);
                break;
            case R.id.tv_smlv:
                Intent smlvIntent = new Intent(this, SwipeMenuListViewActivity.class);
                startActivity(smlvIntent);
                break;
            case R.id.tv_th:
                Intent thIntent = new Intent(this, TabHostActivity.class);
                startActivity(thIntent);
                break;
            case R.id.tv_skb:
                Intent skbIntent = new Intent(this, SoftKeyBoardActivity.class);
                startActivity(skbIntent);
                break;
            case R.id.tv_dialog:
                Intent dialogIntent = new Intent(this, DialogActivity.class);
                startActivity(dialogIntent);
                break;
            case R.id.tv_wave:
                Intent waveIntent = new Intent(this, WaveActivity.class);
                startActivity(waveIntent);
                break;
            case R.id.recycleview:
                Intent recycleViewIntent = new Intent(this, RecycleViewActivity.class);
                startActivity(recycleViewIntent);
                break;
            case R.id.threeListView:
                Intent threeListViewIntent = new Intent(this, ThreeListViewActivity.class);
                startActivity(threeListViewIntent);
                break;
            case R.id.sandglassview:
                Intent SandGlassViewIntent = new Intent(this, SandGlassViewActivity.class);
                startActivity(SandGlassViewIntent);
                break;
            case R.id.sensor:
                Intent SensorIntent = new Intent(this, SensorActivity.class);
                startActivity(SensorIntent);
                break;
            case R.id.speak:
                Intent SpeakIntent = new Intent(this, SpeakActivity.class);
                startActivity(SpeakIntent);
                break;
            case R.id.edit:
                Intent EditIntent = new Intent(this, EditActivity.class);
                startActivity(EditIntent);
                break;
            case R.id.message:
                Intent MessageIntent = new Intent(this, MessageActivity.class);
                startActivity(MessageIntent);
                break;
            case R.id.save:
                Intent SaveIntent = new Intent(this, SaveActivity.class);
                startActivity(SaveIntent);
                break;
            case R.id.reflect:
                Intent reflectActivity = new Intent(this, ReflectActivity.class);
                startActivity(reflectActivity);
                break;
            case R.id.fragment:
                Intent myFragmentActivity = new Intent(this, MyFragmentActivity.class);
                startActivity(myFragmentActivity);
                break;
            case R.id.barcode:
                Intent barcodeActivity = new Intent(this, BarcodeActivity.class);
                startActivity(barcodeActivity);
                break;
            case R.id.scroll:
                Intent scrollActivity = new Intent(this, ScrollActivity.class);
                startActivity(scrollActivity);
                break;
            case R.id.multi_point:
                Intent multiPoiontActivity = new Intent(this, MultiPoiontActivity.class);
                startActivity(multiPoiontActivity);
                break;
            case R.id.game:
                Intent gameActivity = new Intent(this, GameActivity.class);
                startActivity(gameActivity);
                break;
            case R.id.surface:
                Intent surfaceActivity = new Intent(this, SurfaceActivity.class);
                startActivity(surfaceActivity);
                break;
            case R.id.picture:
                Intent pictureActivity = new Intent(this, PictureActivity.class);
                startActivity(pictureActivity);
                break;
            case R.id.cube:
                Intent cubeActivity = new Intent(this, CubeActivity.class);
                startActivity(cubeActivity);
                break;
            case R.id.howmuch:
                Intent howMuchActivity = new Intent(this, HowMuchActivity.class);
                startActivity(howMuchActivity);
                break;
            case R.id.ball:
                Intent ballActivity = new Intent(this, BallActivity.class);
                startActivity(ballActivity);
                break;
            case R.id.what:
                Intent whatActivity = new Intent(this, WhatActivity.class);
                startActivity(whatActivity);
                break;
            case R.id.matrix:
                Intent matrixIntent = new Intent(this, MatrixActivity.class);
                startActivity(matrixIntent);
                break;
            case R.id.myview:
                Intent myViewIntent = new Intent(this, MyViewActivity.class);
                startActivity(myViewIntent);
                break;
            case R.id.dragon:
                Intent dragonIntent = new Intent(this, DragonActivity.class);
                startActivity(dragonIntent);
                break;
            case R.id.openGL:
                Intent openGLIntent = new Intent(this, OpenGLActivity.class);
                startActivity(openGLIntent);
                break;
            case R.id.touchEvent:
                Intent touchEventIntent = new Intent(this, TouchEventActivity.class);
                startActivity(touchEventIntent);
                break;
            case R.id.fireWork:
                Intent fireWorkIntent = new Intent(this, FireWorkActivity.class);
                startActivity(fireWorkIntent);
                break;
            case R.id.fireWork2:
                Intent fireWorkIntent2 = new Intent(this, FireWorkActivity2.class);
                startActivity(fireWorkIntent2);
                break;
            case R.id.reflectBall:
                Intent reflectBallIntent = new Intent(this, ReflectBallActivity.class);
                startActivity(reflectBallIntent);
                break;
        }
    }
}
