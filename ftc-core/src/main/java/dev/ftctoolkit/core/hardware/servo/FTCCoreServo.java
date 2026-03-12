package dev.ftctoolkit.core.hardware.servo;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class FTCCoreServo extends FTCCoreServoBase<Servo> {

    /**
     * Constructor for HardwareDevice
     *
     * @param deviceName the name of the device in the HardwareMap
     */
    protected FTCCoreServo(String deviceName) {
        super(deviceName);
    }

    @Override
    protected void onInit(HardwareMap hardwareMap) {

    }
}
