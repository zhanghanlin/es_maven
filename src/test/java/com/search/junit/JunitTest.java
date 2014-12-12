package com.search.junit;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.search.bean.Product;
import com.search.es.bussiness.ProductFacadeImpl;
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
	@Qualifier("productFacade")
	ProductFacadeImpl productFacade;

	@Test
	public void test() {
		List<String> pidList = new ArrayList<String>();
		pidList.add("8260");
		// job.flushProduct(pidList);
		Product product = productFacade.get(8260L);
		System.out.println(JSONObject.toJSONString(product));
	}
}