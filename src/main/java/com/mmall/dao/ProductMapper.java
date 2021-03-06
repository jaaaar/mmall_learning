package com.mmall.dao;

import com.mmall.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectList();

    List<Product> selectByNameAndProductId(
            @Param("productName") String productName,
            @Param("productId") Integer productId);

    List<Product> selectByNameAndCategoryIds(
            @Param("productName") String productName,
            @Param("categoryIdList") List<Integer> categoryIdList);


    //查询某个id 商品的库存数量,返回值必须为Integer 因为int不能为null, 考虑到商品已经删除的情况
    Integer selectStockByProductId(Integer productId);
}