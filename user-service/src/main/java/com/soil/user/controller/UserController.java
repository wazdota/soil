package com.soil.user.controller;

import com.soil.user.bean.User;
import com.soil.user.enums.ErrorCode;
import com.soil.user.response.ApiResult;
import com.soil.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @CrossOrigin
    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public ApiResult register(@RequestBody User user){
        return userService.register(user);
    }

    @CrossOrigin
    @RequestMapping(value = "/user", method = RequestMethod.PUT, produces = "application/json; charset=UTF-8")
    public ApiResult updateUser(@RequestHeader("userId") int userId,
                                @RequestBody User user){
        user.setId(userId);
        if(user.getName() != null) {
            if (userService.updateName(user) != 201)
                return new ApiResult(ErrorCode.INVALID_REQUEST);
        }
        if(user.getPassword() != null) {
            if(userService.updatePwd(user) != 201)
                return new ApiResult(ErrorCode.INVALID_REQUEST);
        }
        return new ApiResult(ErrorCode.CREATED);
    }

    @RequestMapping(value = "/user_count/{id}", method = RequestMethod.PUT, produces = "application/json; charset=UTF-8")
    public ApiResult updateCount(@PathVariable int id,
                                 @RequestBody User user){
        user.setId(id);
        return userService.updateCount(user);
    }
}
