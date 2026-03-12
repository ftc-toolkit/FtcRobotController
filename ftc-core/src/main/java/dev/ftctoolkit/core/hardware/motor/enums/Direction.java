package dev.ftctoolkit.core.hardware.motor.enums;

import com.qualcomm.robotcore.hardware.DcMotor;

public enum Direction {

    FORWARD(DcMotor.Direction.FORWARD),
    REVERSE(DcMotor.Direction.REVERSE);

    public final DcMotor.Direction sdkDirection;

    Direction(DcMotor.Direction sdkDirection) {
        this.sdkDirection = sdkDirection;
    }
}