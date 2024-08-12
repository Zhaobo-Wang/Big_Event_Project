package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.JwtUtil;
import com.itheima.utils.Md5Util;
import com.itheima.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$")String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //查询用户
        User u = userService.findByUserName(username);
        if (u == null) {
           //没被占用
            // 注册
           userService.register(username,password);
           return Result.success();
        }else{
            //被占用
            return Result.error("用户名已被占用");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$")String username, @Pattern(regexp = "^\\S{5,16}$") String password){
            //根据用户名查询user
            User loginUser = userService.findByUserName(username);
            //判断是否查询得到
            if(loginUser == null){
                return Result.error("用户名错误");
            }
            //判断密码是否正确
            if(Md5Util.getMD5String(password).equals(loginUser.getPassword())){
                //登陆成功
                //生成token
                Map<String,Object> claims = new HashMap<>();
                claims.put("id",loginUser.getId());
                claims.put("username",loginUser.getUsername());
                String token = JwtUtil.genToken(claims);
                return Result.success(token);
            }

            return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result <User> userInfo(@RequestHeader(name = "Authorization") String token){
        //先解析token 获取用户的username
        Map<String, Object> map = JwtUtil.parseToken(token);

        String username = (String)map.get("username");
        //查询数据库中user的其他信息
        User user = userService.findByUserName(username);

        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }


    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params){
        //1.校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if(!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("传参错误");
        }

        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUserName(username);
        if(!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码输入错误");
        }

        if(!newPwd.equals(rePwd)){
            return Result.error("新密码两次输入不一致");
        }
        //调用service完成密码更新
        userService.updatePwd(newPwd);
        return Result.success();
    }


}
