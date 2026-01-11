package dev.ftctoolkit.core.hardware.motor.enums;

import com.qualcomm.robotcore.hardware.DcMotor;

public enum MotorDirection {

    FORWARD(DcMotor.Direction.FORWARD),
    REVERSE(DcMotor.Direction.REVERSE);

    public final DcMotor.Direction sdkDirection;

    MotorDirection(DcMotor.Direction sdkDirection) {
        this.sdkDirection = sdkDirection;
    }
}