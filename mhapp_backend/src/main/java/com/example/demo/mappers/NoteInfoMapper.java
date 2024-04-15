package com.example.demo.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.NoteInfo;
import com.example.demo.vo.NoteInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoteInfoMapper extends BaseMapper<NoteInfo> {
    List<NoteInfoVo> findAllNotes();

    NoteInfoVo findById(@Param("id") String id);

    List<NoteInfoVo> findByUserId(@Param("userId") String userId);
}
