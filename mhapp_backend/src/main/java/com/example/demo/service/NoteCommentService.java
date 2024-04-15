package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.NoteComment;
import com.example.demo.vo.NoteCommentVo;

import java.util.List;

public interface NoteCommentService extends IService<NoteComment> {
    NoteComment add(String noteId, String userId, String content);

    List<NoteCommentVo> findByNoteId(String noteId);
}
