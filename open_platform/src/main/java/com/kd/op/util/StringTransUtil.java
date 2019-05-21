package com.kd.op.util;



public class StringTransUtil {
    /**
     * 处理页面传过来的特殊字符问题
     * @param str 传入的字符串
     * @return 处理后的字符串
     */
    public static String stringReplace(String str) {
        if (str.contains("_")) {
            str=str.replace("_", "\\_");
        }
        if (str.contains("%")) {
            str=str.replace("%", "\\%");
        }
        if (str.contains(">")) {
            str=str.replace(">", "\\>");
        }
        if (str.contains("<")) {
            str=str.replace("<", "\\<");
        }
        if (str.contains("'")) {
            str=str.replace("'", "\\'");
        }
        if (str.contains("\"")) {
            str=str.replace("\"", "\\\"");
        }
        if (str.contains("(")) {
            str=str.replace("(", "\\(");
        }
        if (str.contains(")")) {
            str=str.replace(")", "\\)");
        }
        if (str.contains("&")) {
            str=str.replace("&", "\\&");
        }
        if (str.contains("@")) {
            str=str.replace("@", "\\@");
        }
        if (str.contains("#")) {
            str=str.replace("#", "\\#");
        }
        if (str.contains("$")) {
            str=str.replace("$", "\\$");
        }
        if (str.contains("^")) {
            str=str.replace("^", "\\^");
        }
        if (str.contains(",")) {
            str=str.replace(",", "\\,");
        }
        if (str.contains(".")) {
            str=str.replace(".", "\\.");
        }
        if (str.contains("/")) {
            str=str.replace("/", "\\/");
        }
        if (str.contains("[")) {
            str=str.replace("[", "\\[");
        }
        if (str.contains("]")) {
            str=str.replace("]", "\\]");
        }
        if (str.contains("{")) {
            str=str.replace("{", "\\{");
        }
        if (str.contains("}")) {
            str=str.replace("}", "\\}");
        }
        if (str.contains("*")) {
            str=str.replace("*", "\\*");
        }
        if (str.contains("?")) {
            str=str.replace("?", "\\?");
        }
        if (str.contains("=")) {
            str=str.replace("=", "\\=");
        }
        if (str.contains("+")) {
            str=str.replace("+", "\\+");
        }
        if (str.contains("!")) {
            str=str.replace("!", "\\!");
        }
        return str;
    }
}
