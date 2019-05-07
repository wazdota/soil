package com.soil.th.feign;

import com.soil.th.bean.TempAndHum;
import com.soil.th.enums.ErrorCode;
import com.soil.th.response.ApiResult;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class HistoryClientFallbackFactory implements FallbackFactory<HistoryClient> {

    @Override
    public HistoryClient create(Throwable cause) {
        return new HistoryClient() {
            @Override
            public ApiResult addHistory(TempAndHum tempAndHum) {
                return new ApiResult(ErrorCode.INTERNAL_SERVER_ERROR);
            }
        };
    }
}
