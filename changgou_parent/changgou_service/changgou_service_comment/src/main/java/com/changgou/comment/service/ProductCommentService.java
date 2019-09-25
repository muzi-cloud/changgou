package com.changgou.comment.service;

import com.changgou.comment.pojo.ProductComment;
import com.github.pagehelper.PageInfo;

import java.util.List;

/****
 * @Author:shenkunlin
 * @Description:ProductComment业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface ProductCommentService {

    /***
     * ProductComment多条件分页查询
     * @param productComment
     * @param page
     * @param size
     * @return
     */
    PageInfo<ProductComment> findPage(ProductComment productComment, int page, int size);

    /***
     * ProductComment分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<ProductComment> findPage(int page, int size);

    /***
     * ProductComment多条件搜索方法
     * @param productComment
     * @return
     */
    List<ProductComment> findList(ProductComment productComment);

    /***
     * 删除ProductComment
     * @param id
     */
    void delete(Integer id);

    /***
     * 修改ProductComment数据
     * @param productComment
     */
    void update(ProductComment productComment);

    /***
     * 新增ProductComment
     * @param productComment
     */
    void add(ProductComment productComment);

    /**
     * 根据ID查询ProductComment
     * @param id
     * @return
     */
     ProductComment findById(Integer id);

    /***
     * 查询所有ProductComment
     * @return
     */
    List<ProductComment> findAll();

    /**
     * 根据skuId查询评价数据
     * @param skuId
     * @return
     */
    List<ProductComment> findBySkuId(Long skuId);
}
