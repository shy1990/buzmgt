package com.wangge.buzmgt.salesman.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.salesman.entity.salesMan;
import com.wangge.buzmgt.salesman.repository.salesManRepository;
import com.wangge.buzmgt.sys.entity.User;

@Service
public class salesManServiceImpl implements salesManService {

	@Resource
	private salesManRepository salesManRepository;
	/*
	* <p>Title: addSalesman</p> 
	* <p>Description: 添加一条业务员数据</p> 
	* @param salesman 
	* @see com.wangge.buzmgt.salesman.service.salesManService#addSalesman(com.wangge.buzmgt.salesman.entity.salesMan)
	 */
	@Override
	public void addSalesman(salesMan salesman) {
		
		salesManRepository.save(salesman);
	}
	
  /*
  * <p>Title: findByReginId</p> 
  * <p>Description:根据id查询区域 </p> 
  * @param regionId
  * @return 
  * @see com.wangge.buzmgt.salesman.service.salesManService#findByReginId(java.lang.String)
   */
	@Override
	@Transactional
	public List<User> findByReginId(String regionId) {
		
		return salesManRepository.findByRegionId(regionId);
	}

  /*
  * <p>Title: getSalesmanList</p> 
  * <p>Description: 获取业务员列表</p> 
  * @return 
  * @see com.wangge.buzmgt.salesman.service.salesManService#getSalesmanList()
   */
  @Override
  public List<salesMan> getSalesmanList() {
          salesManRepository.findAll(new PageRequest(0, 20,new Sort("desc")));
    return null;
  }}
