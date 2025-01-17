package com.thaidot.post.utils.stragety.dateformatter;

import java.time.Instant;

public interface DateFormatterStrategy {
    String formatDate(Instant createdDate);
}
