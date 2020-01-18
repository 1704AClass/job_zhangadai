package com.health.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.health.constant.QueryPageBean;
import com.health.entity.PageResult;
import com.health.mapper.CheckGroupDao;
import com.health.pojo.CheckGroup;
import com.health.pojo.CheckItem;
import com.health.service.CheckGroupService;
/**
 * 检查组服务
 * @author wangb
 *
 */
@Service
public class CheckGroupServiceImpl implements CheckGroupService{
	@Autowired
	private CheckGroupDao checkGroupDao;
	@Override
	public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
		// TODO Auto-generated method stub
		checkGroupDao.add(checkGroup);
		setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
	}
	//向中间表(t_checkgroup_checkitem)插入数据（建立检查组和检查项关联关系） 
	public void setCheckGroupAndCheckItem(Integer checkGroupId,Integer[] checkitemIds){
		if(checkitemIds!=null && checkitemIds.length>0){
			for (Integer checkitemId : checkitemIds) {
				Map<String, Integer> map=new HashMap<>();
				map.put("checkgroup_id", checkGroupId);
				map.put("checkitem_id", checkitemId);
				checkGroupDao.setCheckGroupAndCheckItem(map);
			}
		}
	}
	@Override
	public CheckGroup findById(Integer id) {
		// TODO Auto-generated method stub
		return checkGroupDao.findById(id);
	}
	@Override
	public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
		// TODO Auto-generated method stub
		return checkGroupDao.findCheckItemIdsByCheckGroupId(id); 
	}
	//编辑检查组，同时需要更新和检查项的关联关系 
	@Override
	public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
		// TODO Auto-generated method stub
		//根据检查组id删除中间表数据（清理原有关联关系） 
		checkGroupDao.deleteAssociation(checkGroup.getId()); 
		//向中间表(t_checkgroup_checkitem)插入数据（建立检查组和检查项关联关系） 
		setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
		//更新检查组基本信息 
		checkGroupDao.edit(checkGroup); 
	}
	@Override
	public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
		PageHelper.startPage(currentPage,pageSize);
		Page<CheckItem> page=checkGroupDao.selectByCondition(queryString);
		return new PageResult(page.getTotal(),page.getResult());
	}
	
	@Override
	public List<CheckGroup> findAll() {
		return checkGroupDao.findAll();
	}
	
}
