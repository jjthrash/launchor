package com.jimmythrasher.launchor;

import net.rim.device.api.system.Characters;

import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.HorizontalFieldManager;

import com.jimmythrasher.common.util.Logger;

public class LaunchPopup extends PopupScreen {
    public static interface Listener {
        public void onCommand(String command);
    }

    public LaunchPopup() {
        super(new HorizontalFieldManager());

        this.commandField = new CommandEditField("Command: ", "");

        add(this.commandField);
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
        }

        protected boolean keyDown(char key, int time) {
            Logger.debug(null, "Got key: " + key);
            if (key == Characters.ENTER) {
                Logger.debug(null, "It's Characters.ENTER!");
                String text = getText();
                setText("");
                if (LaunchPopup.this.listener != null)
                    LaunchPopup.this.listener.onCommand(text);
                return true;
            }

            return super.keyDown(key, time);
        }
    }

    private CommandEditField commandField;
    private Listener listener;
}
