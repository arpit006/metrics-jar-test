package com.arpit.metricsexample.util;

import com.domedo.metrics.util.MetricUtils;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
 */
@Service
public class TestMetricUtils extends MetricUtils {

    private MeterRegistry meterRegistry;

    public TestMetricUtils(MeterRegistry meterRegistry) {
        super(meterRegistry);
        this.meterRegistry = meterRegistry;
    }
}
