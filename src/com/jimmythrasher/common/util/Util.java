package com.jimmythrasher.common.util;

import java.io.*;
import java.util.*;

import com.jimmythrasher.common.util.Logger;

public class Util {
    public static String readToNewline(Reader reader) throws IOException {
        //log("readToNewline", "entry");
        StringBuffer writer = new StringBuffer();
        while (true) {
            int readValue = reader.read();
            if (readValue == -1)
                break;

            char c = (char)readValue;
            //log("readToNewline", "read a character: value " + (int)c);
            if (c == '\n' || c == '\r') //TODO handle CRLF properly
                break;

            writer.append(c);
        }
        return writer.toString();
    }

    public static Hashtable getRFC822Headers(String rfc822Message) {
        String methodName = "getRFC822Headers";
        log(methodName, "entry: " + rfc822Message);
        Hashtable result = new Hashtable();
        String headers = rfc822Message.substring(0, getRFC822BodyIndex(rfc822Message) - 4);
        log(methodName, "headers: " + headers);
        Enumeration headerLines = lines(headers).elements();
        while (headerLines.hasMoreElements()) {
            String line = (String)headerLines.nextElement();
            if (line.length() == 0)
                continue;
            log(methodName, "parsing line: " + line);
            String key = (String)line.substring(0, line.indexOf(": "));
            String value = line.substring(line.indexOf(": ") + 2);

            while (value.endsWith(";")) {
                log(methodName, "Found line continuation; appending.");
                value = value + headerLines.nextElement();
            }

            result.put(key.toLowerCase(), value);
        }

        return result;
    }

    public static Vector lines(String s) {
        Vector result = new Vector();
        int index = 0;
        boolean done = false;
        while (!done) {
            int nextNewline = s.indexOf("\r\n", index);
            if (nextNewline < 0) {
                result.addElement(s.substring(index));
                break;
            }
            else
                result.addElement(s.substring(index, nextNewline));
            index = nextNewline + 2;
        }

        return result;
    }

    private static int getRFC822BodyIndex(String rfc822Message) {
        return rfc822Message.indexOf("\r\n\r\n") + 4;
    }

    public static String getRFC822Body(String rfc822Message) {
        return rfc822Message.substring(getRFC822BodyIndex(rfc822Message));
    }

    public static String getRFC822AddressAddress(String address) {
        int lt = address.indexOf('<');
        if (lt == -1)
            return address;

        int gt = address.indexOf('>', lt);

        if (gt == -1)
            return address;

        return address.substring(lt + 1, gt);
    }

    public static String getRFC822AddressName(String address) {
        int lt = address.indexOf('<');
        if (lt == -1)
            return "";

        return trimQuotes(address.substring(0, lt).trim());
    }

    public static String trimQuotes(String s) {
        return StringUtil.trimQuotes(s);
    }

    public static void inputStreamToOutputStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[4096];
        boolean done = false;
        int amountRead = -1;
        while (!done) {
            amountRead = in.read(buffer);
            if (amountRead <= 0)
                done = true;
            else
                out.write(buffer, 0, amountRead);
        }
        out.flush();
    }

    public static byte[] slurp(InputStream s) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        inputStreamToOutputStream(s, baos);
        return baos.toByteArray();
    }

    public static String slurp(Reader reader) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        char[] buffer = new char[1024];
        boolean done = false;
        int amountRead = 0;
        while (!done) {
            amountRead = reader.read(buffer);
            if (amountRead > 0)
                stringBuffer.append(buffer, 0, amountRead);
            else
                done = true;
        }

        return stringBuffer.toString();
    }

    public static byte[] intToBytes(int i) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            new DataOutputStream(baos).writeInt(i);
            return baos.toByteArray();
        }
        catch (IOException ex) {
            throw new RuntimeException("BAD PROGRAMMER");
        }
    }

    public static int bytesToInt(byte[] bytes) {
        try {
            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
            return dis.readInt();
        }
        catch (IOException ex) {
            return Integer.MAX_VALUE;
        }
    }

    private static void log(String method, String message) {
        Logger.debug(null, "Util: " + message);
    }
}
