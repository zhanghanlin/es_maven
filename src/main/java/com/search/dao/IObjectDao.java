package com.search.dao;

import java.util.List;

/**
 * DAO父类
 * @author zhanghanlin
 *
 * @param <T>
 */
public interface IObjectDao<T> {
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<T> searchAll();

	/**
	 * 根据参数查询
	 * @param param
	 * @return
	 */
	public List<T> searchByIds(List<String> param);
}