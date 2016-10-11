package com.wangge.buzmgt.superposition.web;

import com.alibaba.fastjson.JSONObject;
import com.wangge.buzmgt.income.main.vo.PlanUserVo;
import com.wangge.buzmgt.plan.entity.MachineType;
import com.wangge.buzmgt.plan.service.MachineTypeService;
import com.wangge.buzmgt.superposition.entity.Result;
import com.wangge.buzmgt.superposition.entity.Superposition;
import com.wangge.buzmgt.superposition.pojo.SuperpositionProgress;
import com.wangge.buzmgt.superposition.service.GoodsOrderService;
import com.wangge.buzmgt.superposition.service.SuperpositonService;
import com.wangge.buzmgt.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
    public String toSuperposition(String planId, Model model) {
        model.addAttribute("planId", planId);
        return "superposition/superposition";
    }

    /**
     * 跳转到添加人员分组的页面
     *
     * @return
     */
    @RequestMapping(value = "addGroup", method = RequestMethod.GET)
    public String toGroupJSP(String planId, Model model) {
        model.addAttribute("planId", planId);
        return "superposition/add_group_1";
    }

    /**
     * 跳转到添加页面
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toSuperJSP(String planId, Model model) {
        List<MachineType> machineTypes = machineTypeServer.findAll();
        model.addAttribute("machineTypes", machineTypes);
        model.addAttribute("planId", planId);
        return "superposition/superposition_add";
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
    public String findById(@PathVariable("id") Superposition superposition, Model model) {

        model.addAttribute("superposition", superposition);
        return "superposition/show_one";
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
     * 终止/驳回 方案
     * @param superposition
     * @return
     */
    @RequestMapping(value = "stop/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject stop(@PathVariable("id") Superposition superposition,@RequestParam String checkStatus){
        JSONObject jsonObject = new JSONObject();
        try{
            superpositonService.stop(superposition,checkStatus);
            jsonObject.put("result","success");
            jsonObject.put("msg","操作成功");
            return jsonObject;
        }catch (Exception e){
            jsonObject.put("result","error");
            jsonObject.put("msg","系统异常,操作失败");
            return jsonObject;
        }
    }
//---------------------------   end   -------------------------------------------//

//--------------------------- 渠道操作 --------------------------------------------//


    /**
     * 跳转到财务显示全部的页面
     *
     * @param model
     * @return
     */

    @RequestMapping(value = "listAll", method = RequestMethod.GET)
    public String findAllQD(Model model, String planId) {

        model.addAttribute("planId", planId);
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

//--------------------------- end --------------------------------------------//
    /**
     * 计算
     *
     * @param superposition
     * @return
     */
    @RequestMapping(value = "compute/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<SuperpositionProgress> compute(Long planId,@PathVariable("id") Superposition superposition) {

        return superpositonService.compute(planId,superposition);
    }


    /**
     * 进程
     * @param planId
     * @param id
     * @return
     */
    @RequestMapping(value = "progress", method = RequestMethod.GET)
    public String findProgress(String planId,String id,Model model) {
        model.addAttribute("planId",planId);
        model.addAttribute("id",id);
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
     * @param planId
     * @param id
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String findDetail(String planId,String id,String userId,Model model) {
        model.addAttribute("planId",planId);
        model.addAttribute("id",id);
        model.addAttribute("userId",userId);
        return "superposition/detail";
    }

    @RequestMapping(value = "detail/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Page<SuperpositionProgress> findDetail(
            @PathVariable("id") Superposition superposition,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            String name,String userId) {
        logger.info(superposition);
        Page<SuperpositionProgress> progressPage = superpositonService.searchDetail(superposition.getPlanId(), superposition.getId(),userId,DateUtil.date2String(superposition.getImplDate(), "yyyy-MM-dd"), DateUtil.date2String(superposition.getEndDate(), "yyyy-MM-dd"), name, page, size);
        return progressPage;
    }


    @RequestMapping(value = "ceshi", method = RequestMethod.GET)
    @ResponseBody
    public Superposition ceshi(Long planId) {

        return superpositonService.computeAfterReturnGoods("C370113210","f52ec6414ab14626a02ff9d41881d4f9","2016-10-02",1,planId);
    }
}






