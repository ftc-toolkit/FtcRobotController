package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.robot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.robot.subsystems.Shooter;

import dev.ftctoolkit.core.robot.Robot;

public class CompRobot extends Robot {
    public final Intake intake;
    public final Shooter shooter;

    public CompRobot(HardwareMap hardwareMap) {
        super(hardwareMap);
        intake = new Intake();
        shooter = new Shooter();
    }

    @Override
    public void onInit() {
        registerSubsystem("intake", intake);
        registerSubsystem("shooter", shooter);
    }
}
