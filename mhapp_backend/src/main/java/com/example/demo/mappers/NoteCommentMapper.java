package com.example.demo.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.NoteComment;
import com.example.demo.vo.NoteCommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface NoteCommentMapper extends BaseMapper<NoteComment> {
    List<NoteCommentVo> findByNoteId(@Param("noteId") String noteId);
}
