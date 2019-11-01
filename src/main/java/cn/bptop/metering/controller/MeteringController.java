package cn.bptop.metering.controller;

import cn.bptop.metering.dao.MeteringMapper;
import cn.bptop.metering.dao.MeteringRecordMapper;
import cn.bptop.metering.pojo.Metering;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static cn.bptop.metering.until.Json.getJson;
import static cn.bptop.metering.until.ding.getDepartment;

@Controller
public class MeteringController
{
    @Autowired
    MeteringRecordMapper meteringRecordMapper;
    @Autowired
    MeteringMapper meteringMapper;

    @ResponseBody
    @RequestMapping("/metering/findRecord")
    public String findTool(String userId)
    {
        return getJson(meteringRecordMapper.findRecord(userId));
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
        meteringRecordMapper.addRecord(meteringId, unifyId, meteringValidity, meteringRange, department, userId, ddName, manufacturingId, notes);
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
}
