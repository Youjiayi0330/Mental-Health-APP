package com.example.demo.controller;

import com.example.demo.Netty.session.SessionFactory;
import com.example.demo.common.Result;
import com.example.demo.entity.Person;
import com.example.demo.service.PersonService;
import com.example.demo.vo.NoteInfoVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Resource
    PersonService personService;
    @Resource
    GetEmailController getEmailController;

    @GetMapping("/findNameById/{id}")
    public Result<String> findNameById(@PathVariable("id") String id){
        String personName = personService.findNameById(id);
        return Result.success(personName);
    }

    @GetMapping("/findAll")
    public Result<PageInfo<Person>> findAll(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "3") Integer pageSize){
        return Result.success(personService.findAll(pageNum,pageSize));
    }

    @GetMapping("/resetPwd/{id}")
    public Result resetPwd(@PathVariable(value = "id") String id){
        personService.resetPwd(id);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(String id, String nickname, String pwd){
        personService.update(id,nickname,pwd);
        return Result.success();
    }

    @GetMapping("/findVisitNum")
    public Result<Integer> findVisitNum(){
        Integer num = SessionFactory.getSession().getVisitNum();
        return Result.success(num);
    }

    @GetMapping("/findDoctor")
    public Result<PageInfo<Person>> findDoctor(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "3") Integer pageSize){
        return Result.success(personService.findDoctor(pageNum,pageSize));
    }

    @PutMapping("/check/{id}")
    public Result checkPass(@PathVariable(value = "id") String id){
        //改变审核状态
        personService.checkPass(id);
        //发送审核通过邮件
        getEmailController.sendPass(id);
        return Result.success();
    }

    @DeleteMapping("/reject/{id}")
    public Result checkReject(@PathVariable(value = "id") String id, @RequestParam String reason){
        personService.delete(id);
        //发送审核失败邮件
        getEmailController.sendReject(id,reason);
        return Result.success();
    }
}
