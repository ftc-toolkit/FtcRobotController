package org.firstinspires.ftc.teamcode.robot.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import dev.ftctoolkit.core.hardware.motor.FTCCoreMotor;
import dev.ftctoolkit.core.hardware.motor.MotorConfig;
import dev.ftctoolkit.core.hardware.motor.enums.MotorZeroPowerBehavior;
import dev.ftctoolkit.core.subsystem.SubSystemBase;

public class Intake extends SubSystemBase {
    FTCCoreMotor simple;
    FTCCoreMotor spinner;

    public Intake() {
        simple = new FTCCoreMotor("simple");
        spinner = new FTCCoreMotor("spinner", new MotorConfig()
                .setZeroPowerBehavior(MotorZeroPowerBehavior.BRAKE)
                .setEnable()
                .build());
    }

    @Override
    public boolean init(HardwareMap hardwareDevices) {
        spinner.init(hardwareDevices);
        spinner.init(hardwareDevices);
        return false;
    }
}
