package com.github.video.recorder;

import java.io.IOException;

import com.github.android.AndroidDeviceConfiguration;
import com.github.manager.AppiumDeviceManager;
import com.github.utils.MobilePlatform;

public class AppiumScreenRecordFactory {
    private static AndroidDeviceConfiguration androidDeviceConfiguration = new AndroidDeviceConfiguration();

    public static IScreenRecord recordScreen() throws IOException, InterruptedException {
        if (AppiumDeviceManager.getMobilePlatform()
            .equals(MobilePlatform.ANDROID)) {
            if (androidDeviceConfiguration.getDeviceManufacturer()
                .equals("unknown") && !androidDeviceConfiguration.checkIfRecordable()) {
                return new Flick();
            }
        }
        return new AppiumScreenRecorder();
    }
}
