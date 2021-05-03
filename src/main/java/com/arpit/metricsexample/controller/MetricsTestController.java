package com.arpit.metricsexample.controller;

import com.arpit.metricsexample.util.TestMetricUtils;
import com.domedo.metrics.vo.MetricVo;
import com.domedo.metrics.vo.metrics.BaseMetricVo;
import com.domedo.metrics.vo.metrics.CounterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
 */
@RestController
@RequestMapping(value = "/metrics-test")
@Slf4j
public class MetricsTestController {

    @Autowired
    private TestMetricUtils metricUtils;

    @Autowired
    private RestTemplate restTemplate;

//    @Value("${hostname}")
    private String hostName = "localhost";

    @GetMapping(value = "/test1")
    public ResponseEntity<String> test1() {
        long start = System.currentTimeMillis();
        String resp = "Metrics-Test app response from test1";
        long random = (long) (Math.random() * 1000);
        try {
            Thread.sleep(random);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pushSuccessMetrics("TEST_micrometer-test1", start, System.currentTimeMillis());
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping(value = "/test2")
    public ResponseEntity<String> test2() {
        long start = System.currentTimeMillis();
        String resp = "Metrics-Test app response from test2";
        long random = (long) (Math.random() * 1000);
        try {
            Thread.sleep(random);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pushSuccessMetrics("TEST_micrometer-test2", start, System.currentTimeMillis());
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping(value = "/test3")
    public void test3() {
        long start = System.currentTimeMillis();
        try {
            long random = (long) (Math.random() * 1000);
            try {
                Thread.sleep(random);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new RuntimeException("Exception occurred. Exception is test3()");
        } catch (Exception e) {
            pushExceptionMetrics("TEST_micrometer-" + e.getClass().getSimpleName(), start, System.currentTimeMillis());
            log.error("Error occurred while hitting test3 api. Error is " + e.getMessage());
        }
    }

   /* @GetMapping(value = "/testext")
    public ResponseEntity<ResponseObj<String>> externalApi() {
        String uri = "http://" + hostName + ":8086/response";
        try {
            long start = System.currentTimeMillis();
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
            log.error("HTTP Call successfully made to Ext API:: " + uri);
            HttpStatus statusCode = responseEntity.getStatusCode();
            log.error("Status code received as  " + statusCode + " while making call to " + uri);
            long now = System.currentTimeMillis();
            metricUtils.pushExternalApiSuccessMetrics(start, now, uri);
            String body = responseEntity.getBody();
            ResponseObj<String> responseObj = ResponseObj.<String>builder().url(uri).response(body).milliSeconds(now - start).build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            log.error("Error occurred while making call to ext api. Error is " + e.getMessage());
            metricUtils.pushExternalApiErrorMetrics(e.getClass().getSimpleName(), uri, HttpStatus.BAD_REQUEST.name(), HttpMethod.GET.name());
            throw new RuntimeException("Error occurred while making rest call. Error is " + e.getMessage());
        }
    }*/

    private void pushSuccessMetrics(String metricName, long start, long end) {
        MetricVo<BaseMetricVo> counterMetricVo = metricUtils.pushCounterMetrics(metricName, getSuccessTags(metricName));
        MetricVo<BaseMetricVo> gaugeMetricVo = metricUtils.pushGaugeMetrics(metricName, start, end, getSuccessTags(metricName));
        log.info(String.format("SUCCESS METRICS RESPONSE:: \nCOUNTER = [%s] \nGAUGE = [%s]\n", counterMetricVo, gaugeMetricVo));
    }

    private void pushExceptionMetrics(String metricName, long start, long end) {
        MetricVo<BaseMetricVo> counterMetricVo = metricUtils.pushCounterMetrics(metricName, getSuccessTags(metricName));
        MetricVo<BaseMetricVo> gaugeMetricVo = metricUtils.pushGaugeMetrics(metricName, start, end, getSuccessTags(metricName));
        log.info(String.format("EXCEPTION METRICS RESPONSE:: \nCOUNTER = [%s] \nGAUGE = [%s]\n", counterMetricVo, gaugeMetricVo));
    }

    private String[] getSuccessTags(String metricName) {
        return new String[]{
                "TEST_SUCCESS_" + metricName,
                metricName + "_SUCCESS"
        };
    }

    private String[] getExceptionTags(String metricName) {
        return new String[] {
                "TEST_EXCEPTION_" + metricName,
                metricName + "_EXCEPTION"
        };
    }
}
