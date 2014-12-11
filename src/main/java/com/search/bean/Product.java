package com.search.bean;



/**
 * 商品对象
 * 
 * @author zhanghanlin
 *
 */
public class Product extends ExtendObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8305954163700417093L;

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
