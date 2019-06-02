package com.soil.th.feign;

import com.soil.th.bean.Sensor;
import com.soil.th.bean.TempAndHum;
import com.soil.th.response.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value="sensor-service",fallbackFactory = SensorClientFallbackFactory.class)
public interface SensorClient {
    @RequestMapping(value = "/v1/sensor_th/{id}",method = RequestMethod.PUT)
    ApiResult<Sensor> updateTH(@PathVariable int id, @RequestBody TempAndHum tempAndHum);
}
