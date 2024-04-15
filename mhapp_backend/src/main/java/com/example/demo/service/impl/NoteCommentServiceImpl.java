package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.NoteComment;
import com.example.demo.mappers.NoteCommentMapper;
import com.example.demo.service.NoteCommentService;
import com.example.demo.vo.NoteCommentVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NoteCommentServiceImpl extends ServiceImpl<NoteCommentMapper, NoteComment> implements NoteCommentService {
    @Resource
    NoteCommentMapper noteCommentMapper;

    @Override
    public NoteComment add(String noteId, String userId, String content) {
        NoteComment noteComment = new NoteComment();
        String id = userId + System.currentTimeMillis();
        noteComment.setId(id);
        noteComment.setNoteId(noteId);
        noteComment.setUserId(userId);
        noteComment.setContent(content);
        noteComment.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        noteCommentMapper.insert(noteComment);
        return noteComment;
    }

    @Override
    public List<NoteCommentVo> findByNoteId(String noteId) {
        List<NoteCommentVo> CommentList = noteCommentMapper.findByNoteId(noteId);
        return CommentList;
    }
}
