package com.soil.info.service;

import com.soil.info.bean.SoilInfo;
import com.soil.info.enums.ErrorCode;
import com.soil.info.mapper.SoilInfoMapper;
import com.soil.info.response.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoilInfoService {

    private SoilInfoMapper soilInfoMapper;

    @Autowired
    public SoilInfoService(SoilInfoMapper soilInfoMapper){
        this.soilInfoMapper = soilInfoMapper;
    }

    public ApiResult getAllInfos(){
        try{
            List<SoilInfo> list = soilInfoMapper.selectAll();
            return new ApiResult<>(ErrorCode.OK,list);
        }catch(DataAccessException e){
            return new ApiResult(ErrorCode.INVALID_REQUEST);
        }
    }

    public ApiResult insertInfo(SoilInfo record) {
        try{
            soilInfoMapper.insert(record);
            return new ApiResult(ErrorCode.CREATED);
        }catch (DataAccessException e) {
            return new ApiResult(ErrorCode.INVALID_REQUEST);
        }
    }

    public ApiResult deleteInfo(int id) {
        try{
            soilInfoMapper.delete(id);
            return new ApiResult(ErrorCode.NO_CONTENT);
        }catch (DataAccessException e) {
            return new ApiResult(ErrorCode.INVALID_REQUEST);
        }
    }

    public ApiResult updateInfo(SoilInfo record) {
        try{
            soilInfoMapper.update(record);
            return new ApiResult(ErrorCode.CREATED);
        }catch (DataAccessException e) {
            return new ApiResult(ErrorCode.INVALID_REQUEST);
        }
    }
}
