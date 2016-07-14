package com.wangge.buzmgt.teammember.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.log.entity.Log.EventType;
import com.wangge.buzmgt.log.service.LogService;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.sys.base.BaseController;
import com.wangge.buzmgt.sys.entity.ChildAccount;
import com.wangge.buzmgt.sys.entity.Organization;
import com.wangge.buzmgt.sys.entity.Role;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.entity.User.UserStatus;
import com.wangge.buzmgt.sys.service.ChildAccountService;
import com.wangge.buzmgt.sys.service.OrganizationService;
import com.wangge.buzmgt.sys.service.RoleService;
import com.wangge.buzmgt.sys.service.UserService;
import com.wangge.buzmgt.sys.util.PageData;
import com.wangge.buzmgt.sys.util.PageNavUtil;
import com.wangge.buzmgt.sys.util.SortUtil;
import com.wangge.buzmgt.teammember.entity.Manager;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.service.AccountService;
import com.wangge.buzmgt.teammember.service.ManagerService;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.buzmgt.teammember.vo.AccountBean;
import com.wangge.json.JSONFormat;

@Controller
public class AccountManageController extends BaseController {

  private static final Logger LOG = Logger.getLogger(AccountManageController.class);

  @Autowired
  private AccountService as;
  @Autowired
  private ManagerService ms;
  @Autowired
  private SalesManService sm;
  @Resource
  private OrganizationService os;
  @Autowired
  private RegionService rs;
  @Autowired
  private RoleService ros;
  @Autowired
  private UserService us;
  @Autowired
  private ChildAccountService ca;
  @Autowired
  private LogService logService;

  /**
   * 
   * @Description: 账号列表
   * @param @param
   *          page
   * @param @param
   *          model
   * @param @return
   * @return String
   * @throws @author
   *           changjun
   * @date 2016年2月24日
   */
  @RequestMapping(value = "/accountManage")
  public String accountList(Integer page, Model model, HttpServletRequest req, String regionId, String searchParam) {
    String rName = "";
    String rId = "";

    if (null != req.getParameter("orgName")) {
      String orgName = req.getParameter("orgName");
      req.getSession().setAttribute("orgName", orgName);
    }

    if (null != regionId && !"".equals(regionId)) {
      Region r = rs.getRegionById(regionId);
      rName = r.getName();
      rId = r.getId();

      req.getSession().setAttribute("rName", rName);
      req.getSession().setAttribute("rId", rId);

      model.addAttribute("regionName", rName);
      model.addAttribute("regionId", rId);
    } else {
      Subject subject = SecurityUtils.getSubject();
      User user = (User) subject.getPrincipal();
      Manager manager = ms.getById(user.getId());
      if (null != req.getSession().getAttribute("rName")) {
        rName = req.getSession().getAttribute("rName") + "";
        rId = req.getSession().getAttribute("rId") + "";
      } else {
        rName = manager.getRegion().getName();
        rId = manager.getRegion().getId();
      }
      model.addAttribute("regionName", rName);
      model.addAttribute("regionId", rId);
    }

    model.addAttribute("searchParam", searchParam);
    return "account/account_list";
  }

