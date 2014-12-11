package com.search.es.bussiness;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.get.GetResponse;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.search.bean.Brand;
import com.search.es.BrandFacade;
import com.search.es.ExtendFacade;
import com.search.util.Constants;

/**
 * 品牌业务
 * 
 * @author zhanghanlin
 *
 */
@Service(BrandFacade.BEAN_ID)
public class BrandFacadeImpl extends ExtendFacade<Brand> implements BrandFacade {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public Brand get(Long id) {
		Brand brand = null;
		GetResponse gp = getClient()
				.prepareGet(Constants.GLOBAL_INDEX_NAME, "brand", id.toString())
				.execute().actionGet();
		if (gp.isExists()) {
			brand = JSON.parseObject(gp.getSourceAsString(), Brand.class);
		} else {
			logger.info("Not Exists : " + id);
		}
		return brand;
	}

}
