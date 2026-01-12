package dev.ftctoolkit.core.hardware;

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Base class for all hardware devices.
 * Wraps an FTC SDK hardware component.
 */
public abstract class FTCCoreHardwareDevice<T extends HardwareDevice> {
    // Device information
    protected final String deviceName;
    protected T device;
    protected DeviceState state = DeviceState.NEW;

    // Idle management
    private boolean idleEnabled = false;
    private long idleTimeoutMs = 10000; // default 10 seconds
    private long lastCommandMs = System.currentTimeMillis();

    // State listeners
    private final List<DeviceStateListener> listeners = new CopyOnWriteArrayList<>();

    /**
     * Constructor for HardwareDevice
     * @param deviceName the name of the device in the HardwareMap
     */
    protected FTCCoreHardwareDevice(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * Initialize the hardware device from the HardwareMap
     * @param hardwareMap the HardwareMap to initialize from
     * @throws IllegalStateException if the device is not in the NEW state
     * @throws RuntimeException if initialization fails
     */
    public final void init(HardwareMap hardwareMap) {
        if (state != DeviceState.NEW) {
            throw new IllegalStateException("Attempted to reinitialize device: " + deviceName);
        }

        setState(DeviceState.INITIALIZING);
        try {
            onInit(hardwareMap);
            setState(DeviceState.READY);
        } catch (Exception e) {
            setState(DeviceState.FAULT);
            throw new RuntimeException("Failed to initialize device: " + deviceName, e);
        }
    }

    // ===== Hooks =====
    /**
     * Initialization hook to be implemented by subclasses.
     * @param hardwareMap the HardwareMap to initialize from
     */
    protected abstract void onInit(HardwareMap hardwareMap);

    /**
     * Idle hook to be implemented by subclasses.
     * Called once each time the device enters the IDLE state.
     */
    protected void onIdle() {}

    /**
     * Tick hook to be implemented by subclasses.
     * Called periodically in the tick() method.
     */
    protected void onTick() {}

    /**
     * Stop hook to be implemented by subclasses.
     * Called when the stop() method is invoked.
     */
    protected void onStop() {}

    /**
     * Get the underlying hardware device
     * @return the hardware device
     * @throws IllegalStateException if the device is not initialized or is in an invalid state.
     */
    public final T get() {
        if ( device != null && isNormalState())
            return device;
        else
            throw new IllegalStateException("Device not initialized or in invalid state: " + deviceName
                    + " (state: " + state + ")");
    }


    // ===== State =====
    /**
     * Get the current device state
     * @return the device state
     */
    public final DeviceState getState() { return state; }

    /**
     * Check if the device is initialized and in a normal operational state
     * @return true if initialized and in ACTIVE, IDLE, or READY state. False otherwise.
     */
    public final boolean isInitialized() { return device != null && isNormalState(); }

    /**
     * Check if the device is currently idle
     * @return true if the device is in IDLE state. False otherwise.
     */
    public final boolean isIdling() { return state == DeviceState.IDLE; }

    /**
     * Set the device state and notify listeners.
     * @param newState the new state to set
     */
    protected final void setState(DeviceState newState) {
        if (newState == null || newState == DeviceState.NEW || newState == state) return;
        DeviceState old = state;
        state = newState;

        for (DeviceStateListener l : listeners) {
            l.onStateChanged(this, old, newState);
        }
    }

    /**
     * Add a listener for state changes.
     * @param listener the listener to add
     */
    public final void addStateListener(DeviceStateListener listener) {
        if (listener != null) listeners.add(listener);
    }

    /**
     * Remove a listener for state changes.
     * @param listener the listener to remove
     */
    public final void removeStateListener(DeviceStateListener listener) {
        listeners.remove(listener);
    }

    /**
     * Reset the idle timer and mark the device as active.
     * @throws IllegalStateException if the device is not in a normal state.
     */
    protected final void markCommanded() {
        if (!isNormalState())
            throw new IllegalStateException("Cannot command device in state: " + state);

        lastCommandMs = System.currentTimeMillis();
        setState(DeviceState.ACTIVE);
    }

    /**
     * Enable or disable idle behavior.
     * @param enable true to enable idle, false to disable
     */
    protected final void enableIdle(boolean enable) {
        idleEnabled = enable;
        if (enable) {
            lastCommandMs = System.currentTimeMillis();
        }
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
        if (!isNormalState()) return;
        onTick();

        boolean shouldIdle =
                idleEnabled &&
                        (System.currentTimeMillis() - lastCommandMs) >= idleTimeoutMs;

        // The idle callback is only called once when entering idle state
        if (shouldIdle && state != DeviceState.IDLE) {
            setState(DeviceState.IDLE);
            onIdle();
        }
    }

    /**
     * Stop the device and set its state to STOPPED.
     * Once stopped, the device cannot be restarted,
     * and subsequent calls will throw an exception.
     * @throws IllegalStateException if the device is already stopped.
     */
    public final void stop() {
        if (state == DeviceState.STOPPED) {
            throw new IllegalStateException("Device already stopped: " + deviceName);
        }

        enableIdle(false);
        onStop();
        setState(DeviceState.STOPPED);
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

    /**
     * Check if the device is in a normal operational state
     * @return true if the device is ACTIVE, IDLE, or READY. False otherwise.
     */
    protected boolean isNormalState() {
        return state == DeviceState.ACTIVE || state == DeviceState.IDLE || state == DeviceState.READY;
    }
}