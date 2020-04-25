package cn.itcast.shop.product.service;

import cn.itcast.shop.dto.Cart;
import cn.itcast.shop.dto.PageBean;
import cn.itcast.shop.dto.ProductCategory;
import cn.itcast.shop.pojo.Category;
import cn.itcast.shop.pojo.Product;

import java.util.List;

public interface ProductService {

    List<Category> selectCategoryList();

    List<Product> getHotProductList(int limit);

    List<Product> getNewProductList(int limit);

    PageBean<ProductCategory> getProductPage(Integer currentPage, Integer pageSize, String cid);

    ProductCategory getProductById(String pid);

    PageBean<ProductCategory> searchProduct(Integer currentPage,Integer pageSize,String pname);

    Cart addToCart(String pid,Integer num);

    Cart delItemFromCart(String pid);

    Cart delCartAll();

    Cart getCart();
}
