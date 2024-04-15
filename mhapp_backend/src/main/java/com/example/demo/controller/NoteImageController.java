package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.NoteImageInfo;
import com.example.demo.service.NoteImageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/noteImage")
public class NoteImageController {
    @Resource
    private NoteImageService noteImageService;

    @GetMapping("/{noteId}")
    public List<NoteImageInfo> findByNoteId(@PathVariable("noteId") String noteId){
        List<NoteImageInfo> imageList = noteImageService.findByNoteId(noteId);
        return imageList;
    }
}
