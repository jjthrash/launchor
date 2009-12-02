package com.jimmythrasher.common.util;

public class ConsoleTraceListener implements Logger.Listener {
    public void onMessage(String message, int level) {
        System.out.println("Launchor: " + message);
    }
}
