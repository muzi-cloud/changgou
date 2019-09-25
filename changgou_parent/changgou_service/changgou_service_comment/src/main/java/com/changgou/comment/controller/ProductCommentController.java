package com.changgou.comment.controller;

import com.changgou.comment.pojo.ProductComment;
import com.changgou.comment.service.ProductCommentService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:shenkunlin
 * @Description:
 * @Date 2019/6/14 0:18
 *****/

@RestController
@RequestMapping("/productComment")
@CrossOrigin
public class ProductCommentController {

    @Autowired
    private ProductCommentService productCommentService;

    /***
     * ProductComment分页条件搜索实现
     * @param productComment
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false) ProductComment productComment, @PathVariable  int page, @PathVariable  int size){
        //调用ProductCommentService实现分页条件查询ProductComment
        PageInfo<ProductComment> pageInfo = productCommentService.findPage(productComment, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * ProductComment分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable  int page, @PathVariable  int size){
        //调用ProductCommentService实现分页查询ProductComment
        PageInfo<ProductComment> pageInfo = productCommentService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param productComment
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<ProductComment>> findList(@RequestBody(required = false) ProductComment productComment){
        //调用ProductCommentService实现条件查询ProductComment
        List<ProductComment> list = productCommentService.findList(productComment);
        return new Result<List<ProductComment>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        //调用ProductCommentService实现根据主键删除
        productCommentService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改ProductComment数据
     * @param productComment
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody ProductComment productComment, @PathVariable Integer id){
        //设置主键值
        productComment.setId(id);
        //调用ProductCommentService实现修改ProductComment
        productCommentService.update(productComment);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增ProductComment数据
     * @param productComment
     * @return
     */
    @PostMapping
    public Result add(@RequestBody ProductComment productComment){
        //调用ProductCommentService实现添加ProductComment
        productCommentService.add(productComment);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询ProductComment数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<ProductComment> findById(@PathVariable Integer id){
        //调用ProductCommentService实现根据主键查询ProductComment
        ProductComment productComment = productCommentService.findById(id);
        return new Result<ProductComment>(true,StatusCode.OK,"查询成功",productComment);
    }

    /***
     * 查询ProductComment全部数据
     * @return
     */
    @GetMapping
    public Result<List<ProductComment>> findAll(){
        //调用ProductCommentService实现查询所有ProductComment
        List<ProductComment> list = productCommentService.findAll();
        return new Result<List<ProductComment>>(true, StatusCode.OK,"查询成功",list) ;
    }

    /**
     * 根据skuId 查询评价数据
     * @param skuId
     * @return
     */
    @GetMapping("/findBySkuId/{skuId}")
    public Result<List<ProductComment>> findBySkuId(@PathVariable("skuId") Long skuId) {
        List<ProductComment> productCommentList = productCommentService.findBySkuId(skuId);
        return new Result<List<ProductComment>>(true,StatusCode.OK,"查询成功",productCommentList);
    }
}
