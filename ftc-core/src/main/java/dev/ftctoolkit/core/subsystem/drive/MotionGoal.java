package dev.ftctoolkit.core.subsystem.drive;

public final class MotionGoal {
    private final Pose targetPose;
    private final double positionTolerance;
    private final double headingTolerance;

    public MotionGoal(Pose targetPose, double positionTolerance, double headingTolerance) {
        this.targetPose = targetPose;
        this.positionTolerance = positionTolerance;
        this.headingTolerance = headingTolerance;
    }

    public Pose getTargetPose() {
        return targetPose;
    }

    public double getPositionTolerance() {
        return positionTolerance;
    }

    public double getHeadingTolerance() {
        return headingTolerance;
    }
}