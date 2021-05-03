package com.arpit.metricsexample.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
 */
@Configuration
@ComponentScan(basePackages = {"com.domedo.metrics.*"})
public class SharedMetricsConfig {
}
