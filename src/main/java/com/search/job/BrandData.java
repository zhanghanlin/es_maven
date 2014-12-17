package com.search.job;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search.bean.Brand;
import com.search.dao.BrandDao;

@Service
public class BrandData extends ExtendData<Brand> {

	@Autowired
	BrandDao brandDao;

	/**
	 * 数据集合
	 */
	List<Brand> list = new ArrayList<Brand>();

	@Override
	protected void businessPut(Brand t) throws Exception {
		put(t.getId(), t);
	}

	@Override
	protected List<Brand> getList(List<String> param) {
		if (null != param && param.size() > 0) {
			list = brandDao.searchByIds(param);
		} else {
			list = brandDao.searchAll();
		}
		return list;
	}

	@Override
	protected String beanType() {
		// TODO Auto-generated method stub
		return "brand";
	}
}
