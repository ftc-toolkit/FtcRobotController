package dev.ftctoolkit.core.subsystem;

/**
 * Placeholder state for subsystems that do not expose meaningful public state.
 */
public final class NoState {
    public static final NoState INSTANCE = new NoState();

    private NoState() {
    }
}