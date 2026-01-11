package dev.ftctoolkit.core.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Base class for all hardware devices.
 * Wraps an FTC SDK hardware component.
 */
public abstract class HardwareDevice<T> {
    // Device information
    protected final String deviceName;
    protected T device;
    protected DeviceState state = DeviceState.NEW;
    private Throwable lastError;

    // Idle management
    private boolean idleEnabled = false;
    private long idleTimeoutMs = 150;
    private long lastCommandMs = System.currentTimeMillis();

    // State listeners
    private final List<DeviceStateListener> listeners = new CopyOnWriteArrayList<>();

    /**
     * Constructor for HardwareDevice
     * @param deviceName the name of the device in the HardwareMap
     */
    protected HardwareDevice(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * Initialize the hardware device from the HardwareMap
     */
    public abstract void init(HardwareMap hardwareMap);

    /**
     * Get the underlying hardware device
     * @return the hardware device
     * @throws IllegalStateException if the device is not initialized
     */
    public final T get() {
        try {
            if (device == null || state == DeviceState.NEW) {
                throw new IllegalStateException("Device not initialized: " + deviceName);
            }
            return device;
        } catch (Exception e) {
            lastError = e;
            setState(DeviceState.FAULT);
        }
        return null;
    }

    // ===== State =====
    public final DeviceState getState() { return state; }
    public final Throwable getLastError() { return lastError; }
    public final boolean isInitialized() { return state != DeviceState.NEW && device != null; }
    public final boolean isIdling() { return state == DeviceState.IDLE; }

    protected final void setState(DeviceState newState) {
        if (newState == null || newState == state) return;
        DeviceState old = state;
        state = newState;

        // If there are no listeners, then rethrow the last error if in FAULT state
        if (listeners.isEmpty()) {
            throw lastError;
        }

        for (DeviceStateListener l : listeners) {
            l.onStateChanged(this, old, newState);
        }
    }

    protected final void fault(Throwable t) {
        lastError = t;
        setState(DeviceState.FAULT);
    }

    public final void addStateListener(DeviceStateListener listener) {
        if (listener != null) listeners.add(listener);
    }

    public final void removeStateListener(DeviceStateListener listener) {
        listeners.remove(listener);
    }

    /**
     * Reset the idle timer and mark the device as active.
     */
    protected final void markCommanded() {
        lastCommandMs = System.currentTimeMillis();
        state = DeviceState.ACTIVE;
    }

    /**
     * Enable or disable idle behavior.
     * @param enable true to enable idle, false to disable
     */
    protected final void enableIdle(boolean enable) {
        idleEnabled = enable;
        if (enable) markCommanded(); // avoid instant idle
    }

    /**
     * Set the idle timeout in milliseconds.
     * @param ms timeout in milliseconds
     */
    protected final void setIdleTimeoutMs(long ms) {
        idleTimeoutMs = Math.max(0, ms);
    }

    /**
     * Tick method to be called periodically.
     * Manages idle state and calls onTick hook.
     */
    public final void tick() {
        if (state == DeviceState.NEW || state == DeviceState.STOPPED || state == DeviceState.FAULT) return;
        onTick();

        boolean shouldIdle =
                idleEnabled &&
                        (System.currentTimeMillis() - lastCommandMs) >= idleTimeoutMs;

        // The idle callback is only called once when entering idle state
        if (shouldIdle && !isIdling) {
            isIdling = true;
            onIdle();
        }
    }

    // Hooks
    protected void onIdle() {}
    protected void onTick() {}

    /**
     * Stop / safe the device
     */
    public void stop() {
        // Optional override
    }

    /**
     * Get the device name
     * @return device name
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * Get the device type as a string
     * @return device type
     */
    public String getDeviceType() {
        return (device != null) ? device.getClass().getSimpleName() : "Unknown";
    }

    public boolean isNormalState() {
        return state == DeviceState.ACTIVE || state == DeviceState.IDLE || state == DeviceState.READY;
    }
}