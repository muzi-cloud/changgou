package com.changgou.user.feign;

import com.changgou.user.pojo.Collection;
import com.changgou.user.pojo.User;
import entity.Result;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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
    /***
     * 多条件搜索品牌数据
     * @param user
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<User>> findList(@RequestBody(required = false)  User user);
    /***
     * 修改User数据
     * @param user
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  User user,@PathVariable("id") String id);

}
