package com.soil.sensor.feign;

import com.soil.sensor.enums.ErrorCode;
import com.soil.sensor.response.ApiResult;
import com.soil.sensor.bean.User;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class UserClientFallbackFactory implements FallbackFactory<UserClient> {

    @Override
    public UserClient create(Throwable cause) {
        return new UserClient() {
            @Override
            public ApiResult updateCount(int id,User user) {
                return new ApiResult(ErrorCode.INTERNAL_SERVER_ERROR);
            }
        };
    }
}
