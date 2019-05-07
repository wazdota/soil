package com.soil.th.controller;

import com.alibaba.fastjson.JSON;
import com.soil.th.bean.Sensor;
import com.soil.th.bean.TempAndHum;
import com.soil.th.enums.ErrorCode;
import com.soil.th.feign.HistoryClient;
import com.soil.th.feign.SensorClient;
import com.soil.th.response.ApiResult;
import com.soil.th.websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class ThController {

    private WebSocket webSocket;
    private SensorClient sensorClient;
    private HistoryClient historyClient;

    @Autowired
    public ThController(WebSocket webSocket, SensorClient sensorClient, HistoryClient historyClient){
        this.webSocket = webSocket;
        this.historyClient = historyClient;
        this.sensorClient = sensorClient;
    }

    @CrossOrigin
    @RequestMapping(value = "/submit_th", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public ApiResult updateSensorTH(@RequestBody TempAndHum tempAndHum){
        ApiResult<Sensor> response1 = sensorClient.getSensor(tempAndHum.getSensorId());
        Sensor sensor = (Sensor)response1.getValue();
        if(response1.getValue() == null){
            return new ApiResult(ErrorCode.NOT_FOUND);
        }
        ApiResult response2 = sensorClient.updateTH(tempAndHum.getSensorId(),tempAndHum);
        if(response2.getCode() != 201){
            return response2;
        }
        webSocket.sendOneMessage(sensor.getUserId(), JSON.toJSONString(tempAndHum));
        return historyClient.addHistory(tempAndHum);
    }
}
