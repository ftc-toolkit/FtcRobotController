package dev.ftctoolkit.core.subsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Base subsystem with lifecycle hooks and optional typed state exposure.
 *
 * @param <TState> the public state type exposed by this subsystem
 */
public abstract class StatedSubsystem<TState> {

    protected HardwareMap hardwareMap;
    private boolean initialized = false;

    /**
     * Internal init entry point used by Robot.
     */
    public final void init(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        onInit(hardwareMap);
        initialized = true;
    }

    /**
     * Internal stop entry point used by Robot.
     */
    public final void stop() {
        ensureInitialized();
        onStop();
        initialized = false;
    }

    /**
     * Internal read entry point used by Robot.
     */
    public final void update() {
        ensureInitialized();
        onUpdate();
    }

    /**
     * Internal plan entry point used by Robot.
     */
    public final void plan() {
        ensureInitialized();
        onPlan();
    }

    /**
     * Internal execute entry point used by Robot.
     */
    public final void execute() {
        ensureInitialized();
        onExecute();
    }

    /**
     * Returns the subsystem's current public state snapshot.
     */
    public abstract TState getState();

    /**
     * Called once during robot initialization.
     */
    protected abstract void onInit(HardwareMap hardwareMap);

    protected void onStop() {}

    /**
     * Called during the update phase of each tick.
     */
    protected void onUpdate() {}

    /**
     * Called during the planning phase of each tick.
     */
    protected void onPlan() {}

    /**
     * Called during the execute phase of each tick.
     */
    protected void onExecute() {}

    /**
     * Returns whether this subsystem has been initialized.
     * @return true if initialized, false otherwise
     */
    public final boolean isInitialized() {
        return initialized;
    }

    /**
     * Ensures that this subsystem has been initialized, throwing an exception if not.
     */
    protected final void ensureInitialized() {
        if (!initialized) {
            throw new IllegalStateException(getClass().getSimpleName() + " has not been initialized.");
        }
    }
}