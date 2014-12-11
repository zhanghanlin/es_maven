package com.search.es;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.search.util.Constants;

/**
 * *Facade父类
 * @author zhanghanlin
 *
 * @param <T>
 */
public abstract class ExtendFacade<T> {

	@Autowired
	@Qualifier("esNode")
	private ElasticsearchNodeFactoryBean esNodeFactory;

	public Client getClient() {
		try {
			Client c = esNodeFactory.getObject().client();
			return c;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public SearchRequestBuilder getBuilder() {
		return getClient().prepareSearch(Constants.GLOBAL_INDEX_NAME);
	}
	
	/**
	 * 根据Id得到对象
	 * @param id
	 * @return
	 */
	public abstract T get(Long id);
}
