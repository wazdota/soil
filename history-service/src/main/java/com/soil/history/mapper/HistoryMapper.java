package com.soil.history.mapper;

import com.soil.history.bean.History;
import com.soil.history.bean.HumAvg;
import com.soil.history.bean.TempAvg;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryMapper {

    List<History> selectBySensorId(int sensorId);

    int insertHistoryNow(History record);

    int deleteBySensorId(int sensorId);

    List<TempAvg> selectTempAvg(int sensorId);

    List<HumAvg> selectHumAvg(int sensorId);
}
