package cn.bptop.metering.controller;

import cn.bptop.metering.dao.MeteringMapper;
import cn.bptop.metering.dao.MeteringRecordMapper;
import cn.bptop.metering.dao.UserMapper;
import cn.bptop.metering.pojo.Metering;
import cn.bptop.metering.pojo.MeteringRecordVO;
import cn.bptop.metering.pojo.User;
import cn.bptop.metering.service.EmailServer;
import cn.bptop.metering.service.MeteringService;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiUserSimplelistRequest;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiUserSimplelistResponse;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

import static cn.bptop.metering.until.Ding.getAccess_token;
import static cn.bptop.metering.until.Ding.getDepartment;
import static cn.bptop.metering.until.Ding.sendCardMsg;
import static cn.bptop.metering.until.FileTool.deleteFile;
import static cn.bptop.metering.until.Json.getJson;

@Controller
public class MeteringController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MeteringController.class);
    @Autowired
    MeteringRecordMapper meteringRecordMapper;
    @Autowired
    MeteringMapper meteringMapper;
    @Autowired
    MeteringService meteringService;
    @Autowired
    EmailServer emailServer;
    @Autowired
    UserMapper userMapper;

    @ResponseBody
    @RequestMapping("/metering/findRecord")
    public String findTool(String userId)
    {
        return getJson(meteringRecordMapper.findRecord(userId, ""));
    }

    @ResponseBody
    @RequestMapping("/metering/findMeteringName")
    public String findMeteringName()
    {
        List<Metering> meteringList = meteringMapper.findMeteringName();
        String[] meteringArray = new String[meteringList.size()];
        for ( int i = 0; i < meteringList.size(); i++ )
        {
            meteringArray[i] = meteringList.get(i).getMeteringName();
        }
        return getJson(meteringArray);
    }

    @ResponseBody
    @RequestMapping("/metering/findMeteringModel")
    public String findMeteringModel(String meteringName)
    {
        List<Metering> meteringList = meteringMapper.findMeteringModel(meteringName);
        String[] meteringArray = new String[meteringList.size()];
        for ( int i = 0; i < meteringList.size(); i++ )
        {
            meteringArray[i] = meteringList.get(i).getMeteringModel();
        }
        return getJson(meteringArray);
    }

    @ResponseBody
    @RequestMapping("/metering/findMetering")
    public String findMetering(String meteringName, String meteringModel)
    {
        return getJson(meteringMapper.findMetering(meteringName, meteringModel));
    }

    @ResponseBody
    @RequestMapping("/metering/addRecord")
    public void addRecord(String meteringId, String unifyId, String meteringValidity, String meteringRange, String departmentId, String userId, String ddName, String manufacturingId, String notes) throws ApiException
    {
        String department = getDepartment(departmentId).getName();
        meteringRecordMapper.addRecord(meteringId, unifyId, "", meteringValidity, meteringRange, department, userId, ddName, manufacturingId, "2", notes, "");
    }

    @ResponseBody
    @RequestMapping("/metering/updateValidity")
    public void updateValidity(String meteringValidity, String meteringRecordId)
    {
        meteringRecordMapper.updateValidity(meteringValidity, meteringRecordId);
    }

    @ResponseBody
    @RequestMapping("/metering/updateStatus")
    public void updateStatus(String meteringStatus, String meteringRecordId)
    {
        meteringRecordMapper.updateStatus(meteringStatus, meteringRecordId);
    }

    @ResponseBody
    @RequestMapping("/metering/updateNotes")
    public void updateNotes(String notes, String meteringRecordId)
    {
        meteringRecordMapper.updateNotes(notes, meteringRecordId);
    }

    @ResponseBody
    @RequestMapping("/metering/getDepartments")
    public String getDepartments() throws ApiException
    {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setId("1");
        request.setHttpMethod("GET");
        OapiDepartmentListResponse response = client.execute(request, getAccess_token());
        return getJson(response.getDepartment());
    }

    @ResponseBody
    @RequestMapping("/metering/getUserList")
    public String getUserList(String department) throws ApiException
    {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/simplelist");
        OapiUserSimplelistRequest request = new OapiUserSimplelistRequest();
        request.setDepartmentId(Long.valueOf(department));
        request.setHttpMethod("GET");
        OapiUserSimplelistResponse response = client.execute(request, getAccess_token());
        return getJson(response);
    }

    @ResponseBody
    @RequestMapping("/metering/makeOver")
    public void makeOver(String makeOverUser, String makeOverUserId, String department, String meteringRecordId) throws ApiException
    {
        List<MeteringRecordVO> record = meteringRecordMapper.findRecord("", meteringRecordId);
        User user = userMapper.findUser("", makeOverUserId, "");
        meteringRecordMapper.makeOver(makeOverUser, user.getUserId(), department, meteringRecordId);
        sendCardMsg(makeOverUserId, "计量工具转让通知", record.get(0).getMeteringRecord().getDdName() + "将计量工具 **" + record.get(0).getMetering().getMeteringName() + "-" + record.get(0).getMeteringRecord().getUnifyId() + "** 的所有权移交给您，请查验。", "eapp://pages/index/index");
    }

    @ResponseBody
    @RequestMapping("/metering/sendMail")
    public String sendMail(String to)
    {
        String file = null;
        try
        {
            file = meteringService.exportExcel();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        String mp = emailServer.sendEmail(file, to);
        deleteFile(file);
        LOGGER.info("向" + to + "发送邮件" + mp.toString());
        return getJson(mp);
    }
}
