package com.thaidot.post.utils.stragety.dateformatter;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DateFormatterContext {
    private DateFormatterStrategy strategy;

    public void setStrategy(DateFormatterStrategy strategy) {
        this.strategy = strategy;
    }

    public String format(Instant createdDate) {
        return strategy.formatDate(createdDate);
    }
}