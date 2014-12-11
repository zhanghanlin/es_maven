package com.search.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Component;

import com.search.bean.Product;

/**
 * 商品DAO
 * 
 * @author zhanghanlin
 *
 */
@Component
public class ProductDao extends SqlSessionDaoSupport implements
		IBeanDao<Product> {

	@Override
	public List<Product> searchAll() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("Product.searchAll");
	}

	@Override
	public List<Product> searchByIds(List<String> param) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("Product.searchByIds", param);
	}
}