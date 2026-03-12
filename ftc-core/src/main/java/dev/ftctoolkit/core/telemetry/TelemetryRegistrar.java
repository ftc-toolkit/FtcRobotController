package dev.ftctoolkit.core.telemetry;

import java.util.function.Supplier;

public interface TelemetryRegistrar {

    void add(String tag, String data);

    void add(String tag, Supplier<?> supplier);

    void addf(String tag, String format, Supplier<?>... suppliers);

    int removeByTag(String tag);

    void clearAll(); // removes all entries (not FTC telemetry clear)
}