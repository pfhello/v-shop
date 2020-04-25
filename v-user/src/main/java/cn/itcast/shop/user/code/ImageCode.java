package cn.itcast.shop.user.code;

import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

@Getter
@Setter
public class ImageCode {

    private String code;

    private LocalDateTime expireTime;

    private BufferedImage image;

    public ImageCode(String code, int expireTime, BufferedImage image) {
        this.code = code;
        this.expireTime =LocalDateTime.now().plusSeconds(expireTime);
        this.image = image;
    }

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(expireTime);
    }
}
