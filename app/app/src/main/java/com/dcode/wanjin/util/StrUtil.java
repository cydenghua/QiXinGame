package com.dcode.wanjin.util;

import android.text.StaticLayout;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StrUtil {

    public static String integerToStr(Integer num) {
        if(null == num) {
            return "";
        }
        return num.toString();
    }

    public static String shortToStr(Short num) {
        if(null == num) {
            return "";
        }
        return num.toString();
    }

    public static String intFenToYuan(Integer num) {
        return numTrim(num == null ? "0" : "" + num/100.0);
    }

    public static String intLiToYuan(Integer num) {
        return numTrim(num == null ? "0" : "" + num/1000.0);
    }

    public static String numTrim(String sNum) {
        //delete 0
        for (int i = 0; i < 5; i++) {
            if(sNum.endsWith("0")) {
                sNum = sNum.substring(0, sNum.length()-1);
            }
        }
        //delete .
        if(sNum.endsWith(".")) {
            sNum = sNum.substring(0, sNum.length()-1);
        }
        return sNum;
    }

    public static String decimalToStr(BigDecimal decimal) {
        return numTrim(decimal.toString());
    }

    public static String dateToStr(Date dt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(dt);
    }

    public static Date strToDate(String dt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static Date strToDateTime(String dt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static String strDateStr(String dt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("d日HH:mm:ss");
        try {
            return dateFormat2.format(dateFormat.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static String getPlayType(String sInput) {
//        口X口X
        //长度不够4位，则是N字现
        if(sInput.length() < 4) {
            if(sInput.length() == 3) {   //三位则为三字现
                return QXConstant.CLASS_3PX;
            }
            if(sInput.length() == 2) {   //二位则为二字现
                return QXConstant.CLASS_2PX;
            }
        }else {
            sInput = sInput.replaceAll("\\d", "口");

            //二定位
            if(QXConstant.PLAY_TYPE_NAME_2P1.equals(sInput)) {
                return QXConstant.CLASS_TYPE_2P1;
            }
            if(QXConstant.PLAY_TYPE_NAME_2P2.equals(sInput)) {
                return QXConstant.CLASS_TYPE_2P2;
            }
            if(QXConstant.PLAY_TYPE_NAME_2P3.equals(sInput)) {
                return QXConstant.CLASS_TYPE_2P3;
            }
            if(QXConstant.PLAY_TYPE_NAME_2P4.equals(sInput)) {
                return QXConstant.CLASS_TYPE_2P4;
            }
            if(QXConstant.PLAY_TYPE_NAME_2P5.equals(sInput)) {
                return QXConstant.CLASS_TYPE_2P5;
            }
            if(QXConstant.PLAY_TYPE_NAME_2P6.equals(sInput)) {
                return QXConstant.CLASS_TYPE_2P6;
            }

            //三定位
            if(QXConstant.PLAY_TYPE_NAME_3P1.equals(sInput)) {
                return QXConstant.CLASS_TYPE_3P1;
            }
            if(QXConstant.PLAY_TYPE_NAME_3P2.equals(sInput)) {
                return QXConstant.CLASS_TYPE_3P2;
            }
            if(QXConstant.PLAY_TYPE_NAME_3P3.equals(sInput)) {
                return QXConstant.CLASS_TYPE_3P3;
            }
            if(QXConstant.PLAY_TYPE_NAME_3P4.equals(sInput)) {
                return QXConstant.CLASS_TYPE_3P4;
            }

            //四定位
            if(QXConstant.PLAY_TYPE_NAME_4P1.equals(sInput)) {
                return QXConstant.CLASS_TYPE_4P1;
            }
        }

        return "";
    }

    public static String getPlayClass(String sType) {
        if(QXConstant.CLASS_TYPE_2P1.equals(sType) ||
                QXConstant.CLASS_TYPE_2P2.equals(sType) ||
                QXConstant.CLASS_TYPE_2P3.equals(sType) ||
                QXConstant.CLASS_TYPE_2P4.equals(sType) ||
                QXConstant.CLASS_TYPE_2P5.equals(sType) ||
                QXConstant.CLASS_TYPE_2P6.equals(sType) ) {
            return QXConstant.CLASS_2P;
        }

        if(QXConstant.CLASS_TYPE_3P1.equals(sType) ||
                QXConstant.CLASS_TYPE_3P2.equals(sType) ||
                QXConstant.CLASS_TYPE_3P3.equals(sType) ||
                QXConstant.CLASS_TYPE_3P4.equals(sType) ) {
            return QXConstant.CLASS_3P;
        }

        if(QXConstant.CLASS_TYPE_4P1.equals(sType)) {
            return QXConstant.CLASS_4P;
        }

        if(QXConstant.CLASS_TYPE_2PX.equals(sType)) {
            return QXConstant.CLASS_2PX;
        }

        if(QXConstant.CLASS_TYPE_3PX.equals(sType)) {
            return QXConstant.CLASS_3PX;
        }

        if(QXConstant.CLASS_TYPE_4PX.equals(sType)) {
            return QXConstant.CLASS_4PX;
        }

        return "";
    }


}
