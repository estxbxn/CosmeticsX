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

    /**
     * Process the polling state to get the data
     *
     * @param state
     */
    void process(T state);

    /**
     * Cancel the polling state and return the task as cancelled
     */
    void cancel();
}
