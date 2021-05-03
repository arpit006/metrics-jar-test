package com.arpit.metricsexample.config;

import com.domedo.metrics.helper.MonitoringSystemFactory;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
 */
@Configuration
public class AppConfig {

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> meterRegistryCustomizer() {
        return registry -> registry.config().commonTags("TEST_METRICS", "DEMO_APP");
    }

    @Bean
    public MeterRegistry meterRegistry() {
        CompositeMeterRegistry meterRegistry = new CompositeMeterRegistry();
        meterRegistry.add(MonitoringSystemFactory.jmx());
//        meterRegistry.add(MonitoringSystemFactory.datadog());
//        meterRegistry.add(MonitoringSystemFactory.prometheus());
        return meterRegistry;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
