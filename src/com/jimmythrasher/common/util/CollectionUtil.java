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

    /**
     * Create a Hashtable from a key, value, key, value... style
     * object array.
     */
    public static Hashtable createHashtable(Object[] ary) {
        return createHashtable(createVector(ary));
    }

    /**
     * Create a Hashtable from a key, value, key, value... style
     * Vector.
     */
    public static Hashtable createHashtable(Vector v) {
        return createHashtable(v.elements());
    }

    /**
     * Create a Hashtable from a key, value, key, value... style
     * Enumeration.
     */
    public static Hashtable createHashtable(Enumeration e) {
        Hashtable result = new Hashtable();
        while (e.hasMoreElements())
            result.put(e.nextElement(), e.nextElement());
        return result;
    }

    /**
     * Create a Hashtable by pairing the given keys with the given values.
     * Equivalent to python code <code>dict(zip(keys, values))</code>
     */
    public static Hashtable createHashtable(Vector keys, Vector values) {
        Enumeration keyEnum = keys.elements();
        Enumeration valEnum = values.elements();

        Hashtable result = new Hashtable();
        while (keyEnum.hasMoreElements() && valEnum.hasMoreElements())
            result.put(keyEnum.nextElement(), valEnum.nextElement());

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

    public static interface ObjectBlock {
        public Object op(Object element);
    }

    public static Vector collect(ObjectBlock b, Vector v) {
        Vector result = new Vector();
        Enumeration e = v.elements();
        while (e.hasMoreElements())
            result.addElement(b.op(e.nextElement()));
        return result;
    }
}
