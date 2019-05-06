package com.soil.history.mapper;

import com.soil.history.bean.History;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryMapper {

    List<History> selectBySensorId(int sensorId);

    int insertHistoryNow(History record);
}
