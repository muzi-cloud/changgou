package com.changgou.user.controller;

import com.changgou.user.pojo.User;
import com.changgou.user.service.UserService;
import entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping(value = "/security")
@CrossOrigin
public class SecurityController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;




    @RequestMapping("/phone")
    public Result phone(@RequestParam("phone") String phone) {
        User user = new User();
        user.setPhone(phone);
        List<User> users = userService.findList(user);
        if (users != null && users.size() > 0) {
            //生成验证码
            Integer validateCode = ValidateCodeUtils.generateValidateCode(4);
            //把验证码放到redis缓存里,设置失效时间 60秒
            redisTemplate.opsForValue().set(phone + "_code", validateCode, 60 * 5, TimeUnit.SECONDS);
            //发送给用户,此处打印到控制台
            System.out.println(validateCode);

            return new Result(true, StatusCode.OK, "验证码已发送,请输入验证码!");
        }
        return new Result(false, StatusCode.OK, "该手机未注册！");
    }


    @RequestMapping("/check")
    public Result check(@RequestBody Map map, HttpServletRequest request, HttpServletResponse response) {
        //获取用户传来的 电话号,验证码
        String phone = (String) map.get("phone");
        String userCode = (String) map.get("validateCode");
        Integer code = new Integer(userCode);
        //获取redis缓存中的验证码
        Integer trueCode = (Integer) redisTemplate.opsForValue().get(phone + "_code");
        if (trueCode == null && code != trueCode) {
            return new Result(false, StatusCode.OK, "验证码错误");
        }
        Cookie cookie = new Cookie("phone", phone);
        response.addCookie(cookie);
        return new Result(true, StatusCode.OK, "验证码正确");
    }

    /**
     * 未登录状态重置密码
     * @param
     * @param request
     * @return
     */
    @RequestMapping("/resetPassword")
//    @RequestParam(name = "newpassword")
    public Result resetPassword(@RequestParam(name = "pwd") String newpassword, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String phone = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("phone".equals(cookie.getName())) {
                    phone = cookie.getValue();
                }
            }
        }
        String pwd = bCryptPasswordEncoder.encode(newpassword);

       userService.updatePassword(pwd,phone);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 登录状态修改密码
     * @param
     * @return
     */
    @RequestMapping("/changePassword")
    public Result changePassword(@RequestParam("oldpwd")String oldpassword, @RequestParam("newpwd") String newpassword ){
        String username = TokenDecode.getUserInfo().get("username");
        User user = userService.findById(username);

            if (BCrypt.checkpw(oldpassword,user.getPassword())){
                String newpwd= bCryptPasswordEncoder.encode(newpassword);
                userService.changePassword(username,newpwd);
                return new Result(true, StatusCode.OK, "修改成功,请重新登录");
            }else {
                return new Result(false, StatusCode.OK, "当前密码输入错误");
            }



    }


}