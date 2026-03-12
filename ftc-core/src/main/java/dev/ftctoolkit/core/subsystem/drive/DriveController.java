package dev.ftctoolkit.core.subsystem.drive;

public interface DriveController {
    void setGoal(MotionGoal goal);

    Signal update(Pose currentPose);

    boolean isFinished(Pose currentPose);

    void reset();
}