package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.NoteComment;
import com.example.demo.service.NoteCommentService;
import com.example.demo.vo.NoteCommentVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/noteComment")
public class NoteCommentController {
    @Resource
    NoteCommentService noteCommentService;

    @PostMapping
    public Result<NoteComment> add(String noteId, String userId, String content){
        NoteComment noteComment = noteCommentService.add(noteId,userId,content);
        return Result.success(noteComment);
    }

    @GetMapping("/findByNoteId/{noteId}")
    public List<NoteCommentVo> findByNoteId(@PathVariable("noteId") String noteId){
        List<NoteCommentVo> CommentList = noteCommentService.findByNoteId(noteId);
        return CommentList;
    }
}
