package com.health.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.health.constant.RedisConstant;
import com.health.entity.PageResult;
import com.health.mapper.SetmealDao;
import com.health.pojo.CheckItem;
import com.health.pojo.Setmeal;
import com.health.service.SetmealService;

import redis.clients.jedis.JedisPool;
@Service
public class SetmealServiceImpl implements SetmealService{
	@Autowired
	private SetmealDao setmealDao;
	@Autowired
	private JedisPool jedisPool;
	//新增套餐 
	@Override
	public void add(Setmeal setmeal, Integer[] checkgroupIds) {
		// TODO Auto-generated method stub
		setmealDao.add(setmeal);
		if(checkgroupIds!=null && checkgroupIds.length>0){
			//绑定套餐和检查组的多对多关系 
			setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);
		}
		//将图片名称保存到Redis
		savePic2Redis(setmeal.getImg());
	}
	//绑定套餐和检查组的多对多关系
	private void setSetmealAndCheckGroup(Integer id,Integer[] checkgroupIds){
		for (Integer checkgroupId : checkgroupIds) {
			Map<String, Integer> map=new HashMap<>();
			map.put("setmeal_id", id);
			map.put("checkgroup_id", checkgroupId);
			setmealDao.setSetmealAndCheckGroup(map);
		}
	}
	//将图片名称保存到Redis 
	private void savePic2Redis(String pic){
		jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, pic);
	}
	@Override
	public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
		PageHelper.startPage(currentPage,pageSize);
		Page<Setmeal> page=setmealDao.selectByCondition(queryString);
		return new PageResult(page.getTotal(),page.getResult());
	}
	@Override
	public List<Setmeal> findAll() {
		return setmealDao.findAll();
	}
	@Override
	public Setmeal findById(int id) {
		return setmealDao.findById(id);
	}
	@Override
	public List<Map<String, Object>> findSetmealCount() {
		return setmealDao.findSetmealCount();
	}

}
