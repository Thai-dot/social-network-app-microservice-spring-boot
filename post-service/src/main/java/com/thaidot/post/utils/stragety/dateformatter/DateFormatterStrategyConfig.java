package com.thaidot.post.utils.stragety.dateformatter;

import com.thaidot.post.utils.stragety.dateformatter.impl.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class DateFormatterStrategyConfig {
    public static Map<Long, DateFormatterStrategy> getStrategyMap() {
        Map<Long, DateFormatterStrategy> strategyMap = new LinkedHashMap<>();
        strategyMap.put(60L, new SecondsFormatter());
        strategyMap.put(3600L, new MinutesFormatter());
        strategyMap.put(86400L, new HoursFormatter());
        strategyMap.put(2592000L, new DaysFormatter());
        strategyMap.put(31536000L, new MonthsFormatter());
        strategyMap.put(Long.MAX_VALUE, new YearsFormatter());
        return strategyMap;
    }
}