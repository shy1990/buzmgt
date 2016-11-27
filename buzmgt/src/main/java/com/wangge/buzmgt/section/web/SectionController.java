package com.wangge.buzmgt.section.web;

import com.alibaba.fastjson.JSONObject;
import com.wangge.buzmgt.plan.entity.MachineType;
import com.wangge.buzmgt.plan.service.MachineTypeService;
import com.wangge.buzmgt.section.entity.PriceRange;
import com.wangge.buzmgt.section.entity.Production;
import com.wangge.buzmgt.section.pojo.ChannelManager;
import com.wangge.buzmgt.section.service.ChannelManagerService;
import com.wangge.buzmgt.section.service.PriceRangeService;
import com.wangge.buzmgt.section.service.ProductionService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.util.DateUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
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

    @Autowired
    private ChannelManagerService channelManagerService;
//--------------------------------- 财务的操作 -----------------------------------------------------//

    /**
     * 查询正在使用的区间方案
     *
     * @param type
     * @param model
     * @return
     */

    @RequestMapping(value = "findNow", method = RequestMethod.GET)
    public String findNowToJSP(String type, Long planId, Model model) {
        Map<String, Object> map = productionService.findNowCW(type, planId);
        List<PriceRange> pc = (List) map.get("list");
        List<MachineType> machineTypes = machineTypeServer.findAll();//手机类型
        Long productionId = (Long) map.get("productionId");
        model.addAttribute("list", pc);
        model.addAttribute("productionId", productionId);
        model.addAttribute("type", type);
        model.addAttribute("machineTypes", machineTypes);
        model.addAttribute("planId", planId);
        model.addAttribute("machineId", type);
        return "section/cw_set";
    }


    /**
     * 跳转到添加页面
     *
     * @return
     */
    @RequestMapping(value = "addPriceRanges", method = RequestMethod.GET)
    public String toAddJSP(String type, String planId,String check, String machineName,Model model) {
        model.addAttribute("type", type);
        model.addAttribute("planId", planId);
        model.addAttribute("check",check);
        model.addAttribute("machineName",machineName);
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
    public Map<String, Object> addPriceRanges(String implementationDate, @RequestBody List<PriceRange> priceRanges, String productionType,String productionName, Long planId) {

        Production production = productionService.addProduction(priceRanges, productionType, implementationDate, planId,productionName);

        Long id = production.getProductionId();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("planId", planId);
        return map;
    }

    /**
     * 跳转到添加区域页面
     *
     * @param production
     * @param model
     * @return
     */
    @RequestMapping(value = "production/{id}", method = RequestMethod.GET)
    public String addPriceRanges(@PathVariable("id") Production production, String planId,String check,String machineName, Model model) {

        model.addAttribute("production", production);
        model.addAttribute("planId", planId);
        model.addAttribute("check",check);
        model.addAttribute("machineName",machineName);
        return "section/add_form2";
    }


    /**
     * 添加区域属性之后(点击确定之后---状态:审核中,添加审核人)
     *
     * @return
     */
    @RequestMapping(value = "toReview", method = RequestMethod.GET)
    @ResponseBody
    public String toReview(Long id, String status, String auditor, String planId,String userId) {
        productionService.toReview(id, status, auditor,userId);
        return planId;
    }


    /**
     * 查询未过期/过期 的数据(未来设置的:审核中,通过,被驳回)
     *
     * @param type:手机类型
     * @return
     */
    @RequestMapping(value = "toNotExpiredJsp", method = RequestMethod.GET)
    public String findNotExpiredJsp(String type,
                                    Long planId,
                                    Model model,
                                    @RequestParam(value = "page", defaultValue = "0") Integer page,
                                    @RequestParam(value = "size", defaultValue = "20") Integer size
    ) {
        Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.ASC, "productionId"));
        model.addAttribute("list", productionService.findNotExpired(planId, type));//当前进行的
