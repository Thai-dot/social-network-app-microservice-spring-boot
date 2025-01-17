package com.thaidot.post.utils.stragety.dateformatter.impl;

import com.thaidot.post.utils.stragety.dateformatter.DateFormatterStrategy;

import java.time.Duration;
import java.time.Instant;

public class YearsFormatter implements DateFormatterStrategy {
    @Override
    public String formatDate(Instant createdDate) {
        long years = Duration.between(createdDate, Instant.now()).toDays() / 365;
        return years + " years ago";
    }
}