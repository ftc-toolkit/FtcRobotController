package dev.ftctoolkit.core.hardware.servo;

import dev.ftctoolkit.core.hardware.motor.MotorIdleAction;

public final class ServoConfig {
    // ===== Servo mode =====
    public final ServoMode servoMode;

    // ===== Power limits =====
    public final double minPosition;
    public final double maxPosition;
    public final double minPower;
    public final double maxPower;

    // ===== Behavior flags =====
    public final boolean reversed;

    // ===== Idle behavior =====
    public final boolean enableIdle;
    public final MotorIdleAction idleAction;
    public final long idleDelayMs;

    private ServoConfig(Builder b) {
        this.servoMode = b.servoMode;
        this.minPosition = b.minPosition;
        this.maxPosition = b.maxPosition;
        this.minPower = b.minPower;
        this.maxPower = b.maxPower;
        this.reversed = b.reversed;
        this.idleAction = b.idleAction;
        this.idleDelayMs = b.idleDelayMs;
        this.enableIdle = b.enableIdle;
    }

    // ================= BUILDER =================
    /** Builder for ServoConfig */
    public static class Builder {

        private ServoMode servoMode = ServoMode.STANDARD;

        private double minPosition = 0.0;
        private double maxPosition = 1.0;

        private double minPower = -1.0;
        private double maxPower = 1.0;

        private boolean reversed = false;

        // Default idle is do nothing
        private MotorIdleAction idleAction = servo -> {};
        private long idleDelayMs = 2000;
        private boolean enableIdle = false;

        /** Set servo mode
         *
         * @param mode the ServoMode for the servo. Default STANDARD.
         * @return Builder
         */
        public Builder servoMode(ServoMode mode) {
            this.servoMode = mode;
            return this;
        }

        /** Set position limits for the servo
         *
         * @param min the lower position limit for the servo. Default 0.0. min >= 0.0.
         * @param max the upper position limit for the servo. Default 1.0. max <= 1.0.
         * @return Builder
         */
        public Builder positionLimits(double min, double max) {
            this.minPosition = clamp(min);
            this.maxPosition = clamp(max);
            if (this.minPosition > this.maxPosition) {
                throw new IllegalArgumentException("minPosition > maxPosition");
            }
            return this;
        }

        /** Set power limits for the servo
         *
         * @param min the lower power limit for the servo. Default -1.0. min >= -1.0.
         * @param max the upper power limit for the servo. Default 1.0. max <= 1.0.
         * @return Builder
         */
        public Builder powerLimits(double min, double max) {
            this.minPower = clamp(min);
            this.maxPower = clamp(max);
            if (this.minPower > this.maxPower) {
                throw new IllegalArgumentException("minPower > maxPower");
            }
            return this;
        }

        /** Set whether the servo direction is reversed
         *
         * @param reversed true to reverse the servo direction, false for normal direction. Default false.
         * @return Builder
         */
        public Builder reversed(boolean reversed) {
            this.reversed = reversed;
            return this;
        }

        /** Set idle behavior for the servo
         *
         * @param action the MotorIdleAction to perform when the servo is idle. Default does nothing.
         * @param delayMs the delay in milliseconds before the servo is considered idle. Default 2000ms.
         * @param enableIdle true to enable idle behavior, false to disable. Default false.
         * @return Builder
         */
        public Builder idleBehavior(MotorIdleAction action, long delayMs, boolean enableIdle) {
            this.idleAction = (action != null) ? action : servo -> {
            };
            this.idleDelayMs = Math.max(0, delayMs);
            this.enableIdle = enableIdle;
            return this;
        }

        /** Build the ServoConfig
         *
         * @return ServoConfig
         */
        public ServoConfig build() {
            return new ServoConfig(this);
        }

        // Helper to clamp values between -1.0 and 1.0
        private double clamp(double value) {
            return Math.max(-1.0, Math.min(1.0, value));
        }
    }
}
