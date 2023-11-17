package fr.zebulon.cosmeticsx.storage.polls;

import java.util.Optional;

public interface IPollingContainer<T> {
    /**
     * Initializes the polling container, tries to pull data once
     * synchronously then processes future requests for data async
     */
    void init();

    /**
     * Force the poll and process the polled state
     */
    void forcePollAndProcess();

    /**
     * Poll the RestAPI and return the polled state
     *
     * @return the optional
     */
    Optional<T> poll();

    void process(T state);

    void cancel();
}