  /**
   * 
   * @Title: accountListAjax @Description: 替换原有列表查询 @param @param
   * page @param @param model @param @param req @param @param
   * regionId @param @param searchParam @param @return 设定文件 @return String
   * 返回类型 @throws
   */
  @RequestMapping(value = "/getAccountManage")
  @JSONFormat(filterField={"Region.parent","Region.parent"})
  public Page<AccountBean> accountListAjax(HttpServletRequest req, String regionId, String searchParam,
      @PageableDefault(page = 0,size = 20) Pageable pageable) {
    PageRequest pageRequest =(PageRequest) pageable;
    // 过滤查询
    List<AccountBean> accList = new ArrayList<AccountBean>();
    String orgName = req.getParameter("orgName");
    String rName = "";
    String rId = "";
    // 职务查询
    if (null != orgName && !"".equals(orgName)) {
      req.getSession().setAttribute("orgName", orgName);
    }
    String sessionOrgName = (String) req.getSession().getAttribute("orgName");
    // 有区域id查询
    if (null != regionId && !"".equals(regionId)) {
      Region r = rs.getRegionById(regionId);
      rName = r.getName();
      rId = r.getId();
      // 区域信息存放到session中
      req.getSession().setAttribute("rName", rName);
      req.getSession().setAttribute("rId", rId);
      // 组织名称
      if (null == orgName) {
        if (null != req.getSession().getAttribute("orgName")) {
          accList = as.selectAccountByPositionAndStatus(sessionOrgName, "used", rName,
              pageRequest, searchParam);
        } else {
          accList = as.selectAccountByPositionAndStatus("all", "used", rName, pageRequest, searchParam);
        }

      } else {

        String status = req.getParameter("status");
        if (null != sessionOrgName) {
          accList = as.selectAccountByPositionAndStatus(sessionOrgName, status, rName,
              pageRequest, searchParam);
        } else {
          accList = as.selectAccountByPositionAndStatus("all", status, rName, pageRequest, searchParam);
        }
        // accList = as.selectAccountByPositionAndStatus(orgName, status,rName,
        // pageRequest);
      }
    } else {
      Subject subject = SecurityUtils.getSubject();
      User user = (User) subject.getPrincipal();
      Manager manager = ms.getById(user.getId());
      if (null != req.getSession().getAttribute("rName")) {
        rName = req.getSession().getAttribute("rName") + "";
        rId = req.getSession().getAttribute("rId") + "";
      } else {
        rName = manager.getRegion().getName();
        rId = manager.getRegion().getId();
      }
      if (null == orgName) {
        if (null != sessionOrgName) {
          accList = as.selectAccountByPositionAndStatus(sessionOrgName, "used", rName,
              pageRequest, searchParam);
        } else {
          accList = as.selectAccountByPositionAndStatus("all", "used", rName, pageRequest, searchParam);
        }
        // accList = as.selectAccountByPositionAndStatus("all", "used",rName,
        // pageRequest);
      } else {
        String status = req.getParameter("status");
        if (null != sessionOrgName) {
          accList = as.selectAccountByPositionAndStatus(sessionOrgName, status, rName,
              pageRequest, searchParam);
        } else {
          accList = as.selectAccountByPositionAndStatus("all", status, rName, pageRequest, searchParam);
        }
        // accList = as.selectAccountByPositionAndStatus(orgName, status,rName,
        // pageRequest);
      }
    }

    int totalNum = accList.size() > 0 ? accList.get(0).getTotalNum() : 0;
    Page<AccountBean> accPage = new PageImpl<>(accList, pageRequest, totalNum);
    return accPage;
  }

  /**
   * 
   * @Description: 账号资料修改
   * @param @param
   *          accountNum
   * @param @param
   *          position
   * @param @param
   *          model
   * @param @return
   * @return String
   * @throws @author
   *           changjun
   * @date 2016年2月26日
   */
  @RequestMapping(value = "/modifyAccount", method = RequestMethod.GET)
  public String modifyAccount(String accountNum, String position, Model model) {
    System.out.println(position + accountNum);
    if ("服务站经理".equals(position)) {
      SalesMan sman = sm.findById(accountNum);
      model.addAttribute("man", sman);
      Iterator<Role> role = sman.getUser().getRoles().iterator();
      while (role.hasNext()) {
        Role r = role.next();
        model.addAttribute("roleId", r.getId());
        model.addAttribute("roleName", r.getName());
      }
    } else {
      Manager mman = ms.getById(accountNum);
      model.addAttribute("man", mman);
      Iterator<Role> role = mman.getUser().getRoles().iterator();
      while (role.hasNext()) {
        Role r = role.next();
        model.addAttribute("roleId", r.getId());
        model.addAttribute("roleName", r.getName());
      }
    }
    return "account/account_modify";
  }

  /**
   * 
   * @Description: 保存修改
   * @param @param
   *          req
   * @param @param
   *          model
   * @param @return
   * @return String
   * @throws @author
   *           changjun
   * @date 2016年2月26日
   */
  @RequestMapping(value = "/saveAccount", method = RequestMethod.POST)
  public String saveyAccount(HttpServletRequest req, Model model) {

    Organization ora = os.getOrganById(Integer.parseInt(req.getParameter("organizationId")));
    Region region = rs.getRegionById(req.getParameter("regionId").trim());
    Role role = ros.getRoleById(req.getParameter("roleId"));
    Set<Role> rSet = new HashSet<Role>();
    rSet.add(role);
    // User u = us.getById(req.getParameter("userId"));
    // u.setOrganization(ora);
    // u.setRoles(rSet);

    if ("服务站经理".equals(ora.getName())) {
      SalesMan sman = sm.findById(req.getParameter("userId"));
      sman.setMobile(req.getParameter("mobile"));
      sman.setRegion(region);
      sman.setTruename(req.getParameter("truename"));
      // sman.getUser().setOrganization(ora);
      sman.getUser().setRoles(rSet);
      sm.addSalesman(sman);
    } else {
      Manager mman = ms.getById(req.getParameter("userId"));
      mman.setMobile(req.getParameter("mobile"));
      mman.setRegion(region);
      mman.setTruename(req.getParameter("truename"));
      // mman.getUser().setOrganization(ora);
      mman.getUser().setRoles(rSet);
      ms.addManager(mman);
    }
    return "redirect:/accountManage";
  }

