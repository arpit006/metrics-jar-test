package com.arpit.metricsexample.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author Arpit Srivastava <a> mailTo: iarpitsrivastava06@gmail.com</a>
 */
@Data
@Builder
public class ResponseObj<T> {
    private String url;

    private T response;

    private long milliSeconds;
}
