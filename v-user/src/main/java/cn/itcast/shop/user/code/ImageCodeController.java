package cn.itcast.shop.user.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ImageCodeController {

    @Autowired
    private ImageCodeGenerator generator;

    @GetMapping("code/image")
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //生成验证码
        ImageCode imageCode = generator.generate(request);
        //将验证码保存到session中
        request.getSession().setAttribute(CodeKey.IMAGE_CODE,imageCode);
        //将图片响应到前端
        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
    }
}
