package com.example.demo.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.NoteImageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoteImageMapper extends BaseMapper<NoteImageInfo> {
    List<NoteImageInfo> findByNoteId(@Param("noteId") String noteId);
}
