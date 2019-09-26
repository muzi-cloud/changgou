package com.changgou.comment.feign;

import com.changgou.comment.pojo.ProductComment;
import com.github.pagehelper.PageInfo;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("comment")
@RequestMapping("/productComment")
public interface ProductCommentFeign {

    /***
     * ProductComment分页条件搜索实现
     * @param productComment
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo<ProductComment>> findPage(@RequestBody(required = false) ProductComment productComment, @PathVariable("page") int page, @PathVariable("size") int size);

    /***
     * ProductComment分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable("page") int page, @PathVariable("size") int size);

    /***
     * 多条件搜索品牌数据
     * @param productComment
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<ProductComment>> findList(@RequestBody(required = false) ProductComment productComment);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable("id") Integer id);

    /***
     * 修改ProductComment数据
     * @param productComment
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody ProductComment productComment, @PathVariable("id") Integer id);

    /***
     * 新增ProductComment数据
     * @param productComment
     * @return
     */
    @PostMapping
    public Result add(@RequestBody ProductComment productComment);

    /***
     * 根据ID查询ProductComment数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<ProductComment> findById(@PathVariable("id") Integer id);

    /***
     * 查询ProductComment全部数据
     * @return
     */
    @GetMapping
    public Result<List<ProductComment>> findAll();

    /**
     * 根据skuId 查询评价数据
     * @param skuId
     * @return
     */
    @GetMapping("/findBySkuId/{skuId}")
    public Result<List<ProductComment>> findBySkuId(@PathVariable("skuId") Long skuId) ;
}
