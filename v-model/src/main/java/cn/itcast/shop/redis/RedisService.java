package cn.itcast.shop.redis;

import cn.itcast.shop.context.UserContext;
import cn.itcast.shop.pojo.User;
import cn.itcast.shop.util.CookieUtils;
import cn.itcast.shop.util.JsonUtils;
import cn.itcast.shop.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public User getUser(HttpServletResponse response,String token){
        String userToken=UserKey.USER_TOKEN+":"+token;
        Boolean hasKey = redisTemplate.hasKey(userToken);
        if (hasKey){
            String json = redisTemplate.opsForValue().get(userToken);
            User user = JsonUtils.jsonToPojo(json, User.class);
            redisTemplate.expire(userToken,UserKey.USER_EXPIRE,TimeUnit.HOURS);
            CookieUtils.addCookie(response,"127.0.0.1","/",UserKey.USER_TOKEN,token,-1,false);
            return user;
        }
        return null;
    }

    public void saveUser(HttpServletResponse response,User user){
        String uuid = UUIDUtil.getUUID();
        CookieUtils.addCookie(response,"127.0.0.1","/",UserKey.USER_TOKEN,uuid,-1,false);
        String token=UserKey.USER_TOKEN+":"+uuid;
        redisTemplate.opsForValue().set(token,JsonUtils.objectToJson(user),UserKey.USER_EXPIRE, TimeUnit.HOURS);
    }

    public void deleteUser(HttpServletResponse response,String token){
        String userToken=UserKey.USER_TOKEN+":"+token;
        redisTemplate.delete(userToken);
        CookieUtils.deleteCookie(response,token);
        UserContext.removeUser();
    }
}
