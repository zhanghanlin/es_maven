package com.search.job;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.search.bean.Product;
import com.search.dao.ProductDao;
import com.search.util.Constants;
import com.sun.jersey.api.client.WebResource;

/**
 * 刷新Product
 * 
 * @author zhanghanlin
 *
 */
@Service
public class ProductData extends ExtendData<Product> {

	@Autowired
	ProductDao productDao;

	/**
	 * 数据集合
	 */
	List<Product> objList = new ArrayList<Product>();

	@Override
	public void put(Product t) throws Exception {
		Long id = t.getId();
		WebResource wr = client.path("/" + Constants.GLOBAL_INDEX_NAME
				+ "/product/" + id);
		String pjson = JSON.toJSON(t).toString();
		wr.put(pjson);
	}

	@Override
	public List<Product> getList(List<String> param) {
		if (null != param && param.size() > 0) {
			objList = productDao.searchByIds(param);
		} else {
			objList = productDao.searchAll();
		}
		return objList;
	}
}
