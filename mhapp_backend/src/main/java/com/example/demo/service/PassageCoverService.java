package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.PassageCover;

public interface PassageCoverService extends IService<PassageCover> {
    PassageCover add(PassageCover passageCover);
}
