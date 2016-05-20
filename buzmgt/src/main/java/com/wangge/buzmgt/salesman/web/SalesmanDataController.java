package com.wangge.buzmgt.salesman.web;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.salesman.entity.BankCard;
import com.wangge.buzmgt.salesman.entity.SalesmanData;
import com.wangge.buzmgt.salesman.repository.BankCardRepository;
import com.wangge.buzmgt.salesman.service.BankCardService;
import com.wangge.buzmgt.salesman.service.SalesmanDataService;

/**
 * controller
 * 
 * @author 神盾局
 *
 */
@Controller
@RequestMapping("/salesmanData")
public class SalesmanDataController {

	@Autowired
	private SalesmanDataService service;

	@Autowired
	private BankCardService bankService;

	/**
	 * 跳转到显示页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String toListJsp() {
		return "salesmanBasicData/cash_basis_yw";
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listByPage", method = RequestMethod.POST)
	public @ResponseBody Page<SalesmanData> getEntryByParams(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "3") Integer size,
			String name) {
		Sort sort = new Sort(Direction.DESC, "id");
		Pageable pageable = new PageRequest(page, size, sort);
		return service.findAll(name,pageable);
//		return null;
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 */
//	@RequestMapping(value = "/listByPage3", method = RequestMethod.GET)
//	public @ResponseBody Page<SalesmanData> getEntryByParams1(
//			@RequestParam(value = "page", defaultValue = "0") Integer page,
//			@RequestParam(value = "size", defaultValue = "3") Integer size,
//			String name) {
//		System.out.println("***************"+name);
//		Sort sort = new Sort(Direction.DESC, "id");
//		Pageable pageable = new PageRequest(page, size, sort);
//		return service.findAll(name,pageable);
////		return null;
//	}
//	
	
	
	
	

	/**
	 * 分页查询（测试）
	 * 
	 * @return
	 */
//	@RequestMapping(value = "/listByPage1", method = RequestMethod.POST)
//	public @ResponseBody Page<SalesmanData> getEntryByParams1(
//			@RequestParam(value = "page", defaultValue = "0") Integer page,
//			@RequestParam(value = "size", defaultValue = "3") Integer size,
//			String name) {
//
//		// 通常使用 Specification 的匿名内部类
//		Specification<SalesmanData> specification = new Specification<SalesmanData>() {
//			/**
//			 * @param *root:
//			 *            代表查询的实体类.
//			 * @param query:
//			 *            可以从中可到 Root 对象, 即告知 JPA Criteria 查询要查询哪一个实体类. 还可以
//			 *            来添加查询条件, 还可以结合 EntityManager 对象得到最终查询的 TypedQuery 对象.
//			 * @param *cb:
//			 *            CriteriaBuilder 对象. 用于创建 Criteria 相关对象的工厂. 当然可以从中获取到
//			 *            Predicate 对象
//			 * @return: *Predicate 类型, 代表一个查询条件.
//			 */
//			@Override
//			public Predicate toPredicate(Root<SalesmanData> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//				Predicate predicate = cb.like(root.get("name").as(String.class), "%"+name+"%"); 
//				return predicate;
//			}
//		};
//
//		Sort sort = new Sort(Direction.DESC, "id");
//
//		Pageable pageable = new PageRequest(page, size, sort);
//		return service.findAll(specification,pageable);
//	}

	/**
	 * 添加salesmanData
	 * 
	 * @param salesmanData
	 * @param bankCard
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody String saveSalesmanData(SalesmanData salesmanData, BankCard bankCard) {

		System.out.println(salesmanData + "     " + bankCard);

		// 同时添加银行卡
		if (bankCard != null) {
			List<BankCard> card = new ArrayList<BankCard>();
			card.add(bankCard);
			salesmanData.setCard(card);
		}

		service.save(salesmanData);
		return "chengong";
	}

	/**
	 * 修改
	 * 
	 * @param salesmanData
	 * @param bankCard
	 * @return
	 */
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public @ResponseBody String ModifySalesmanData(SalesmanData salesmanData, BankCard bankCard) {

		// 在数据库中查询出数据
		SalesmanData salesmanData1 = service.findById(salesmanData.getId());
		BankCard bankCard1 = bankService.findById(bankCard.getBankId());

		salesmanData1.setName(salesmanData.getName());
		bankCard1.setBankName(bankCard.getBankName());
		bankCard1.setCardNumber(bankCard.getCardNumber());
		salesmanData1.getCard().add(bankCard1);

		service.save(salesmanData1);
		return "000000";
	}

	@RequestMapping(value = "/saveBankCard", method = RequestMethod.POST)
	public @ResponseBody String saveBankCard(BankCard bankCard, Long id) {
		System.out.println(bankCard);
		SalesmanData salesmanData = service.findById(id);
		salesmanData.getCard().add(bankCard);
		service.save(salesmanData);
		return "chengong";
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @param bankId
	 * @return
	 */
	@RequestMapping(value = "/delete/salesman/{id}/bankCard/{bankId}", method = RequestMethod.DELETE)
	public @ResponseBody String delete(@PathVariable("id") Long id, @PathVariable("bankId") Long bankId) {
		System.out.println("**********" + id + "  " + bankId);
		SalesmanData salesmanData = service.findById(id);

		// 删除银行卡
		bankService.delete(bankId);
		if (salesmanData.getCard().size() <= 1) {
			// 删除基础数据
			service.deleteById(id);
		}
		System.out.println(id + "  " + bankId);
		return "chengong";
	}

}
