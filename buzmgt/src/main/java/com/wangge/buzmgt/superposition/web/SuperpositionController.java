package com.wangge.buzmgt.superposition.web;

import com.alibaba.fastjson.JSONObject;
import com.wangge.buzmgt.income.main.service.MainPlanService;
import com.wangge.buzmgt.income.main.vo.PlanUserVo;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.plan.entity.MachineType;
import com.wangge.buzmgt.plan.service.MachineTypeService;
import com.wangge.buzmgt.section.pojo.ChannelManager;
import com.wangge.buzmgt.section.service.ChannelManagerService;
import com.wangge.buzmgt.superposition.entity.Result;
import com.wangge.buzmgt.superposition.entity.Superposition;
import com.wangge.buzmgt.superposition.entity.SuperpositionRecord;
import com.wangge.buzmgt.superposition.pojo.SuperpositionProgress;
import com.wangge.buzmgt.superposition.pojo.SuperpositionRecordDetails;
import com.wangge.buzmgt.superposition.service.GoodsOrderService;
import com.wangge.buzmgt.superposition.service.SuperpositionRecordService;
import com.wangge.buzmgt.superposition.service.SuperpositonService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.util.DateUtil;
import com.wangge.buzmgt.util.excel.ExcelExport;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by joe on 16-9-5.
 * 叠加收益controller层
 */
@Controller
@RequestMapping("superposition")
public class SuperpositionController {

  @Autowired
  private SuperpositonService superpositonService;

  @Autowired
  private MachineTypeService machineTypeServer;

  @Autowired
  private GoodsOrderService goodsOrderService;

  @Autowired
  private MainPlanService mainPlanService;

  @Autowired
  private SuperpositionRecordService superpositionRecordService;

  @Autowired
  private ChannelManagerService channelManagerService;

  private static final String SEARCH_OPERTOR = "sc_";


  private static final Logger logger = Logger.getLogger(SuperpositionController.class);

//---------------------------   财务操作   ------------ -------------------------------//

