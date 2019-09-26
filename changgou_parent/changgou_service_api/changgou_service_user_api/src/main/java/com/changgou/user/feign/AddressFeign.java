package com.changgou.user.feign;

import com.changgou.user.pojo.Address;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Steven
 * @version 1.0
 * @description com.changgou.user.feign
 * @date 2019-9-15
 */
@FeignClient(name = "user")
@RequestMapping("address")
public interface AddressFeign {

    @GetMapping("/user/list")
    public Result<List<Address>> list();
    /***
     * 修改Address数据
     * @param address
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody Address address, @PathVariable("id") Integer id);
    /***
     * 多条件搜索品牌数据
     * @param address
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<Address>> findList(@RequestBody(required = false)  Address address);
    /***
     * 修改地址数据
     * @param address
     * @return
     */
    @PutMapping
    public Result updateByUsername(@RequestBody  Address address);

}
