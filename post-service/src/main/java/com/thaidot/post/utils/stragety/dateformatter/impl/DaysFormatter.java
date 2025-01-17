package com.thaidot.post.utils.stragety.dateformatter.impl;

import com.thaidot.post.utils.stragety.dateformatter.DateFormatterStrategy;

import java.time.Duration;
import java.time.Instant;

public class DaysFormatter implements DateFormatterStrategy {
    @Override
    public String formatDate(Instant createdDate) {
        long days = Duration.between(createdDate, Instant.now()).toDays();
        return days + " days ago";
    }
}