package com.soil.info.controller;

import com.soil.info.bean.SoilInfo;
import com.soil.info.response.ApiResult;
import com.soil.info.service.SoilInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1")
public class SoilInfoController {

    private SoilInfoService soilInfoService;

    @Autowired
    public SoilInfoController(SoilInfoService soilInfoService){
        this.soilInfoService = soilInfoService;
    }

    @CrossOrigin
    @RequestMapping(value = "/infos", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public ApiResult getAllInfos(){
        return soilInfoService.getAllInfos();
    }

    @CrossOrigin
    @RequestMapping(value = "/info", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public ApiResult insertInfo(@RequestBody SoilInfo soilInfo){
        return soilInfoService.insertInfo(soilInfo);
    }

    @CrossOrigin
    @RequestMapping(value = "/info/{id}", method = RequestMethod.PUT, produces = "application/json; charset=UTF-8")
    public ApiResult insertInfo(@PathVariable int id, @RequestBody SoilInfo soilInfo){
        soilInfo.setId(id);
        return soilInfoService.updateInfo(soilInfo);
    }

    @CrossOrigin
    @RequestMapping(value = "/info/{id}", method = RequestMethod.DELETE, produces = "application/json; charset=UTF-8")
    public ApiResult deleteInfo(@PathVariable int id){
        return soilInfoService.deleteInfo(id);
    }
}
