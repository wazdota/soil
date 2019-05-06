package com.soil.sensor.feign;

import com.soil.sensor.bean.User;
import com.soil.sensor.response.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value="user-service",fallback = UserClientFallbackFactory.class)
public interface UserClient {

    @RequestMapping(value = "/v1/user_count/{id}",method = RequestMethod.PUT)
    ApiResult updateCount(@PathVariable int id, @RequestBody User user);
}
