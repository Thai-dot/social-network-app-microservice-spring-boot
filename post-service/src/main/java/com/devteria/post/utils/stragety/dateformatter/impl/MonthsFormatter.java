package com.devteria.post.utils.stragety.dateformatter.impl;

import com.devteria.post.utils.stragety.dateformatter.DateFormatterStrategy;

import java.time.Duration;
import java.time.Instant;

public class MonthsFormatter implements DateFormatterStrategy {
    @Override
    public String formatDate(Instant createdDate) {
        long months = Duration.between(createdDate, Instant.now()).toDays() / 30;
        return months + " months ago";
    }
}