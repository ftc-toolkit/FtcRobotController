package dev.ftctoolkit.core.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import dev.ftctoolkit.core.subsystem.StatedSubsystem;

/**
 * Main robot container that owns and runs subsystem lifecycles.
 */
public abstract class Robot {

    protected final HardwareMap hardwareMap;
    private final Map<String, StatedSubsystem<?>> subsystems = new LinkedHashMap<>();
    private boolean initialized = false;

    protected Robot(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    /**
     * Adds a subsystem to this robot.
     */
    protected final void registerSubsystem(String name, StatedSubsystem<?> subsystem) {
        if (initialized) {
            throw new IllegalStateException("Cannot add subsystems after robot initialization.");
        }

        if (subsystems.containsKey(name)) {
            throw new IllegalArgumentException("Subsystem already registered with name: " + name);
        }

        subsystems.put(name, subsystem);
    }

    /**
     * Initializes all subsystems.
     */
    public final void init() {
        onInit();

        for (StatedSubsystem<?> subsystem : subsystems.values()) {
            subsystem.init(hardwareMap);
        }

        initialized = true;
    }

    /**
     * Subclasses should override this method to register their subsystems.
     * This method is called during the robot's initialization phase.
     */
    public abstract void onInit();

    /**
     * Stops all subsystems.
     */
    public final void stop() {
        for (StatedSubsystem<?> subsystem : subsystems.values()) {
            subsystem.stop();
        }
    }

    /**
     * Updates all subsystems (read phase).
     */
    public final void update() {
        for (StatedSubsystem<?> subsystem : subsystems.values()) {
            subsystem.update();
        }
    }

    /**
     * Plans all subsystems (plan phase).
     */
    public final void plan() {
        for (StatedSubsystem<?> subsystem : subsystems.values()) {
            subsystem.plan();
        }
    }

    /**
     * Executes all subsystems (execute phase).
     */
    public final void execute() {
        for (StatedSubsystem<?> subsystem : subsystems.values()) {
            subsystem.execute();
        }
    }

    /**
     * Runs one full robot lifecycle tick:
     * 1. read all
     * 2. plan all
     * 3. execute all
     */
    public final void tick() {
        ensureInitialized();

        for (StatedSubsystem<?> subsystem : subsystems.values()) {
            subsystem.update();
        }

        for (StatedSubsystem<?> subsystem : subsystems.values()) {
            subsystem.plan();
        }

        for (StatedSubsystem<?> subsystem : subsystems.values()) {
            subsystem.execute();
        }
    }

    /**
     * @return an unmodifiable collection of all registered subsystems.
     */
    public final Collection<StatedSubsystem<?>> getSubsystems() {
        return subsystems.values();
    }

    /**
     * Gets a registered subsystem by name.
     *
     * @param name the name of the subsystem to retrieve
     * @return the subsystem with the given name, or null if no such subsystem exists
     */
    public final StatedSubsystem<?> getSubsystem(String name) {
        return subsystems.get(name);
    }

    /**
     * @return true if the robot has been initialized, false otherwise
     */
    public final boolean isInitialized() {
        return initialized;
    }

    /**
     * Ensures that the robot has been initialized before allowing subsystem operations.
     * Throws an IllegalStateException if the robot is not initialized.
     */
    private void ensureInitialized() {
        if (!initialized) {
            throw new IllegalStateException("Robot has not been initialized.");
        }
    }
}