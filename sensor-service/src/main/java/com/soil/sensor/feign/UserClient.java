package com.soil.sensor.feign;

import com.soil.sensor.bean.User;
import com.soil.sensor.response.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value="user-service",fallbackFactory = UserClientFallbackFactory.class)
public interface UserClient {

    @RequestMapping(value = "/v1/user_count/{id}",method = RequestMethod.PUT)
    ApiResult updateCount(@PathVariable int id, @RequestBody User user);

    @RequestMapping(value = "/v1/user_a",method = RequestMethod.GET)
    ApiResult<User> getUser(@RequestParam int id);
}
