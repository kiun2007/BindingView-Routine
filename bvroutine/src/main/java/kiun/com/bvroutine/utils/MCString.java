package kiun.com.bvroutine.utils;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kiun_2007 on 2018/8/9.
 */

public class MCString {

    public static String FORMAT_DATE = "yyyy-mm-ss HH:mm:ss";

    public static List listByArray(JSONArray array, String fieldName) {

        List newList = new ArrayList();
        for (int i = 0; i < array.length(); i++) {
            try {
                newList.add(array.getJSONObject(i).opt(fieldName));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return newList;
    }

    public static Date dateByFormat(String value, String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] patternValues(String[] patternStrs, String rootStr) {

        String[] values = new String[patternStrs.length];
        for (int i = 0; i < patternStrs.length; i++) {
            Matcher matcher = Pattern.compile(patternStrs[i]).matcher(rootStr);
            values[i] = matcher.find() ? matcher.group(1) : null;
        }
        return values;
    }

    public static String[] patternValues(String pattern, String rootStr){

        Matcher matcher = Pattern.compile(pattern).matcher(rootStr);
        List<String> strings = new ArrayList<>();

        while (matcher.find()) {
            for (int i = 0; i < matcher.groupCount(); i++) {
                strings.add(matcher.group(i+1));
            }
        }
        String[] newStrings = new String[strings.size()];
        strings.toArray(newStrings);
        return newStrings;
    }

    public static int asInt(String str) {
        if (str.isEmpty()) {
            return 0;
        }
        int hex = str.startsWith("0x") ? 16 : 10;
        return Integer.parseInt(str.replace("0x", ""), hex);
    }

    public static String[] stringsSort(String[] inStrings) {

        ArrayList<String> newStrings = new ArrayList<String>();
        for (String itemStr : inStrings) {
            if (itemStr.equals("")) {
                continue;
            }
            boolean isWith = false;
            for (int i = 0; i < newStrings.size(); i++) {
                if (itemStr.equals(newStrings.get(i))) {
                    isWith = true;
                    break;
                }
            }
            if (!isWith) {
                newStrings.add(itemStr);
            }
        }
        String[] outString = new String[newStrings.size()];
        for (int i = 0; i < newStrings.size(); i++) {
            outString[i] = newStrings.get(i);
        }
        return outString;
    }

    public static String formatDate(String format, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
        return formatter.format(date);
    }

    public static String formatDate(String format, long time){
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
        return formatter.format(new Date(time));
    }

    public static String bankCardTail(String cardNo) {

        int length = cardNo.length();
        if(length > 19 || length < 16){
            return null;
        }
        int afterLength = (length == 19 ? 3 : 4);
//        return cardNo.substring(length - afterLength, length);
        return "("+cardNo.substring(length - afterLength, length)+")";
    }

    public static String maskPhone(String phone){
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    public static Number toNumber(String str) {

        if (str.isEmpty()) {
            return 0;
        }
        try{
            if (str.indexOf(".") > -1) {
                return Float.parseFloat(str);
            } else {
                return Integer.parseInt(str);
            }
        }catch (Exception ex){
        }
        return 0;
    }

    public static String arrayToString(List<String> array, String separate) {

        StringBuilder stringBuilder = new StringBuilder(2000);
        for (int i = 0; i < array.size(); i++) {
            stringBuilder.append(array.get(i));
            if (i < array.size() - 1) {
                stringBuilder.append(separate);
            }
        }
        return stringBuilder.toString();
    }

    public static String trim(String source, char trimValue) {
        int len = source.length();
        int st = 0;
        while ((st < len) && (source.charAt(len - 1) <= trimValue)) {
            len--;
        }
        return ((st > 0) || (len < source.length())) ? source.substring(st, len) : source;
    }

    public static String dateConvert(String value, String inFormat, String outFormat){
        SimpleDateFormat inDateFormat = new SimpleDateFormat(inFormat);
        SimpleDateFormat outDateFormat = new SimpleDateFormat(outFormat, Locale.CHINA);
        try {
            Date date = inDateFormat.parse(value);
            return outDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decimalFormat(float value, int num, String uint){

        String formatValue = String.format("%%.%df", num);
        String decimalStr = String.format(formatValue, value);
        String[] numbers = decimalStr.split("\\.");
        String intValue = numbers[0];
        String decimalValue = numbers[numbers.length - 1];
        decimalValue = trim(decimalValue, '0');

        if (TextUtils.isEmpty(decimalValue)){//
            return String.format("<font><big>%s</big></font>", intValue);
        }else{
            return String.format("<font><big>%s</big></font><font><small>.%s</small></font>", intValue, decimalValue);
        }
    }

    public static String toPayType(String payType){
        String[] payStr = {"微信", "支付宝", "优惠券"};
        if (TextUtils.isEmpty(payType)){
            return "";
        }
        int type = Integer.parseInt(payType);
        if (type < 3){
            return payStr[type];
        }
        return "";
    }

    public static String parmaFormat(JSONObject jsonObject, String parmaFormat){
        if(jsonObject == null || parmaFormat == null){
            return null;
        }

        Matcher matcher = Pattern.compile("\\[(.+?)\\]").matcher(parmaFormat);
        Set<String> allKeyName = new HashSet<>();
        while (matcher.find()){
            String key = matcher.group(1);
            allKeyName.add(key);
        }

        if(allKeyName.size() > 0){
            for (String key : allKeyName){
                Object value = jsonObject.opt(key);
                if (value != null){
                    parmaFormat = parmaFormat.replace("[" + key +"]", value.toString());
                }
            }
            return parmaFormat;
        }
        return jsonObject.optString(parmaFormat);
    }

    public static JSONObject toJSON(String jsonStr){
        if (!TextUtils.isEmpty(jsonStr)){
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                return jsonObject;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean isWith(String value, String[] array){
        if(array == null) return false;
        for (String item : array) {
            if (item.equals(value)) return true;
        }
        return false;
    }

    public static String randUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static String randNum(int numCount){
        StringBuffer stringBuffer = new StringBuffer(numCount+5);
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        for (int i = 0; i < numCount; i++) {
            stringBuffer.append(String.format("%d", (Math.abs(random.nextInt())%10)));
        }
        return stringBuffer.toString();
    }

    private static final char SEPARATOR = '_';

    public static String toUnderlineName(String s, boolean isUpperCase){
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i >= 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    if (i > 0) sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(isUpperCase ? Character.toUpperCase(c) : Character.toLowerCase(c));
        }

        return sb.toString();
    }

    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
