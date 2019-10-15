package cn.bptop.metering.controller;

import cn.bptop.metering.dao.MeteringMapper;
import cn.bptop.metering.pojo.Metering;
import cn.bptop.metering.service.MeteringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static cn.bptop.metering.until.Json.getJson;

@Controller
public class MeteringController
{
    @Autowired
    MeteringMapper meteringMapper;
    @Autowired
    MeteringService meteringService;

    //查询
    @RequestMapping("/findMetering")
    @ResponseBody
    public String findMetering(String name, String[] statusCode)
    {
        List<Metering> list = meteringMapper.findMetering(name, statusCode);
        return getJson(list);
    }

    @RequestMapping("/updateNewTool")
    @ResponseBody
    public void updateNewmTool(String newTool, String id)
    {
        meteringMapper.updateTool(newTool, id);
    }

    @RequestMapping("/updateValidity")
    @ResponseBody
    public void updateValidity(String date, String id)
    {
        meteringMapper.updateValidity(date, id);
    }

    //修改状态码
    @RequestMapping("/updateStatusCode")
    @ResponseBody
    public void updateStatusCode(String statusCode, String id)
    {
        meteringMapper.updateStatusCode(statusCode, id);
        meteringService.updateStatus(statusCode, id);
    }

    //    添加
    @RequestMapping("/addTool")
    @ResponseBody
    public void addTool(String tool, String date, String name)
    {
        meteringMapper.addTool(tool, date, name);
    }
}
