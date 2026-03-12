package dev.ftctoolkit.core.subsystem.drive;

public interface Localizer {
    Pose getPose();

    void reset(Pose pose);

    void update();
}