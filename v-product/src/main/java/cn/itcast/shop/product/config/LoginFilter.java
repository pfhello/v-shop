package cn.itcast.shop.product.config;

import cn.itcast.shop.context.UserContext;
import cn.itcast.shop.pojo.User;
import cn.itcast.shop.redis.RedisService;
import cn.itcast.shop.redis.UserKey;
import cn.itcast.shop.util.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFilter extends OncePerRequestFilter {

    @Autowired
    private RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String paramToken = request.getParameter(UserKey.USER_TOKEN);
        String cookieToken = CookieUtils.getCookieValue(request, UserKey.USER_TOKEN);
        User user=UserContext.getUser();
        if (user==null){
            if (!StringUtils.isEmpty(paramToken)||!StringUtils.isEmpty(cookieToken)){
                String token=StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
                user=redisService.getUser(response,token);
                UserContext.setUser(user);
            }
        }
        filterChain.doFilter(request,response);
    }
}
