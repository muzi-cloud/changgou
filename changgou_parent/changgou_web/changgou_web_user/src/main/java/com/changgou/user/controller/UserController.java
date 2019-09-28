package com.changgou.user.controller;

import com.changgou.file.feign.FileFeign;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.user.feign.*;
import com.changgou.user.pojo.*;
import com.changgou.user.pojo.Collection;
import com.github.pagehelper.PageInfo;
import entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.text.ParseException;
import java.util.*;


@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
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
    @Autowired
    private UserFeign userFeign;
    @Autowired
    private ProvincesFeign provincesFeign;//省
    @Autowired
    private CitiesFeign citiesFeign;//市
    @Autowired
    private AreasFeign areasFeign;//区
    @Autowired
    private AddressFeign addressFeign;
    @Autowired
    private FileFeign fileFeign;


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
        Result<PageInfo<Collection>> page = collectionFeign.findPage(new Collection(),pageNum, 8);
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
     * 回显数据
     *
     * @param model
     * @return
     */
    @RequestMapping("info")
    public String userInfo(Model model) {
        String username = TokenDecode.getUserInfo().get("username");
        //获取到用户信息
        User user = userFeign.findById(username).getData();
        Address where = new Address();
        where.setUsername(username);
        List<Address> addressList = addressFeign.findList(where).getData();
        //将数据封装到map中
        Map<String, Object> map = new HashMap<>();
        if (user != null) {
            //封装map
            map.put("user", user);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                String provinceid = address.getProvinceid();
                String cityid = address.getCityid();
                String areaid = address.getAreaid();
                String province = provincesFeign.findById(provinceid).getData().getProvince();
                String city = citiesFeign.findById(cityid).getData().getCity();
                String area = areasFeign.findById(areaid).getData().getArea();
                map.put("province", province);
                map.put("city", city);
                map.put("area", area);
            }
            model.addAttribute("map", map);
            return "home-setting-info";
        } else {
            throw new RuntimeException("该用户不存在");
        }
    }
    /**
     * 校验昵称
     * @return
     */
    @RequestMapping("nickName")
    @ResponseBody
    public Result checkNickname(@RequestParam String nickName,Model model) {
        User where = new User();
        where.setNickName(nickName);
        Result<List<User>> result = userFeign.findList(where);
        if (result.getData()!=null && result.getData().size()>0) {
            return new Result(false, StatusCode.ERROR, "昵称已存在");
        }
        return new Result(true, StatusCode.OK, "昵称可以使用");
    }

    /**
     * 修改保存数据
     *
     * @param map 修改后的数据
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public Result update(@RequestParam Map map) {
        if (map != null) {
            try {
                User user = BeanUtils.populate(map, User.class);
                String month = ((String) map.get("month")).length()>1?(String) map.get("month"):(0+(String) map.get("month"));
                String dateStr=(String)map.get("year")+month+(String)map.get("day");
                Date date = DateUtil.str2Date(dateStr,"yyyyMMdd");
                String username = TokenDecode.getUserInfo().get("username");
                user.setBirthday(date);
                //获得前端传入的省市区名
                Address address=new Address();
                String provinceStr = (String)map.get("province");
                String cityStr = (String)map.get("city");
                String areaStr = (String)map.get("area");
                Areas area=new Areas();
                area.setArea(areaStr);
                //通过区名查到区的对象
                List<Areas> areasList = areasFeign.findList(area).getData();
                if(areasList!=null){
                    Areas areas = areasList.get(0);
                    //获取city对象
                    Cities city = citiesFeign.findById(areas.getCityid()).getData();
                    //封装修改后的address参数
                    address.setAreaid(areas.getAreaid());
                    address.setCityid(areas.getCityid());
                    address.setProvinceid(city.getProvinceid());
                    address.setAddress(provinceStr+cityStr+areaStr);
                    //将用户地址值保存到数据库中
                    addressFeign.updateByUsername(address);
                }
                userFeign.update(user, username);
                return new Result(true, StatusCode.OK, "修改保存数据成功");
            } catch (ParseException e) {
                e.printStackTrace();
                return new Result(false, StatusCode.ERROR, "服务器异常");
            }
        }
        return new Result(false, StatusCode.ERROR, "修改保存数据失败");
    }

    /**
     * 上传图片到festDFS并将路径保存到数据库
     */
    @RequestMapping("upload")
    public Result upload(@RequestPart(value = "file",required = false) MultipartFile file) {
        try {
            if(file.isEmpty()){
                return new Result(false, StatusCode.ERROR, "上传文件不能为空");
            }
            String username = TokenDecode.getUserInfo().get("username");
            String fileUrl = (String) fileFeign.upload(file).getMessage();
            User where = new User();
            where.setHeadPic(fileUrl);
            Result result = userFeign.update(where, username);
            return new Result(true, StatusCode.OK, "上传图片成功", fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "上传图片失败");
        }
    }

}
