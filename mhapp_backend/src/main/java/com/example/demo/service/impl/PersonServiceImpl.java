package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.ChatRecord;
import com.example.demo.entity.DoctorPrivacy;
import com.example.demo.entity.Person;
import com.example.demo.mappers.ChatRecordMapper;
import com.example.demo.mappers.DoctorPrivacyMapper;
import com.example.demo.mappers.PersonMapper;
import com.example.demo.service.PersonService;
import com.example.demo.vo.ChatListVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements PersonService {
    @Resource
    private PersonMapper personMapper;
    @Resource
    private ChatRecordMapper chatRecordMapper;
    @Resource
    private DoctorPrivacyMapper doctorPrivacyMapper;

    public String add(Person person){
        try{
            personMapper.insert(person);
        }catch (Exception e){
            e.printStackTrace();
            return "注册异常";
        }
        return "注册成功";
    }

    @Override
    public Person login(String loginId, String loginPwd) {
        Person person = personMapper.getPerson(loginId,loginPwd);
        if(person == null)
            return null;
        return person;
    }

    public String findNameById(String id){
        String personName = personMapper.findNameById(id);
        return personName;
    }

    @Override
    /**
     * @personId 该用户Id
     */
    public List<ChatListVo> getChatFriends(String personId) {
        ArrayList<ChatListVo> list = new ArrayList<>();
        //获得所有相关的聊天记录
        List<ChatRecord> chatRecords = chatRecordMapper.getChatRecordByPersonId(personId);
        //保证记录不重复
        HashSet<String> friendId = new HashSet<>();
        //从后往前查，获得最新消息
        for(int i=chatRecords.size()-1;i>=0;i--){
            ChatRecord chatRecord = chatRecords.get(i);
            //去重
            if(friendId.contains(chatRecord.getFriendId()) || friendId.contains(chatRecord.getPersonId()))
                continue;
            ChatListVo chatListVo = new ChatListVo();
            chatListVo.setCreateTime(chatRecord.getCreateTime());
            chatListVo.setMessage(chatRecord.getMessage());
            //消息是我发的
            if(chatRecord.getPersonId().equals(personId)){
                chatListVo.setHasRead(1);
                friendId.add(chatRecord.getFriendId());
                chatListVo.setPersonId(chatRecord.getFriendId());
                String friendName = personMapper.findNameById(chatRecord.getFriendId());
                chatListVo.setPersonName(friendName);
                list.add(chatListVo);
            } else { //别人给我发的消息
                chatListVo.setHasRead(chatRecord.getHasRead());
                friendId.add(chatRecord.getPersonId());
                chatListVo.setPersonId(chatRecord.getPersonId());
                String friendName = personMapper.findNameById(chatRecord.getPersonId());
                chatListVo.setPersonName(friendName);
                list.add(chatListVo);
            }
        }
        return list;
    }

    @Override
    public PageInfo<Person> findAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize,"");
        List<Person> list = personMapper.findAll();
        return PageInfo.of(list);
    }

    @Override
    public void resetPwd(String id) {
        Person person = personMapper.findById(id);
        person.setPwd("123456");
        personMapper.updateById(person);
    }

    @Override
    public void update(String id, String nickname, String pwd) {
        personMapper.update(id,nickname,pwd);
    }

    @Override
    public PageInfo<Person> findDoctor(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize,"");
        List<Person> list = personMapper.findDoctor();
        return PageInfo.of(list);
    }

    @Override
    public void checkPass(String id) {
        Person person = personMapper.findById(id);
        person.setStatus(1);
        personMapper.updateById(person);
    }

    @Override
    public void delete(String id) {
        //删除相关图片
        DoctorPrivacy doctorPrivacy = doctorPrivacyMapper.selectById(id);
        String creditPath = "D:/OORPhoto/creditCard/" + doctorPrivacy.getCreditId();
        File file = new File(creditPath);
        file.delete();
        String certifyPath = "D:/OORPhoto/doctorCertify/" + doctorPrivacy.getCertification();
        File file1 = new File(certifyPath);
        file1.delete();
        //删除privacy表信息
        doctorPrivacyMapper.deleteById(id);
        //删除user表信息
        personMapper.deleteById(id);
    }

}
