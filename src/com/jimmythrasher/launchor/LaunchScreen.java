package com.jimmythrasher.launchor;

import java.util.*;

import net.rim.device.api.system.Characters;

import net.rim.device.api.collection.ReadableList;

import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.KeywordProvider;
import net.rim.device.api.ui.component.KeywordFilterField;
import net.rim.device.api.ui.container.HorizontalFieldManager;

import com.jimmythrasher.common.util.Logger;
import com.jimmythrasher.common.util.CollectionUtil;

public class LaunchScreen extends MainScreen {
    public static interface Listener {
        public void onCommand(String command);
    }

    public LaunchScreen(final Vector commands) {
        super();

        this.commandList = new CommandList(commands);
        this.commandListField = new KeywordFilterField();

        this.commandListField.setSourceList(this.commandList, this.commandList);
        this.commandListField.setKeywordField(new CommandEditField("Launch: ", ""));

        add(this.commandListField.getKeywordField());
        add(this.commandListField);
    }

    public boolean onClose() {
        setDirty(false);
        return super.onClose();
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
                String text = (String)commandListField.getSelectedElement();
                setText("");
                if (LaunchScreen.this.listener != null)
                    LaunchScreen.this.listener.onCommand(text);
                return true;
            }

            return super.keyChar(key, status, time);
        }
    }

    private CommandList        commandList;
    private KeywordFilterField commandListField;
    private Listener           listener;
}
