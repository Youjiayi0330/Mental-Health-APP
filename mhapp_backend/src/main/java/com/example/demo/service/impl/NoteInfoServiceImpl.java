package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.NoteComment;
import com.example.demo.entity.NoteImageInfo;
import com.example.demo.entity.NoteInfo;
import com.example.demo.entity.Paper;
import com.example.demo.mappers.NoteCommentMapper;
import com.example.demo.mappers.NoteImageMapper;
import com.example.demo.mappers.NoteInfoMapper;
import com.example.demo.service.NoteInfoService;
import com.example.demo.vo.NoteCommentVo;
import com.example.demo.vo.NoteInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NoteInfoServiceImpl extends ServiceImpl<NoteInfoMapper, NoteInfo> implements NoteInfoService {
    @Resource
    NoteInfoMapper noteInfoMapper;
    @Resource
    NoteCommentMapper noteCommentMapper;
    @Resource
    NoteImageMapper noteImageMapper;

    public static final String BASE_PATH= "D:/OORPhoto/noteImages/";

    @Override
    public List<NoteInfoVo> findAllNotes() {
        return noteInfoMapper.findAllNotes();
    }

    @Override
    public void addNote(String noteId, String noteName, String userId, String content, String coverId) {
        NoteInfo noteInfo = new NoteInfo();
        noteInfo.setId(noteId);
        noteInfo.setName(noteName);
        noteInfo.setUserId(userId);
        noteInfo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        noteInfo.setContent(content);
        noteInfo.setCoverId(coverId);
        noteInfoMapper.insert(noteInfo);
    }

    @Override
    public NoteInfoVo findById(String id) {
        return noteInfoMapper.findById(id);
    }

    @Override
    public PageInfo<NoteInfoVo> findAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize,"");
        List<NoteInfoVo> list = noteInfoMapper.findAllNotes();
        return PageInfo.of(list);
    }

    @Override
    public void delete(String noteId) {
        //删除相关评论
        List<NoteCommentVo> commentList = noteCommentMapper.findByNoteId(noteId);
        for(int i=0; i<commentList.size(); i++){
            noteCommentMapper.deleteById(commentList.get(i));
        }
        //删除相关图片
        List<NoteImageInfo> noteImageInfos = noteImageMapper.findByNoteId(noteId);
        for(int i=0; i<noteImageInfos.size(); i++){
            String id = noteImageInfos.get(i).getId();
            String imagePath = BASE_PATH + id;
            File file = new File(imagePath);
            file.delete();
            noteImageMapper.deleteById(noteImageInfos.get(i));
        }
        //删除帖子
        NoteInfo noteInfo = noteInfoMapper.findById(noteId);
        noteInfoMapper.deleteById(noteInfo);
    }

    @Override
    public List<NoteInfoVo> findByUserId(String userId) {
        return noteInfoMapper.findByUserId(userId);
    }

    @Override
    public String findPopNote() {
        List<NoteInfoVo> list = noteInfoMapper.findAllNotes();
        Integer count = noteCommentMapper.findByNoteId(list.get(0).getId()).size();
        String noteName = list.get(0).getName();
        for(int i=1;i<list.size();i++){
            List<NoteCommentVo> noteComments = noteCommentMapper.findByNoteId(list.get(i).getId());
            if(noteComments.size()>count){
                count = noteComments.size();
                noteName = list.get(i).getName();
            }
        }
        return noteName;
    }
}
