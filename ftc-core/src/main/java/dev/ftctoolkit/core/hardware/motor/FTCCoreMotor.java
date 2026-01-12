package dev.ftctoolkit.core.hardware.motor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import dev.ftctoolkit.core.hardware.FTCCoreHardwareDevice;

public final class FTCCoreMotor extends FTCCoreHardwareDevice<DcMotor> {
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
        this.config = (config != null) ? config : new MotorConfig.Builder().build();
    }

    /**
     * Initialize the motor from the HardwareMap
     * @param hardwareMap the FTC robot HardwareMap
     */
    @Override
    protected void onInit(HardwareMap hardwareMap) {
        device = hardwareMap.get(DcMotor.class, deviceName);
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
}
