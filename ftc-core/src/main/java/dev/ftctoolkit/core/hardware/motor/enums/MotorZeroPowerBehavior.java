package dev.ftctoolkit.core.hardware.motor.enums;

import com.qualcomm.robotcore.hardware.DcMotor;

public enum MotorZeroPowerBehavior {

    BRAKE(DcMotor.ZeroPowerBehavior.BRAKE),
    FLOAT(DcMotor.ZeroPowerBehavior.FLOAT);

    public final DcMotor.ZeroPowerBehavior sdkBehavior;

    MotorZeroPowerBehavior(DcMotor.ZeroPowerBehavior sdkBehavior) {
        this.sdkBehavior = sdkBehavior;
    }
}