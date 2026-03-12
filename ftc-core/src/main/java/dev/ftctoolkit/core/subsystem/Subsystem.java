package dev.ftctoolkit.core.subsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Simpler subsystem for users who do not need public state exposure.
 */
public abstract class Subsystem extends StatedSubsystem<NoState> {

    @Override
    public final NoState getState() {
        return NoState.INSTANCE;
    }

    @Override
    protected abstract void onInit(HardwareMap hardwareMap);
}