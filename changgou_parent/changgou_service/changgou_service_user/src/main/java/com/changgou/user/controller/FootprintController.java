package com.changgou.user.controller;
import com.changgou.user.pojo.Footprint;
import com.changgou.user.service.FootprintService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;

import entity.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/****
 * @Author:shenkunlin
 * @Description:
 * @Date 2019/6/14 0:18
 *****/

@RestController
@RequestMapping("/footprint")
@CrossOrigin
public class FootprintController {

    @Autowired
    private FootprintService footprintService;

    /***
     * Footprint分页条件搜索实现
     * @param footprint
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo<Footprint>> findPage(@RequestBody(required = false)  Footprint footprint, @PathVariable  int page, @PathVariable  int size){
        //调用FootprintService实现分页条件查询Footprint
        String username = TokenDecode.getUserInfo().get("username");
        footprint.setUsername(username);
        PageInfo<Footprint> pageInfo = footprintService.findPage(footprint, page, size);
        return new Result<PageInfo<Footprint>>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * Footprint分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用FootprintService实现分页查询Footprint
        PageInfo<Footprint> pageInfo = footprintService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param footprint
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<Footprint>> findList(@RequestBody(required = false)  Footprint footprint){
        //调用FootprintService实现条件查询Footprint
        List<Footprint> list = footprintService.findList(footprint);
        return new Result<List<Footprint>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        //调用FootprintService实现根据主键删除
        footprintService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改Footprint数据
     * @param footprint
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody  Footprint footprint,@PathVariable Integer id){
        //设置主键值
        footprint.setId(id);
        //调用FootprintService实现修改Footprint
        footprintService.update(footprint);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增Footprint数据
     * @param
     * @return
     */
    @PostMapping
    public Result add(@RequestParam("skuId") Long skuId){
        //调用FootprintService实现添加Footprint
        String username = TokenDecode.getUserInfo().get("username");
        Footprint one = footprintService.findBySkuId(skuId);
        if(one!=null){
            return null;
        }
        Footprint footprint=new Footprint();
        footprint.setSkuId(skuId);
        footprint.setUsername(username);
        footprint.setBrowseTime(new Date());
        footprintService.add(footprint);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询Footprint数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Footprint> findById(@PathVariable Integer id){
        //调用FootprintService实现根据主键查询Footprint
        Footprint footprint = footprintService.findById(id);
        return new Result<Footprint>(true,StatusCode.OK,"查询成功",footprint);
    }

    /***
     * 查询Footprint全部数据
     * @return
    @GetMapping
    public Result<List<Footprint>> findAll(){
        //调用FootprintService实现查询所有Footprint
        List<Footprint> list = footprintService.findAll();
        return new Result<List<Footprint>>(true, StatusCode.OK,"查询成功",list) ;
    }*/
}
