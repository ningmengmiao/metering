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
   //查所有工具类型
   List<Metering> findMeteringName();

   //查同名所有型号
   List<Metering> findMeteringModel(@Param("meteringName") String meteringName);

   //以名称 型号查工具详情
   List<Metering> findMetering(@Param("meteringName") String meteringName, @Param("meteringModel") String meteringModel);

}
