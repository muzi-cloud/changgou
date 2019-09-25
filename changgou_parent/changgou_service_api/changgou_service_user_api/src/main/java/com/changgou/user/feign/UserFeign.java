package com.changgou.user.feign;

import com.changgou.user.pojo.Collection;
import com.changgou.user.pojo.User;
import entity.Result;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Steven
 * @version 1.0
 * @description com.changgou.user.feign
 * @date 2019-9-14
 */
@FeignClient(name = "user")
@RequestMapping("user")
public interface UserFeign {

    @GetMapping("load/{id}")
    public Result<User> findById(@PathVariable("id") String id);

    @GetMapping(value = "/points/add")
    public Result addPoints(@RequestParam(value = "points") Integer points);

    @GetMapping("myCollect")
    public List<Collection> myCollect(String username);
}
