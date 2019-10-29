package cn.bptop.metering.controller;

import com.dingtalk.api.response.OapiUserGetResponse;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static cn.bptop.metering.until.Json.getJson;
import static cn.bptop.metering.until.ding.getDdUser;
import static cn.bptop.metering.until.ding.getDdUserId;

@Controller
public class UserController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @ResponseBody
    @RequestMapping("/metering/authCode")
    public String authCode(String authCode) throws ApiException
    {
        OapiUserGetResponse response = getDdUser(getDdUserId(authCode));
        LOGGER.info(response.getName() + authCode);
        return getJson(response);
    }
}
