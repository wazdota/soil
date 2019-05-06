package com.soil.sensor.feign;

import com.soil.sensor.enums.ErrorCode;
import com.soil.sensor.response.ApiResult;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class HistoryClientFallbackFactory implements FallbackFactory<HistoryClient> {

    @Override
    public HistoryClient create(Throwable cause) {
        return new HistoryClient() {
            @Override
            public ApiResult deleteHistories(int sensorId) {
                return new ApiResult(ErrorCode.INTERNAL_SERVER_ERROR);
            }
        };
    }
}
