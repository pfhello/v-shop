package cn.itcast.shop.product.service.impl;

import cn.itcast.shop.context.UserContext;
import cn.itcast.shop.dto.Cart;
import cn.itcast.shop.dto.PageBean;
import cn.itcast.shop.dto.ProductCartItem;
import cn.itcast.shop.dto.ProductCategory;
import cn.itcast.shop.pojo.Category;
import cn.itcast.shop.pojo.Product;
import cn.itcast.shop.pojo.User;
import cn.itcast.shop.product.mapper.ProductMapper;
import cn.itcast.shop.product.service.ProductService;
import cn.itcast.shop.redis.UserKey;
import cn.itcast.shop.util.JsonUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public List<Category> selectCategoryList() {
        return productMapper.selectCategoryList();
    }

    @Override
    public List<Product> getHotProductList(int limit) {
        return productMapper.getHotProductList(limit);
    }

    @Override
    public List<Product> getNewProductList(int limit) {
        return productMapper.getNewProductList(limit);
    }

    @Override
    public PageBean<ProductCategory> getProductPage(Integer currentPage, Integer pageSize, String cid) {
        PageHelper.startPage(currentPage,pageSize);
        List<ProductCategory> productList = productMapper.getProductByCid(cid);
        PageInfo<ProductCategory> info=new PageInfo<>(productList);
        PageBean<ProductCategory> pageBean=new PageBean<>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        pageBean.setTotal(info.getTotal());
        pageBean.setData(info.getList());
        return pageBean;
    }

    @Override
    public ProductCategory getProductById(String pid) {
        return productMapper.getProductById(pid);
    }

    @Override
    public PageBean<ProductCategory> searchProduct(Integer currentPage,Integer pageSize,String pname) {
        PageHelper.startPage(currentPage,pageSize);
        List<ProductCategory> list = productMapper.searchProduct(pname);
        PageInfo<ProductCategory> info=new PageInfo<>(list);
        PageBean<ProductCategory> pageBean=new PageBean<>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        pageBean.setTotal(info.getTotal());
        pageBean.setData(info.getList());
        return pageBean;
    }

    @Override
    public Cart addToCart(String pid,Integer num) {
        ProductCategory product = getProductById(pid);
        User user = UserContext.getUser();
        String token= UserKey.USER_CART+":"+user.getUid();
        Boolean hasKey = redisTemplate.hasKey(token);
        Cart cart=null;
        ProductCartItem item=new ProductCartItem();
        BeanUtils.copyProperties(product,item);
        item.setNum(num);
        if (hasKey){
            String json = redisTemplate.opsForValue().get(token);
            cart = JsonUtils.jsonToPojo(json, Cart.class);
            List<ProductCartItem> list = cart.getList();
            List<String> pidList = list.stream().map(i -> i.getPid()).collect(Collectors.toList());
            if (pidList.contains(pid)){
                for (int i=0;i<list.size();i++){
                    ProductCartItem cartItem = list.get(i);
                    if (cartItem.getPid().equals(product.getPid())){
                        cartItem.setNum(cartItem.getNum()+num);
                        cart.setTotalPrice(cart.getTotalPrice()+product.getShopPrice()*num);
                        break;
                    }
                }
            }else {
                cart.setTotalPrice(cart.getTotalPrice()+product.getShopPrice()*num);
                list.add(item);
            }

        }else {
            cart=new Cart();
            cart.setTotalPrice(product.getShopPrice()*num);
            cart.getList().add(item);
        }
        redisTemplate.opsForValue().set(token,JsonUtils.objectToJson(cart),UserKey.USER_CART_EXPIRE, TimeUnit.DAYS);
        return cart;
    }

    @Override
    public Cart delItemFromCart(String pid) {
        User user = UserContext.getUser();
        String token= UserKey.USER_CART+":"+user.getUid();
        Boolean hasKey = redisTemplate.hasKey(token);
        if (hasKey){
            String json = redisTemplate.opsForValue().get(token);
            Cart cart = JsonUtils.jsonToPojo(json, Cart.class);
            List<ProductCartItem> list = cart.getList();
            for (int i = 0; i <list.size() ; i++) {
                ProductCartItem item = list.get(i);
                if (item.getPid().equals(pid)){
                    list.remove(item);
                    cart.setTotalPrice(cart.getTotalPrice()-item.getShopPrice()*item.getNum());
                }
            }
            redisTemplate.opsForValue().set(token,JsonUtils.objectToJson(cart),UserKey.USER_CART_EXPIRE, TimeUnit.DAYS);
            return cart;
        }
        return new Cart();
    }

    @Override
    public Cart delCartAll() {
        User user = UserContext.getUser();
        String token= UserKey.USER_CART+":"+user.getUid();
        redisTemplate.delete(token);
        return new Cart();
    }

    @Override
    public Cart getCart() {
        User user = UserContext.getUser();
        String token= UserKey.USER_CART+":"+user.getUid();
        Boolean hasKey = redisTemplate.hasKey(token);
        if (hasKey){
            String json = redisTemplate.opsForValue().get(token);
            return JsonUtils.jsonToPojo(json,Cart.class);
        }
        return new Cart();
    }

}
