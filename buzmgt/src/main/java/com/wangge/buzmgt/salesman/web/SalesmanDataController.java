package com.wangge.buzmgt.salesman.web;

import com.wangge.buzmgt.salesman.entity.BankCard;
import com.wangge.buzmgt.salesman.entity.SalesmanData;
import com.wangge.buzmgt.salesman.service.BankCardService;
import com.wangge.buzmgt.salesman.service.SalesmanDataService;
import com.wangge.buzmgt.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * controller
 *
 * @author 神盾局
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
    public
    @ResponseBody
    Page<SalesmanData> getEntryByParams(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "3") Integer size,
            String name) {
        Sort sort = new Sort(Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, size, sort);
        return service.findAll(name, pageable);
//    return null;
    }

    /**
     * 分页查询
     *
     * @return
     */
//  @RequestMapping(value = "/listByPage3", method = RequestMethod.GET)
//  public @ResponseBody Page<SalesmanData> getEntryByParams1(
//      @RequestParam(value = "page", defaultValue = "0") Integer page,
//      @RequestParam(value = "size", defaultValue = "3") Integer size,
//      String name) {
//    System.out.println("***************"+name);
//    Sort sort = new Sort(Direction.DESC, "id");
//    Pageable pageable = new PageRequest(page, size, sort);
//    return service.findAll(name,pageable);
////    return null;
//  }
//  


    /**
     * 分页查询（测试）
     *
     * @return
     */
