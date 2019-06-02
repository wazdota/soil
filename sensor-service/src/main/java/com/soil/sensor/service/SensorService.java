package com.soil.sensor.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.soil.sensor.bean.Sensor;
import com.soil.sensor.enums.ErrorCode;
import com.soil.sensor.mapper.SensorMapper;
import com.soil.sensor.response.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorService {

    private SensorMapper sensorMapper;

    @Autowired
    public SensorService(SensorMapper sensorMapper){
        this.sensorMapper = sensorMapper;
    }

    public ApiResult deleteSensor(int id) {
        try{
            sensorMapper.deleteById(id);
            return new ApiResult(ErrorCode.NO_CONTENT);
        }catch (DataAccessException e){
            return new ApiResult(ErrorCode.INVALID_REQUEST);
        }
    }

    public Sensor selectSensor(int id) {
        try{
            return sensorMapper.selectById(id);
        }catch (DataAccessException e){
            return null;
        }
    }

    public List<Sensor> selectSensors(int userId) {
        return sensorMapper.selectByUserId(userId);
    }

    public ApiResult updateSensor(Sensor sensor) {
        try{
            sensorMapper.update(sensor);
            return new ApiResult(ErrorCode.CREATED);
        }catch (DataAccessException e){
            return new ApiResult(ErrorCode.INVALID_REQUEST);
        }
    }

    public ApiResult insertSensor(Sensor sensor) {
        try{
            sensorMapper.insert(sensor);
            return new ApiResult(ErrorCode.CREATED);
        }catch (DataAccessException e){
            return new ApiResult(ErrorCode.INVALID_REQUEST);
        }
    }

    public ApiResult updateSensorTH(Sensor sensor) {
        try{
            sensorMapper.updateTH(sensor);
            Sensor result = sensorMapper.selectById(sensor.getId());
            return new ApiResult<>(ErrorCode.CREATED,result);
        }catch (DataAccessException e){
            return new ApiResult(ErrorCode.INVALID_REQUEST);
        }
    }

    public ApiResult selectAllSensors(int pageNo, int pageSize, Integer id, String name, int userId){
        if(id != null){
            try{
                PageHelper.startPage(pageNo, pageSize);
                List<Sensor> list = sensorMapper.findById(id,userId);
                PageInfo<Sensor> page = new PageInfo<>(list);
                return new ApiResult<>(ErrorCode.OK,page);
            }catch (DataAccessException e) {
                return new ApiResult(ErrorCode.INVALID_REQUEST);
            }
        }
        else {
            try{
                PageHelper.startPage(pageNo,pageSize);
                List<Sensor> list = sensorMapper.selectSensors(userId,name);
                PageInfo<Sensor> page = new PageInfo<>(list);
                return new ApiResult<>(ErrorCode.OK,page);
            }catch (DataAccessException e) {
                return new ApiResult(ErrorCode.INVALID_REQUEST);
            }
        }
    }
}
