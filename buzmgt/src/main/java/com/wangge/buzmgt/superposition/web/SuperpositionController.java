package com.wangge.buzmgt.superposition.web;

import com.wangge.buzmgt.plan.entity.MachineType;
import com.wangge.buzmgt.plan.server.MachineTypeServer;
import com.wangge.buzmgt.superposition.entity.Superposition;
import com.wangge.buzmgt.superposition.service.SuperpositonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private MachineTypeServer machineTypeServer;

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
        model.addAttribute("machineTypes",machineTypes);
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
    public Superposition add(@RequestBody Superposition superposition) {

        logger.info(superposition);

        superpositonService.save(superposition);
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


}


//---------------------------   end   -------------------------------------------//
