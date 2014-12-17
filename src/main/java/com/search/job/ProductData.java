package com.search.job;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search.bean.Product;
import com.search.dao.ProductDao;

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
	List<Product> list = new ArrayList<Product>();

	@Override
	protected void businessPut(Product t) throws Exception {
		put(t.getId(), t);
	}

	@Override
	protected List<Product> getList(List<String> param) {
		if (null != param && param.size() > 0) {
			list = productDao.searchByIds(param);
		} else {
			list = productDao.searchAll();
		}
		return list;
	}

	@Override
	protected String beanType() {
		// TODO Auto-generated method stub
		return "product";
	}
}
