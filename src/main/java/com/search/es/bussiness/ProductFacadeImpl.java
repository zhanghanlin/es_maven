package com.search.es.bussiness;

import org.elasticsearch.action.get.GetResponse;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.search.bean.Product;
import com.search.es.ExtendFacade;
import com.search.es.ProductFacade;
import com.search.util.Constants;

/**
 * 商品业务
 * 
 * @author zhanghanlin
 *
 */
@Service(ProductFacade.BEAN_ID)
public class ProductFacadeImpl extends ExtendFacade<Product> implements
		ProductFacade {

	@Override
	public Product get(Long id) {
		Product product = null;
		GetResponse gp = getClient()
				.prepareGet(Constants.GLOBAL_INDEX_NAME, "product",
						id.toString()).execute().actionGet();
		if (gp.isExists()) {
			product = JSON.parseObject(gp.getSourceAsString(), Product.class);
		} else {
			logger.info("Not Exists : " + id);
		}
		return product;
	}
}