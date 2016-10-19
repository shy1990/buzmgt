package com.wangge.buzmgt.receipt.service;

import com.wangge.buzmgt.receipt.entity.BillSalesmanVo;
import com.wangge.buzmgt.receipt.entity.BillVo;
import com.wangge.buzmgt.receipt.entity.Receipt;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReceiptService {

    public Page<BillVo> findAllBill(String date, String salesmanName, int pageNum);

    public Page<BillSalesmanVo> findByUserIdAndCreateTime(String userId, String todayDate, int pageNum);

    List<Receipt> findByOrderno(String orderno);
}
