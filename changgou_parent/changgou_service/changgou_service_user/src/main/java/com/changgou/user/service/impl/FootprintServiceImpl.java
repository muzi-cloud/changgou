package com.changgou.user.service.impl;
import com.changgou.user.dao.FootprintMapper;
import com.changgou.user.pojo.Footprint;
import com.changgou.user.service.FootprintService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.List;
/****
 * @Author:shenkunlin
 * @Description:Footprint业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class FootprintServiceImpl implements FootprintService {

    @Autowired
    private FootprintMapper footprintMapper;


    /**
     * Footprint条件+分页查询
     * @param footprint 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Footprint> findPage(Footprint footprint, int page, int size){
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(footprint);
        //执行搜索
        return new PageInfo<Footprint>(footprintMapper.selectByExample(example));
    }

    /**
     * Footprint分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Footprint> findPage(int page, int size){
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<Footprint>(footprintMapper.selectAll());
    }

    /**
     * Footprint条件查询
     * @param footprint
     * @return
     */
    @Override
    public List<Footprint> findList(Footprint footprint){
        //构建查询条件
        Example example = createExample(footprint);
        //根据构建的条件查询数据
        return footprintMapper.selectByExample(example);
    }


    /**
     * Footprint构建查询对象
     * @param footprint
     * @return
     */
    public Example createExample(Footprint footprint){
        Example example=new Example(Footprint.class);
        Example.Criteria criteria = example.createCriteria();
        if(footprint!=null){
            // 浏览足迹ID
            if(!StringUtils.isEmpty(footprint.getId())){
                    criteria.andEqualTo("id",footprint.getId());
            }
            // 商品ID
            if(!StringUtils.isEmpty(footprint.getSkuId())){
                    criteria.andEqualTo("skuId",footprint.getSkuId());
            }
            // 登陆用户名
            if(!StringUtils.isEmpty(footprint.getUsername())){
                    criteria.andLike("username","%"+footprint.getUsername()+"%");
            }
            // 浏览时间
            if(!StringUtils.isEmpty(footprint.getBrowseTime())){
                    criteria.andEqualTo("BrowseTime",footprint.getBrowseTime());
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
        footprintMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Footprint
     * @param footprint
     */
    @Override
    public void update(Footprint footprint){
        footprintMapper.updateByPrimaryKey(footprint);
    }

    /**
     * 增加Footprint
     * @param footprint
     */
    @Override
    public void add(Footprint footprint){
        footprintMapper.insert(footprint);
    }

    /**
     * 根据ID查询Footprint
     * @param id
     * @return
     */
    @Override
    public Footprint findById(Integer id){
        return  footprintMapper.selectByPrimaryKey(id);
    }

    @Override
    public Footprint findBySkuId(Long skuId) {
        Example example=new Example(Footprint.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("skuId",skuId);
        return footprintMapper.selectOneByExample(example);
    }

    /**
     * 查询Footprint全部数据
     * @return
     */
    @Override
    public List<Footprint> findAll() {
        return footprintMapper.selectAll();
    }
}
