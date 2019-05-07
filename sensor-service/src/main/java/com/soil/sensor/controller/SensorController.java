package com.soil.sensor.controller;

import com.soil.sensor.bean.Sensor;
import com.soil.sensor.bean.User;
import com.soil.sensor.enums.ErrorCode;
import com.soil.sensor.feign.HistoryClient;
import com.soil.sensor.feign.UserClient;
import com.soil.sensor.response.ApiResult;
import com.soil.sensor.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class SensorController {

    private SensorService sensorService;
    private HistoryClient historyClient;
    private UserClient userClient;

    @Autowired
    public SensorController(SensorService sensorService, HistoryClient historyClient, UserClient userClient) {
        this.sensorService = sensorService;
        this.historyClient = historyClient;
        this.userClient = userClient;
    }

    @CrossOrigin
    @RequestMapping(value = "/sensors", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public ApiResult getSensorByUserId(@RequestParam("userId") int userId){
        List<Sensor> list = sensorService.selectSensors(userId);
        return new ApiResult<>(ErrorCode.OK,list);
    }

    @CrossOrigin
    @RequestMapping(value = "/sensor", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public ApiResult getSensorById(@RequestParam("id") int id){
        Sensor sensor = sensorService.selectSensor(id);
        return new ApiResult<>(ErrorCode.OK,sensor);
    }

    @CrossOrigin
    @RequestMapping(value = "/sensor", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public ApiResult addSensor(@RequestBody Sensor sensor){
        ApiResult response =  sensorService.insertSensor(sensor);
        if(response.getCode() == 201){
            User user = new User();
            user.setCount(1);
            user.setId(sensor.getUserId());
            userClient.updateCount(user.getId(),user);
        }
        return response;
    }

    @CrossOrigin
    @RequestMapping(value = "/sensor/{id}", method = RequestMethod.DELETE, produces = "application/json; charset=UTF-8")
    public ApiResult removeSenor(@PathVariable int id){
        Sensor sensor = sensorService.selectSensor(id);
        if(sensor == null){
            return new ApiResult(ErrorCode.NOT_FOUND);
        }
        ApiResult response = historyClient.deleteHistories(id);
        if(response.getCode() == 204){
            ApiResult result = sensorService.deleteSensor(id);
            if(result.getCode() == 204){
                User user = new User();
                user.setCount(-1);
                user.setId(sensor.getUserId());
                userClient.updateCount(user.getId(),user);
            }
            return result;
        }
        return response;
    }

    @CrossOrigin
    @RequestMapping(value = "/sensor/{id}", method = RequestMethod.PUT, produces = "application/json; charset=UTF-8")
    public ApiResult updateSensor(@PathVariable int id,@RequestBody Sensor sensor){
        sensor.setId(id);
        return sensorService.updateSensor(sensor);
    }

    @CrossOrigin
    @RequestMapping(value = "/sensor_th", method = RequestMethod.PUT, produces = "application/json; charset=UTF-8")
    public ApiResult updateSensorTH(@RequestBody Sensor sensor){
        Sensor record = sensorService.selectSensor(sensor.getId());
        if(record != null){
            return sensorService.updateSensorTH(sensor);
        }
        else{
            return new ApiResult(ErrorCode.NOT_FOUND);
        }
    }
}
