package dev.ftctoolkit.core.subsystem.drive;

public final class Pose {
    private final double x;
    private final double y;
    private final double heading;

    public Pose(double x, double y, double heading) {
        this.x = x;
        this.y = y;
        this.heading = heading;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getHeading() {
        return heading;
    }

    public static Pose origin() {
        return new Pose(0.0, 0.0, 0.0);
    }
}