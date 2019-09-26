package com.changgou.user.feign;
import com.changgou.user.pojo.Areas;
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
@RequestMapping("/areas")
public interface AreasFeign {


    /***
     * 多条件搜索品牌数据
     * @param areas
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<Areas>> findList(@RequestBody(required = false) Areas areas);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable("id") String id);

    /***
     * 修改Areas数据
     * @param areas
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody Areas areas, @PathVariable("id") String id);

    /***
     * 新增Areas数据
     * @param areas
     * @return
     */
    @PostMapping
    Result add(@RequestBody Areas areas);

    /***
     * 根据ID查询Areas数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Areas> findById(@PathVariable("id") String id);

    /***
     * 查询Areas全部数据
     * @return
     */
    @GetMapping
    Result<List<Areas>> findAll();
}