package cn.bptop.metering.controller;

import cn.bptop.metering.dao.UserMapper;
import cn.bptop.metering.pojo.User;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static cn.bptop.metering.until.Json.getJson;
import static cn.bptop.metering.until.ding.getDdUserId;
import static cn.bptop.metering.until.ding.getUser;

@Controller
public class UserController
{
    @Autowired
    UserMapper userMapper;

    @RequestMapping("/authCode")
    @ResponseBody
    public String authCode(String authCode) throws ApiException
    {
        User user = getUser(getDdUserId(authCode));
        return getJson(user);
    }
}
