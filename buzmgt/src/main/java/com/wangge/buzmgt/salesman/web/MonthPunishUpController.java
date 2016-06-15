package com.wangge.buzmgt.salesman.web;

import com.wangge.buzmgt.salesman.entity.MonthPunishUpResult;
import com.wangge.buzmgt.salesman.repository.MothPunishUpRepository;
import com.wangge.buzmgt.salesman.service.MonthPunishUpService;
import com.wangge.json.JSONFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by 神盾局 on 2016/5/21.
 */@Controller
@RequestMapping("/MonthPunishUp")
public class MonthPunishUpController {

    @Autowired
    private MonthPunishUpService service;
    @Autowired
    private MothPunishUpRepository mothPunishUpRepository;

    /**
     * 模糊查询（button写的注解方式返回）
     * @return
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    public String toList(){
        return "punishset/month_punish_record2";
    }
    @RequestMapping(value="/MonthPunishUps",method= RequestMethod.GET)
    @JSONFormat(filterField = {"SalesMan.user","region.parent","region.children"})
    public  MonthPunishUpResult findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                @RequestParam(value = "size", defaultValue = "3") Integer size,
                                @RequestParam(value = "startTime", defaultValue = "2016-01-26")String startTime,
                                @RequestParam(value = "endTime", defaultValue = "2016-07-26") String endTime
                                ){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, size, sort);
        Float sum = mothPunishUpRepository.amerceSum();
        MonthPunishUpResult monthPunishUpResult = new MonthPunishUpResult();
        monthPunishUpResult.setPage(service.findByPage(startTime,endTime,pageable));
        monthPunishUpResult.setSum(sum);
        return monthPunishUpResult;
    }
    /**
     * 暂时用这个
     * 模糊查询
     * @param page
     * @param size
     * @param model
     * @return
     */
    @RequestMapping(value="/list3",method = RequestMethod.GET)
    public String findAll2(@RequestParam(value = "page", defaultValue = "0") Integer page,
                           @RequestParam(value = "size", defaultValue = "3") Integer size,
                           @RequestParam(value = "startTime", defaultValue = "2016-01-26")String startTime,
                           @RequestParam(value = "endTime", defaultValue = "2200-07-26") String endTime,
                           Model model){

        model.addAttribute("startTimeFen",startTime);
        model.addAttribute("endTimeFen",endTime);
        Float sum = mothPunishUpRepository.amerceSum();
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        Pageable pageable = new PageRequest(page, size, sort);
        model.addAttribute("page", service.findByPage(startTime,endTime,pageable));
        model.addAttribute("sum",sum);
        return  "punishset/month_punish_record1";

    }
}
