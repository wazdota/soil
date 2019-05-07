package com.soil.info.mapper;

import com.soil.info.bean.SoilInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoilInfoMapper {

    List<SoilInfo> selectAll();

    int insert(SoilInfo record);

    int update(SoilInfo record);

    int delete(int id);
}
