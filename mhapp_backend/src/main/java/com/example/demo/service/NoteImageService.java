package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.NoteImageInfo;

import java.util.List;

public interface NoteImageService extends IService<NoteImageInfo> {
    void addNote(String fileName, String noteId);

    List<NoteImageInfo> findByNoteId(String noteId);
}
