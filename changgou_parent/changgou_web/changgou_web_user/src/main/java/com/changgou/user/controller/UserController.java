package com.changgou.user.controller;

import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.user.feign.CollectionFeign;
import com.changgou.user.feign.FootprintFeign;
import com.changgou.user.pojo.Collection;
import com.changgou.user.pojo.Footprint;
import com.github.pagehelper.PageInfo;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("userweb")
@WebServlet(urlPatterns = "/ajaxUser")
public class UserController extends HttpServlet {

    @Autowired
    private CollectionFeign collectionFeign;

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private FootprintFeign footprintFeign;



    @GetMapping("myFoot")
    public String myFoot(@RequestParam(value = "pageNum",required = false) Integer pageNum,Model model){
        if(pageNum==null||pageNum==0){
            pageNum=1;
        }
        List<Long> skuIds=new ArrayList<>();
        Result<PageInfo<Footprint>> page = footprintFeign.findPage(new Footprint(),pageNum, 5);
        PageInfo<Footprint> data = page.getData();
        List<Footprint> list = data.getList();
        for (Footprint footprint : list) {
            Long skuId = footprint.getSkuId();
            skuIds.add(skuId);
        }
        List<Sku> skus = skuFeign.myCollect(skuIds);
        model.addAttribute("url", "/api/userweb/myFoot");
        model.addAttribute("result", skus);
        model.addAttribute("page",data);
        return "home-person-footmark";
    }

    @GetMapping("item/{id}")
    public String item(@PathVariable("id")Long skuId,Model model){
        Result<Sku> sku = skuFeign.findById(skuId);
        model.addAttribute("sku",sku.getData());
        Result add = footprintFeign.add(skuId);
        System.out.println(add);
        return "item";
    }

    @GetMapping("myCollect")
    public String myCollect(@RequestParam(value = "pageNum",required = false) Integer pageNum,Model model){
       if(pageNum==null||pageNum==0){
           pageNum=1;
       }
        List<Long> skuIds=new ArrayList<>();
        Result<PageInfo<Collection>> page = collectionFeign.findPage(new Collection(),pageNum, 5);
        PageInfo<Collection> data = page.getData();
        List<Collection> list = data.getList();
        for (Collection collection : list) {
            Long skuId = collection.getSkuId();
            skuIds.add(skuId);
        }
        List<Sku> skus = skuFeign.myCollect(skuIds);
        model.addAttribute("url", "/api/userweb/myCollect");
        model.addAttribute("result", skus);
        model.addAttribute("page",data);
        return "home-person-collect";
    }


    /**
     * 把Map转换成/search/list?key=value&key2=value2
     * @param searchMap
     * @return
    private String getUrl(Integer pageNum){
        String url = "/search/list";
        if(pageNum != null){
            url += "?";
            for (String key : searchMap.keySet()) {
                ////如果是排序的参数，不拼接到url上，便于下次换种方式排序
                if(key.indexOf("sort") > -1 || key.equals("pageNum")){
                    continue;
                }
                url += key + "=" + searchMap.get(key) + "&";
            }
            url = url.substring(0,url.length() - 1);
        }
        return url;
    }*/
}
