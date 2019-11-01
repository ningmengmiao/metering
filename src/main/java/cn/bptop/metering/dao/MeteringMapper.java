package cn.bptop.metering.dao;

import cn.bptop.metering.pojo.Metering;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Mapper
public interface MeteringMapper
{
   List<Metering> findMeteringName();

   List<Metering> findMeteringModel(@Param("meteringName") String meteringName);

   List<Metering> findMetering(@Param("meteringName") String meteringName, @Param("meteringModel") String meteringModel);

}
