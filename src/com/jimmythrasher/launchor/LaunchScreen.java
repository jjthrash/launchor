package com.jimmythrasher.launchor;

import net.rim.device.api.system.Characters;

import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.container.FullScreen;
import net.rim.device.api.ui.container.HorizontalFieldManager;

import com.jimmythrasher.common.util.Logger;

public class LaunchScreen extends FullScreen {
    public static interface Listener {
        public void onCommand(String command);
    }

    public LaunchScreen() {
        super();

        this.commandField = new CommandEditField("Command: ", "");

        this.commandList = new ListField();

        add(this.commandField);
        add(this.commandList);
    }

    public boolean onClose() {
        return true;
    }

    public void setLaunchCommandListener(Listener listener) {
        this.listener = listener;
    }

    private class CommandEditField extends BasicEditField {
        public CommandEditField(String label, String text) {
            super(label, text);
            Logger.debug(null, "Using CommandEditField");
        }

        protected boolean keyChar(char key, int status, int time) {
            Logger.debug(null, "Got key: " + key);
            if (key == Characters.ENTER) {
                Logger.debug(null, "It's Characters.ENTER!");
                String text = getText();
                setText("");
                if (LaunchScreen.this.listener != null)
                    LaunchScreen.this.listener.onCommand(text);
                return true;
            }

            return super.keyChar(key, status, time);
        }
    }

    private CommandEditField commandField;
    private ListField commandList;
    private Listener listener;
}
