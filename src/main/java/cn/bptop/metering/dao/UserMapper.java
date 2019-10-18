package cn.bptop.metering.dao;

import cn.bptop.metering.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper
{
    public void addUser(@Param("userId") String userId, @Param("ddUserId") String ddUserId, @Param("ddName") String ddName);

    public User findUser(@Param("ddName") String ddName);
}
