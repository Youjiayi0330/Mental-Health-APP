package com.example.demo.controller;

import cn.hutool.log.Log;
import com.example.demo.common.Result;
import com.example.demo.service.PersonService;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/reset")
@Slf4j
public class GetEmailController {

    @Resource
    private final RedisTemplate redisTemplate;

    @Resource
    JavaMailSender mailSender;//注入发送邮件的bean

    @Resource
    private PersonService personService;

    @Value("${spring.mail.username}")
    private String emailUserName;

    //定义发送的标题
    public static String title="心理健康科普App密码重置验证码";

    public GetEmailController(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/getCode/{email}")
    public Result getEmail(@PathVariable("email") String email) {
        try {
            System.out.println("邮箱号为"+email);
            String body = setEmailBody(email);
            MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setFrom(emailUserName);//设置发件qq邮箱
            message.setTo(email);       //设置收件人
            message.setSubject(title);  //设置标题
            message.setText(body);      //第二个参数true表示使用HTML语言来编写邮件
            log.info("getEmail send email message: [{}]", message);
            this.mailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("[{}] send email message exception", email, e);
            return Result.error();
        }
        return Result.success();
    }

    @PostMapping(value = "/verifyCode")
    public Result verifyCode(String email, String code){
        String initCode = (String) redisTemplate.opsForValue().get(email);
        if(code.equals(initCode)){
            personService.resetPwd(email);
            return Result.success();
        } else {
            return Result.error("1","验证码输入错误");
        }
    }

    private String setEmailBody(String email){
        //redisTemplate.opsForValue().set(email, "347680");
        int random6 = (int) ((Math.random() * 9 + 1) * 100000);
        String emailCode = String.valueOf(random6);
        redisTemplate.opsForValue().set(email, emailCode);
        redisTemplate.expire(email,300, TimeUnit.SECONDS);
        StringBuffer body = new StringBuffer();
        body.append("欢迎使用心理健康科普App!\n\n").append("    您的验证码为:  ").append(emailCode+"\n\n");
        body.append("    请注意:需要您在收到邮件后5分钟内使用，否则该验证码将会失效。\n\n");
        body.append("    验证成功后，您的密码初始化为123456，使用此密码登录后即可在个人中心修改您的密码。\n\n");
        return body.toString();

    }

    public void sendPass(String email) {
        try {
            String body = setPassBody(email);
            MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setFrom(emailUserName);//设置发件qq邮箱
            message.setTo(email);       //设置收件人
            message.setSubject("医生认证审核通过");  //设置标题
            message.setText(body);      //第二个参数true表示使用HTML语言来编写邮件
            log.info("getEmail send email message: [{}]", message);
            this.mailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("[{}] send email message exception", email, e);
        }
    }

    private String setPassBody(String email){
        StringBuffer body = new StringBuffer();
        body.append("欢迎使用心理健康科普App!\n\n");
        body.append("    您的医生认证已审核通过。可登录App开始使用。\n\n");
        return body.toString();
    }

    public void sendReject(String email, String reason) {
        try {
            String body = setRejectBody(reason);
            MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setFrom(emailUserName);//设置发件qq邮箱
            message.setTo(email);       //设置收件人
            message.setSubject("医生认证审核未通过");  //设置标题
            message.setText(body);      //第二个参数true表示使用HTML语言来编写邮件
            log.info("getEmail send email message: [{}]", message);
            this.mailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("[{}] send email message exception", email, e);
        }
    }

    private String setRejectBody(String reason){
        StringBuffer body = new StringBuffer();
        body.append("欢迎使用心理健康科普App!\n\n");
        body.append("    很遗憾，您的医生认证已审核通过。\n");
        body.append("    认证驳回的原因为：").append(reason+"\n\n");
        body.append("    请重新注册并提交相关材料，谢谢！\n");
        return body.toString();
    }

}
