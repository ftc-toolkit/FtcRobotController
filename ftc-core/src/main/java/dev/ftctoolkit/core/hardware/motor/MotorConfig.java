package dev.ftctoolkit.core.hardware.motor;

import dev.ftctoolkit.core.hardware.motor.enums.MotorZeroPowerBehavior;

public final class MotorConfig {

    // ===== Power limits =====
    public final double minPower;
    public final double maxPower;

    // ===== Behavior flags =====
    public final boolean useEncoder;
    public final boolean reversed;

    // ===== Zero power behavior =====
    public final MotorZeroPowerBehavior zeroPowerBehavior;

    // ===== Idle behavior =====
    public final boolean enableIdle;
    public final MotorIdleAction idleAction;
    public final long idleDelayMs;

    private MotorConfig(Builder b) {
        this.minPower = b.minPower;
        this.maxPower = b.maxPower;
        this.useEncoder = b.useEncoder;
        this.reversed = b.reversed;
        this.zeroPowerBehavior = b.zeroPowerBehavior;
        this.idleAction = b.idleAction;
        this.idleDelayMs = b.idleDelayMs;
        this.enableIdle = b.enableIdle;
    }

    // ================= BUILDER =================
    /** Builder for MotorConfig */
    public static class Builder {

        private double minPower = -1.0;
        private double maxPower = 1.0;

        private boolean useEncoder = false;
        private boolean reversed = false;

        private MotorZeroPowerBehavior zeroPowerBehavior =
                MotorZeroPowerBehavior.BRAKE;

        // Default idle is do nothing
        private MotorIdleAction idleAction = motor -> {};
        private long idleDelayMs = 2000;
        private boolean enableIdle = false;


        /** Set power limits for the motor
         *
         * @param min the lower power limit for the motor. Default -1.0. min >= -1.0.
         * @param max the upper power limit for the motor. Default 1.0. max <= 1.0.
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

        /** Set whether to use encoder for the motor
         *
         * @param useEncoder true to use encoder, false otherwise
         * @return Builder
         */
        public Builder useEncoder(boolean useEncoder) {
            this.useEncoder = useEncoder;
            return this;
        }

        /** Set whether to reverse the motor direction
         *
         * @param reversed true to reverse, false otherwise. Defaults false.
         * @return Builder
         */
        public Builder reversed(boolean reversed) {
            this.reversed = reversed;
            return this;
        }

        /** Set zero power behavior for the motor
         *
         * @param behavior zero power behavior. Default is MotorZeroPowerBehavior.BRAKE.
         * @return Builder
         */
        public Builder zeroPowerBehavior(MotorZeroPowerBehavior behavior) {
            this.zeroPowerBehavior = (behavior != null) ? behavior : MotorZeroPowerBehavior.BRAKE;
            return this;
        }

        /** Enable or disable idle behavior
         *
         * @param enable true to enable idle, false to disable. Default is false.
         * @return Builder
         */
        public Builder enableIdle(boolean enable) {
            this.enableIdle = enable;
            return this;
        }

        /** Set the idle function callback
         *
         * @param action function to call on idle. Default is nothing.
         * @return Builder
         */
        public Builder idleAction(MotorIdleAction action) {
            this.idleAction = (action != null) ? action : (motor -> {});
            return this;
        }

        /** Set time delay to start idle
         *
         * @param delayMs delay amount in milliseconds. Default 2000ms.
         * @return Builder
         */
        public Builder idleDelayMs(long delayMs) {
            this.idleDelayMs = Math.max(0, delayMs);
            return this;
        }

        /** Creates a MotorConfig
         *
         * @return MotorConfig
         */
        public MotorConfig build() {
            return new MotorConfig(this);
        }

        private static double clamp(double v) {
            return Math.max(-1.0, Math.min(1.0, v));
        }
    }
}