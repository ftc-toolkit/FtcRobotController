package dev.ftctoolkit.core.hardware.motor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import dev.ftctoolkit.core.hardware.FTCCoreHardwareDevice;

/**
 * Wrapper class for FTC DcMotorEx with additional configuration and idle management.
 * @see DcMotorEx
 * @see MotorConfig
 */
public final class FTCCoreMotor extends FTCCoreHardwareDevice<DcMotorEx> {
    private final MotorConfig config;

    /**
     * Constructor for Motor with default configuration
     * @see MotorConfig
     * @see DcMotor
     * @param deviceName the name of the motor device in the HardwareMap
     */
    public FTCCoreMotor(String deviceName) {
        this(deviceName, new MotorConfig.Builder().build());
    }

    /**
     * Constructor for Motor with custom configuration
     * @see MotorConfig
     * @see DcMotor
     * @param deviceName the name of the motor device in the HardwareMap
     * @param config the MotorConfig for this motor
     */
    public FTCCoreMotor(String deviceName, MotorConfig config) {
        super(deviceName);
        this.config = config;
    }

    /**
     * Initialize the motor from the HardwareMap
     * @param hardwareMap the FTC robot HardwareMap
     */
    @Override
    protected void onInit(HardwareMap hardwareMap) {
        device = hardwareMap.get(DcMotorEx.class, deviceName);
        device.setMode(config.useEncoder ? DcMotor.RunMode.RUN_USING_ENCODER : DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        device.setDirection(config.reversed ? DcMotor.Direction.REVERSE : DcMotor.Direction.FORWARD);
        device.setZeroPowerBehavior(config.zeroPowerBehavior.sdkBehavior);
        device.setPower(0);
        setIdleTimeoutMs(config.idleDelayMs);
        enableIdle(config.enableIdle);
    }

    /**
     * Called when the motor is set to idle state.
     */
    @Override
    protected void onIdle() {
        config.idleAction.run(this);
    }

    /**
     * Set the power for the motor. Power is clamped based on MotorConfig.
     * SDK wrapper for DcMotor.setPower(double).
     * @param power the power level (default -1.0 to 1.0)
     * @see DcMotor#setPower(double)
     * @see MotorConfig#clamp(double)
     */
    public void setPower(double power) {
        device.setPower(config.clamp(power));
        markCommanded();
        DcMotorEx
    }

    /**
     * Set the target position for the motor encoder.
     * SDK wrapper for DcMotor.setTargetPosition(int).
     * @param position the target position
     * @see DcMotor#setTargetPosition(int)
     */
    public void setTargetPosition(int position) {
        device.setTargetPosition(position);
        markCommanded();
    }

    /**
     * Get the target position for the motor encoder.
     * SDK wrapper for DcMotor.getTargetPosition().
     * @return the target position
     * @see DcMotor#getTargetPosition()
     */
    public int getTargetPosition() {
        return device.getTargetPosition();
    }

    public void setTargetRpm(double rpm) {
        device.set
        device.getMotorType().getTicksPerRev()
        double ticksPerSecond = (rpm / 60.0) * config.ticksPerRev;
        double power = ticksPerSecond / config.maxTicksPerSecond;
        setPower(power);
    }

    /**
     * Check if the motor is busy (i.e., still moving to target position).
     * SDK wrapper for DcMotor.isBusy().
     * @return true if the motor is busy, false otherwise
     * @see DcMotor#isBusy()
     */
    public boolean isBusy() {
        return device.isBusy();
    }

    /**
     * Get the current position of the motor encoder.
     * SDK wrapper for DcMotor.getCurrentPosition().
     * @return the current position
     * @see DcMotor#getCurrentPosition()
     */
    public int getCurrentPosition() {
        return device.getCurrentPosition();
    }

    /**
     * Reset the motor encoder to zero.
     * SDK wrapper for DcMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER).
     * After resetting, the motor mode is set back to RUN_USING_ENCODER or RUN_WITHOUT_ENCODER
     * based on the MotorConfig.
     * @see DcMotor#setMode(DcMotor.RunMode)
     */
    public void resetEncoder() {
        device.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        device.setMode(config.useEncoder ? DcMotor.RunMode.RUN_USING_ENCODER : DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
