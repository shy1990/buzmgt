package com.wangge.buzmgt.salesman.service;

import com.wangge.buzmgt.salesman.entity.BankCard;

public interface BankCardService {

	public BankCard findById(Long Id);
	public void save(BankCard bankCard); 
	public void delete(Long bankId);
}
