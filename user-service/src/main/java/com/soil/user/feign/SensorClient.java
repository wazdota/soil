package com.soil.user.feign;

import com.soil.user.bean.Sensor;
import com.soil.user.bean.User;
import com.soil.user.response.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value="sensor-service",fallbackFactory = SensorClientFallbackFactory.class)
public interface SensorClient {

    @RequestMapping(value = "/v1/all_sensors_a",method = RequestMethod.GET)
    ApiResult<List<Sensor>> getSensors(@RequestParam("id") int id);

    @RequestMapping(value = "/v1/sensor_a/{id}",method = RequestMethod.DELETE)
    ApiResult deleteSensor(@PathVariable int id);
}
