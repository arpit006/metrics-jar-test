package com.arpit.metricsexample;

import com.arpit.metricsexample.config.SharedMetricsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Import(value = SharedMetricsConfig.class)
@SpringBootApplication
public class MetricsExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetricsExampleApplication.class, args);
	}

}
