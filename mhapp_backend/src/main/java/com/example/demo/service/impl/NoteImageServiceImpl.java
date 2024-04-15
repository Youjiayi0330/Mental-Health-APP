package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.NoteImageInfo;
import com.example.demo.mappers.NoteImageMapper;
import com.example.demo.service.NoteImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NoteImageServiceImpl extends ServiceImpl<NoteImageMapper, NoteImageInfo> implements NoteImageService {
    @Resource
    NoteImageMapper noteImageMapper;

    @Override
    public void addNote(String fileName, String noteId) {
        NoteImageInfo noteImageInfo = new NoteImageInfo();
        noteImageInfo.setId(fileName);
        noteImageInfo.setNoteId(noteId);
        noteImageMapper.insert(noteImageInfo);
    }

    @Override
    public List<NoteImageInfo> findByNoteId(String noteId) {
        List<NoteImageInfo> noteImageList = noteImageMapper.findByNoteId(noteId);
        return noteImageList;
    }
}
