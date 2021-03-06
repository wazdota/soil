package com.soil.sensor.mapper;

import com.soil.sensor.bean.Sensor;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("sensorMapper")
public interface SensorMapper {
    int deleteById(int id);

    int insert(Sensor record);

    Sensor selectById(int id);

    int update(Sensor record);

    int updateTH(Sensor record);

    List<Sensor> selectByUserId(int userId);

    List<Sensor> findById(@Param("id") int id, @Param("userId") int userId);

    List<Sensor> selectSensors(@Param("userId") int userId, @Param("name") String name);
}
