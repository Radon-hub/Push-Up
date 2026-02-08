package org.radon.pushup.features.event.ingestion.domain.validation;

import java.time.Duration;

public class EventTimePolicy {

    private static final long MAX_PAST_MS = Duration.ofDays(30).toMillis();


    public static long resolveEventTime(Long clientTs) {

        long CURRENT_TIME = System.currentTimeMillis();

        if (clientTs == null) {
            return CURRENT_TIME;
        }

        if (clientTs > CURRENT_TIME) {
            return CURRENT_TIME;
        }

        if (clientTs < CURRENT_TIME - MAX_PAST_MS) {
            return CURRENT_TIME;
        }

        return clientTs;
    }
}

