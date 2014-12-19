package com.search.bean;

/**
 * 品牌对象
 * 
 * @author zhanghanlin
 *
 */
public class Brand extends ExtendObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1461931422443399167L;

	private Long id;

	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}