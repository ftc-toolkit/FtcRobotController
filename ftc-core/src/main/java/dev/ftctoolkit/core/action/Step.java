package dev.ftctoolkit.core.action;

import dev.ftctoolkit.core.robot.Robot;

/**
 * Represents a high-level planning step.
 *
 * Steps do not control hardware directly. They only set
 * subsystem goals during the planning phase.
 */
public abstract class Step<TRobot extends Robot> {

    private final int id;
    private final String title;
    private final String description;

    private boolean started = false;

    protected Step(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    final void startInternal(TRobot robot) {
        if (!started) {
            started = true;
            onStart(robot);
        }
    }

    final void finishInternal(TRobot robot) {
        onFinish(robot);
    }

    public final boolean hasStarted() {
        return started;
    }

    /**
     * Called once when the step begins.
     */
    protected void onStart(TRobot robot) {
        // Optional override
    }

    /**
     * Called every loop during the planning phase.
     */
    public abstract void plan(TRobot robot);

    /**
     * Determines when the step is complete.
     */
    public abstract boolean isFinished(TRobot robot);

    /**
     * Called once when the step finishes.
     */
    protected void onFinish(TRobot robot) {
        // Optional override
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}