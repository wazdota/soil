package com.soil.sensor.feign;

import com.soil.sensor.response.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value="history-service",fallbackFactory = HistoryClientFallbackFactory.class)
public interface HistoryClient {
    @RequestMapping(value = "/v1/histories/{sensorId}",method = RequestMethod.DELETE)
    ApiResult deleteHistories(@PathVariable int sensorId);
}
