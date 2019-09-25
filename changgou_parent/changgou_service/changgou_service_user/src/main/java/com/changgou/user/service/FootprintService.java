package com.changgou.user.service;
import com.changgou.user.pojo.Footprint;
import com.github.pagehelper.PageInfo;
import java.util.List;
/****
 * @Author:shenkunlin
 * @Description:Footprint业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface FootprintService {



    /***
     * Footprint多条件分页查询
     * @param footprint
     * @param page
     * @param size
     * @return
     */
    PageInfo<Footprint> findPage(Footprint footprint, int page, int size);

    /***
     * Footprint分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Footprint> findPage(int page, int size);

    /***
     * Footprint多条件搜索方法
     * @param footprint
     * @return
     */
    List<Footprint> findList(Footprint footprint);

    /***
     * 删除Footprint
     * @param id
     */
    void delete(Integer id);

    /***
     * 修改Footprint数据
     * @param footprint
     */
    void update(Footprint footprint);

    /***
     * 新增Footprint
     * @param footprint
     */
    void add(Footprint footprint);

    /**
     * 根据ID查询Footprint
     * @param id
     * @return
     */
     Footprint findById(Integer id);

     Footprint findBySkuId(Long skuId);
    /***
     * 查询所有Footprint
     * @return
     */
    List<Footprint> findAll();
}