//  @RequestMapping(value = "/listByPage1", method = RequestMethod.POST)
//  public @ResponseBody Page<SalesmanData> getEntryByParams1(
//      @RequestParam(value = "page", defaultValue = "0") Integer page,
//      @RequestParam(value = "size", defaultValue = "3") Integer size,
//      String name) {
//
//    // 通常使用 Specification 的匿名内部类
//    Specification<SalesmanData> specification = new Specification<SalesmanData>() {
//      /**
//       * @param *root:
//       *            代表查询的实体类.
//       * @param query:
//       *            可以从中可到 Root 对象, 即告知 JPA Criteria 查询要查询哪一个实体类. 还可以
//       *            来添加查询条件, 还可以结合 EntityManager 对象得到最终查询的 TypedQuery 对象.
//       * @param *cb:
//       *            CriteriaBuilder 对象. 用于创建 Criteria 相关对象的工厂. 当然可以从中获取到
//       *            Predicate 对象
//       * @return: *Predicate 类型, 代表一个查询条件.
//       */
//      @Override
//      public Predicate toPredicate(Root<SalesmanData> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//        Predicate predicate = cb.like(root.get("name").as(String.class), "%"+name+"%"); 
//        return predicate;
//      }
//    };
//
//    Sort sort = new Sort(Direction.DESC, "id");
//
//    Pageable pageable = new PageRequest(page, size, sort);
//    return service.findAll(specification,pageable);
//  }

    /**
     * 添加salesmanData
     *
     * @param salesmanData
     * @param bankCard
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public
    @ResponseBody
    String saveSalesmanData(SalesmanData salesmanData, BankCard bankCard) {

//    System.out.println(salesmanData + "     " + bankCard);

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
    public
    @ResponseBody
    String ModifySalesmanData(SalesmanData salesmanData, BankCard bankCard) {

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
    public
    @ResponseBody
    String saveBankCard(BankCard bankCard, Long id) {
//    System.out.println(bankCard);
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
    public
    @ResponseBody
    String delete(@PathVariable("id") Long id, @PathVariable("bankId") Long bankId) {
//    System.out.println("**********" + id + "  " + bankId);
        SalesmanData salesmanData = service.findById(id);

        // 删除银行卡
        bankService.delete(bankId);
        if (salesmanData.getCard().size() < 1) {
//      System.out.println("走进if");
            // 删除基础数据
            service.deleteById(id);
        }
//    System.out.println(id + "  " + bankId);
        return "chengong";
    }





//以下是代码修改成resetful(给的页面样式暂时无法用handlebars去做)

    /**
     * 跳转到显示页面
     *
     * @return
     */
    @RequestMapping(value = "/list1", method = RequestMethod.GET)
    public String toListJsp1() {
        return "salesmanBasicData/cash_basis_yw_resetful";
    }


    /**
     * 分页查询
     * 用ResponseEntity<JsonResponse>返回的的方式
     * <p>
     * resetful方式
     *
     * @return
     */
    @RequestMapping(value = "/salesmanDatas", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<JsonResponse> getEntryByParams1(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "3") Integer size,
            String name) {
        Sort sort = new Sort(Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, size, sort);
        return new ResponseEntity<JsonResponse>(new JsonResponse(service.findAll(name, pageable)), HttpStatus.OK);
    }

    /**
     * 根据id查询基础数据
     *
     * @param salesmanData
     * @return
     */
    @RequestMapping(value = "/salesmanDatas/{id}/{bankId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<JsonResponse> findById(@PathVariable("id") SalesmanData salesmanData, @PathVariable("bankId") BankCard bankCard) {
        List list = new ArrayList<>();
        list.add(salesmanData);
        list.add(bankCard);
        return new ResponseEntity<JsonResponse>(new JsonResponse(list), HttpStatus.OK);
    }


    /**
     * 添加salesmanData
     *
     * @param salesmanData
     * @param bankCard
     * @return
     */
    @RequestMapping(value = "/salesmanDatas", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<JsonResponse> saveSalesmanData1(SalesmanData salesmanData, BankCard bankCard) {

//    System.out.println(salesmanData + "     " + bankCard);

        // 同时添加银行卡
        if (bankCard != null) {
            List<BankCard> card = new ArrayList<BankCard>();
            card.add(bankCard);
            salesmanData.setCard(card);
        }
        service.save(salesmanData);
        return new ResponseEntity<JsonResponse>(new JsonResponse("添加成功"), HttpStatus.OK);
    }


    /**
     * 修改
     *
     * @param salesmanData
     * @param bankCard
     * @return
     */
    @RequestMapping(value = "salesmanDatas/{id}/{bankId}", method = RequestMethod.PUT)
    public
    @ResponseBody
    ResponseEntity<JsonResponse> ModifySalesmanData1(@PathVariable("id") SalesmanData salesmanData,
                               @PathVariable("bankId") BankCard bankCard,
                               SalesmanData salesmanDataUp,
                               BankCard bankCardUp) {

        System.out.println(salesmanData + ":    " + bankCard);
        System.out.println(salesmanDataUp + ":    " + bankCardUp);
        salesmanData.setName(salesmanDataUp.getName());
        bankCard.setBankName(bankCardUp.getBankName());
        bankCard.setCardNumber(bankCardUp.getCardNumber());
        salesmanData.getCard().add(bankCard);
        service.save(salesmanData);
        return new ResponseEntity<JsonResponse>(new JsonResponse("添加成功"), HttpStatus.OK);
    }



    /**
     * 删除
     *
     * @param id
     * @param bankId
     * @return
     */
    @RequestMapping(value = "/salesmanDatas/{id}/{bankId}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    ResponseEntity<JsonResponse> delete1(@PathVariable("id") Long id, @PathVariable("bankId") Long bankId) {
    System.out.println("**********" + id + "  " + bankId);
        SalesmanData salesmanData = service.findById(id);

        // 删除银行卡
        bankService.delete(bankId);
        if (salesmanData.getCard().size() < 1) {
//      System.out.println("走进if");
            // 删除基础数据
            service.deleteById(id);
        }
//    System.out.println(id + "  " + bankId);
        return new ResponseEntity<JsonResponse>(new JsonResponse("删除成功"),HttpStatus.OK);
    }


    @RequestMapping(value = "/salesmanDatas/{id}/bankCard", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<JsonResponse> saveBankCard1(BankCard bankCard, @PathVariable("id") SalesmanData salesmanData) {
        salesmanData.getCard().add(bankCard);
        service.save(salesmanData);
        return new ResponseEntity<JsonResponse>(new JsonResponse("添加成功"),HttpStatus.OK);
    }
}
