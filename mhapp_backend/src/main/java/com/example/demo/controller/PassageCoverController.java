package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.Passage;
import com.example.demo.entity.PassageCover;
import com.example.demo.service.PassageCoverService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/passageCover")
public class PassageCoverController {
    @Resource
    PassageCoverService passageCoverService;
    String finalFileName;

    //添加至草稿箱
    @PostMapping("/upload")
    public Result upload(MultipartFile file, HttpServletRequest request) throws IOException {
        String fileName=file.getOriginalFilename();
        finalFileName=fileName;
        return Result.success();
    }
}
