package dev.ftctoolkit.core.hardware.servo;

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

import dev.ftctoolkit.core.hardware.FTCCoreHardwareDevice;

public abstract class FTCCoreServoBase<T extends HardwareDevice> extends FTCCoreHardwareDevice<T> {
    /**
     * Constructor for HardwareDevice
     *
     * @param deviceName the name of the device in the HardwareMap
     */
    protected FTCCoreServoBase(String deviceName) {
        super(deviceName);
    }
}
