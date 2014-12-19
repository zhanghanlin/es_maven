package com.search.es.bussiness;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
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

	private final String BEAN_TYPE = "brand";

	@Override
	public Brand get(Long id) {
		Brand brand = null;
		GetResponse gp = getClient()
				.prepareGet(Constants.GLOBAL_INDEX_NAME, BEAN_TYPE,
						id.toString()).execute().actionGet();
		if (gp.isExists()) {
			brand = JSON.parseObject(gp.getSourceAsString(), Brand.class);
		} else {
			logger.info("Not Exists : " + id);
		}
		return brand;
	}

	/**
	 * 联想搜索
	 * 
	 * @param key
	 * @return
	 */
	public List<Brand> associateWord(String key) {
		BoolQueryBuilder bool = new BoolQueryBuilder();
		MultiMatchQueryBuilder builder = QueryBuilders.multiMatchQuery(key,
				"name.name_ik", "name.name_pinyin",
				"name.name_pinyin_first_letter",
				"name.name_lowercase_keyword_ngram_min_size1").operator(
				MatchQueryBuilder.Operator.AND);
		bool.must(builder);
		SearchRequestBuilder srb = getBuilder().setTypes(BEAN_TYPE);
		srb.setQuery(bool).setFrom(0).setSize(10);
		SearchResponse searchResponse = srb.execute().actionGet();
		getClient().close();
		final SearchHits hits = searchResponse.getHits();
		List<Brand> items = new ArrayList<Brand>();
		for (final SearchHit searchHit : hits.getHits()) {
			final Brand brand = JSON.parseObject(searchHit.source(),
					Brand.class);
			items.add(brand);
		}
		return items;
	}

	/**
	 * all brand
	 * 
	 * @return
	 */
	public SearchResult<Brand> getAll() {
		final SearchResult<Brand> searchResult = new SearchResult<Brand>();
		SearchRequestBuilder srb = getBuilder().setTypes(BEAN_TYPE);
		srb.addSort("id", SortOrder.DESC).setFrom(0).setSize(9999);
		SearchResponse searchResponse = srb.execute().actionGet();
		getClient().close();
		final SearchHits hits = searchResponse.getHits();
		List<Brand> items = new ArrayList<Brand>();
		for (final SearchHit searchHit : hits.getHits()) {
			final Brand brand = JSON.parseObject(searchHit.source(),
					Brand.class);
			items.add(brand);
		}
		searchResult.setTotalHits((int) hits.getTotalHits());
		searchResult.setItems(items);
		return searchResult;
	}
}
