package com.wangge.buzmgt.salesman.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.salesman.entity.BankCard;
import com.wangge.buzmgt.salesman.repository.BankCardRepository;
@Service
public class BankCardServiceImpl implements BankCardService {
	@Autowired
	private BankCardRepository BankCardRepository;
	/**
	 * 根据id查询
	 */
	@Override
	public BankCard findById(Long id) {
		return BankCardRepository.findByBankId(id);
	}
	@Override
	public void save(BankCard bankCard) {
		BankCardRepository.save(bankCard);
	}
	@Override
	public void delete(Long bankId) {
		BankCardRepository.deleteById(bankId);
		
	}

}
