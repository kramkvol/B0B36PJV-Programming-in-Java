package cz.cvut.fel.logic;

import javafx.application.Platform;

/**
 * A class representing a game timer for tracking elapsed time in a game.
 */
public class GameTimer implements Runnable {
    private volatile boolean running = true;
    private volatile boolean paused = false;
    private long elapsedTime = 0;

    private final TimerListener listener;
    private String timeString;

    /**
     * Constructs a GameTimer with the specified listener.
     *
     * @param listener the listener for time changes.
     */
    public GameTimer(TimerListener listener) {
        this.listener = listener;
    }

    /**
     * Sets the elapsed time.
     *
     * @param time the elapsed time to set.
     */
    public synchronized void setElapsedTime(long time) {
        elapsedTime = time;
    }

    /**
     * Gets the elapsed time.
     *
     * @return the elapsed time.
     */
    public synchronized Long getElapsedTime() {
        return elapsedTime;
    }

    /**
     * Formats the elapsed time into a string representation of hours, minutes, and seconds.
     *
     * @param elapsedTime the elapsed time in seconds.
     * @return the formatted string representation of the elapsed time.
     */
    private String formatElapsedTime(long elapsedTime) {
        long hours = elapsedTime / 3600;
        long minutes = (elapsedTime % 3600) / 60;
        long seconds = elapsedTime % 60;
        if (hours == 99 && minutes == 59 && seconds == 59) {
            stop();
        }
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * The run method implements the main logic of a timer thread that tracks elapsed time.
     * <p>
     * This method runs in a loop while the {@code running} flag is set to {@code true}.
     * It checks for a {@code paused} state and, if the timer is paused, waits until it is resumed.
     * When the timer is running, it increments the elapsed time every second and updates the
     * listener with the new formatted time string using the JavaFX Application Thread.
     * <p>
     * The method performs the following steps:
     * <ul>
     *     <li>While the {@code running} flag is {@code true}:
     *     <ul>
     *         <li>If the timer is paused, it waits until notified to resume.</li>
     *         <li>Sleeps for one second to simulate the timer ticking.</li>
     *         <li>If the timer is not paused, it increments the elapsed time.</li>
     *         <li>Formats the elapsed time into a time string.</li>
     *         <li>Updates the listener with the new time string using {@code Platform.runLater} to ensure
     *         the update occurs on the JavaFX Application Thread.</li>
     *     </ul>
     *     </li>
     * </ul>
     * </p>
     * <p>
     * If the thread is interrupted, it throws a {@code RuntimeException}.
     * </p>
     *
     * @throws RuntimeException if the thread is interrupted while waiting or sleeping
     */
    @Override
    public void run() {
        while (running) {
            try {
                synchronized (this) {
                    while (paused) {
                        wait();
                    }
                }
                Thread.sleep(1000);
                synchronized (this) {
                    if (!paused) {
                        elapsedTime++;
                        timeString = formatElapsedTime(elapsedTime);
                        Platform.runLater(() -> listener.onTimeChanged(timeString));
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Pauses the timer.
     */
    public synchronized void pause() {
        paused = true;
    }

    /**
     * Resumes the timer.
     */
    public synchronized void resume() {
        paused = false;
        notifyAll();
    }

    /**
     * Stops the timer.
     */
    public synchronized void stop() {
        elapsedTime = 0;
        running = false;
        notifyAll();
    }

    /**
     * Resets the timer.
     */
    public synchronized void reset() {
        elapsedTime = 0;
        paused = false;
        notifyAll();
    }

    /**
     * Interface for receiving time change events.
     */
    public interface TimerListener {
        /**
         * Called when the time changes.
         *
         * @param time the new time string.
         */
        void onTimeChanged(String time);
    }
}
