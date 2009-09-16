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

        CodeModuleGroup[] groups = CodeModuleGroupManager.loadAll();
        for (int i = 0; i < groups.length; i++) {
            Logger.debug(null, "Examining CodeModuleGroup " + groups[i].getFriendlyName());
            //Enumeration e = groups[i].getModules();
            //while (e.hasMoreElements()) {
            //    Logger.debug(null, "Module " + e.nextElement());
            //}
        }

        this.popup = new LaunchPopup();
        this.popup.setLaunchCommandListener(new LaunchPopup.Listener() {
            public void onCommand(String command) {
                Logger.debug(null, "Got command " + command);
            }
        });
    }

    public void activate() {
        pushGlobalScreen(this.popup, 1, true);
    }

    public void deactivate() {
        dismissStatus(this.popup);
    }

    public static void main(String[] args) {
        Main instance = new Main();
        instance.enterEventDispatcher();
    }

    private LaunchPopup popup;
}
