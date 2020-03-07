package com.github.ios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.device.Device;
import com.github.interfaces.Manager;
import com.github.utils.CommandPromptUtil;
import org.json.JSONObject;

public class IOSManager implements Manager {
    private              CommandPromptUtil cmd;
    private final static int               IOS_UDID_LENGTH = 40;
    private final static int               SIM_UDID_LENGTH = 36;
    JSONObject iOSDevices;
    String     profile = "system_profiler SPUSBDataType | sed -n -E -e '/(iPhone|iPad|iPod)/" + ",/Serial/s/ *Serial " +
        "Number: *(.+)/\\1/p'";

    public IOSManager() {
        cmd = new CommandPromptUtil();
        iOSDevices = new JSONObject();
    }

    @Override
    public Device getDevice(String udid) {
        Optional<Device> device = getDevices().stream()
            .filter(d -> udid.equals(d.getUdid()))
            .findFirst();
        return device.orElseThrow(
            () -> new RuntimeException("Provided DeviceUDID " + udid + " is not found on the machine"));
    }

    public List<Device> getDevices() {
        List<Device> device = new ArrayList<>();
        getIOSUDID().forEach(udid -> {
            try {
                device.add(new Device(getDeviceInfo(udid)));
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
        return device;
    }

    private JSONObject getDeviceInfo(String udid) throws InterruptedException, IOException {

        String model = cmd.runProcessCommandToGetDeviceID("ideviceinfo -u " + udid + " | grep ProductType")
            .replace("\n", "");

        String name = cmd.runProcessCommandToGetDeviceID("idevicename --udid " + udid);
        String osVersion = cmd.runProcessCommandToGetDeviceID("ideviceinfo --udid " + udid + " | grep ProductVersion")
            .replace("ProductVersion:", "")
            .replace("\n", "")
            .trim();

        iOSDevices.put("deviceModel", model);
        iOSDevices.put("udid", udid);
        iOSDevices.put("name", name);
        iOSDevices.put("brand", "Apple");
        iOSDevices.put("isDevice", "true");
        iOSDevices.put("screenSize", "Not Supported");
        iOSDevices.put("apiLevel", "");
        iOSDevices.put("osVersion", osVersion);
        iOSDevices.put("os", "iOS");
        iOSDevices.put("deviceManufacturer", "apple");
        return iOSDevices;
    }

    private ArrayList<String> getIOSUDID() {
        ArrayList<String> deviceUDIDiOS = new ArrayList<String>();
        try {
            int startPos = 0;
            int endPos = IOS_UDID_LENGTH - 1;
            Optional<String> getIOSDeviceID = Optional.of(cmd.runProcessCommandToGetDeviceID(profile));
            while (endPos < getIOSDeviceID.get()
                .length()) {
                deviceUDIDiOS.add(getIOSDeviceID.get()
                    .substring(startPos, endPos + 1)
                    .replace("\n", ""));
                startPos += IOS_UDID_LENGTH;
                endPos += IOS_UDID_LENGTH;
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to fetch iOS device connected");
        }
        return deviceUDIDiOS;
    }
}
