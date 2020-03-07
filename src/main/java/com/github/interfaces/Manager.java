package com.github.interfaces;

import java.util.List;

import com.github.device.Device;

public interface Manager {
    Device getDevice(String udid) throws Exception;

    List<Device> getDevices() throws Exception;
}