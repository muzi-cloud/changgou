package com.changgou.comment.service.impl;

import com.changgou.comment.dao.ProductCommentMapper;
import com.changgou.comment.pojo.ProductComment;
import com.changgou.comment.service.ProductCommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/****
 * @Author:shenkunlin
 * @Description:ProductComment业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class ProductCommentServiceImpl implements ProductCommentService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ProductCommentMapper productCommentMapper;


    /**
     * ProductComment条件+分页查询
     * @param productComment 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<ProductComment> findPage(ProductComment productComment, int page, int size){
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(productComment);
        //执行搜索
        return new PageInfo<ProductComment>(productCommentMapper.selectByExample(example));
    }

    /**
     * ProductComment分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<ProductComment> findPage(int page, int size){
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<ProductComment>(productCommentMapper.selectAll());
    }

    /**
     * ProductComment条件查询
     * @param productComment
     * @return
     */
    @Override
    public List<ProductComment> findList(ProductComment productComment){
        //构建查询条件
        Example example = createExample(productComment);
        //根据构建的条件查询数据
        return productCommentMapper.selectByExample(example);
    }


    /**
     * ProductComment构建查询对象
     * @param productComment
     * @return
     */
    public Example createExample(ProductComment productComment){
        Example example=new Example(ProductComment.class);
        Example.Criteria criteria = example.createCriteria();
        if(productComment!=null){
            // 评论ID
            if(!StringUtils.isEmpty(productComment.getId())){
                    criteria.andEqualTo("id",productComment.getId());
            }
            // 商品ID
            if(!StringUtils.isEmpty(productComment.getSkuId())){
                    criteria.andEqualTo("skuId",productComment.getSkuId());
            }
            // 订单ID
            if(!StringUtils.isEmpty(productComment.getOrderId())){
                    criteria.andEqualTo("orderId",productComment.getOrderId());
            }
            // 登陆用户名
            if(!StringUtils.isEmpty(productComment.getUsername())){
                    criteria.andLike("username","%"+productComment.getUsername()+"%");
            }
            // 评论内容
            if(!StringUtils.isEmpty(productComment.getContent())){
                    criteria.andEqualTo("content",productComment.getContent());
            }
            // 评论时间
            if(!StringUtils.isEmpty(productComment.getAuditTime())){
                    criteria.andEqualTo("auditTime",productComment.getAuditTime());
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
        productCommentMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改ProductComment
     * @param productComment
     */
    @Override
    public void update(ProductComment productComment){
        productCommentMapper.updateByPrimaryKey(productComment);
    }

    /**
     * 增加ProductComment
     * @param productComment
     */
    @Override
    public void add(ProductComment productComment){
        String username = TokenDecode.getUserInfo().get("username");
        productComment.setUsername(username);
        productComment.setAuditTime(new Date());
        productCommentMapper.insertSelective(productComment);
    }

    /**
     * 根据ID查询ProductComment
     * @param id
     * @return
     */
    @Override
    public ProductComment findById(Integer id){
        return  productCommentMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询ProductComment全部数据
     * @return
     */
    @Override
    public List<ProductComment> findAll() {
        return productCommentMapper.selectAll();
    }

    /**
     * 根据skuId查询评价数据
     * @param skuId
     * @return
     */
    @Override
    public List<ProductComment> findBySkuId(Long skuId) {
        ProductComment productComment = new ProductComment();
        productComment.setSkuId(skuId);
        List<ProductComment> productCommentList = productCommentMapper.select(productComment);
        return productCommentList;
    }
}
