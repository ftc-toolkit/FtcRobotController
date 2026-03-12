package dev.ftctoolkit.core.hardware.servo;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class FTCCoreCRServo extends FTCCoreServoBase<CRServo> {

    /**
     * Constructor for HardwareDevice
     *
     * @param deviceName the name of the device in the HardwareMap
     */
    protected FTCCoreCRServo(String deviceName) {
        super(deviceName);
    }

    @Override
    protected void onInit(HardwareMap hardwareMap) {

    }
}
