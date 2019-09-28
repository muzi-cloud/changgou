package com.changgou.item.controller;

import com.alibaba.fastjson.JSON;
import com.changgou.comment.feign.ProductCommentFeign;
import com.changgou.comment.pojo.ProductComment;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.item.service.PageService;
import com.github.pagehelper.PageInfo;
import entity.DateUtil;
import entity.Page;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Controller
@RequestMapping("/page")
public class PageController {
    @Autowired
    PageService pageService;
    @Autowired
    ProductCommentFeign productCommentFeign;
    @Autowired
    SkuFeign skuFeign;
    @RequestMapping("/createHtml/{id}")
    public Result creatHtml(@PathVariable Long id) throws FileNotFoundException, UnsupportedEncodingException {
        pageService.createPageHtml(id);
        return new Result(true, StatusCode.OK,"查询成功");
    }

   /* *//**
     * 根据skuId查询全部
     * @param skuId
     * @return
     *//*
    @RequestMapping("/searchComment/{skuId}")
    @ResponseBody
    public Result<Map> searchComment(@PathVariable Long skuId) {
        //根据skuid查询评论列表
        Result<List<ProductComment>> result = productCommentFeign.findBySkuId(skuId);
        List<ProductComment> productCommentList = result.getData();
        List<Map> mapList=new ArrayList<>();
        for (ProductComment productComment : productCommentList) {
            Map map=new HashMap();
            Result<Sku> skuResult = skuFeign.findById(productComment.getSkuId());
            Sku sku = skuResult.getData();
            String spec = sku.getSpec();
            Map<String,String> specMap = JSON.parseObject(spec, Map.class);
            String specPlus="";
            for (String key : specMap.keySet()) {
                specPlus+=specMap.get(key)+" ";
            }
            map.put("productComment",productComment);
            map.put("time", DateUtil.data2str(productComment.getAuditTime(),"yyyy-MM-dd HH:mm:ss"));
            map.put("spec",specPlus);
            mapList.add(map);
        }
        Map commentMap=new HashMap();
        commentMap.put("commentList",mapList);
        commentMap.put("size",mapList.size());
        return new Result<Map>(true,StatusCode.OK,"评论查询成功",commentMap);
    }*/

    /**
     * 评论分页查询
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/searchPage/{skuId}/{page}/{size}")
    @ResponseBody
    public Result searchPage(@PathVariable Long skuId,@PathVariable  Integer page, @PathVariable  Integer size){
        if(page==null|| page<=0){
            page=1;
        }
        ProductComment productComment1=new ProductComment();
        productComment1.setSkuId(skuId);
        Result<PageInfo<ProductComment>> pageInfoResult = productCommentFeign.findPage(productComment1,page, size);
        PageInfo<ProductComment> pageInfo = pageInfoResult.getData();

        List<ProductComment> list = pageInfo.getList();
        List<Map> mapList=new ArrayList<>();
        for (ProductComment productComment : list) {
            Map map=new HashMap();
            Result<Sku> skuResult = skuFeign.findById(productComment.getSkuId());
            Sku sku = skuResult.getData();
            String spec = sku.getSpec();
            Map<String,String> specMap = JSON.parseObject(spec, Map.class);
            String specPlus="";
            for (String key : specMap.keySet()) {
                specPlus+=specMap.get(key)+" ";
            }
            map.put("comment",productComment);
            map.put("time", DateUtil.data2str(productComment.getAuditTime(),"yyyy-MM-dd HH:mm:ss"));
            map.put("spec",specPlus);
            mapList.add(map);
        }
        Page commentPage = new Page(pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize());
        List<Long> numList=new ArrayList<>();
        for (long i = commentPage.getLpage(); i<=commentPage.getRpage(); i++){
            numList.add(i);
        }

        Map map=new HashMap();
        map.put("commentPage",commentPage);
        map.put("mapList",mapList);
        map.put("numList",numList);
        return new Result(true,StatusCode.OK,"分页查询成功",map);
    }

    @RequestMapping("/searchItem")
    public String searchItem(){
        return "item";
    }
}
