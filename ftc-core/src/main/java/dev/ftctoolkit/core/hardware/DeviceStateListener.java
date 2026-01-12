package dev.ftctoolkit.core.hardware;

@FunctionalInterface
public interface DeviceStateListener {
    void onStateChanged(FTCCoreHardwareDevice<?> device, DeviceState from, DeviceState to);
}
