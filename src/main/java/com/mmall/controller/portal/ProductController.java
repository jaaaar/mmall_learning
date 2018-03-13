package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.service.interf.IProductService;
import com.mmall.vo.ProductDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * create by YuWen
 *
 * 该Restful风格 URL
 */
@Controller
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    //前台获取产品详情
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail(Integer productId) {
        //只展示在售状态的产品
        return iProductService.getProductDetail(productId);
    }

    //前台获取产品详情
    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<ProductDetailVo> detailRestful(@PathVariable("productId") Integer productId) {
        //只展示在售状态的产品
        return iProductService.getProductDetail(productId);
    }

    //模糊搜索动态排序查看产品列表
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "orderBy", defaultValue = "") String orderBy) {
        return iProductService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
    }



    //http://localhost:8080/product手机/100012/1/20/price_asc
    //模糊搜索动态排序查看产品列表
    @RequestMapping(value = "/{keyword}/{categoryId}/{pageNum}/{pageSize}/{orderBy}",
            method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> listRestful(
            @PathVariable(value = "keyword") String keyword,
            @PathVariable(value = "categoryId") Integer categoryId,
            @PathVariable(value = "pageNum") Integer pageNum,
            @PathVariable(value = "pageSize") Integer pageSize,
            @PathVariable(value = "orderBy") String orderBy) {

        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        if (StringUtils.isBlank(orderBy)) {
            orderBy = "price_asc";
        }
        return iProductService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
    }



    @RequestMapping(value = "/category/{categoryId}/{pageNum}/{pageSize}/{orderBy}",
            method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> listRestful(
            @PathVariable(value = "categoryId") Integer categoryId,
            @PathVariable(value = "pageNum") Integer pageNum,
            @PathVariable(value = "pageSize") Integer pageSize,
            @PathVariable(value = "orderBy") String orderBy) {

        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        if (StringUtils.isBlank(orderBy)) {
            orderBy = "price_asc";
        }
        return iProductService.getProductByKeywordCategory(StringUtils.EMPTY,
                categoryId, pageNum, pageSize, orderBy);
    }



    @RequestMapping(value = "/keyword/{keyword}/{pageNum}/{pageSize}/{orderBy}",
            method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> listRestful(
            @PathVariable(value = "keyword") String keyword,
            @PathVariable(value = "pageNum") Integer pageNum,
            @PathVariable(value = "pageSize") Integer pageSize,
            @PathVariable(value = "orderBy") String orderBy) {

        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        if (StringUtils.isBlank(orderBy)) {
            orderBy = "price_asc";
        }
        return iProductService.getProductByKeywordCategory(keyword, null,
                pageNum, pageSize, orderBy);
    }
}
