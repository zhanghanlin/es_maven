package com.search.es.bussiness;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.search.es.AbstractObject;

@XmlRootElement
public class SearchResult<T> extends AbstractObject {

	private int totalHits;

	private List<T> items;

	public int getTotalHits() {
		return totalHits;
	}

	public void setTotalHits(int totalHits) {
		this.totalHits = totalHits;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}
}