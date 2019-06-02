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

    @RequestMapping(value = "/sensors", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public ApiResult getSensorByUserId(@RequestHeader("id") int userId,
                                       @RequestParam(value="id" ,required = false) Integer id,
                                       @RequestParam(value="account" ,required = false,defaultValue = "") String account,
                                       @RequestParam(value="name" ,required = false,defaultValue = "") String name,
                                       @RequestParam(value = "page" ,required = false ,defaultValue = "1") int pageNo,
                                       @RequestParam(value = "pageSize" ,required = false , defaultValue = "20") int pageSize){
        if(name != null && name.length()>0) {
            name = name.replaceAll("%","/%").replaceAll("_","/_").replaceAll("\\[","/[").replaceAll("\\]","/]");
        }

        return sensorService.selectAllSensors(pageNo,pageSize,id,name,userId);
    }

    @RequestMapping(value = "/all_sensors", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public ApiResult getSensorByUserId(@RequestHeader("id") int userId){
        List<Sensor> list = sensorService.selectSensors(userId);
        return new ApiResult<>(ErrorCode.OK,list);
    }

    @RequestMapping(value = "/all_sensors_a", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public ApiResult getSensors(@RequestParam("id") int userId){
        List<Sensor> list = sensorService.selectSensors(userId);
        return new ApiResult<>(ErrorCode.OK,list);
    }

    @RequestMapping(value = "/sensor", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public ApiResult getSensorById(@RequestParam("id") int id){
        Sensor sensor = sensorService.selectSensor(id);
        return new ApiResult<>(ErrorCode.OK,sensor);
    }

    @RequestMapping(value = "/sensor", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public ApiResult addSensor(@RequestBody Sensor sensor){
        ApiResult<User> result = userClient.getUser(sensor.getUserId());
        if(result.getValue() == null) return result;
        User record = result.getValue();
        if(record.getCount() >= record.getMax()) return new ApiResult(400,"用户测点已达上限");
        ApiResult response =  sensorService.insertSensor(sensor);
        if(response.getCode() == 201){
            User user = new User();
            user.setCount(1);
            user.setId(sensor.getUserId());
            userClient.updateCount(user.getId(),user);
        }
        return response;
    }

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

    @RequestMapping(value = "/sensor_a/{id}", method = RequestMethod.DELETE, produces = "application/json; charset=UTF-8")
    public ApiResult removeSenors(@PathVariable int id){
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

    @RequestMapping(value = "/sensor/{id}", method = RequestMethod.PUT, produces = "application/json; charset=UTF-8")
    public ApiResult updateSensor(@PathVariable int id,@RequestBody Sensor sensor){
        sensor.setId(id);
        return sensorService.updateSensor(sensor);
    }

    @RequestMapping(value = "/sensor_th/{id}", method = RequestMethod.PUT, produces = "application/json; charset=UTF-8")
    public ApiResult updateSensorTH(@PathVariable int id,@RequestBody Sensor sensor){
        sensor.setId(id);
        Sensor record = sensorService.selectSensor(sensor.getId());
        if(record != null){
            return sensorService.updateSensorTH(sensor);
        }
        else{
            return new ApiResult(ErrorCode.NOT_FOUND);
        }
    }
}
