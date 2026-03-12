package dev.ftctoolkit.core.telemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class FTCCoreTelemetry {
    private final Telemetry telemetry;
    private final TelemetryConfig config;

    public FTCCoreTelemetry(Telemetry t) {
        this.config = new TelemetryConfig();
        this.telemetry = t;
    }

    public FTCCoreTelemetry(Telemetry t, TelemetryConfig config) {
        this.config = config;
        this.telemetry = t;
    }
}