  /**
   * 跳转到叠加任务首页
   *
   * @param planId
   * @param model
   * @return
   */
  @RequestMapping(value = "superposition", method = RequestMethod.GET)
  public String toSuperposition(String planId, String check, Model model) {
    model.addAttribute("planId", planId);
    model.addAttribute("check", check);
    model.addAttribute("auditor", getUser().getId());
    return "superposition/superposition";
  }

//    /**
//     * 跳转到添加人员分组的页面
//     *
//     * @return
//     */
//    @RequestMapping(value = "addGroup", method = RequestMethod.GET)
//    public String toGroupJSP(String planId, Model model) {
//        model.addAttribute("planId", planId);
//        return "superposition/add_group_1";
//    }
//
//    /**
//     * 跳转到添加页面
//     *
//     * @return
//     */
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public String toSuperJSP(String planId, Model model) {
//        List<MachineType> machineTypes = machineTypeServer.findAll();
//        model.addAttribute("machineTypes", machineTypes);
//        model.addAttribute("planId", planId);
//        return "superposition/superposition_add";
//    }
//


  /**
   * 进入添加页面
   *
   * @param planId
   * @param model
   * @return
   */

  @RequestMapping(value = "/add", method = RequestMethod.GET)
  public String showAdd(@RequestParam String planId, String check, Model model) {

    List<MachineType> machineTypes = machineTypeServer.findAll();
    model.addAttribute("planId", planId);
    model.addAttribute("machineTypes", machineTypes);
    model.addAttribute("check", check);
    return "superposition/super_add";
  }


  @RequestMapping(value = "findChannelManager")
  @ResponseBody
  public List<ChannelManager> find() {
    return channelManagerService.findChannelManager("区域总监");
  }

  /**
   * @param @param  planId
   * @param @param  pageRequest
   * @param @return 设定文件
   * @return Page<PlanUserVo>    返回类型
   * @throws
   * @Title: getPlanUsers
   * @Description: 查询方案的所有用户
   */
  @RequestMapping(value = "/planUser")
  @ResponseBody
  public Page<PlanUserVo> getPlanUsers(@RequestParam String planId,
                                       @PageableDefault(page = 0, size = 10, sort = {"regdate"}, direction = Sort.Direction.DESC) Pageable pageRequest) {
    Map<String, Object> searchParams = new HashMap<>();
    Page<PlanUserVo> page = new PageImpl<>(new ArrayList<>());
    try {
      searchParams.put("EQ_planId", Integer.parseInt(planId));
      page = mainPlanService.getUserpage(pageRequest, searchParams);
    } catch (Exception e) {
      LogUtil.error("查询planId=" + planId + "方案人员失败", e);
    }
    return page;
  }


  /**
   * 添加 superposition
   *
   * @param superposition
   * @return
   */
  @RequestMapping(value = "add", method = RequestMethod.POST)
  @ResponseBody
  public Result add(@RequestBody Superposition superposition) {
    System.out.println(superposition);
    Result result = new Result();
    try {
      superpositonService.save(superposition);
      result.setMsg("success");
      result.setStatus("1");//添加成功
      return result;
    } catch (Exception e) {
      result.setMsg("error");
      result.setStatus("0");//添加失败
      return result;
    }
  }

  /**
   * 根据id查询
   *
   * @param superposition
   * @return
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public String findById(@PathVariable("id") Superposition superposition, String check, Model model) {
    model.addAttribute("superposition", superposition);
    model.addAttribute("check", check);
    model.addAttribute("auditor", getUser().getId());
    return "superposition/show_one_qd";
  }


  @RequestMapping(value = "delete/{id}")
  @ResponseBody
  public String delete(@PathVariable("id") Long id) {
    superpositonService.delete(id);
    return "";
  }


  /**
   * 跳转到财务显示全部的页面
   *
   * @param model
   * @return
   */

  @RequestMapping(value = "findAll", method = RequestMethod.GET)
  public String findAll(Model model, String planId) {

    model.addAttribute("planId", planId);
    return "superposition/set_list_cw";
  }

  /**
   * 所有方案
   *
   * @param pageable
   * @param type
   * @return
   */
  @RequestMapping(value = "findAll", method = RequestMethod.POST)
  @ResponseBody
  public Page<Superposition> findAll(@PageableDefault(page = 0,
          size = 10,
          sort = {"createDate"},
          direction = Sort.Direction.DESC) Pageable pageable, String type, String sign, Long planId) {

    Page<Superposition> pageReposne = superpositonService.findAll(pageable, type, sign, planId);

    return pageReposne;
  }

  /**
   * 跳转叠加主页面
   *
   * @return
   *//*
    @RequestMapping(value = "proceeding/{id}", method = RequestMethod.GET)
    public String toProceeding(@PathVariable("id") Superposition superposition) {

        return "superposition/proceeding";
    }*/


  /**
   * 查询方案中人员
   *
   * @param pageable
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "planUsers", method = RequestMethod.GET)
  @ResponseBody
  public Page<PlanUserVo> findMainPlanUsers(@PageableDefault(
          page = 0,
          size = 20,
          sort = {"regdate"},
          direction = Sort.Direction.DESC) Pageable pageable,
                                            HttpServletRequest request) throws Exception {

    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);

    return superpositonService.findMainPlanUsers(pageable, searchParams);
  }

  /**
   * 终止/驳回/审核通过 方案
   *
   * @param superposition
   * @return
   */
  @RequestMapping(value = "stop/{id}", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject stop(@PathVariable("id") Superposition superposition, @RequestParam String checkStatus) {
    JSONObject jsonObject = new JSONObject();
    try {
      superpositonService.changeStatus(superposition, checkStatus);
      jsonObject.put("result", "success");
      jsonObject.put("msg", "操作成功");
      return jsonObject;
    } catch (Exception e) {
      jsonObject.put("result", "error");
      jsonObject.put("msg", "系统异常,操作失败");
      return jsonObject;
    }
  }
//---------------------------   end   -------------------------------------------//

//--------------------------- 渠道操作 --------------------------------------------//

  /**
   * 跳转到渠道全部显示页面
   *
   * @param model
   * @return
   */

  @RequestMapping(value = "listAll", method = RequestMethod.GET)
  public String findAllQD(Model model, String planId, String check) {
    model.addAttribute("check", check);
    model.addAttribute("planId", planId);
    model.addAttribute("auditor", getUser().getId());
    return "superposition/set_list_qd";
  }

  /**
   * 所有方案
   *
   * @param pageable
   * @param type
   * @return
   */
  @RequestMapping(value = "listAll", method = RequestMethod.POST)
  @ResponseBody
  public Page<Superposition> findAllQD(@PageableDefault(page = 0,
          size = 10,
          sort = {"createDate"},
          direction = Sort.Direction.DESC) Pageable pageable, String type, String sign, Long planId) {

    Page<Superposition> pageReposne = superpositonService.findAll(pageable, type, sign, planId);

    return pageReposne;
  }


  /**
   * 根据id查询
   *
   * @param superposition
   * @return
   */
  @RequestMapping(value = "find/{id}", method = RequestMethod.GET)
  public String findByIdQd(@PathVariable("id") Superposition superposition, Model model) {

    model.addAttribute("superposition", superposition);
    return "superposition/show_one_qd";
  }
//--------------------------- end --------------------------------------------//


  /**
   * 进程
   *
   * @param planId
   * @param id
   * @return
   */
  @RequestMapping(value = "progress", method = RequestMethod.GET)
  public String findProgress(String planId, String id, Model model) {
    model.addAttribute("planId", planId);
    model.addAttribute("id", id);
    return "superposition/proceeding";
  }

  @RequestMapping(value = "progress/{id}", method = RequestMethod.POST)
  @ResponseBody
  public Page<SuperpositionProgress> findProgress(
          @PathVariable("id") Superposition superposition,
          @RequestParam(value = "page", defaultValue = "0") Integer page,
          @RequestParam(value = "size", defaultValue = "20") Integer size,
          String name) {
    logger.info(superposition);
    Page<SuperpositionProgress> progressPage = superpositonService.findAll(superposition.getPlanId(), superposition.getId(), DateUtil.date2String(superposition.getImplDate(), "yyyy-MM-dd"), DateUtil.date2String(superposition.getEndDate(), "yyyy-MM-dd"), name, page, size);
    return progressPage;
  }

  /**
   * 详情
   *
   * @param planId
   * @param id
   * @return
   */
  @RequestMapping(value = "detail", method = RequestMethod.GET)
  public String findDetail(String planId, String id, String userId, Model model) {
    model.addAttribute("planId", planId);
    model.addAttribute("id", id);
    model.addAttribute("userId", userId);
    return "superposition/detail";
  }

  @RequestMapping(value = "detail/{id}", method = RequestMethod.POST)
  @ResponseBody
  public Page<SuperpositionProgress> findDetail(
          @PathVariable("id") Superposition superposition,
          @RequestParam(value = "page", defaultValue = "0") Integer page,
          @RequestParam(value = "size", defaultValue = "20") Integer size,
          String name, String userId) {
    logger.info(superposition);
    Page<SuperpositionProgress> progressPage = superpositonService.searchDetail(superposition.getPlanId(), superposition.getId(), userId, DateUtil.date2String(superposition.getImplDate(), "yyyy-MM-dd"), DateUtil.date2String(superposition.getEndDate(), "yyyy-MM-dd"), name, page, size);
    return progressPage;
  }


  /**
   * 计算
   *
   * @return
   */
  @RequestMapping(value = "compute", method = RequestMethod.GET)
  @ResponseBody
  public String compute(Long planId, Long superId) throws Exception {

    superpositonService.superIncomeCompute(planId, superId);
    return null;
  }

  @RequestMapping(value = "ceshi", method = RequestMethod.GET)
  @ResponseBody
  public Superposition ceshi(Long planId) {
//        superpositonService.computeAfterReturnGoods("C370113210","f52ec6414ab14626a02ff9d41881d4f9","2016-10-02",1,planId,"",1l)

//        superpositonService.computeOneSingleAfterReturnGoods("C370113210",12l,"41d06af8abc74857b5342a860af6ff31","02868a4172b7486683169b4b121f54ad","2016-05-06","2016-05-06",1);
    superpositonService.superIncomeCompute(12l, 1l);
    return null;
  }


  @RequestMapping(value = "ceshi1", method = RequestMethod.GET)
  @ResponseBody
  public void ceshi1() {

//        superpositonService.computeOneSingle(12l,1l);
//        SuperpositionRecord superpositionRecord = superpositonService.getBySalesmanIdAndPlanIdAndSuperIdAndStatus("A371121210",12l,1l,"2");
//        logger.info(superpositionRecord);
//        String userId, String goodsId, String payTime, Integer num, Long planId, String receivingTime, Long hedgeId
    //售后冲减
    superpositonService.computeAfterReturnGoods("C370113210", "02868a4172b7486683169b4b121f54ad", "2016-05-10", 1, 12l, "", 1l);
    //一单达量
//        superpositonService.computeOneSingle(12L,1L);
//        superpositonService.superIncomeCompute(12l,1l);

  }


  /**
   * 导出进程表
   *
   * @return
   */
  @RequestMapping(value = "exportProgress/{id}")
  public void exportExcelProgress(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Superposition superposition) {
    List<SuperpositionProgress> list = superpositonService.exportProgress(superposition.getPlanId(), superposition.getId(), DateUtil.date2String(superposition.getImplDate(), "yyyy-MM-dd"), DateUtil.date2String(superposition.getEndDate(), "yyyy-MM-dd"));
    String[] title_ = new String[]{"姓名", "区域", "指标1", "指标2", "指标3", "分组", "提货量", "任务开始日期", "任务结束日期"};
    String[] coloumsKey_ = new String[]{"trueName", "namePath", "taskOne", "taskTwo", "taskThree", "name", "nums", "implDate", "endDate"};
    ExcelExport.doExcelExport("进程.xls", list, title_, coloumsKey_, request, response);
  }

  /**
   * 导出详情表
   *
   * @return
   */
  @RequestMapping(value = "exportDetail/{id}")
  public void exportExcelDetail(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Superposition superposition, String userId) {
    List<SuperpositionProgress> list = superpositonService.exportDetail(superposition.getPlanId(), superposition.getId(), userId, DateUtil.date2String(superposition.getImplDate(), "yyyy-MM-dd"), DateUtil.date2String(superposition.getEndDate(), "yyyy-MM-dd"));
    String[] title_ = new String[]{"商家名称", "负责区域", "订单号", "产品", "数量", "付款日期"};
    String[] coloumsKey_ = new String[]{"shopName", "shopAddress", "orderNum", "goodsName", "nums", "payTime"};
    ExcelExport.doExcelExport("详情.xls", list, title_, coloumsKey_, request, response);
  }

  /**
   * 显示收益记录信息
   * @param time
   * @param salesmanId
   * @param model
   * @return
   */
  @RequestMapping(value = "showRecords", method = RequestMethod.GET)
  public String showRecord(String time, String salesmanId,Model model) {
    model.addAttribute("time",time);
    model.addAttribute("salesmanId",salesmanId);
    return "superposition/show_records";
  }
  @RequestMapping(value = "showRecords", method = RequestMethod.POST)
  @ResponseBody
  public List<SuperpositionRecord> showRecord(String time, String salesmanId) {
    return superpositonService.findRecord(time,salesmanId);
  }

  /**
   * 显示收益信息详情
   * @param userId
   * @param superId
   * @param startTime
   * @param endTime
   * @param model
   * @return
   */
  @RequestMapping(value = "showRecordsDetails", method = RequestMethod.GET)
  public String showRecordsDetails(String userId, Long superId, String startTime, String endTime,Model model) {
    return "superposition/show_records_details";
  }
  @RequestMapping(value = "showRecordsDetails", method = RequestMethod.POST)
  @ResponseBody
  public List<SuperpositionRecordDetails> showRecordsDetails(String userId, Long superId, String startTime, String endTime) {
    userId = "M370105czgp0";
    superId = 1l;
    startTime = "2016-11-18";
    endTime = "2016-11-19";
    return superpositonService.showDetails(userId,superId,DateUtil.string2Date(startTime,"yyyy-MM-dd"),DateUtil.string2Date(endTime,"yyyy-MM-dd"));
  }



  /*
   * 获取用户的方法,用于判断是否有权限
   */
  public User getUser() {
    Subject subject = SecurityUtils.getSubject();
    User user = (User) subject.getPrincipal();
    return user;
  }

}






