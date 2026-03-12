package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.CompRobot;

@TeleOp(name = "Sample Teleop", group = "Examples")
public class SampleIterativeTeleop extends OpMode {
    CompRobot rob = new CompRobot(hardwareMap);

    @Override
    public void init() {
        rob.init();
    }

    @Override
    public void loop() {
        // The FTC Toolkit uses a three-phase lifecycle for subsystems: update, plan, and execute.
        // The goal of this lifecycle is to separate the reading of hardware (update) from the
        // decision-making process (plan) and the writing to hardware (execute).
        // By separating these phases, it helps to ensure that all subsystems are working with the
        // most up-to-date information, and that writing to hardware is done in a controlled and
        // predictable manner.

        // During the update phase, subsystems should read from their hardware and update their
        // internal state accordingly.
        rob.update();

        // During the plan phase, subsystems should use their internal state to determine what
        // actions they need to take. This is where they should set their internal "desired state"
        // based on the current conditions.
        rob.plan();

        // During the execute phase, subsystems should take the actions necessary to achieve their
        // desired state. This is where they should write to their hardware to make the necessary
        // changes.
        rob.execute();
    }

    @Override
    public void stop() {
        rob.stop();
    }
}
