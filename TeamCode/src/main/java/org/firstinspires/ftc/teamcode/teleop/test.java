package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp(name = "Basic: Iterative OpMode", group = "Iterative OpMode")
public class test extends OpMode {
    Robot r = new Robot();

    @Override
    public void init() {
        r.init();
    }

    @Override
    public void loop() {

    }
}
