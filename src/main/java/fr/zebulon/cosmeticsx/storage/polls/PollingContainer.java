package fr.zebulon.cosmeticsx.storage.polls;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public abstract class PollingContainer<T> implements IPollingContainer<T> {

    private T state;
    private final long pollRate;
    private BukkitTask task = null;
    private final Plugin plugin;

    public PollingContainer(Plugin plugin, T base, long pollRate) {
        this.plugin = plugin;
        this.state = base;
        this.pollRate = pollRate;
    }

    /**
     * Initializes the polling container, tries to pull data once
     * synchronously then processes future requests for data async
     */
    @Override
    public void init() {
        cancel();
        forcePollAndProcess();
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::forcePollAndProcess, 0, pollRate);
    }

    /**
     * Force the poll and process the polled state
     */
    @Override
    public void forcePollAndProcess() {
        poll().ifPresent(state -> {
            process(state);
            this.state = state;
        });
    }

    /**
     * Cancel the polling task
     */
    @Override
    public void cancel() {
        if (task == null) return;
        task.cancel();
        task = null;
    }
}
