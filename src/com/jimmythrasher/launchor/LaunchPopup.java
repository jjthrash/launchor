package com.jimmythrasher.launchor;

import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.HorizontalFieldManager;

public class LaunchPopup extends PopupScreen {
    public LaunchPopup() {
        super(new HorizontalFieldManager());
    }

    public boolean onClose() {
        System.exit(0);
        return true;
    }
}
