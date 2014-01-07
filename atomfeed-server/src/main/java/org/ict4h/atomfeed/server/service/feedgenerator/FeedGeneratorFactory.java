package org.ict4h.atomfeed.server.service.feedgenerator;

import org.ict4h.atomfeed.server.repository.AllEventRecords;
import org.ict4h.atomfeed.server.repository.ChunkingEntries;
import org.ict4h.atomfeed.server.repository.EventRecordsOffsetMarkers;
import org.ict4h.atomfeed.server.service.helper.ResourceHelper;

public class FeedGeneratorFactory {

    public static final String NumberBasedChunkingStrategy = "number";
    private ResourceHelper resourceHelper;

    public FeedGenerator getFeedGenerator(AllEventRecords allEventRecords, EventRecordsOffsetMarkers eventRecordsOffsetMarkers, ChunkingEntries allChunkingEntries, ResourceHelper resourceHelper) {
        this.resourceHelper = resourceHelper;
        return get(getChunkingStrategy(), allEventRecords, eventRecordsOffsetMarkers, allChunkingEntries);
    }

    private FeedGenerator get(String chunkingStrategy, AllEventRecords allEventRecords, EventRecordsOffsetMarkers eventRecordsOffsetMarkers, ChunkingEntries allChunkingEntries) {
        if (NumberBasedChunkingStrategy.equals(chunkingStrategy)) {
            return new NumberFeedGenerator(allEventRecords, eventRecordsOffsetMarkers, allChunkingEntries);
        } else {
            return new TimeFeedGenerator(allEventRecords, allChunkingEntries);
        }
    }

    //TODO:This logic of retrieving keys using a bundle is duplicated. Use a ResourceWrapper.
    private String getChunkingStrategy() {
        return resourceHelper.fetchKeyOrDefault("chunking.strategy", NumberBasedChunkingStrategy);
    }
}
