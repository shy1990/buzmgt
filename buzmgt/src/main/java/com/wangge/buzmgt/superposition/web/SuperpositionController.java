package com.wangge.buzmgt.superposition.web;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.plan.entity.MachineType;
import com.wangge.buzmgt.plan.service.MachineTypeService;
import com.wangge.buzmgt.superposition.entity.Result;
import com.wangge.buzmgt.superposition.entity.Superposition;
import com.wangge.buzmgt.superposition.service.GoodsOrderService;
import com.wangge.buzmgt.superposition.service.SuperpositonService;

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


    private static final Logger logger = Logger.getLogger(SuperpositionController.class);

//---------------------------   财务操作   ------------ -------------------------------//

    /**
     * 跳转到添加组页面
     *
     * @return
     */
    @RequestMapping(value = "ceshi", method = RequestMethod.GET)
    public String toGroupJSP() {

        return "superposition/test_list";
    }


    /**
     * 跳转到添加页面
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toSuperJSP(Model model) {
        List<MachineType> machineTypes = machineTypeServer.findAll();
        model.addAttribute("machineTypes", machineTypes);
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
        JSONObject jsonObject = new JSONObject();
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
     * 根据id查询手机品牌
     * @param superposition
     * @return
     */
    @RequestMapping(value = "find/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Superposition findById(@PathVariable("id") Superposition superposition) {

        return superposition;
    }









    @RequestMapping(value = "delete/{id}")
    @ResponseBody
    public String delete(@PathVariable("id") Long id) {
        superpositonService.delete(id);
        return "";
    }

    /**
     * 判断使用人员
     *
     * @return
     */
    @RequestMapping(value = "find", method = RequestMethod.GET)
    @ResponseBody
    public Superposition checkMember() {
        Superposition superposition = superpositonService.checkMember("4556644");
        return superposition;
    }





    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    public String findAll(Model model) {
        model.addAttribute("machineTypes", machineTypeServer.findAll());//手机类型

        return "superposition/list_cw";
    }


    @RequestMapping(value = "findAll", method = RequestMethod.POST)
    @ResponseBody
    public Page<Superposition> findAll(@PageableDefault(page = 0,
            size = 10,
            sort = {"createDate"},
            direction = Sort.Direction.DESC) Pageable pageable, String type) {

        Page<Superposition> pageReposne = superpositonService.findAll(pageable);

        return pageReposne;
    }
//---------------------------   end   -------------------------------------------//





//
//    @RequestMapping(value = "test1", method = RequestMethod.GET)
//    @ResponseBody
//    public Page<GoodsOrder> test1(@PageableDefault(page = 0,size = 10,sort = {"payTime"},direction = Sort.Direction.DESC) Pageable pageable) {
//
//        return goodsOrderService.findAll(pageable);
//    }



    @RequestMapping(value = "find1/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String find(@PathVariable("id") Superposition superposition) {
        superpositonService.find1(superposition);
        return "ssssss";
    }
}






