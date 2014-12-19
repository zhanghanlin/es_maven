package com.search.bean;

import java.io.Serializable;

/**
 * Bean父类
 * 
 * @author zhanghanlin
 *
 */
public class ExtendObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4639739352013178314L;
	
	private Long version;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
