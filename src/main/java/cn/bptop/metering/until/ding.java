package cn.bptop.metering.until;

import cn.bptop.metering.pojo.User;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;

import com.taobao.api.ApiException;

/**
 * 钉钉API
 * 获取令牌及用户信息
 */

public class ding
{
    //获取令牌
    public static String getAccess_token() throws ApiException
    {
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey("dingj84o3tsgywd2f8bj");
        request.setAppsecret("TKZCtUnpFyaROqHU2Y6n_kfZkU9XjlTNnxt7JNmdFZ0Vjb4qNHj2_QnIpbQNNGn7");
        request.setHttpMethod("GET");
        OapiGettokenResponse response = client.execute(request);
        return response.getAccessToken();
    }

    //企业内部免登
    public static String getDdUserId(String authCode) throws ApiException
    {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getuserinfo");
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(authCode);
        request.setHttpMethod("GET");
        OapiUserGetuserinfoResponse response = client.execute(request, getAccess_token());
        //
        return response.getUserid();
    }

    //获取钉钉用户详情
    public static OapiUserGetResponse getDdUser(String userId) throws ApiException
    {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid(userId);
        request.setHttpMethod("GET");
        OapiUserGetResponse response = client.execute(request, getAccess_token());
        return response;
    }

    //返回User实体类
    public static User getUser(String userId) throws ApiException
    {
        User user = new User();
        OapiUserGetResponse response;
        response = getDdUser(userId);
        user.setDdUserid(response.getUserid());
        user.setDdName(response.getName());
        user.setUserId(response.getJobnumber());
        return user;
    }

    //发送钉钉消息
    public static OapiMessageCorpconversationAsyncsendV2Request getMsgRequest(String userid)
    {
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setUseridList(userid);
        request.setAgentId(301470841L);
        request.setToAllUser(false);
        return request;
    }

    //    获取部门详情
    public static OapiDepartmentGetResponse getDepartment(String id) throws ApiException
    {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/get");
        OapiDepartmentGetRequest request = new OapiDepartmentGetRequest();
        request.setId(id);
        request.setHttpMethod("GET");
        OapiDepartmentGetResponse response = client.execute(request, getAccess_token());
        return response;
    }

    public static void sendCardMsg(String userid, String title, String markdown) throws ApiException
    {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = getMsgRequest(userid);
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
        msg.getActionCard().setTitle(title);
        msg.getActionCard().setMarkdown(markdown);
        msg.getActionCard().setSingleTitle("查看详情");
        msg.getActionCard().setSingleUrl("eapp://pages/index/index");
        msg.setMsgtype("action_card");
        request.setHttpMethod("GET");
        request.setMsg(msg);
        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, getAccess_token());
    }
}
