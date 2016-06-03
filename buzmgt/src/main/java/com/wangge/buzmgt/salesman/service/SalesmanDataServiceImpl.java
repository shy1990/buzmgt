package com.wangge.buzmgt.salesman.service;

import com.wangge.buzmgt.salesman.entity.SalesmanData;
import com.wangge.buzmgt.salesman.repository.SalesmanDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;


@Service
public class SalesmanDataServiceImpl implements SalesmanDataService{
	@Autowired
	private SalesmanDataRepository salesmanDataRepository;
	@Override
	public List<SalesmanData> findAll() {
		List<SalesmanData> list = salesmanDataRepository.findAll();
		return list;
	}
	/**
	 * 添加
	 */
	@Override
	public void save(SalesmanData salesmanData) {
		salesmanDataRepository.save(salesmanData);
	}
	/**
	 * 分页查询
	 */
	@Override
	public Page<SalesmanData> findAll(Specification specification,Pageable pageable) {
		
		return salesmanDataRepository.findAll(specification,pageable);
	}
	/**
	 * 分页查询
	 */
	@Override
	public Page<SalesmanData> findAll(String name,Pageable pageable) {
		if(name != null && !"".equals(name)){
			// 通常使用 Specification 的匿名内部类
			Specification<SalesmanData> specification = new Specification<SalesmanData>() {
				/**
				 * @param *root:
				 *            代表查询的实体类.
				 * @param query:
				 *            可以从中可到 Root 对象, 即告知 JPA Criteria 查询要查询哪一个实体类. 还可以
				 *            来添加查询条件, 还可以结合 EntityManager 对象得到最终查询的 TypedQuery 对象.
				 * @param *cb:
				 *            CriteriaBuilder 对象. 用于创建 Criteria 相关对象的工厂. 当然可以从中获取到
				 *            Predicate 对象
				 * @return: *Predicate 类型, 代表一个查询条件.
				 */
				@Override
				public Predicate toPredicate(Root<SalesmanData> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate predicate = cb.like(root.get("name").as(String.class), "%"+name+"%");
					return predicate;
				}
				
			};
			return salesmanDataRepository.findAll(specification,pageable);
		}
		return salesmanDataRepository.findAll(pageable);
	}
	/**
	 * 根据id查询
	 */
	@Override
	public SalesmanData findById(Long id) {
		return salesmanDataRepository.findById(id);
	}
	@Override
	public void deleteById(Long id) {
		salesmanDataRepository.deleteById(id);
		
	}
  @Override
  public SalesmanData findByNameAndCard_cardNumber(String name,String cardNu) {
    return salesmanDataRepository.findByNameAndCard_cardNumber(name, cardNu);
  }

}
