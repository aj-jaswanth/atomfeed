package org.ict4h.atomfeed.client.repository;

import org.ict4h.atomfeed.client.domain.FailedEvent;
import org.ict4h.atomfeed.client.domain.FailedEventRetryLog;

import java.util.List;

public interface AllFailedEvents {

    public FailedEvent get(String feedUri, String eventId);

    public void addOrUpdate(FailedEvent failedEvent);

    public List<FailedEvent> getOldestNFailedEvents(String feedUri, int numberOfFailedEvents, int failedEventMaxRetry);

    public int getNumberOfFailedEvents(String feedUri);

    void remove(FailedEvent failedEvent);

    void insert(FailedEventRetryLog failedEventRetryLog);
}
