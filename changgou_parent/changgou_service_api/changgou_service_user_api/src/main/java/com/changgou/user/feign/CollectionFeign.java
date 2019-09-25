package com.changgou.user.feign;
import com.changgou.user.pojo.Collection;
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
@RequestMapping("/collection")
public interface CollectionFeign {

    @GetMapping("/myCollect")
    public List<Collection> myCollect();

    /***
     * Collection分页条件搜索实现
     * @param collection
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    Result<PageInfo<Collection>> findPage(@RequestBody(required = false) Collection collection, @PathVariable("page") int page, @PathVariable("size") int size);

    /***
     * Collection分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    Result<PageInfo<Collection>> findPage(@PathVariable("page")  int page, @PathVariable("size")  int size);

    /***
     * 多条件搜索品牌数据
     * @param collection
     * @return
     */
    @PostMapping(value = "/search" )
    Result<List<Collection>> findList(@RequestBody(required = false) Collection collection);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    Result delete(@PathVariable("id") Integer id);

    /***
     * 修改Collection数据
     * @param collection
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    Result update(@RequestBody Collection collection,@PathVariable("id") Integer id);

    /***
     * 新增Collection数据
     * @param collection
     * @return
     */
    @PostMapping
    Result add(@RequestBody Collection collection);

    /***
     * 根据ID查询Collection数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<Collection> findById(@PathVariable("id") Integer id);

    /***
     * 查询Collection全部数据
     * @return
     */
    @GetMapping
    Result<List<Collection>> findAll();
}