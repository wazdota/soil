package com.soil.history.controller;

import com.soil.history.bean.History;
import com.soil.history.response.ApiResult;
import com.soil.history.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class HistoryController {

    private HistoryService historyService;

    @Autowired
    public HistoryController(HistoryService historyService){
        this.historyService = historyService;
    }

    @CrossOrigin
    @RequestMapping(value = "/histories", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public ApiResult getSensorHistoryBySensorId(@RequestParam("sensorId") int sensorId){
        return historyService.getSensorHistoryBySensorId(sensorId);
    }

    @CrossOrigin
    @RequestMapping(value = "/history", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public ApiResult addHistoryNow(@RequestBody History history){
        return historyService.insertHistoryNow(history);
    }
}
