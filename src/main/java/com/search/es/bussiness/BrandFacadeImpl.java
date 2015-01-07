package com.search.es.bussiness;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightField;
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
	 * 自动完成
	 * 
	 * @param key
	 *            搜索Key
	 * @return List<Brand>
	 */
	public List<Brand> associateWord(String key) {
		return search(key, 10).getItems();
	}

	/**
	 * 搜索使用
	 * 
	 * @param key
	 *            搜索Key
	 * @param count
	 *            返回数据数
	 * @return SearchResult<Brand>
	 */
	public SearchResult<Brand> search(String key, int count) {
		final SearchResult<Brand> searchResult = new SearchResult<Brand>();
		BoolQueryBuilder bool = new BoolQueryBuilder();
		MultiMatchQueryBuilder builder = QueryBuilders.multiMatchQuery(key,
				highlightedFields).operator(MatchQueryBuilder.Operator.AND);
		bool.must(builder);
		SearchRequestBuilder srb = getBuilder().setTypes(BEAN_TYPE);
		// 设置查询类型
		// 1.SearchType.DFS_QUERY_THEN_FETCH = 精确查询
		// 2.SearchType.SCAN = 扫描查询,无序
		srb.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		// 设置查询条件
		srb.setQuery(bool);
		// 分页应用
		srb.setFrom(0).setSize(count);
		// 设置是否按查询匹配度排序
		srb.setExplain(false);
		// 设置高亮显示
		for (int i = 0; i < highlightedFields.length; i++) {
			srb.addHighlightedField(highlightedFields[i]);
		}
		srb.setHighlighterPreTags("<span style='color:red'>");
		srb.setHighlighterPostTags("</span>");
		SearchResponse searchResponse = srb.execute().actionGet();
		getClient().close();
		final SearchHits hits = searchResponse.getHits();
		List<Brand> items = new ArrayList<Brand>();
		for (final SearchHit searchHit : hits.getHits()) {
			final Brand brand = JSON.parseObject(searchHit.getSourceAsString(),
					Brand.class);
			// 获取对应的高亮域
			Map<String, HighlightField> hig = searchHit.highlightFields();
			// 从设定的高亮域中取得指定域
			HighlightField nameField = getHighlightField(hig);
			if (nameField != null) {
				// 取得定义的高亮标签
				Text[] nameTexts = nameField.fragments();
				String name = "";
				// 为name串值增加自定义的高亮标签
				for (Text text : nameTexts) {
					name += text;
				}
				// 将追加了高亮标签的串值重新填充到对应的对象
				brand.setName(name);
			}
			items.add(brand);
		}
		searchResult.setTotalHits((int) hits.getTotalHits());
		searchResult.setItems(items);
		return searchResult;
	}

	/**
	 * HighlightField 高亮
	 * 
	 * @param hig
	 * @return
	 */
	private HighlightField getHighlightField(Map<String, HighlightField> hig) {
		HighlightField field = hig.get(highlightedFields[0]);
		// 设置高亮显示
		// for (int i = 0; i < highlightedFields.length; i++) {
		// field = hig.get(highlightedFields[i]);
		// if (field != null) {
		// return field;
		// }
		// }
		return field;
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
