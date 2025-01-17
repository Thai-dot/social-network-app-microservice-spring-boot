package com.thaidot.post.utils.stragety.dateformatter.impl;

import com.thaidot.post.utils.stragety.dateformatter.DateFormatterStrategy;

import java.time.Duration;
import java.time.Instant;

public class SecondsFormatter implements DateFormatterStrategy {
    @Override
    public String formatDate(Instant createdDate) {
        long seconds = Duration.between(createdDate, Instant.now()).getSeconds();
        return seconds + " seconds ago";
    }
}
