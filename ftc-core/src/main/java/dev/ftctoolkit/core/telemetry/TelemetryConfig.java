package dev.ftctoolkit.core.telemetry;

import dev.ftctoolkit.core.telemetry.enums.DisplayMode;
import dev.ftctoolkit.core.telemetry.enums.TelemetryLevel;

public class TelemetryConfig {
    public static final DisplayMode DEFAULT_DISPLAY_TYPE = DisplayMode.CLASSIC;
    public static final boolean DEFAULT_AUTO_UPDATE = true;
    public static final TelemetryLevel DEFAULT_LEVEL = TelemetryLevel.INFO;

    private DisplayMode displayMode = DEFAULT_DISPLAY_TYPE;
    private boolean autoUpdate = DEFAULT_AUTO_UPDATE;
    private TelemetryLevel level = DEFAULT_LEVEL;

    public DisplayMode getDisplayMode() {
        return displayMode;
    }

    public TelemetryConfig setDisplayMode(DisplayMode displayMode) {
        this.displayMode = displayMode;
        return this;
    }

    public boolean isAutoUpdate() {
        return autoUpdate;
    }

    public TelemetryConfig setAutoUpdate(boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
        return this;
    }

    public TelemetryLevel getLevel() {
        return level;
    }

    public TelemetryConfig setLevel(TelemetryLevel level) {
        this.level = level;
        return this;
    }
}
