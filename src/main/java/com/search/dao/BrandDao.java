package com.search.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Component;

import com.search.bean.Brand;

/**
 * 品牌DAO
 * 
 * @author zhanghanlin
 *
 */
@Component
public class BrandDao extends SqlSessionDaoSupport implements IObjectDao<Brand> {

	@Override
	public List<Brand> searchAll() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("Brand.searchAll");
	}

	@Override
	public List<Brand> searchByIds(List<String> param) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("Brand.searchByIds", param);
	}
}