package com.soil.user.controller;

import com.soil.user.bean.Sensor;
import com.soil.user.bean.User;
import com.soil.user.enums.ErrorCode;
import com.soil.user.feign.SensorClient;
import com.soil.user.response.ApiResult;
import com.soil.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1")
public class UserController {

    private UserService userService;
    private SensorClient sensorClient;

    @Autowired
    public UserController(UserService userService, SensorClient sensorClient){
        this.userService = userService;
        this.sensorClient = sensorClient;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public ApiResult register(@RequestBody User user){
        return userService.register(user);
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT, produces = "application/json; charset=UTF-8")
    public ApiResult updateUser(@RequestHeader("id") int userId,
                                @RequestBody User user){
        user.setId(userId);
        user.setMax(0);
        return userService.updateUser(user);
    }

    @RequestMapping(value = "/user_a/{id}", method = RequestMethod.PUT, produces = "application/json; charset=UTF-8")
    public ApiResult updateUserA(@PathVariable int id,
                                @RequestBody User user){
        user.setId(id);
        return userService.updateUser(user);
    }

    @RequestMapping(value = "/user_count/{id}", method = RequestMethod.PUT, produces = "application/json; charset=UTF-8")
    public ApiResult updateCount(@PathVariable int id,
                                 @RequestBody User user){
        user.setId(id);
        return userService.updateCount(user);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public ApiResult getUser(@RequestHeader("id") int userId){
        User user = userService.findById(userId);
        if(user == null){
            return new ApiResult(ErrorCode.NOT_FOUND);
        }
        return new ApiResult<>(ErrorCode.OK,user);
    }

    @RequestMapping(value = "/user_a", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public ApiResult getUserA(@RequestParam("id") int id){
        User user = userService.findById(id);
        if(user == null){
            return new ApiResult(ErrorCode.NOT_FOUND);
        }
        return new ApiResult<>(ErrorCode.OK,user);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public ApiResult getUsers(@RequestParam(value="id" ,required = false) Integer userId,
                              @RequestParam(value="account" ,required = false,defaultValue = "") String account,
                              @RequestParam(value="name" ,required = false,defaultValue = "") String name,
                              @RequestParam(value = "page" ,required = false ,defaultValue = "1") int pageNo,
                              @RequestParam(value = "pageSize" ,required = false , defaultValue = "20") int pageSize){
        if(account != null && account.length()>0) {
            account = account.replaceAll("%","/%").replaceAll("_","/_").replaceAll("\\[","/[").replaceAll("\\]","/]");
        }
        if(name != null && name.length()>0) {
            name = name.replaceAll("%","/%").replaceAll("_","/_").replaceAll("\\[","/[").replaceAll("\\]","/]");
        }
        return userService.selectAllUsers(pageNo,pageSize,userId,account,name);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE, produces = "application/json; charset=UTF-8")
    public ApiResult deleteUser(@PathVariable int id){
        ApiResult<List<Sensor>> response = sensorClient.getSensors(id);
        if(response.getCode()!=200) return response;
        if(response.getValue()!=null){
            List<Sensor> sensors = response.getValue();
            for(Sensor sensor : sensors){
                ApiResult response1 = sensorClient.deleteSensor(sensor.getId());
                if(response1.getCode()!=204) return response1;
            }
        }
        return userService.deleteUser(id);
    }
}
