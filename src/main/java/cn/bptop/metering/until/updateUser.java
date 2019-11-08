package cn.bptop.metering.until;

import cn.bptop.metering.dao.UserMapper;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiUserGetDeptMemberRequest;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiUserGetDeptMemberResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static cn.bptop.metering.until.Ding.getAccess_token;
import static cn.bptop.metering.until.Ding.getDdUser;
import static cn.bptop.metering.until.Json.getJson;

@Component
public class updateUser
{

    @Autowired
    UserMapper userMapper;

    public int updateUser() throws ApiException
    {
        userMapper.deleteUser();
        int counter = 0;
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setId("");
        request.setHttpMethod("GET");
        OapiDepartmentListResponse response = client.execute(request, getAccess_token());
        for ( int i = 1; i < response.getDepartment().size(); i++ )
        {
            DingTalkClient _client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getDeptMember");
            OapiUserGetDeptMemberRequest req = new OapiUserGetDeptMemberRequest();
            req.setDeptId(response.getDepartment().get(i).getId().toString());
            req.setHttpMethod("GET");
            OapiUserGetDeptMemberResponse rsp = _client.execute(req, getAccess_token());
            for ( int j = 0; j < rsp.getUserIds().size(); j++ )
            {
                OapiUserGetResponse user = getDdUser(rsp.getUserIds().get(j));
                System.out.println(getJson(rsp.getUserIds().get(j)));
                String userId = user.getJobnumber();
                String ddName = user.getName();
                String ddUserId = user.getUserid();
                userMapper.addUser(userId, ddUserId, ddName);
                System.out.println("插入" + userId + ddName + ddUserId);
                counter++;
            }
        }
        return counter;
    }
}
