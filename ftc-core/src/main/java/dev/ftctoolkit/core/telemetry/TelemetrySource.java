package dev.ftctoolkit.core.telemetry;

import java.util.List;

@FunctionalInterface
public interface TelemetrySource {
    List<TelemetryEntry> getTelemetryEntries();
}
