package com.health.service;

import java.util.List;

import com.health.constant.QueryPageBean;
import com.health.entity.PageResult;
import com.health.pojo.CheckGroup;
/**
 * 检查组服务接口
 * @author wangb
 *
 */
public interface CheckGroupService {

	void add(CheckGroup checkGroup, Integer[] checkitemIds);

	CheckGroup findById(Integer id);

	List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

	void edit(CheckGroup checkGroup, Integer[] checkitemIds);

	PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

	List<CheckGroup> findAll();

}
