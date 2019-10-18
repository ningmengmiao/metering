package cn.bptop.metering.dao;

import cn.bptop.metering.pojo.Metering;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface MeteringMapper
{

    public List<Metering> findMetering(@Param(value = "userId") String userId, @Param(value = "statusCode") String[] statusCode);

    public void updateTool(@Param(value = "newTool") String newTool, @Param(value = "id") String id);

    public void updateValidity(@Param(value = "date") String date, @Param(value = "id") String id);

    public void updateStatusCode(@Param(value = "statusCode") String statusCode, @Param(value = "id") String id);

    public void updateStatus(@Param(value = "status") String status, @Param(value = "id") String id);

    public void addTool(@Param(value = "userId") String userId, @Param(value = "tool") String tool, @Param(value = "date") String date, @Param(value = "ddName") String ddName);
}
