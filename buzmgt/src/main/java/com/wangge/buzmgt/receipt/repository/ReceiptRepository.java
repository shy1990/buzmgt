package com.wangge.buzmgt.receipt.repository;

import com.wangge.buzmgt.receipt.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

	 List<Receipt> findByOrderNo(String orderno);


}
