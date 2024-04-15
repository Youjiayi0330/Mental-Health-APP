package com.example.demo.controller;

import cn.hutool.core.io.FileUtil;
import com.example.demo.common.Result;
import com.example.demo.entity.NoteInfo;
import com.example.demo.entity.Paper;
import com.example.demo.service.NoteImageService;
import com.example.demo.service.NoteInfoService;
import com.example.demo.vo.NoteInfoVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/noteInfo")
public class NoteInfoController {
    private static final String BASE_PATH = "D:/OORPhoto/noteImages/";
    private static final String COVER_BASE_PATH = "D:/OORPhoto/noteCover/";

    @Resource
    NoteInfoService noteInfoService;
    @Resource
    NoteImageService noteImageService;

    @GetMapping("/all")
    public List<NoteInfoVo> findAllNotes(){
        return noteInfoService.findAllNotes();
    }

    @PostMapping("/addNote")
    public Result addNote(MultipartFile uploadCover, String noteId, String userId, String noteName, String content) throws IOException {
        String coverId = noteId+".jpg";
        String location = COVER_BASE_PATH + coverId;
        //将封面图片存入硬盘中
        FileUtil.writeBytes(uploadCover.getBytes(),location);
        //添加笔记信息
        noteInfoService.addNote(noteId, noteName, userId, content, coverId);
        return Result.success();
    }

    @PostMapping("/uploadImages")
    public Result uploadImages(MultipartFile uploadfile, String name, String noteId) throws IOException {
        String fileName = name+System.currentTimeMillis()+".jpg";
        String location = BASE_PATH+fileName;
        //将笔记图片存入硬盘中
        FileUtil.writeBytes(uploadfile.getBytes(), location);
        //添加图片
        noteImageService.addNote(fileName, noteId);
        return Result.success();
    }

    //根据id获得笔记信息
    @GetMapping("/{id}")
    public Result<NoteInfoVo> findById(@PathVariable("id") String id){
        NoteInfoVo noteInfoVo = noteInfoService.findById(id);
        return Result.success(noteInfoVo);
    }

    @GetMapping("/findAllNote")
    public Result<PageInfo<NoteInfoVo>> findAll(@RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "3") Integer pageSize){
        return Result.success(noteInfoService.findAll(pageNum,pageSize));
    }

    @DeleteMapping("/{noteId}")
    public Result delete(@PathVariable(value = "noteId")String noteId){
        noteInfoService.delete(noteId);
        return Result.success();
    }

    @GetMapping("/findByUserId/{userId}")
    public List<NoteInfoVo> findByUserId(@PathVariable("userId") String userId){
        return noteInfoService.findByUserId(userId);
    }

    @GetMapping("/findPopNote")
    public Result<String> findPopNote(){
        return Result.success(noteInfoService.findPopNote()) ;
    }
}
