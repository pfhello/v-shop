package cn.itcast.shop.product.mapper;

import cn.itcast.shop.dto.ProductCategory;
import cn.itcast.shop.pojo.Category;
import cn.itcast.shop.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {

    List<Category> selectCategoryList();

    List<Product> getHotProductList(int limit);

    List<Product> getNewProductList(int limit);

    List<ProductCategory> getProductByCid(String cid);

    ProductCategory getProductById(String pid);

    List<ProductCategory> searchProduct(String pname);
}
