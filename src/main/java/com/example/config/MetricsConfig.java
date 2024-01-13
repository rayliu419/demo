package com.example.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.logging.LoggingMeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  配置Metric打印到日志中。
 */
@Configuration
@Slf4j
public class MetricsConfig {

    @Bean
    public MeterRegistry newMetricRegistry() {
        MeterRegistry meterRegistry = new LoggingMeterRegistry();
        return meterRegistry;
    }

}
