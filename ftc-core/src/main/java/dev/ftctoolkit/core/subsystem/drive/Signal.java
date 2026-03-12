// ============================
// Signal.java
// ============================
package dev.ftctoolkit.core.subsystem.drive;

public final class Signal {
    private final double vx;
    private final double vy;
    private final double omega;

    public Signal(double vx, double vy, double omega) {
        this.vx = vx;
        this.vy = vy;
        this.omega = omega;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public double getOmega() {
        return omega;
    }

    public static Signal zero() {
        return new Signal(0.0, 0.0, 0.0);
    }
}