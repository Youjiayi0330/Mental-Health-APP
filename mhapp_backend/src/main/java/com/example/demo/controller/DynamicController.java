package com.example.demo.controller;

import cn.hutool.core.net.NetUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.common.Result;
import com.example.demo.entity.Passage;
import com.example.demo.service.PaperService;
import com.example.demo.vo.PaperDetail;
import com.example.demo.vo.SingleQuestion;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dynamic")
public class DynamicController {
    @Resource
    PaperService paperService;

    //添加至已发送
    @PostMapping
    public Result addPaper(@RequestBody PaperDetail paperDetail) throws UnknownHostException {
//        Long time = System.currentTimeMillis();
//        InetAddress inetAddress = InetAddress.getLocalHost();
//        String macAddress = NetUtil.getMacAddress(inetAddress);
//        String paperId = macAddress + time.toString();
//
//        JSONArray passageArray= JSON.parseArray(paperDetail.getValue());
//        for(int i=0; i<passageArray.size();i++){
//            Map map = JSON.parseObject(passageArray.get(i).toString(), Map.class);
//            System.out.println(map.get("question"));
//            JSONArray array = (JSONArray) map.get("answerList");
//            for(int j=0;j<array.size();j++){
//                Map answerMap = JSON.parseObject(array.get(j).toString(), Map.class);
//                System.out.println(answerMap.get("answer"));
//                System.out.println(answerMap.get("score"));
//            }
//        }
//
        return Result.success();
    }
}
