package com.jimmythrasher.common.util;

import java.util.*;

public class CollectionUtil {
    public static Vector createVector(Object[] array) {
        return appendInto(new Vector(), array);
    }

    public static Vector createVector(Enumeration e) {
        Vector result = new Vector();
        while (e.hasMoreElements())
            result.addElement(e.nextElement());
        return result;
    }

    public static Vector tail(Vector v) {
        Vector result = new Vector();
        Enumeration e = v.elements();
        e.nextElement();
        while (e.hasMoreElements())
            result.addElement(e.nextElement());

        return result;
    }

    public static Vector appendInto(Vector v, Object[] array) {
        appendInto(v, array, array.length);
        return v;
    }

    public static Vector appendInto(Vector v, Object[] array, int quantity) {
        v.ensureCapacity(v.size() + quantity);
        for (int i = 0; i < quantity; i++) {
            v.addElement(array[i]);
        }

        return v;
    }

    public static Vector appendInto(Vector dest, Vector src) {
        Enumeration e = src.elements();
        while (e.hasMoreElements())
            dest.addElement(e.nextElement());
        return dest;
    }
}
