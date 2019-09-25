package com.changgou.user.service;
import com.changgou.user.pojo.Collection;
import com.github.pagehelper.PageInfo;
import java.util.List;
/****
 * @Author:shenkunlin
 * @Description:Collection业务层接口
 * @Date 2019/6/14 0:16
 *****/
public interface CollectionService {

    /***
     * Collection多条件分页查询
     * @param collection
     * @param page
     * @param size
     * @return
     */
    PageInfo<Collection> findPage(Collection collection, int page, int size);

    /***
     * Collection分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Collection> findPage(int page, int size);

    /***
     * Collection多条件搜索方法
     * @param collection
     * @return
     */
    List<Collection> findList(Collection collection);

    /***
     * 删除Collection
     * @param id
     */
    void delete(Integer id);

    /***
     * 修改Collection数据
     * @param collection
     */
    void update(Collection collection);

    /***
     * 新增Collection
     * @param collection
     */
    void add(Collection collection);

    /**
     * 根据ID查询Collection
     * @param id
     * @return
     */
     Collection findById(Integer id);

    /***
     * 查询所有Collection
     * @return
     */
    List<Collection> findAll();

    List<Collection> myCollect(String username);
}
