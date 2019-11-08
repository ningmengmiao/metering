package cn.bptop.metering.controller;

import cn.bptop.metering.until.updateUser;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static cn.bptop.metering.until.Ding.getDdUser;
import static cn.bptop.metering.until.Ding.getDdUserId;
import static cn.bptop.metering.until.Json.getJson;

@Controller
public class UserController
{
    @Autowired
    updateUser updateUser;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @ResponseBody
    @RequestMapping("/metering/authCode")
    public String authCode(String authCode) throws ApiException
    {
        OapiUserGetResponse response = getDdUser(getDdUserId(authCode));
        LOGGER.info(response.getName() + authCode);
        return getJson(response);
    }

    @ResponseBody
    @RequestMapping("/metering/updateUser")
    public int updateUser() throws ApiException
    {
        return updateUser.updateUser();
    }
}
