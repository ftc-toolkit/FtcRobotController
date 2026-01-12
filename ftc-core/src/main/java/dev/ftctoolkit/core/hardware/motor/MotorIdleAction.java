package dev.ftctoolkit.core.hardware.motor;

@FunctionalInterface
public interface MotorIdleAction {
    void run(FTCCoreMotor motor);
}