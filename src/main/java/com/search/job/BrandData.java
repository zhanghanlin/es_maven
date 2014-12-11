package com.search.job;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.search.bean.Brand;
import com.search.dao.BrandDao;
import com.search.util.Constants;
import com.sun.jersey.api.client.WebResource;

@Service
public class BrandData extends ExtendData<Brand> {

	@Autowired
	BrandDao brandDao;

	/**
	 * 数据集合
	 */
	List<Brand> objList = new ArrayList<Brand>();

	@Override
	public void put(Brand t) throws Exception {
		Long id = t.getId();
		WebResource wr = client.path("/" + Constants.GLOBAL_INDEX_NAME
				+ "/brand/" + id);
		String pjson = JSON.toJSON(t).toString();
		wr.put(pjson);
	}

	@Override
	public List<Brand> getList(List<String> param) {
		if (null != param && param.size() > 0) {
			objList = brandDao.searchByIds(param);
		} else {
			objList = brandDao.searchAll();
		}
		return objList;
	}
}
