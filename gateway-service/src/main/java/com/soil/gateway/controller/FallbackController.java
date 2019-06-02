package com.soil.gateway.controller;

import com.soil.gateway.enums.ErrorCode;
import com.soil.gateway.response.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/fallback")
    public ApiResult fallback() {
        return new ApiResult(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
