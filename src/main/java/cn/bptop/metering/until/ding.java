package cn.bptop.metering.until;

import cn.bptop.metering.pojo.User;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.mysql.cj.x.json.JsonArray;
import com.taobao.api.ApiException;

/**
 * 钉钉API
 * 获取令牌及用户信息
 */

public class ding
{

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

    public static User getUser(String authCode) throws ApiException
    {
        User user = new User();
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getuserinfo");
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(authCode);
        request.setHttpMethod("GET");
        OapiUserGetuserinfoResponse response = client.execute(request, getAccess_token());
        //
        DingTalkClient _client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest _request = new OapiUserGetRequest();
        _request.setUserid(response.getUserid());
        _request.setHttpMethod("GET");
        OapiUserGetResponse _response = _client.execute(_request, getAccess_token());
        user.setDdUserId(response.getUserid());
        user.setDdName(_response.getName());
        user.setId(_response.getJobnumber());
        return user;
    }

    //发送钉钉消息
    public static OapiMessageCorpconversationAsyncsendV2Request getMsgRequest(String userid) throws ApiException
    {
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setUseridList(userid);
        request.setAgentId(301470841L);
        request.setToAllUser(false);
        //
//        msg.setMsgtype("text");
//        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
//        msg.getText().setContent(text);
//        request.setMsg(msg);
        return request;
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
        msg.getActionCard().setSingleUrl("eapp:/ /pages/index/index");
        msg.setMsgtype("action_card");
        request.setHttpMethod("GET");
        request.setMsg(msg);
        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, getAccess_token());
    }
}
