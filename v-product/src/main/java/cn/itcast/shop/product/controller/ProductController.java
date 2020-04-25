package cn.itcast.shop.product.controller;

import cn.itcast.shop.dto.Cart;
import cn.itcast.shop.dto.PageBean;
import cn.itcast.shop.dto.ProductCategory;
import cn.itcast.shop.pojo.Category;
import cn.itcast.shop.pojo.Product;
import cn.itcast.shop.product.service.ProductService;
import cn.itcast.shop.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/category")
    public Result category(){
        List<Category> categoryList = productService.selectCategoryList();
        return Result.success(categoryList);
    }

    @GetMapping("/hotProduct")
    public Result getHotProduct(){
        List<Product> productList = productService.getHotProductList(10);
        return Result.success(productList);
    }

    @GetMapping("/newProduct")
    public Result getNewProduct(){
        List<Product> productList = productService.getNewProductList(10);
        return Result.success(productList);
    }

    @GetMapping("/categoryProduct")
    public Result getProductByCategory(@RequestParam("cid") String cid,
                                       @RequestParam(value = "currentPage",defaultValue = "1")Integer currentPage,
                                       @RequestParam(value = "pageSize",defaultValue = "12")Integer pageSize){
        PageBean<ProductCategory> pageBean = productService.getProductPage(currentPage, pageSize, cid);
        return Result.success(pageBean);
    }

    @GetMapping("/productInfo")
    public Result getProductInfo(@RequestParam("pid")String pid){
        return Result.success(productService.getProductById(pid));
    }

    @PostMapping("/addToCart")
    public Result addToCart(@RequestParam("pid") String pid,@RequestParam("num") Integer num){
        Cart cart = productService.addToCart(pid, num);
        return Result.success(cart);
    }

    @PostMapping("/delItemFromCart")
    public Result delItemFromCart(@RequestParam("pid")String pid){
        Cart cart = productService.delItemFromCart(pid);
        return Result.success(cart);
    }

    @PostMapping("/delCartAll")
    public Result delCartAll(){
        Cart cart = productService.delCartAll();
        return Result.success(cart);
    }

    @GetMapping("/getCart")
    public Result getCart(){
        Cart cart = productService.getCart();
        return Result.success(cart);
    }

    @GetMapping("/search")
    public Result search(@RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage,
                         @RequestParam(value = "pageSize",defaultValue = "12") Integer pageSize,
                         @RequestParam("pname") String pname){
        PageBean<ProductCategory> pageBean = productService.searchProduct(currentPage, pageSize, pname);
        return Result.success(pageBean);
    }
}
