package com.jimmythrasher.launchor;

import java.io.*;
import java.util.*;

import javax.microedition.media.Player;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.component.ButtonField;

import net.rim.device.api.system.CodeModuleGroup;
import net.rim.device.api.system.CodeModuleGroupManager;

import com.jimmythrasher.common.util.Logger;
import com.jimmythrasher.common.blackberry.util.EventLoggerTraceListener;

class Main extends net.rim.device.api.ui.UiApplication {
    private static long LOG_KEY = 0x1b88a348d94553e8L; // echo -n com.jimmythrasher.launchor.LOG | sha1sum

    public Main() {
        Logger.addListener(new EventLoggerTraceListener(LOG_KEY, "Launchor"));
        //CodeModuleGroup[] groups = CodeModuleGroupManager.loadAll();
        //for (int i = 0; i < groups.length; i++) {
        //    Logger.debug("Info", "Examining CodeModuleGroup " + groups[i].getFriendlyName());
        //    Enumeration e = groups[i].getModules();
        //    while (e.hasMoreElements()) {
        //        Logger.debug("Info", "Module has type " + e.nextElement().getClass().getName());
        //    }
        //}

        pushScreen(new LaunchPopup());
    }

    public static void main(String[] args) {
        Main instance = new Main();
        instance.enterEventDispatcher();
    }
}
