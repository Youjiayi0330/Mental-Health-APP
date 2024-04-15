package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Person;
import com.example.demo.vo.ChatListVo;
import com.example.demo.vo.NoteInfoVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface PersonService extends IService<Person> {
    String add(Person person);

    Person login(String loginId, String loginPwd);

    String findNameById(String id);

    List<ChatListVo> getChatFriends(String personId);

    PageInfo<Person> findAll(Integer pageNum, Integer pageSize);

    void resetPwd(String id);

    void update(String id, String nickname, String pwd);

    PageInfo<Person> findDoctor(Integer pageNum, Integer pageSize);

    void checkPass(String id);

    void delete(String id);
}
