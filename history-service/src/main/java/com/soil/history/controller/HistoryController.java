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

    @RequestMapping(value = "/histories", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public ApiResult getSensorHistoryBySensorId(@RequestParam("sensorId") int sensorId){
        return historyService.getSensorHistoryBySensorId(sensorId);
    }

    @RequestMapping(value = "/history", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public ApiResult addHistoryNow(@RequestBody History history){
        return historyService.insertHistoryNow(history);
    }

    @RequestMapping(value = "/history/{sensorId}", method = RequestMethod.DELETE, produces = "application/json; charset=UTF-8")
    public ApiResult deleteHistory(@PathVariable int sensorId){
        return historyService.deleteBySensorId(sensorId);
    }

    @RequestMapping(value = "/avg_temp", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public ApiResult getAvgTemp(@RequestParam("sensorId") int sensorId){
        return historyService.getTempAvg(sensorId);
    }

    @RequestMapping(value = "/avg_hum", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public ApiResult getAvgHum(@RequestParam("sensorId") int sensorId){
        return historyService.getHumAvg(sensorId);
    }
}
