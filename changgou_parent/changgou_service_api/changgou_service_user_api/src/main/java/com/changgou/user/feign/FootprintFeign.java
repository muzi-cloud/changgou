package com.changgou.user.feign;
import com.changgou.user.pojo.Footprint;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/footprint")
public interface FootprintFeign {

    /***
     * Footprint分页条件搜索实现
     * @param footprint
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageInfo<Footprint>> findPage(@RequestBody(required = false) Footprint footprint, @PathVariable("page") int page, @PathVariable("size")  int size);

    /***
     * Footprint分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageInfo> findPage(@PathVariable("page")  int page, @PathVariable("size")  int size);

    /***
     * 多条件搜索品牌数据
     * @param footprint
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<Footprint>> findList(@RequestBody(required = false) Footprint footprint);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable("id") Integer id);

    /***
     * 修改Footprint数据
     * @param footprint
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody Footprint footprint,@PathVariable("id") Integer id);

    /***
     * 新增Footprint数据
     * @param
     * @return
     */
    @PostMapping
    public Result add(@RequestParam("skuId") Long skuId);

    /***
     * 根据ID查询Footprint数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Footprint> findById(@PathVariable("id") Integer id);

    /***
     * 查询Footprint全部数据
     * @return
     */
   /* @GetMapping
    Result<List<Footprint>> findAll();*/
}