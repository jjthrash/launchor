package com.jimmythrasher.common.util;

import java.util.*;

public class StringUtil {
    /**
     * Calls indexOfAny(string, chars 0);
     * @see indexOfAny(String, char[], int)
     */
    public static int indexOfAny(String s, char[] chars) {
        return indexOfAny(s, chars, 0);
    }

    /**
     * Return the index of the first char of the given chars that exists in the string, starting from a given position.
     *
     * @param s The String to search in.
     * @param chars The chars to search for.
     * @param currentPosition The start position.
     */
    public static int indexOfAny(String s, char[] chars, int currentPosition) {
        int result = Integer.MAX_VALUE;

        for (int i = 0; i < chars.length; i++) {
            int index = s.indexOf(chars[i], currentPosition);
            if (index >= 0 && index < result)
                result = index;
        }

        if (result == Integer.MAX_VALUE)
            return -1;

        return result;
    }

    public static int indexOf(String s, char c, int startIndex, int count) {
        int index = s.indexOf(c, startIndex);
        if (index >= startIndex + count)
            return -1;

        return index;
    }

    public static String trimQuotes(String s) {
        if (s.startsWith("\""))
            s = s.substring(1);
        if (s.endsWith("\""))
            s = s.substring(0, s.length() - 1);

        return s;
    }

    public static final CollectionUtil.ObjectBlock TRIM = new CollectionUtil.ObjectBlock() {
        public Object op(Object element) {
            if (element instanceof String)
                return ((String)element).trim();
            else
                return element;
        }
    };

    public static String getHost(String uri) {
        int first = uri.indexOf("//") + 2;
        int last = uri.indexOf("/", first);
        return uri.substring(first, last);
    }

    public static String escapeXML(String s) {
        s = replaceAllInstancesOf(s, "&", "&amp;");
        s = replaceAllInstancesOf(s, "'", "&apos;");
        s = replaceAllInstancesOf(s, "\"", "&quot;");
        return s;
    }

    /**
     * Replace one instance of a string with another string.
     * 
     * @param bigString
     * @param toReplace
     * @param with
     * @return String
     */
    public static String replace(String bigString, String toReplace, String with) {
        return replace(bigString, toReplace, with, 0);
    }

    /**
     * Replace one instance of a string with another string, starting from a
     * specified index.
     * 
     * @param bigString
     * @param toReplace
     * @param with
     * @param startingIndex
     * @return String
     */
    public static String replace(String bigString, String toReplace, String with, int startingIndex) {
        return bigString.substring(0, bigString.indexOf(toReplace, startingIndex)) + with + bigString.substring(bigString.indexOf(toReplace, startingIndex) + toReplace.length());
    }

    /**
     * Replace all instances of a string with another string.
     * 
     * @param bigString
     * @param toReplace
     * @param with
     * @return String
     */
    public static String replaceAllInstancesOf(String bigString, String toReplace, String with) {
        return join(split(bigString, toReplace), with);
    }

    /**
     * Given a string filled with strings like %%BLAH%%, replace all instances
     * of %%BLAH%% with values drawn from the given params hash.
     *
     * @param template The template to fill in
     * @param params   The hashtable containing the key/values to fill in with
     * @return the resulting string
     */
    public static String fillInTemplate(String template, Hashtable params) {
        String result = template;
        Enumeration keys = params.keys();
        while (keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            result = replace(result, "%%" + key.toUpperCase() + "%%", (String)params.get(key));
        }

        return result;
    }


    /**
     * Split a string on a delimiter into a List.
     * 
     * @param string
     * @param delimiter
     * @return List
     */
    public static Vector split(String string, String delimiter) {
        Vector result = new Vector();
        int index = string.indexOf(delimiter);
        String tempString = string;
        while (index != -1) {
            result.addElement(tempString.substring(0, index));
            tempString = tempString.substring(index+delimiter.length());
            index = tempString.indexOf(delimiter);
        }
        result.addElement(tempString);
        return result;
    }

    /**
     * Join a list of Object (via toString()) into a String using a delimiter.
     * 
     * @param list
     * @param delimiter
     * @return String
     */
    public static String join(Vector list, String delimiter) {
        StringBuffer result = new StringBuffer();
        int count = 0;
        Enumeration e = list.elements();
        while (e.hasMoreElements()) {
            Object element = e.nextElement();
            if (count > 0)
                result.append(delimiter);
            result.append(element.toString());
            count++;
        }
        return result.toString();
    }
}

