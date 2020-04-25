package cn.itcast.shop.user.service;
import cn.itcast.shop.pojo.User;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String from;

    @Value("${domain}")
    private String domain;

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender javaMailSender;

    //Guava的使用
    private final Cache<String,String> cache= CacheBuilder.newBuilder()
            //设置并发级别为4，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(4)
            //设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
            .maximumSize(100)
            .expireAfterAccess(30, TimeUnit.MINUTES)
            //设置缓存移除通知
            .removalListener(new RemovalListener<String, String>() {
                @Override
                public void onRemoval(RemovalNotification<String, String> notification) {
                    String email = notification.getValue();
                    User user = userService.getUserByEmail(email);
                    if (user!=null){
                        //用户未激活
                        if (user.getState()==0){
                            userService.deleteUserByEmail(email);
                        }
                    }
                }
            }).build();

    //发送激活邮件
    @Async
    public void sendRegister(String email){
        String code = RandomStringUtils.randomAlphabetic(10);
        cache.put(code,email);
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("购物商场激活邮件");
        message.setText("http://"+domain+"/enable?code="+code);
        javaMailSender.send(message);
    }

    //激活账户
    @Transactional
    public boolean enable(String code){
        String email = cache.getIfPresent(code);
        if (email==null){
            return false;
        }
        int row = userService.enable(email);
        return row>0;
    }


}
