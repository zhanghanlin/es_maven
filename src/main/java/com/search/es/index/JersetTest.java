package com.search.es.index;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.alibaba.fastjson.JSON;
import com.search.bean.Product;
import com.search.util.Constants;

public class JersetTest {

	/**
	 * settings
	 */
	private static Settings defaultSettings = ImmutableSettings
			.settingsBuilder().put("cluster.name", Constants.GLOBAL_INDEX_NAME)
			.put("discovery.zen.ping.multicast.enabled", true).build();

	/**
	 * create node
	 */
	final static Node node = NodeBuilder.nodeBuilder().client(true)
			.settings(defaultSettings).build();

	static Settings settings = ImmutableSettings.settingsBuilder()
			.put("cluster.name", Constants.GLOBAL_INDEX_NAME).build();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Client client = new TransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(
						"127.0.0.1", 9300));
		SearchRequestBuilder srb = client.prepareSearch(
				Constants.GLOBAL_INDEX_NAME).setTypes("product");
		srb.setFrom(0).setSize(100);
		SearchResponse searchResponse = srb.execute().actionGet();
		final SearchHits hits = searchResponse.getHits();
		System.out.println("total:" + hits.getTotalHits());
		for (final SearchHit searchHit : hits.getHits()) {
			final Product product = JSON.parseObject(searchHit.source(),
					Product.class);
			System.out.println("id : " + product.getId() + ",name : "
					+ product.getName());
		}
	}
}
