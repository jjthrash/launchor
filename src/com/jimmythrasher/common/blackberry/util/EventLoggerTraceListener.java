package com.jimmythrasher.common.blackberry.util;

import net.rim.device.api.system.EventLogger;

import com.jimmythrasher.common.util.Logger;

public class EventLoggerTraceListener implements Logger.Listener {
    public EventLoggerTraceListener(long id, String name) {
        this.id = id;
        this.name = name;
        EventLogger.register(id, name, EventLogger.VIEWER_STRING);
    }

    public void onMessage(final String message, final int level) {
        final int threadID = System.identityHashCode(Thread.currentThread());
        final long currentTime = System.currentTimeMillis();
        final String formatted = "(" + currentTime + "ms) " + message + " [" + threadID + "]";
        if (Logger.DEBUG == level)
            EventLogger.logEvent(id, formatted.getBytes(), EventLogger.DEBUG_INFO);
        else if (Logger.INFO == level)
            EventLogger.logEvent(id, formatted.getBytes(), EventLogger.INFORMATION);
        else if (Logger.WARNING == level)
            EventLogger.logEvent(id, formatted.getBytes(), EventLogger.WARNING);
        else if (Logger.ERROR == level)
            EventLogger.logEvent(id, formatted.getBytes(), EventLogger.ERROR);
        else if (Logger.ALWAYS == level)
            EventLogger.logEvent(id, formatted.getBytes(), EventLogger.ALWAYS_LOG);
    }

    private long id;
    private String name;
}
