package com.search.job;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.io.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search.es.ElasticsearchNodeFactoryBean;
import com.search.util.Constants;

/**
 * Job
 * 
 * @author zhanghanlin
 *
 */
@Service
public class Job {

	@Autowired
	ElasticsearchNodeFactoryBean esNode;

	private Client esClient;

	public Client getEsClient() {
		if (esClient == null) {
			try {
				esClient = esNode.getObject().client();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return esClient;
	}

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
		init(productData.beanType());
		productData.run(list);
	}

	/**
	 * 刷新商品
	 * 
	 * @param list
	 */
	public void flushBrand(List<String> list) {
		init(brandData.beanType());
		brandData.run(list);
	}

	/**
	 * 初始化类型
	 */
	private void init(String type) {
		try {
			String mapping = Streams
					.copyToStringFromClasspath(Constants.ES_SEARCH_JSON_PATH
							+ type + ".json");
			try {
				@SuppressWarnings("unused")
				PutMappingResponse mappingResponse = getEsClient().admin()
						.indices()
						.preparePutMapping(Constants.GLOBAL_INDEX_NAME)
						.setType(type).setSource(mapping).execute().actionGet();
			} catch (ElasticsearchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
