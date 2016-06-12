package com.lionxxw.babasport.core.service.product.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.lionxxw.babasport.core.dao.product.ProductDao;
import com.lionxxw.babasport.core.dto.product.Img;
import com.lionxxw.babasport.core.dto.product.Product;
import com.lionxxw.babasport.core.dto.product.Sku;
import com.lionxxw.babasport.core.query.product.ImgQuery;
import com.lionxxw.babasport.core.query.product.ProductQuery;
import com.lionxxw.babasport.core.service.product.ImgService;
import com.lionxxw.babasport.core.service.product.ProductService;
import com.lionxxw.babasport.core.service.product.SkuService;
import com.lionxxw.common.model.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商品事务层
 * @author lixu
 * @Date [2014-3-27 下午03:31:57]
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Resource
	ProductDao productDao;
	@Resource
	ImgService imgService;
	@Resource
	SkuService skuService;
	
	/**
	 * 插入数据库
	 * 
	 * @return
	 */
	public Integer addProduct(Product product) {
		//商品编号
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String no = df.format(new Date());
		product.setNo(no);
		//添加时间
		product.setCreateTime(new Date());
		//影响到行数   返回商品ID
		//商品保存  
		Integer i = productDao.addProduct(product);
		//1:商品   图片   sku
		//2:图片
		//1)设置图片商品ID
		product.getImg().setProductId(product.getId());
		//2)
		product.getImg().setIsDef(1);
		imgService.addImg(product.getImg());
		//3:保存Sku    9,13,...
		//  S M  ...
		//实例化一个Sku对象
		Sku sku = new Sku();
		//商品ID
		sku.setProductId(product.getId());
		//运费
		sku.setDeliveFee(10.00);
		//售价
		sku.setSkuPrice(0.00);
		//市场价
		sku.setMarketPrice(0.00);
		//库存
		sku.setStockInventory(0);
		//购买限制
		sku.setSkuUpperLimit(0);
		//添加时间
		sku.setCreateTime(new Date());
		//是否最新
		sku.setLastStatus(1);
		//商品
		sku.setSkuType(1);
		//销量
		sku.setSales(0);
		for(String color : product.getColor().split(",")){
			//颜色ID
			sku.setColorId(Integer.parseInt(color));
			
			for(String size : product.getSize().split(",")){
				//尺码
				sku.setSize(size);
				//保存SKu
				skuService.addSku(sku);
			}
			
		}
		return i;
	}

	/**
	 * 根据主键查找
	 */
	@Transactional(readOnly = true)
	public Product getProductByKey(Integer id) {
		Product product = productDao.getProductByKey(id);
		ImgQuery imgQuery = new ImgQuery();
		imgQuery.setProductId(product.getId());
		imgQuery.setIsDef(1);
		List<Img> imgs = imgService.getImgList(imgQuery);
		product.setImg(imgs.get(0));
		
		return product;
	}
	
	@Transactional(readOnly = true)
	public List<Product> getProductsByKeys(List<Integer> idList) {
		return productDao.getProductsByKeys(idList);
	}

	/**
	 * 根据主键删除
	 * 
	 * @return
	 */
	public Integer deleteByKey(Integer id) {
		return productDao.deleteByKey(id);
	}

	public Integer deleteByKeys(List<Integer> idList) {
		return productDao.deleteByKeys(idList);
	}

	/**
	 * 根据主键更新
	 * 
	 * @return
	 */
	public Integer updateProductByKey(Product product) {
		return productDao.updateProductByKey(product);
	}
	
	@Transactional(readOnly = true)
	public PageResult<Product> getProductListWithPage(ProductQuery productQuery) {
		int total = productDao.getProductListCount(productQuery);
		if (total > 0){
			List<Product> products = productDao.getProductListWithPage(productQuery);
			//遍历商品  添加图片
			for(Product product : products){
				ImgQuery imgQuery = new ImgQuery();
				imgQuery.setProductId(product.getId());
				imgQuery.setIsDef(1);
				List<Img> imgs = imgService.getImgList(imgQuery);
				product.setImg(imgs.get(0));
			}
			return new PageResult<Product>(total, productQuery.getPageSize(), products);
		}
		return null;
	}
	
	@Transactional(readOnly = true)
	public List<Product> getProductList(ProductQuery productQuery) {
		return productDao.getProductList(productQuery);
	}

}