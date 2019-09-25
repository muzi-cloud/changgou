package com.changgou.user.service.impl;
import com.changgou.user.dao.CollectionMapper;
import com.changgou.user.pojo.Collection;
import com.changgou.user.service.CollectionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.List;
/****
 * @Author:shenkunlin
 * @Description:Collection业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionMapper collectionMapper;


    /**
     * Collection条件+分页查询
     * @param collection 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Collection> findPage(Collection collection, int page, int size){
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(collection);
        //执行搜索
        return new PageInfo<Collection>(collectionMapper.selectByExample(example));
    }

    /**
     * Collection分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Collection> findPage(int page, int size){
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<Collection>(collectionMapper.selectAll());
    }

    /**
     * Collection条件查询
     * @param collection
     * @return
     */
    @Override
    public List<Collection> findList(Collection collection){
        //构建查询条件
        Example example = createExample(collection);
        //根据构建的条件查询数据
        return collectionMapper.selectByExample(example);
    }


    /**
     * Collection构建查询对象
     * @param collection
     * @return
     */
    public Example createExample(Collection collection){
        Example example=new Example(Collection.class);
        Example.Criteria criteria = example.createCriteria();
        if(collection!=null){
            // 收藏ID
            if(!StringUtils.isEmpty(collection.getId())){
                    criteria.andEqualTo("id",collection.getId());
            }
            // 商品ID
            if(!StringUtils.isEmpty(collection.getSkuId())){
                    criteria.andEqualTo("skuId",collection.getSkuId());
            }
            // 登陆用户名
            if(!StringUtils.isEmpty(collection.getUsername())){
                    criteria.andLike("username","%"+collection.getUsername()+"%");
            }
        }
        return example;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Integer id){
        collectionMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Collection
     * @param collection
     */
    @Override
    public void update(Collection collection){
        collectionMapper.updateByPrimaryKey(collection);
    }

    /**
     * 增加Collection
     * @param collection
     */
    @Override
    public void add(Collection collection){
        collectionMapper.insert(collection);
    }

    /**
     * 根据ID查询Collection
     * @param id
     * @return
     */
    @Override
    public Collection findById(Integer id){
        return  collectionMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Collection全部数据
     * @return
     */
    @Override
    public List<Collection> findAll() {
        return collectionMapper.selectAll();
    }

    @Override
    public List<Collection> myCollect(String username) {
        Collection collection=new Collection();
        collection.setUsername(username);
        return collectionMapper.select(collection);
    }
}
