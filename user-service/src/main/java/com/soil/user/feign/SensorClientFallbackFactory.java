package com.soil.user.feign;

import com.soil.user.bean.Sensor;
import com.soil.user.enums.ErrorCode;
import com.soil.user.response.ApiResult;
import com.soil.user.bean.User;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SensorClientFallbackFactory implements FallbackFactory<SensorClient> {

    @Override
    public SensorClient create(Throwable cause) {
        return new SensorClient() {
            @Override
            public ApiResult<List<Sensor>> getSensors(int id) {
                return new ApiResult<>(ErrorCode.INTERNAL_SERVER_ERROR,null);
            }

            @Override
            public ApiResult deleteSensor(int id) {
                return new ApiResult(ErrorCode.INTERNAL_SERVER_ERROR);
            }
        };
    }
}
