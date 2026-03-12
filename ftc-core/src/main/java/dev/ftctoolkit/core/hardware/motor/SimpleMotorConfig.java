package dev.ftctoolkit.core.hardware.motor;

import dev.ftctoolkit.core.hardware.motor.enums.MotorZeroPowerBehavior;

public class SimpleMotorConfig {
    // ================= DEFAULTS =================
    public static final double DEFAULT_MIN_POWER = -1.0;
    public static final double DEFAULT_MAX_POWER = 1.0;
    public static final boolean DEFAULT_REVERSED = false;

    // ===== Power limits =====
    protected double minPower;
    protected double maxPower;

    // ===== Behavior flags =====
    protected boolean reversed;

    /** Default constructor for SimpleMotorConfig */
    public SimpleMotorConfig(Builder b) {
        this.minPower = b.minPower;
        this.maxPower = b.maxPower;
        this.reversed = b.reversed;
    }

    /** Reset all settings to defaults */
    public void resetToDefaults() {
        this.minPower = DEFAULT_MIN_POWER;
        this.maxPower = DEFAULT_MAX_POWER;
        this.reversed = DEFAULT_REVERSED;
    }

    /**
     * Set power limits for the motor
     *
     * @param min the lower power limit for the motor. Default -1.0. min >= -1.0.
     * @param max the upper power limit for the motor. Default 1.0. max <= 1.0.
     * @implNote calling this with {@code -1, 1} is equivalent to not calling it at all.
     */
    public void setPowerLimits(double min, double max) {
        this.minPower = clamp(min);
        this.maxPower = clamp(max);
        if (this.minPower > this.maxPower) {
            throw new IllegalArgumentException("minPower > maxPower");
        }
    }

    /** Get the maximum power limit for the motor */
    public double getMinPower() {
        return this.minPower;
    }

    /** Get the maximum power limit for the motor */
    public double getMaxPower() {
        return this.maxPower;
    }

    /** Reverse the motor direction */
    public void reverse() {
        this.reversed = !this.reversed;
    }

    /**
     * Check if the motor is reversed
     * @return true if the motor is reversed
     */
    public boolean isReversed() {
        return this.reversed;
    }

    /**
     * Clamp a value to the configured power limits
     * @param v the value to clamp
     * @return the clamped value
     */
    public double clamp(double v) { return Math.max(minPower, Math.min(maxPower, v)); }

    // ================= BUILDER =================
    /** Builder for MotorConfig */
    public static class Builder {

        private double minPower = -1.0;
        private double maxPower = 1.0;
        private boolean reversed = false;

        /** Set power limits for the motor
         *
         * @param min the lower power limit for the motor. Default -1.0. min >= -1.0.
         * @param max the upper power limit for the motor. Default 1.0. max <= 1.0.
         * @return Builder
         */
        public Builder setPowerLimits(double min, double max) {
            this.minPower = clamp(min);
            this.maxPower = clamp(max);
            if (this.minPower > this.maxPower) {
                throw new IllegalArgumentException("minPower > maxPower");
            }
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

    }
}