  /**
   * 
   * @Description: 重置密码
   * @param @param
   *          id
   * @param @return
   * @return String
   * @throws @author
   *           changjun
   * @date 2016年2月26日
   */
  @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
  @ResponseBody
  public String resetPwd(String id) {
    boolean flag = as.mofidyPwd(id);
    if (flag) {
      return "suc";
    }
    return "err";
  }

  /**
   * 
   * @Description: 修改账号状态 2,辞退,3删除
   * @param @param
   *          id
   * @param @param
   *          status
   * @param @return
   * @return String
   * @throws @author
   *           changjun
   * @date 2016年2月26日
   */
  @RequestMapping(value = "/mofidyAccountStatus", method = RequestMethod.POST)
  @ResponseBody
  public String mofidyAccountStatus(String id, String status) {
    try {
      User u = us.getById(id);
      if ("2".equals(status)) {
        u.setStatus(UserStatus.DISMISS);
      } else if ("3".equals(status)) {
        u.setStatus(UserStatus.DELETE);
      } else if ("0".equals(status)) {
        u.setStatus(UserStatus.LOCKED);
      } else if ("1".equals(status)) {
        u.setStatus(UserStatus.NORMAL);
      } else if ("4".equals(status)) {
        u.getSalseMan().setSimId("");
      }
      User user = us.addUser(u);
      logService.log(u, user, EventType.UPDATE);
      return "suc";
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "err";
  }

  /**
   * 
   * addChildAccount:添加子账号.
   * 
   * @author rebort
   * @param truename
   * @param userId
   * @param model
   * @since JDK 1.8
   */
  @RequestMapping(value = "/addChildAccount", method = RequestMethod.POST)
  @ResponseBody
  public String addChildAccount(String truename, String userId, Model model) {
    String firstCh = userId.substring(0, 1);
    List<ChildAccount> listChildAccount = ca.findChildCountByParentId(userId);
    if (listChildAccount.size() == 9) {
      return "err";
    }
    String childId = null;
    if (listChildAccount.size() == 0) {
      childId = firstCh + (Long.parseLong(userId.substring(1, userId.length())) + 1);
      SalesMan man = sm.findByUserId(userId);
      man.setIsPrimaryAccount(1);
      sm.addSalesman(man);
    } else {
      childId = firstCh + (Long.parseLong(
          listChildAccount.get(0).getChildId().substring(1, listChildAccount.get(0).getChildId().length())) + 1);
    }

    ChildAccount childAccount = new ChildAccount(childId, userId, truename);
    ca.save(childAccount);
    return "suc";
  }

  /**
   * 
   * findChildAccount:查询子账号列表 <br/>
   * 
   * @author Administrator
   * @param userId
   * @param model
   * @return
   * @since JDK 1.8
   */
  @RequestMapping(value = "/findChildAccount", method = RequestMethod.GET)
  public String findChildAccount(String userId, Model model) {

    List<ChildAccount> listChild = ca.findChildCountByParentId(userId);
    model.addAttribute("listChild", listChild);
    model.addAttribute("fatherAccount", sm.getSalesmanByUserId(userId));
    return "account/childAccount_list";
  }

  /**
   * 
   * mofidyChildAccountStatus:修改子账号. <br/>
   * 
   * @author Administrator
   * @param userid
   * @param status
   * @return
   * @since JDK 1.8
   */
  @RequestMapping(value = "/mofidyChildAccountStatus", method = RequestMethod.POST)
  @ResponseBody
  public String mofidyChildAccountStatus(String userid, String status) {
    try {
      ChildAccount cAccount = ca.findbyUserId(Long.parseLong(userid));
      if (status.equals("3")) {
        ca.delete(cAccount);
        if (ca.findChildCountByParentId(cAccount.getParentId()).size() == 0) {
          SalesMan man = sm.findByUserId(cAccount.getParentId());
          man.setIsPrimaryAccount(0);
          sm.addSalesman(man);
        }
      } else {
        cAccount.setEnable(Integer.parseInt(status));
        ca.save(cAccount);
      }
      return "suc";
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "err";
  }

}
