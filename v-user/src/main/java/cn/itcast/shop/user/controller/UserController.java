package cn.itcast.shop.user.controller;

import cn.itcast.shop.exception.GlobalException;
import cn.itcast.shop.pojo.User;
import cn.itcast.shop.redis.RedisService;
import cn.itcast.shop.redis.UserKey;
import cn.itcast.shop.result.CodeMsg;
import cn.itcast.shop.result.Result;
import cn.itcast.shop.user.service.MailService;
import cn.itcast.shop.user.service.UserService;
import cn.itcast.shop.util.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private RedisService redisService;

    @PostMapping("/login")
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletResponse response){
        if (username==null){
            throw new GlobalException(CodeMsg.USERNAME_NOT_EMPTY);
        }
        if (password==null){
            throw new GlobalException(CodeMsg.PASSWORD_NOT_EMPTY);
        }
        User user = userService.getUserByUsername(username);
        if (user==null){
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        if (user.getState()==0){
            throw new GlobalException(CodeMsg.USER_NOT_VERIFY);
        }
        redisService.saveUser(response,user);
        return Result.success(user);
    }

    @PostMapping("/register")
    public Result register(User user){
        if (user==null){
            throw new GlobalException(CodeMsg.USER_PARAMER_EMPTY);
        }
        Result result = checkUsername(user.getUsername());
        if (result.getCode()!=0){
            throw new GlobalException(CodeMsg.USERNAME_EXIST);
        }
        Result checkEmail = checkEmail(user.getEmail());
        if (checkEmail.getCode()!=0){
            throw new GlobalException(CodeMsg.EMAIL_EXIST);
        }
        String password = user.getPassword();
        if (password==null){
            throw new GlobalException(CodeMsg.PASSWORD_NOT_EMPTY);
        }
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userService.addUser(user);
        return  Result.success();
    }

    @GetMapping("/checkUsername")
    public Result checkUsername(@RequestParam("username") String username){
        if (username==null){
            throw new GlobalException(CodeMsg.USERNAME_NOT_EMPTY);
        }
        User user = userService.getUserByUsername(username);
        if (user!=null){
            return Result.fail(CodeMsg.USERNAME_EXIST);
        }
        return Result.success();
    }

    @GetMapping("/checkEmail")
    public Result checkEmail(@RequestParam("email") String email){
        if (email==null){
            throw new GlobalException(CodeMsg.EMAIL_NOT_EMPTY);
        }
        User user = userService.getUserByEmail(email);
        if (user!=null){
            return Result.fail(CodeMsg.EMAIL_EXIST);
        }
        return Result.success();
    }

    @GetMapping("/enable")
    public Result enable(String code){
        boolean enable = mailService.enable(code);
        if (enable){
            return Result.success();
        }
        return Result.fail(CodeMsg.EMAIL_ENABLE_FAIL);
    }

    @GetMapping("/logout")
    public Result logout(HttpServletRequest request,HttpServletResponse response){
        String paramToken = request.getParameter(UserKey.USER_TOKEN);
        String cookieToken = CookieUtils.getCookieValue(request, UserKey.USER_TOKEN);
        if (!StringUtils.isEmpty(paramToken)||!StringUtils.isEmpty(cookieToken)){
            String token=StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
            redisService.deleteUser(response,token);
        }
        return Result.success();
    }
}
