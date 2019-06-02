package com.soil.th.feign;

import com.soil.th.bean.Sensor;
import com.soil.th.bean.TempAndHum;
import com.soil.th.enums.ErrorCode;
import com.soil.th.response.ApiResult;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class SensorClientFallbackFactory implements FallbackFactory<SensorClient> {

    @Override
    public SensorClient create(Throwable cause) {
        return new SensorClient() {
            @Override
            public ApiResult<Sensor> updateTH(int id, TempAndHum tempAndHum) {
                return new ApiResult<>(ErrorCode.INTERNAL_SERVER_ERROR);
            }

        };
    }
}
