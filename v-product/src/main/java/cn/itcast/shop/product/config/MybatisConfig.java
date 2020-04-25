package cn.itcast.shop.product.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class MybatisConfig {

    //配置pageHelper
    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        //该参数对使用RowBounds作为分页参数时有效。 当
        // 该参数设置为true时，会将RowBounds中的offset参数当成pageNum使用，
        // 可以用页码和页面大小两个参数进行分页
        p.setProperty("offsetAsPageNum","true");
        //当该参数设置为true时，使用RowBounds分页会进行 count 查询。
        p.setProperty("rowBoundsWithCount","true");
        //分页合理化参数，默认值为false。当该参数设置为true时，pageNum<=0时会查询第一页
        // ，pageNum>pages（超过总数时），会查询最后一页。
        p.setProperty("reasonable","true");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}
