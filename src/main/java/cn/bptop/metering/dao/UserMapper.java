package cn.bptop.metering.dao;

import cn.bptop.metering.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper
{
    public void addUser(@Param("id") String id, @Param("ddId") String ddId, @Param("name") String name);

    public User findUser(@Param("name") String name);
}
