package dev.ftctoolkit.core.telemetry;

import java.util.Objects;
import java.util.function.Supplier;

import dev.ftctoolkit.core.telemetry.enums.TelemetryLevel;

public final class TelemetryEntry {
    private final String tag;
    private final String label;
    private final Supplier<?> supplier;
    private final String format;
    private final Supplier<?>[] formatSuppliers;

    private Boolean enabled;

    private final TelemetryLevel level;

    TelemetryEntry(String tag, String data) {
        this(tag, () -> data);
    }

    TelemetryEntry(String tag, Supplier<?> supplier) {
        this(tag, TelemetryLevel.INFO, supplier);
    }

    TelemetryEntry(String tag, TelemetryLevel level, Supplier<?> supplier) {
        this.tag = validateTag(tag);
        this.supplier = Objects.requireNonNull(supplier, "supplier");
        this.format = null;
        this.formatSuppliers = null;
        this.level = Objects.requireNonNull(level, "level");
        this.enabled = true;
    }

    @SafeVarargs
    TelemetryEntry(String tag, String format, Supplier<?>... suppliers) {
        this(tag, TelemetryLevel.INFO, format, suppliers);
    }

    @SafeVarargs
    TelemetryEntry(String tag, TelemetryLevel level, String format, Supplier<?>... suppliers) {
        this.tag = validateTag(tag);
        this.supplier = null;
        this.format = Objects.requireNonNull(format, "format");
        this.formatSuppliers = (suppliers != null) ? suppliers : new Supplier<?>[0];
        if (this.formatSuppliers.length == 0) {
            throw new IllegalArgumentException("formatSuppliers cannot be empty");
        }
        this.level = Objects.requireNonNull(level, "level");
        this.enabled = true;
    }

    public String getTag() {
        return tag;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public TelemetryEntry setEnabled(boolean b) {
        enabled = b;
        return this;
    }

    public TelemetryLevel getLevel() {
        return level;
    }

    boolean isFormatted() {
        return format != null;
    }

    Object getValue() {
        if (supplier == null) {
            throw new IllegalStateException("TelemetryEntry is formatted; use getFormat/getFormatValues");
        }
        return supplier.get();
    }

    String getFormat() {
        if (format == null) {
            throw new IllegalStateException("TelemetryEntry is not formatted; use getValue");
        }
        return format;
    }

    Object[] getFormatValues() {
        Object[] out = new Object[formatSuppliers.length];
        for (int i = 0; i < formatSuppliers.length; i++) {
            out[i] = formatSuppliers[i].get();
        }
        return out;
    }

    private static String validateTag(String tag) {
        if (tag == null) throw new IllegalArgumentException("tag is null");
        String t = tag.trim();
        if (t.isEmpty()) throw new IllegalArgumentException("tag is blank");
        return t;
    }
}