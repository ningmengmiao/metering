package cn.bptop.metering.dao;

import cn.bptop.metering.pojo.Metering;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface MeteringRecordMapper
{
    List<Metering> findTool(@Param("userId") String userId);
}
