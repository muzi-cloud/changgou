package com.changgou.user.feign;
import com.changgou.user.pojo.Provinces;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:shenkunlin
 * @Description:
 * @Date 2019/6/18 13:58
 *****/
@FeignClient(name="user")
@RequestMapping("/provinces")
public interface ProvincesFeign {


    /***
     * 多条件搜索品牌数据
     * @param provinces
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<Provinces>> findList(@RequestBody(required = false) Provinces provinces);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable("id") String id);

    /***
     * 修改Provinces数据
     * @param provinces
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody Provinces provinces, @PathVariable("id") String id);

    /***
     * 新增Provinces数据
     * @param provinces
     * @return
     */
    @PostMapping
    Result add(@RequestBody Provinces provinces);

    /***
     * 根据ID查询Provinces数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Provinces> findById(@PathVariable("id") String id);

    /***
     * 查询Provinces全部数据
     * @return
     */
    @GetMapping
    Result<List<Provinces>> findAll();
}