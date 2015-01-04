package com.search.junit;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.search.bean.Brand;
import com.search.es.bussiness.BrandFacadeImpl;
import com.search.es.bussiness.ProductFacadeImpl;
import com.search.es.bussiness.SearchResult;
import com.search.job.Job;

/**
 * JunitTest 测试类
 * 
 * @author zhanghanlin
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf_spring/applicationContext*.xml" })
public class JunitTest {

	@Autowired
	Job job;

	@Autowired
	ProductFacadeImpl productFacade;

	@Autowired
	BrandFacadeImpl brandFacade;

	@Test
	public void test() {
		List<String> list = new ArrayList<String>();
		job.flushBrand(list);
		SearchResult<Brand> srResult = brandFacade.getAll();
		System.out.println(srResult.getTotalHits());
		String key = "test";
		List<Brand> data = brandFacade.associateWord(key);
		for (Brand brand : data) {
			System.out.println(brand.getName());
		}
	}
}