package com.wangge.buzmgt.section.web;

import com.wangge.buzmgt.plan.entity.MachineType;
import com.wangge.buzmgt.plan.service.MachineTypeService;
import com.wangge.buzmgt.section.entity.PriceRange;
import com.wangge.buzmgt.section.entity.Production;
import com.wangge.buzmgt.section.service.PriceRangeService;
import com.wangge.buzmgt.section.service.ProductionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 区间设置controller
 * Created by joe on 16-8-20.
 */
@Controller
@RequestMapping("section")
public class SectionController {

    private static final Logger logger = Logger.getLogger(SectionController.class);

    @Autowired
    private ProductionService productionService;

    @Autowired
    private PriceRangeService priceRangeService;

    @Autowired
    private MachineTypeService machineTypeServer;
//--------------------------------- 财务的操作 -----------------------------------------------------//

    /**
     * 跳转到添加页面
     *
     * @return
     */
    @RequestMapping(value = "addPriceRanges", method = RequestMethod.GET)
    public String toAddJSP(String type, Model model) {
        model.addAttribute("type", type);
        return "section/add_form";
    }

    /**
     * 添加区间:初始设置(状态:创建中)
     *
     * @param implementationDate
     * @param priceRanges
     * @return
     */
    @RequestMapping(value = "addPriceRanges", method = RequestMethod.POST)
    @ResponseBody
    public Long addPriceRanges(String implementationDate, @RequestBody List<PriceRange> priceRanges, String productionType, String status) {

        Production production = productionService.addProduction(priceRanges, productionType, implementationDate, status);

        Long id = production.getProductionId();

        return id;
    }

    /**
     * 跳转到添加区域页面
     *
     * @param production
     * @param model
     * @return
     */
    @RequestMapping(value = "production/{id}", method = RequestMethod.GET)
    public String addPriceRanges(@PathVariable("id") Production production, Model model) {

        model.addAttribute("production", production);
        return "section/add_form2";
    }


    /**
     * 添加区域属性之后(点击确定之后---状态:审核中,添加审核人)
     *
     * @return
     */
    @RequestMapping(value = "toReview", method = RequestMethod.GET)
    @ResponseBody
    public Production toReview(Long id, String status, String auditor) {
        productionService.toReview(id, status, auditor);
        return null;
    }


    /**
     * 查询未过期/过期 的数据(未来设置的:审核中,通过,被驳回)
     *
     * @param type:手机类型
     * @return
     */
    @RequestMapping(value = "toNotExpiredJsp", method = RequestMethod.GET)
    public String findNotExpiredJsp(String type,
                                    Model model,
                                    @RequestParam(value = "page", defaultValue = "0") Integer page,
                                    @RequestParam(value = "size", defaultValue = "20") Integer size
    ) {

        Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.ASC, "productionId"));
        model.addAttribute("list", productionService.findNotExpired(type));//当前进行的
        model.addAttribute("listExpired", productionService.findAll(type, pageable).getContent());//过期的
        model.addAttribute("priceRanges", productionService.findReview(type));//修改小区间的
        model.addAttribute("machineTypes", machineTypeServer.findAll());//手机类型
//        return "section/set_list";
        return "section/set_list_cw";
    }

    /**
     * 修改区间值
     *
     * @param priceRange
     * @return
     */
    @RequestMapping(value = "/modifyPriceRange/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String modifyPriceRange(Long productionId,
                                   String auditorId,
                                   Double percentage,
                                   String implDate,
                                   @PathVariable("id") PriceRange priceRange) {

        logger.info(priceRange);
        priceRangeService.modifyPriceRange(productionId, auditorId, percentage, implDate, priceRange);
        return "cheng gong";
    }

    /**
     * 展示详情
     *
     * @param production
     * @param model
     * @return
     */
    @RequestMapping(value = "/findOne/{id}", method = RequestMethod.GET)
    public String findOneProduction(@PathVariable("id") Production production, Model model) {
        model.addAttribute("production", production);
        return "section/list_one_cw";
    }


//--------------------------------- end -----------------------------------------------------//


//--------------------------------- 渠道的操作 -----------------------------------------------------//

    /**
     * 渠道审核页面
     * 审核功能:(状态:审核通过/驳回)
     *
     * @return
     */
    @RequestMapping(value = "toReviewJsp", method = RequestMethod.GET)
    public String toReviewJsp(String type,
                              Model model,
                              @RequestParam(value = "page", defaultValue = "0") Integer page,
                              @RequestParam(value = "size", defaultValue = "20") Integer size) {
        Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.ASC, "productionId"));
        model.addAttribute("list", productionService.findNotExpired(type));//当前进行的
        model.addAttribute("listExpired", productionService.findAll(type, pageable).getContent());//过期的
        model.addAttribute("priceRanges", productionService.findReview(type));//修改小区间的
        model.addAttribute("machineTypes", machineTypeServer.findAll());//手机类型
//        return "section/review";
        return "section/review_qd";
    }


    /**
     * 渠道审核:方案区间
     * 审核功能:(状态:审核通过/驳回)
     *
     * @return
     */
    @RequestMapping(value = "review", method = RequestMethod.POST)
    @ResponseBody
    public Production review(Long id, String status) {
        productionService.review(id, status);
        return null;
    }

    /**
     * 渠道审核小区间修改
     *
     * @param priceRange
     * @param status
     * @return
     */
    @RequestMapping(value = "reviewPrice/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String reviewPriceRange(@PathVariable("id") PriceRange priceRange, String status) {
        priceRangeService.reviewPriceRange(priceRange, status);
        return "hehehehehehh";

    }


    @RequestMapping(value = "/findToOne/{id}", method = RequestMethod.GET)
    public String findOneProduction1(@PathVariable("id") Production production, Model model) {
        model.addAttribute("production", production);
        return "section/list_one_qd";
    }
//------------------------------------ end ------------------------------------------------------//


    /**
     * 查询正在使用的区间方案
     *
     * @param type
     * @param model
     * @return
     */

    @RequestMapping(value = "findNow", method = RequestMethod.GET)
    public String findNowToJSP(String type, Model model) {
        Map<String, Object> map = productionService.findNowCW(type);
        List<PriceRange> pc = (List) map.get("list");
        List<MachineType> machineTypes = machineTypeServer.findAll();//手机类型
        Long productionId = (Long) map.get("productionId");
        model.addAttribute("list", pc);
        model.addAttribute("productionId", productionId);
        model.addAttribute("type", type);
        model.addAttribute("machineTypes", machineTypes);
        return "section/cw_set";
    }


}
