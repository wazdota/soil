package com.soil.th.feign;

import com.soil.th.bean.TempAndHum;
import com.soil.th.response.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value="history-service",fallbackFactory = HistoryClientFallbackFactory.class)
public interface HistoryClient {
    @RequestMapping(value = "/v1/history",method = RequestMethod.DELETE)
    ApiResult addHistory(@RequestBody TempAndHum tempAndHum);
}