//        model.addAttribute("listExpired", productionService.findAll(planId, type, pageable).getContent());//过期的
        model.addAttribute("priceRanges", productionService.findReview(planId, type));//修改小区间的
        model.addAttribute("machineTypes", machineTypeServer.findAll());//手机类型
        model.addAttribute("planId", planId);
        model.addAttribute("machineId", type);
        model.addAttribute("today", getToday());
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
                                   String auditor,
                                   Double percentage,
                                   String implDate,
                                   String userId,
                                   @PathVariable("id") PriceRange priceRange) {

        logger.info(priceRange);
        priceRangeService.modifyPriceRange(productionId, auditor, percentage, implDate, priceRange,userId);
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

    /**
     * 逻辑删除
     * @param production
     * @return
     */
    @RequestMapping(value = "delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject delete(@PathVariable("id") Production production){
        JSONObject jsonObject = new JSONObject();
        try {
            productionService.delete(production);
            jsonObject.put("result","success");
            jsonObject.put("msg","操作成功");
            return jsonObject;//成功
        }catch (Exception e){
            jsonObject.put("result","error");
            jsonObject.put("msg","系统异常,操作失败");
            return jsonObject;
        }

    }

    /**
     * 物理删除
     * @param
     * @return
     */
    @RequestMapping(value = "realDelete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject realDelete(@PathVariable("id") Long id){
        JSONObject jsonObject = new JSONObject();
        try {
            productionService.realDelete(id);
            jsonObject.put("result","success");
            jsonObject.put("msg","操作成功");
            return jsonObject;//成功
        }catch (Exception e){
            jsonObject.put("result","error");
            jsonObject.put("msg","系统异常,操作失败");
            return jsonObject;
        }

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
                              Long planId,
                              @RequestParam(value = "page", defaultValue = "0") Integer page,
                              @RequestParam(value = "size", defaultValue = "20") Integer size) {
        Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.ASC, "productionId"));
        model.addAttribute("list", productionService.findNotExpiredQd(planId, type));//当前进行的
//        model.addAttribute("listExpired", productionService.findAll(planId, type, pageable).getContent());//过期的
        model.addAttribute("priceRanges", productionService.findReview(planId, type));//修改小区间的
        model.addAttribute("machineTypes", machineTypeServer.findAll());//手机类型
        model.addAttribute("planId", planId);
        model.addAttribute("machineId", type);
        model.addAttribute("today", getToday());
        //用于判断用户是不是审核人的
        String userId = getUser().getId();
        model.addAttribute("userId", userId);
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
    public Map<String, Object> review(Long id, String status) {
        Production p = productionService.review(id, status);
        Map<String, Object> map = new HashedMap();
        map.put("planId", p.getPlanId());
        map.put("type", p.getProductionType());
        return map;
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
        //用于判断用户是不是审核人的
        String userId = getUser().getId();
        boolean flag = false;
        if(userId.equals(production.getUserId()) || "1".equals(userId)){
            flag = true;
        }
        model.addAttribute("flag", flag);
        return "section/list_one_qd";
    }

    @RequestMapping(value = "modify/{id}", method = RequestMethod.GET)
    @ResponseBody
    public PriceRange modify(@PathVariable("id") PriceRange priceRange) {
        return priceRangeService.stopPriceRange(priceRange);
    }




//------------------------------------ end ------------------------------------------------------//


    /**
     * 查询过期的用于分页
     *
     * @param pageable
     * @param planId
     * @param type
     * @return
     */
    @RequestMapping(value = "/findOver", method = RequestMethod.POST)
    @ResponseBody
    public Page<Production> findOver(
            @PageableDefault(page = 0,
                    size = 20,
                    sort = {"productionId"},
                    direction = Sort.Direction.DESC) Pageable pageable,
            Long planId,
            String type) {
        Page<Production> pageResponse = productionService.findAll(planId, type, pageable);
        return pageResponse;
    }

    /**
     * 测试计算
     *
     * @return
     */
    @RequestMapping(value = "compute", method = RequestMethod.GET)
    @ResponseBody
    public String compute() {

        String orderNo = "123454";
        Date payTime = DateUtil.string2Date( "2016-09-28");
        Double price = 1005.0;
        String userId = "A123456";
        String goodsId = "sdfjsdfsjfskdfjdsf";
        String type = "lj";
        Long planId = Long.parseLong("1");
        Integer num = 3;
        String regionId = "37021108";
        if(payTime == null || "".equals(payTime)){
            String msg = productionService.compute(orderNo,price,userId,goodsId,type,planId,num,regionId);//出库计算
            return msg;
        }
        String msg = productionService.compute(orderNo,payTime,price,userId,goodsId,type,planId,num,regionId);//付款计算

        return msg;
    }

    /*
     * 获取用户的方法
     */
    public User getUser() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        return user;
    }

    public static Date getToday(){

        Date today = DateUtil.string2Date(DateUtil.date2String(new Date(),"yyyy-MM-dd"));
        return today;
    }
//------------------------------ 代码优化 ---------------------------------------------------

    /**
     * 查询正在使用的小区间
     *
     * @param type 手机类型
     * @param planId 方案id
     * @return
     */


    @RequestMapping(value = "listNow", method = RequestMethod.GET)
    @ResponseBody
    public List<PriceRange> find(String type, Long planId) {
        Map<String, Object> map = productionService.findNowCW(type, planId);
        List<PriceRange> pc = (List) map.get("list");//获取正在使用的小区间
        return pc;
    }


    @RequestMapping(value = "findChannelManager")
    @ResponseBody
    public List<ChannelManager> find(){
        return channelManagerService.findChannelManager("区域总监");
    }
}
