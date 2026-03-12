package dev.ftctoolkit.core.hardware.motor;

import dev.ftctoolkit.core.hardware.motor.enums.MotorZeroPowerBehavior;


public final class MotorConfig extends SimpleMotorConfig {
    // ================= DEFAULTS =================
    public static final boolean DEFAULT_USE_ENCODER = true;
    public static final MotorZeroPowerBehavior DEFAULT_ZERO_POWER_BEHAVIOR =
            MotorZeroPowerBehavior.BRAKE;
    public static final boolean DEFAULT_ENABLE_IDLE = false;
    public static final long DEFAULT_IDLE_DELAY_MS = 2000;


    // ===== Behavior flags =====
    public boolean useEncoder;

    // ===== Zero power behavior =====
    public MotorZeroPowerBehavior zeroPowerBehavior;

    // ===== Idle behavior =====
    public boolean enableIdle;
    public MotorIdleAction idleAction;
    public long idleDelayMs;

    /** Default constructor for MotorConfig */
    public MotorConfig() {
        super();
        this.useEncoder = DEFAULT_USE_ENCODER;
        this.zeroPowerBehavior = DEFAULT_ZERO_POWER_BEHAVIOR;
        this.enableIdle = DEFAULT_ENABLE_IDLE;
        this.idleAction = (motor -> {});
        this.idleDelayMs = DEFAULT_IDLE_DELAY_MS;
    }

    /** Reset all settings to defaults */
    public void resetToDefaults() {
        super.resetToDefaults();
        this.useEncoder = DEFAULT_USE_ENCODER;
        this.zeroPowerBehavior = DEFAULT_ZERO_POWER_BEHAVIOR;
        this.enableIdle = DEFAULT_ENABLE_IDLE;
        this.idleAction = (motor -> {});
        this.idleDelayMs = DEFAULT_IDLE_DELAY_MS;
    }

    /** Enable or disable the use of encoders for this motor */
    public void useEncoder(boolean useEncoder) {
        this.useEncoder = useEncoder;
    }

    /** Set the zero power behavior for this motor */
    public void setZeroPowerBehavior(MotorZeroPowerBehavior behavior) {
        this.zeroPowerBehavior = (behavior != null) ? behavior : DEFAULT_ZERO_POWER_BEHAVIOR;
    }

    /** Enable or disable idle management for this motor */
    public void setEnableIdle(boolean enableIdle) {
        this.enableIdle = enableIdle;
    }

    /** Check if idle management is enabled for this motor */
    public boolean getIsIdleEnabled() {
        return this.enableIdle;
    }

    /** Set the action to perform when the motor is set to idle */
    public void setIdleAction(MotorIdleAction action) {
        this.idleAction = (action != null) ? action : (motor -> {});
    }

    /** Set the idle delay in milliseconds for this motor */
    public void setIdleDelayMs(long delayMs) {
        this.idleDelayMs = Math.max(0, delayMs);
    }

    /** Get the idle delay in milliseconds for this motor */
    public long getIdleDelayMs() {
        return this.idleDelayMs;
    }
}