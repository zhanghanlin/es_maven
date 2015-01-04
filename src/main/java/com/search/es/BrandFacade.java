package com.search.es;

public interface BrandFacade {
	String BEAN_ID = "brandFacade";
	
	String BEAN_TYPE = "brand";

	String highlightedFields[] = { "name.name_ik", "name.name_pinyin",
			"name.name_pinyin_first_letter",
			"name.name_lowercase_keyword_ngram_min_size1" };
}