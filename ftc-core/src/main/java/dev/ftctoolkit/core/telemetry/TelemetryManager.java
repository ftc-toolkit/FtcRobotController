package dev.ftctoolkit.core.telemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

import dev.ftctoolkit.core.telemetry.enums.DisplayMode;


public final class TelemetryManager implements TelemetryRegistrar {

    private final Telemetry sdk;
    private final TelemetryConfig config;
    private final List<TelemetryEntry> entries = new ArrayList<>();

    private boolean enabled = true;

    private int maxEntries = 50; // Optional: Limit the number of entries to prevent overflow


    public TelemetryManager(Telemetry sdkTelemetry) {
        this(sdkTelemetry, null);
    }

    public TelemetryManager(Telemetry sdkTelemetry, TelemetryConfig config) {
        if (sdkTelemetry == null) throw new IllegalArgumentException("sdkTelemetry is null");
        this.sdk = sdkTelemetry;
        this.config = (config != null) ? config : new TelemetryConfig();
        this.sdk.setAutoClear(true);
    }

    public TelemetryManager setDisplayMode(DisplayMode dm) {
        Telemetry.DisplayFormat format;
        switch (dm) {
            case HTML:
                format = Telemetry.DisplayFormat.HTML;
                break;
            case MONOSPACE:
                format = Telemetry.DisplayFormat.MONOSPACE;
                break;
            default:
                format = Telemetry.DisplayFormat.CLASSIC;
                break;
        }
        sdk.setDisplayFormat(format);
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean on) {
        this.enabled = on;
    }

    public void update() {
        if (!enabled) return;

        // Iterate over entries and print enabled & due ones
        for (TelemetryEntry e : entries) {
            if (!e.isEnabled()) continue;
            if (e.getLevel().ordinal() > config.getLevel().ordinal()) continue;

            if (e.isFormatted()) {
                Object[] values = e.getFormatValues();
                sdk.addData(e.getTag(), e.getFormat(), values);
            } else {
                sdk.addData(e.getTag(), e.getValue());
            }
        }

        sdk.update();
    }

    // ===== TelemetryRegistrar =====
    @Override
    public void add(String tag, String data) {
        TelemetryEntry e = new TelemetryEntry(tag, data);
        entries.add(e);
    }

    @Override
    public void add(String tag, Supplier<?> supplier) {
        TelemetryEntry e = new TelemetryEntry(tag, supplier);
        entries.add(e);
    }

    @Override
    public void addf(String tag, String format, Supplier<?>... suppliers) {
        TelemetryEntry e = new TelemetryEntry(tag, format, suppliers);
        entries.add(e);
    }

    @Override
    public int removeByTag(String tag) {
        if (tag == null) return 0;
        int removed = 0;
        for (Iterator<TelemetryEntry> it = entries.iterator(); it.hasNext(); ) {
            TelemetryEntry e = it.next();
            if (tag.equals(e.getTag())) {
                it.remove();
                removed++;
            }
        }
        return removed;
    }

    @Override
    public void clearAll() {
        entries.clear();
    }
}