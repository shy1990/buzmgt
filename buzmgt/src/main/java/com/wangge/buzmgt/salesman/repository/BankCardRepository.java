package com.wangge.buzmgt.salesman.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.salesman.entity.BankCard;
@Transactional
public interface BankCardRepository extends JpaRepository<BankCard, Long>{
	/**
	 * 查询
	 */
	public BankCard findByBankId(Long id);
	
	public BankCard save(BankCard bank);
	@Modifying
	@Query("delete  from BankCard a where a.bankId= ?1")
	public void deleteById(Long id);
	
}
