package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.CompRobot;

public class LifeCycleAuto extends LinearOpMode{
    CompRobot rob = new CompRobot(hardwareMap);

    int step = 0;



    @Override
    public void runOpMode() throws InterruptedException {
        rob.init();
        this.waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            rob.update();
            rob.plan();
            rob.execute();
        }

        if(isStopRequested()) {
            rob.stop();
        }
    }
}
