package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.PassageCover;
import com.example.demo.mappers.PassageCoverMapper;
import com.example.demo.service.PassageCoverService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PassageCoverServiceImpl extends ServiceImpl<PassageCoverMapper, PassageCover> implements PassageCoverService {
   @Resource
   PassageCoverMapper passageCoverMapper;

   public PassageCover add(PassageCover passageCover){
       passageCoverMapper.insert(passageCover);
       return passageCover;
   }

}
