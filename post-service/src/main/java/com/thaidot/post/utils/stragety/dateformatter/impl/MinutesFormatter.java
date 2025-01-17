package com.thaidot.post.utils.stragety.dateformatter.impl;

import com.thaidot.post.utils.stragety.dateformatter.DateFormatterStrategy;

import java.time.Duration;
import java.time.Instant;

public class MinutesFormatter implements DateFormatterStrategy {
    @Override
    public String formatDate(Instant createdDate) {
        long minutes = Duration.between(createdDate, Instant.now()).toMinutes();
        return minutes + " minutes ago";
    }
}