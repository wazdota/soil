package com.soil.admin.controller;

import com.soil.admin.bean.Admin;
import com.soil.admin.enums.ErrorCode;
import com.soil.admin.response.ApiResult;
import com.soil.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public ApiResult register(@RequestBody Admin admin){
        return adminService.register(admin);
    }

    @RequestMapping(value = "/admin", method = RequestMethod.PUT, produces = "application/json; charset=UTF-8")
    public ApiResult updateAdmin(@RequestHeader("id") int adminId,
                                @RequestBody Admin admin){
        admin.setId(adminId);
        return adminService.updateAdmin(admin);
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public ApiResult updateAdmin(@RequestHeader("id") int adminId){
        Admin admin = adminService.findById(adminId);
        if(admin == null){
            return new ApiResult(ErrorCode.NOT_FOUND);
        }
        return new ApiResult<>(ErrorCode.OK,admin);
    }

    @RequestMapping(value = "/admin_s/{id}", method = RequestMethod.PUT, produces = "application/json; charset=UTF-8")
    public ApiResult updateAdminS(@PathVariable int id,
                                 @RequestBody Admin admin){
        admin.setId(id);
        return adminService.updateAdmin(admin);
    }

    @RequestMapping(value = "/admins", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public ApiResult getAdmins(@RequestParam(value="id" ,required = false) Integer adminId,
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
        return adminService.selectAllAdmins(pageNo,pageSize,adminId,account,name);
    }

    @RequestMapping(value = "/admin/{id}",method = RequestMethod.DELETE, produces = "application/json; charset=UTF-8")
    public ApiResult deleteAdmin(@PathVariable int id){
        return adminService.deleteAdmin(id);
    }

    @RequestMapping(value = "/admin123",method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public ApiResult addAdmin(){
        Admin admin = new Admin();
        admin.setAccount("admin");
        admin.setName("admin");
        admin.setPassword("123456");
        return adminService.register(admin);
    }
}
