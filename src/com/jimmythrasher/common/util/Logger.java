package com.jimmythrasher.common.util;

import java.util.*;

public class Logger {
    public static final int DEBUG = 0;
    public static final int ERROR = 1;
    public static final int INFO = 2;
    public static final int WARNING = 3;
    public static final int ALWAYS = 3;

    public interface Listener {
        public void onMessage(String message, int level);
    }

    public static void addListener(Listener l) {
        listeners.addElement(l);
    }

    public static void addCategory(String c) {
        categories.addElement(c);
    }

    public static void removeCategory(String c) {
        categories.removeElement(c);
    }

    public static void clearCategories() {
        categories.setSize(0);
    }

    public static void setCategories(Vector add) {
        categories = add;
    }

    public static Vector getCategories() {
        return categories;
    }

    public static void error(String message) {
        doMessage(message, ERROR);
    }

    public static void warning(String message) {
        doMessage(message, WARNING);
    }

    public static void info(String message) {
        doMessage(message, INFO);
    }

    public static void debug(String category, String message) {
        if (category == null || categories.contains(category))
            doMessage(message, DEBUG);
    }

    public static void always(String message) {
        doMessage(message, ALWAYS);
    }

    public static void whereami(String message) {
        try {
            throw new RuntimeException(message);
        }
        catch (Throwable t) { }
    }

    private static void doMessage(String message, int level) {
        Enumeration e = listeners.elements();
        while (e.hasMoreElements())
            ((Listener)e.nextElement()).onMessage(message, level);
    }

    static {
        listeners = new Vector();
        categories = new Vector();
    }

    private static Vector listeners;
    private static Vector categories;
}
