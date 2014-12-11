package com.search.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Job
 * 
 * @author zhanghanlin
 *
 */
@Service
public class Job {

	@Autowired
	ProductData productData;

	@Autowired
	BrandData brandData;

	/**
	 * 刷新全部
	 */
	public void flushAll() {
		flushProduct(null);
		flushBrand(null);
	}

	/**
	 * 刷新商品
	 * 
	 * @param list
	 */
	public void flushProduct(List<String> list) {
		productData.run(list);
	}

	/**
	 * 刷新商品
	 * 
	 * @param list
	 */
	public void flushBrand(List<String> list) {
		brandData.run(list);
	}
}
