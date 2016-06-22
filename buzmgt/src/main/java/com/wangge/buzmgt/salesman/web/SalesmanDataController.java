package com.wangge.buzmgt.salesman.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.log.entity.Log.EventType;
import com.wangge.buzmgt.log.service.LogService;
import com.wangge.buzmgt.salesman.entity.BankCard;
import com.wangge.buzmgt.salesman.entity.SalesmanData;
import com.wangge.buzmgt.salesman.service.BankCardService;
import com.wangge.buzmgt.salesman.service.SalesmanDataService;
import com.wangge.buzmgt.util.JsonResponse;

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
    
    @Autowired
    LogService logService;

//以下是代码修改成resetful

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
        salesmanData = service.save(salesmanData);
        logService.log(null, salesmanData, EventType.SAVE);
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
        SalesmanData sd = service.save(salesmanData);
        logService.log(salesmanData, sd, EventType.UPDATE);
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
        BankCard bc = bankService.findById(bankId);
        // 删除银行卡
        bankService.delete(bankId);
        logService.log(bc, null, EventType.DELETE);
        if (salesmanData.getCard().size() < 1) {
//      System.out.println("走进if");
            // 删除基础数据
            service.deleteById(id);
            logService.log(salesmanData, null, EventType.DELETE);
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
