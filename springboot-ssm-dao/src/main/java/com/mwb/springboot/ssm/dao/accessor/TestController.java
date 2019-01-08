package com.mwb.springboot.ssm.dao.accessor;

import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springboot.ssm.dao.entity.User;
import springboot.ssm.dao.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by admin on 2019/1/8.
 */

@RestController

public class TestController {
    @Resource
    private UserMapper userMapper;
    @RequestMapping(value = "test" ,method= RequestMethod.GET)
    public List<User> getUser(){
        List<User> list=userMapper.selectUsers();
        return list;
    }
}
