package dev.ftctoolkit.core.action;

import java.util.ArrayList;
import java.util.List;

import dev.ftctoolkit.core.robot.Robot;

/**
 * Runs Steps sequentially.
 *
 * Intended to be called during the robot planning phase.
 */
public class StepRunner<TRobot extends Robot> {

    private final TRobot robot;
    private final List<Step<TRobot>> steps = new ArrayList<>();

    private int currentIndex = 0;

    public StepRunner(TRobot robot) {
        this.robot = robot;
    }

    public StepRunner<TRobot> addStep(Step<TRobot> step) {
        steps.add(step);
        return this;
    }

    public void plan() {
        Step<TRobot> step = getCurrentStep();
        if (step == null) {
            return;
        }

        if (!step.hasStarted()) {
            step.startInternal(robot);
        }

        step.plan(robot);

        if (step.isFinished(robot)) {
            step.finishInternal(robot);
            currentIndex++;
        }
    }

    public Step<TRobot> getCurrentStep() {
        if (currentIndex >= steps.size()) {
            return null;
        }

        return steps.get(currentIndex);
    }

    public boolean isFinished() {
        return currentIndex >= steps.size();
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int size() {
        return steps.size();
    }

    public void reset() {
        currentIndex = 0;
    }
}