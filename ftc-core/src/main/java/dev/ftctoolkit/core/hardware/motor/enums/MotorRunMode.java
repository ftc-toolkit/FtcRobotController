package dev.ftctoolkit.core.hardware.motor.enums;

import com.qualcomm.robotcore.hardware.DcMotor;

public enum MotorRunMode {

    RUN_WITHOUT_ENCODER(DcMotor.RunMode.RUN_WITHOUT_ENCODER),
    RUN_USING_ENCODER(DcMotor.RunMode.RUN_USING_ENCODER),
    RUN_TO_POSITION(DcMotor.RunMode.RUN_TO_POSITION),
    STOP_AND_RESET_ENCODER(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    public final DcMotor.RunMode sdkMode;

    MotorRunMode(DcMotor.RunMode sdkMode) {
        this.sdkMode = sdkMode;
    }
}