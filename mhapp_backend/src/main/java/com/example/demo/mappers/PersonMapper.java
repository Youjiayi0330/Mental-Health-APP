package com.example.demo.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface PersonMapper extends BaseMapper<Person> {
    Person getPerson(@Param("loginId") String loginId, @Param("loginPwd") String loginPwd);

    String findNameById(@Param("id")String id);

    List<Person> findAll();

    Person findById(@Param("id") String id);

    void update(@Param("id") String id, @Param("nickname") String nickname, @Param("pwd") String pwd);

    List<Person> findDoctor();
}
