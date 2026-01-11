package dev.ftctoolkit.core.hardware.motor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import dev.ftctoolkit.core.hardware.HardwareDevice;

public class Motor extends HardwareDevice<DcMotor> {
    private final MotorConfig config;

    /**
     * Constructor for Motor with default configuration
     * @see MotorConfig
     * @see DcMotor
     * @param deviceName the name of the motor device in the HardwareMap
     */
    public Motor(String deviceName) {
        this(deviceName, new MotorConfig.Builder().build());
    }

    /**
     * Constructor for Motor with custom configuration
     * @see MotorConfig
     * @see DcMotor
     * @param deviceName the name of the motor device in the HardwareMap
     * @param config the MotorConfig for this motor
     */
    public Motor(String deviceName, MotorConfig config) {
        super(deviceName);
        this.config = (config != null) ? config : new MotorConfig.Builder().build();
    }

    /**
     * Initialize the motor from the HardwareMap
     * @param hardwareMap the FTC robot HardwareMap
     */
    @Override
    public void init(HardwareMap hardwareMap) {
        device = hardwareMap.get(DcMotor.class, deviceName);
        device.setMode(config.useEncoder ? DcMotor.RunMode.RUN_USING_ENCODER : DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        device.setDirection(config.reversed ? DcMotor.Direction.REVERSE : DcMotor.Direction.FORWARD);
        device.setZeroPowerBehavior(config.zeroPowerBehavior.sdkBehavior);
        device.setPower(0);
        enableIdle(config.enableIdle);
        setIdleTimeoutMs(config.idleDelayMs);

        initialized = true;
        markCommanded();
    }

    /**
     * Called to stop the motor.
     */
    @Override
    public void stop() {
        if (device != null) {
            device.setPower(0);
        }
    }

    /**
     * Called when the motor is set to idle state.
     */
    @Override
    protected void onIdle() {
        super.onIdle();
        config.idleAction.run(this);
    }

    /**
     * Set the motor power, respecting configured limits.
     * @see MotorConfig
     * @param power the desired motor power
     */
    public void setPower(double power) {
        markCommanded();
        double clampedPower = Math.max(config.minPower, Math.min(config.maxPower, power));
        device.setPower(clampedPower);
    }

    /**
     * Stop and reset the motor encoder.
     */
    public void stopAndResetEncoder() {
        markCommanded();
        if (device == null) throw new IllegalStateException("Motor not initialized: " + deviceName);
        if (device != null) {
            device.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            device.setMode(config.useEncoder ? DcMotor.RunMode.RUN_USING_ENCODER : DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    public void setTargetPosition(int position) {
        markCommanded();
        if (device != null) {
            device.setTargetPosition(position);

        }
    }

    private
}
