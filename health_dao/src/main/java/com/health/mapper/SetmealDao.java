package com.health.mapper;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.health.pojo.Setmeal;

public interface SetmealDao {

	void add(Setmeal setmeal);

	void setSetmealAndCheckGroup(Map<String, Integer> map);

	Page<Setmeal> selectByCondition(String queryString);

	List<Setmeal> findAll();

	Setmeal findById(int id);
	
	List<Map<String, Object>> findSetmealCount();
}
