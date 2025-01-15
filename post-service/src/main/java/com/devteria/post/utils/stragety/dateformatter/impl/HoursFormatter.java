package com.devteria.post.utils.stragety.dateformatter.impl;

import com.devteria.post.utils.stragety.dateformatter.DateFormatterStrategy;

import java.time.Duration;
import java.time.Instant;

public class HoursFormatter implements DateFormatterStrategy {
    @Override
    public String formatDate(Instant createdDate) {
        long hours = Duration.between(createdDate, Instant.now()).toHours();
        return hours + " hours ago";
    }
}