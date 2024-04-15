package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.NoteInfo;
import com.example.demo.vo.NoteInfoVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface NoteInfoService extends IService<NoteInfo> {
    List<NoteInfoVo> findAllNotes();

    void addNote(String noteId, String noteName, String userId, String content, String coverId);

    NoteInfoVo findById(String id);

    PageInfo<NoteInfoVo> findAll(Integer pageNum, Integer pageSize);

    void delete(String noteId);

    List<NoteInfoVo> findByUserId(String userId);

    String findPopNote();
}
