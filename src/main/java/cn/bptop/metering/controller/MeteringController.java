package cn.bptop.metering.controller;

import cn.bptop.metering.dao.MeteringRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static cn.bptop.metering.until.Json.getJson;

@Controller
public class MeteringController
{
    @Autowired
    MeteringRecordMapper meteringRecordMapper;

    @ResponseBody
    @RequestMapping("/metering/findTool")
    public String findTool(String userId)
    {
        return getJson(meteringRecordMapper.findTool(userId));
    }
//    @Autowired
//    MeteringMapper meteringMapper;
//    @Autowired
//    MeteringService meteringService;
//
//    //查询
//    @RequestMapping("/findMetering")
//    @ResponseBody
//    public String findMetering(String userId, String[] statusCode)
//    {
//        List<Metering> list = meteringMapper.findMetering(userId, statusCode);
//        return getJson(list);
//    }
//
//    @RequestMapping("/updateNewTool")
//    @ResponseBody
//    public void updateNewmTool(String newTool, String id)
//    {
//        meteringMapper.updateTool(newTool, id);
//    }
//
//    @RequestMapping("/updateValidity")
//    @ResponseBody
//    public void updateValidity(String date, String id)
//    {
//        meteringMapper.updateValidity(date, id);
//    }
//
//    //修改状态码
//    @RequestMapping("/updateStatusCode")
//    @ResponseBody
//    public void updateStatusCode(String statusCode, String id)
//    {
//        meteringMapper.updateStatusCode(statusCode, id);
//        meteringService.updateStatus(statusCode, id);
//    }
//
//    //    添加
//    @RequestMapping("/addTool")
//    @ResponseBody
//    public void addTool(String userId, String ddName, String tool, String date)
//    {
//        meteringMapper.addTool(userId, ddName, tool, date);
//    }
}
