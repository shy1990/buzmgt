package com.wangge.buzmgt.salesman.web;

import com.wangge.buzmgt.salesman.entity.MonthPunishUp;
import com.wangge.buzmgt.salesman.repository.MothPunishUpRepository;
import com.wangge.buzmgt.salesman.service.MonthPunishUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by 神盾局 on 2016/5/21.
 */@Controller
@RequestMapping("/MonthPunishUp")
public class MonthPunishUpController {

    @Autowired
    private MonthPunishUpService service;
    @Autowired
    private MothPunishUpRepository mothPunishUpRepository;

    @RequestMapping(value="/list",method = RequestMethod.GET)
    public String toList(){
        return "punishset/month_punish_record";
    }
    @RequestMapping(value="/MonthPunishUps",method= RequestMethod.GET)
    public @ResponseBody
    Page<MonthPunishUp> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                @RequestParam(value = "size", defaultValue = "3") Integer size){
        System.out.println("*************");
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, size, sort);

        return  service.findByPage(pageable);
    }


    @RequestMapping(value="/list1",method = RequestMethod.GET)
    public String ceshi(Model model){
        List<MonthPunishUp> list = mothPunishUpRepository.findAll();
        model.addAttribute("list",list);
        return "punishset/month_punish_record";
    }

    /**
     * 暂时用这个
     * @param page
     * @param size
     * @param model
     * @return
     */
    @RequestMapping(value="/list2",method = RequestMethod.GET)
    public String findAll1(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                @RequestParam(value = "size", defaultValue = "3") Integer size,Model model){
        System.out.println("*************");
        Float sum = mothPunishUpRepository.amerceSum();
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, size, sort);
        model.addAttribute("page", service.findByPage(pageable));
        model.addAttribute("sum",sum);
        return  "punishset/month_punish_record1";
    }
}
