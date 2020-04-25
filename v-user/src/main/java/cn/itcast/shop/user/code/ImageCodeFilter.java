package cn.itcast.shop.user.code;

import cn.itcast.shop.result.CodeMsg;
import cn.itcast.shop.result.Result;
import cn.itcast.shop.util.JsonUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class ImageCodeFilter extends OncePerRequestFilter implements InitializingBean {

    @Value("${image.code.urls}")
    private String urls;

    private Set<String> configUrls=new HashSet<>();

    //匹配字符串如/user/*
    private AntPathMatcher pathMatcher=new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        String[] split = urls.split(",");
        if (!StringUtils.isEmpty(split)){
            Arrays.stream(split).forEach(s -> {
                configUrls.add(s);
            });
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean action=false;
        for (String url:configUrls){
            if (pathMatcher.match(url,request.getRequestURI())){
                action=true;
                break;
            }
        }
        if (action){
            validate(request,response);
        }
        filterChain.doFilter(request,response);
    }

    private void validate(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        HttpSession session = request.getSession();
        ImageCode imageCode = (ImageCode) session.getAttribute(CodeKey.IMAGE_CODE);
        String code = request.getParameter("code");
        Writer out= new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
        if (imageCode==null){
            out.write(JsonUtils.objectToJson(Result.fail(CodeMsg.IMAGE_CODE_NOT_EXIST)));
            out.flush();
            out.close();
            return;
        }
        if (StringUtils.isEmpty(code)){
            out.write(JsonUtils.objectToJson(Result.fail(CodeMsg.IMAGE_CODE_NOT_EMPTY)));
            out.flush();
            out.close();
            return;
        }
        if (!code.equalsIgnoreCase(imageCode.getCode())){
            out.write(JsonUtils.objectToJson(Result.fail(CodeMsg.IMAGE_CODE_ERROR)));
            out.flush();
            out.close();
            return;
        }
        if (imageCode.isExpired()){
            out.write(JsonUtils.objectToJson(Result.fail(CodeMsg.IMAGE_CODE_EXPRIED)));
            out.flush();
            out.close();
            return;
        }
        session.removeAttribute(CodeKey.IMAGE_CODE);
    }
}
