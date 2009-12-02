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
import net.rim.device.api.system.CodeModuleManager;
import net.rim.device.api.system.ApplicationManager;
import net.rim.device.api.system.ApplicationDescriptor;
import net.rim.device.api.system.CodeModuleGroupManager;
import net.rim.device.api.system.ApplicationManagerException;

import com.jimmythrasher.common.util.Logger;
import com.jimmythrasher.common.util.CollectionUtil;
import com.jimmythrasher.common.blackberry.util.EventLoggerTraceListener;

class Main extends net.rim.device.api.ui.UiApplication {
    private static long LOG_KEY = 0x1b88a348d94553e8L; // echo -n com.jimmythrasher.launchor.LOG | sha1sum

    public Main() {
        Logger.addListener(new EventLoggerTraceListener(LOG_KEY, "Launchor"));

        this.applicationDescriptors = new Vector();

        int[] handles = CodeModuleManager.getModuleHandles();
        for (int i = 0; i < handles.length; i++) {
            Logger.debug(null, "Examining code module with name " + CodeModuleManager.getModuleName(handles[i]));
            ApplicationDescriptor[] descriptors = CodeModuleManager.getApplicationDescriptors(handles[i]);

            if (descriptors == null)
                Logger.debug(null, "Descriptors are null");
            else
                CollectionUtil.appendInto(this.applicationDescriptors, descriptors);
        }

        this.launchScreen = new LaunchScreen(CollectionUtil.collect(new CollectionUtil.ObjectBlock() {
            public Object op(Object element) {
                return ((ApplicationDescriptor)element).getName();
            }
        }, this.applicationDescriptors));

        this.launchScreen.setLaunchCommandListener(new LaunchScreen.Listener() {
            public void onCommand(String command) {
                // CodeModuleManager -> Handles -> Application Descriptors (has icon and name)
                // ApplicationManager can start an ApplicationDescriptor
                Logger.debug(null, "Got command " + command);
                Logger.debug(null, "Searching through " + Main.this.applicationDescriptors.size() + " descriptors");
                Enumeration e = Main.this.applicationDescriptors.elements();
                while (e.hasMoreElements()) {
                    ApplicationDescriptor descriptor = (ApplicationDescriptor)e.nextElement();
                    if (descriptor.getName().toLowerCase().startsWith(command.toLowerCase())) {
                        try {
                            ApplicationManager.getApplicationManager().runApplication(descriptor);
                        }
                        catch (ApplicationManagerException ex) {
                            Logger.error("Caught ApplicationManagerException: " + ex);
                        }
                    }
                }

                dismissStatus(Main.this.launchScreen);
            }
        });
    }

    public void activate() {
        pushGlobalScreen(this.launchScreen, 1, true);
    }

    public void deactivate() {
        dismissStatus(this.launchScreen);
    }

    public static void main(String[] args) {
        Main instance = new Main();
        instance.enterEventDispatcher();
    }

    private LaunchScreen launchScreen;

    private Vector applicationDescriptors;
}
