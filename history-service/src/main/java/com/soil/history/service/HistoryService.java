package com.soil.history.service;

import com.soil.history.bean.History;
import com.soil.history.enums.ErrorCode;
import com.soil.history.mapper.HistoryMapper;
import com.soil.history.response.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

    private HistoryMapper historyMapper;

    @Autowired
    public HistoryService(HistoryMapper historyMapper){
        this.historyMapper = historyMapper;
    }

    public ApiResult getSensorHistoryBySensorId(int sensorId){
        try{
            List<History> list = historyMapper.selectBySensorId(sensorId);
            return new ApiResult<>(ErrorCode.OK,list);
        }catch(DataAccessException e){
            return new ApiResult(ErrorCode.INVALID_REQUEST);
        }
    }

    public ApiResult insertHistoryNow(History history){
        try {
            historyMapper.insertHistoryNow(history);
            return new ApiResult(ErrorCode.CREATED);
        }catch(DataAccessException e){
            return new ApiResult(ErrorCode.INVALID_REQUEST);
        }
    }
}
