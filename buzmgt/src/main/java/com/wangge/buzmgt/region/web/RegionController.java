package com.wangge.buzmgt.region.web;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.assess.entity.RegistData;
import com.wangge.buzmgt.assess.service.AssessService;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.region.vo.RegionTree;
import com.wangge.buzmgt.saojie.entity.SaojieData;
import com.wangge.buzmgt.saojie.service.SaojieDataService;
import com.wangge.buzmgt.saojie.service.SaojieService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.service.UserService;
import com.wangge.buzmgt.sys.vo.OrganizationVo;
import com.wangge.buzmgt.sys.vo.SaojieDataVo;
import com.wangge.buzmgt.teammember.entity.Manager;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.service.ManagerService;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.buzmgt.util.RegionUtil;
import com.wangge.json.JSONFormat;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/region")
public class RegionController {
	private static final Logger logger = Logger
			.getLogger(RegionController.class);
	@Autowired
	private RegionService regionService;
	@Resource
	private UserService userService;
	@Resource
	private ManagerService managerService;
	@Resource
    private SaojieDataService saojieDateService;
	@Resource
	private AssessService assessService;
	@Resource
	private SaojieDataService sds;
	@Resource
	private SalesManService salesmanservice;
	@Resource
	private SaojieService saojieService;
	private static final String ONELEAVE="0";
	
	/**
	 * 
	* @Title: initRegion 
	* @Description: 初始化区域划分页面
	* @param @param test
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	
	@RequestMapping("/initRegion")
	public String initRegion(String test,Model model){
		return "region/region_view";
	}	
	/**
	 * 
	* @Title: findOneRegion 
	* @Description: 查询一级区域
	* @param @param request
	* @param @return    
	* @return ResponseEntity<List<RegionTree>>    返回类型 
	* @throws
	 */
	
	@RequestMapping(value = "/findOneRegion", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<RegionTree>> findOneRegion() {
		 List<RegionTree> listTreeVo =new ArrayList<RegionTree>();
		
		  listTreeVo=regionService.findTreeRegion(ONELEAVE);
		return new ResponseEntity<List<RegionTree>>(listTreeVo,HttpStatus.OK);
	}
	
	
	/**
	 * 
	* @Title: findRegionByid 
	* @Description: 通过id查询树型结构，包含子节点
	* @param @param request
	* @param @param id
	* @param @return    设定文件 
	* @return ResponseEntity<List<RegionTree>>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/findRegionByid", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<RegionTree>> findRegionByid(String id) {
		 List<RegionTree> listTreeVo =new ArrayList<RegionTree>();
		
		  listTreeVo=regionService.findTreeRegion(id);
		return new ResponseEntity<List<RegionTree>>(listTreeVo,HttpStatus.OK);
	}
	
	/**
	 * 
	* @Title: addRegion 
	* @Description: 添加区域方法
	* @param @param pid
	* @param @param name
	* @param @return   
	* @return ResponseEntity<RegionTree>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/addRegion", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<RegionTree> addRegion(String pid,String name,String centerPoint) {
		Collection<Region>  collectionRegion =new ArrayList<Region>();
		Region region=regionService.findListRegionbyid(pid);
		Long maxid=getMaxId(pid);
		Region newRegion=new Region(String.valueOf(maxid+1),name,RegionUtil.getTYpe(region));
		newRegion.setParent(region);
		newRegion.setChildren(collectionRegion);
		if(!newRegion.getType().getName().equals("其他")){
			regionService.saveRegion(newRegion);
		}
		return new ResponseEntity<RegionTree>(RegionUtil.getRegionTree(newRegion),HttpStatus.OK);
	}
	
	
	/**
	 * 
	* @Title: editRegion 
	* @Description: (这里用一句话描述这个方法的作用) 
	* @param @param request
	* @param @param pid
	* @param @param name
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/editRegion", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Region>   editRegion(String id,String pid,String name) {
		logger.debug(pid+name);
		
		Region region=regionService.findListRegionbyid(id);
		Long maxid=Long.parseLong(id);
		Region newRegion=new Region(String.valueOf(maxid),name,RegionUtil.getTYpe(region));
		newRegion.setParent(region);
		region.setName(name);
		region.setParent(regionService.findListRegionbyid(pid));
		regionService.saveRegion(region);
		logger.debug(region);
		return new ResponseEntity<Region>(region,HttpStatus.OK);
	}
	
	
	
	/**
	 * 
	* @Title: dragRegion 
	* @Description: 拖拽后方法
	* @param @param id
	* @param @param pid
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/dragRegion", method = RequestMethod.POST)
	public void dragRegion(String id, String pid) {
		Region region = regionService.findListRegionbyid(id);
		region.setParent(regionService.findListRegionbyid(pid));
		regionService.saveRegion(region);
	}
	
	
	/**
	 * 
	* @Title: deleteRegionbyId 
	* @Description: 删除方法 
	* @param @param id
	* @param @param pid
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/deleteRegionbyId", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteRegionbyId(String id, String pid) {
		Region region = regionService.findListRegionbyid(id);
		if(region.getChildren().size()>0 || saojieDateService.findByReion(region).size()>0|| null!=saojieService.findByregion(region) || assessService.findByRegion(region).size()>0){
			return false;
		}
		regionService.delete(region);
		return true;
	}
	
	/**
	 * 
	* @Title: initRegionMap 
	* @Description: 初始化地图页面
	* @param @param regionName
	* @param @param parentid
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/initRegionMap")
	public String initRegionMap(String regionName,String parentid,Model model){
		
		Region region = regionService.findListRegionbyid(parentid);
		List<Region> listRegion =new ArrayList<Region>();
		for(Region reg:region.getChildren()){
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(reg.getName());
            String name = m.replaceAll("");
            reg.setName(name);
			listRegion.add(reg);
		}
		String name=regionName;
		String pcoordinates=null;
		if(null!=region.getCoordinates()){
		  name=region.getParent().getName();
		  pcoordinates=region.getCoordinates();
		}
		model.addAttribute("parentName",region.getParent().getName());
 		model.addAttribute("jsonData", listRegion);
 		model.addAttribute("regionName", name);
 		model.addAttribute("parentid", parentid);
 		model.addAttribute("pcoordinates",pcoordinates);
		return "region/region_map";
	}
	
	/**
	 * 
	* @Title: addPoints 
	* @Description: 添加地图轮廓
	* @param @param points
	* @param @param parentid
	* @param @param name
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/addPoints", method = RequestMethod.POST)
	@ResponseBody
	public boolean addPoints(String  points,String parentid,String name,String centerPoint) {
		JSONArray jsonArr = JSONArray .fromObject(points);
		StringBuffer pointbuf=new StringBuffer();
		for(int i=0;i<jsonArr.size();i++){
			JSONObject jsonObject = JSONObject .fromObject(jsonArr.get(i));
			pointbuf.append(jsonObject.get("lng")).append("-").append(jsonObject.get("lat")).append("=");
		}
		Region region=regionService.findListRegionbyid(parentid);
		Long maxid=getMaxId(parentid);
		Region entity=new Region(String.valueOf(maxid+1),name,RegionUtil.getTYpe(region));
		entity.setCoordinates(pointbuf.toString());
		entity.setParent(regionService.findListRegionbyid(parentid));
		entity.setCenterPoint(centerPoint);
		regionService.saveRegion(entity);
		return true;
		
	}
	
	/**
	 * 
	* @Title: getMaxId 
	* @Description: 得到最大regionid
	* @param @param request
	* @param @param pid
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public Long getMaxId(String pid) {
		// 国 0, 大区1,省2,区3,市 4,县5, 镇6;
		Region region = regionService.findListRegionbyid(pid);
		Long maxid = 0L;
		if (region.getType().getName().equals("国")
				|| region.getType().getName().equals("省")) {
			for (Region reg : region.getChildren()) {
				if (reg.getType().getName().equals("大区")
						|| reg.getType().getName().equals("区")) {
					if (maxid < Integer.parseInt(reg.getId())) {
						maxid = Long.parseLong(reg.getId());
					}
				}
			}
			if (maxid == 0) {
				maxid = Long.parseLong(region.getId());
			}
		} else {
			if (region.getChildren().size() > 0) {
				for (Region reg : region.getChildren()) {
					if (maxid < Long.parseLong(reg.getId())) {
						maxid = Long.parseLong(reg.getId());
					}
				}
			} else {
				maxid = Long.parseLong(pid + "00");
			}
		}
		
		while(1==1){
		  List<Region> list = regionService.findByRegion((maxid+1)+"");
		  if(list != null && list.size() > 0){
		      maxid=maxid+1;
	    }else{
	      break;
	    }
		}
	
		return maxid+1;
	}
	
  /**
   * 
  * @Title: getRegionById 
  * @Description: TODO(根据用户区域id获取下级区域) 
  * @param @param id
  * @param @param request
  * @param @return    设定文件 
  * @return ResponseEntity<List<RegionVo>>    返回类型 
  * @throws
   */
	
	@RequestMapping(value="/getRegionById",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<RegionTree>> getRegionById(String id,HttpServletRequest request){
		String regionId = "0";
		if(id != null && !"".equals(id)){
			regionId = id;
		}else{
			Subject subject = SecurityUtils.getSubject();
			User user=(User) subject.getPrincipal();
//			com.wangge.buzmgt.teammember.entity.Manager manager = managerService.getById(user.getId());
			Manager manager1 = managerService.getById(user.getId());
			regionId = String.valueOf(manager1.getRegion().getId());
		}
		   List<RegionTree> regionList = regionService.getRegionByPid(regionId);
		return new ResponseEntity<List<RegionTree>>(regionList,HttpStatus.OK);
	}
	 
	  /**
	   * 
	    * getPersonalRegion:初始化所属区域树页面. <br/> 
	    * 
	    * @author jiabin 
	    * @param id
	    * @param flag
	    * @param request
	    * @return 
	    * @since JDK 1.8
	   */
	  
	  @RequestMapping(value="/getPersonalRegion",method = RequestMethod.GET)
	  public String  getPersonalRegion(String id,String flag,Model model){
	    model.addAttribute("flag",flag);
	    return "region/region_personal";
	  }
	  
	  /**
	   * 
	    * updateYewuData:更改扫街数据 <br/> 
	    * @author Administrator 
	    * @param points
	    * @param parentid
	    * @param name
	    * @param centerPoint
	    * @param model
	    * @return 
	    * @since JDK 1.8
	   */
	  @RequestMapping(value = "/updateYewuData", method = RequestMethod.GET)
	  public String updateYewuData(String  points,String parentid,String name,String centerPoint,Model model) {
	    JSONArray jsonArr = JSONArray .fromObject(points);
	    StringBuffer pointbuf=new StringBuffer();
	    for(int i=0;i<jsonArr.size();i++){
	      JSONObject jsonObject = JSONObject .fromObject(jsonArr.get(i));
	      pointbuf.append(jsonObject.get("lng")).append("-").append(jsonObject.get("lat")).append("=");
	    }
	    Region region=regionService.findListRegionbyid(parentid);
	    region.setCoordinates(pointbuf.toString());
	    region.setCenterPoint(centerPoint);
	    
	    regionService.saveRegion(region);
	    
	    Region parentReigon =region.getParent();
	     SalesMan man=salesmanservice.findSaleamanByRegionId(region.getParent().getId());
	     if(man==null){
	    	 model.addAttribute("man","此区域没有业务员");
	     }else{
	    	 model.addAttribute("man",man.getTruename());
	    	 SaojieDataVo saojiedatalist  = sds.getsaojieDataList(man.getId(),null);
	 	    List<SaojieData> list = saojiedatalist.getList();
	 	    int size = 0;
	 	    if(list!=null && !list.isEmpty()){
	 	      size = list.size();
	 	      saojiedatalist.setShopNum(size);
	 	    }
	 	    model.addAttribute("saojiedatalist",saojiedatalist);
	     }
	   
	    
	    
	    model.addAttribute("areaname",parentReigon.getParent().getName()+parentReigon.getName());
	    model.addAttribute("regionData",regionService.findByRegion(parentReigon.getId()));
	    model.addAttribute("pcoordinates",parentReigon.getCoordinates());
	    model.addAttribute("parentid",parentid);
	
	     return "region/region_yewuData";
	    
	  }
	  
	  
	  
	  @RequestMapping(value = "/getSaojiedataMap", method = RequestMethod.POST)
	  @JSONFormat(filterField={"SaojieData.saojie","SaojieData.registData","Region.children","Region.parent"})
	  public SaojieDataVo getSaojiedataMap(String regionid){
		  System.out.println(regionid);
	    SaojieDataVo saojiedatalist  = sds.getsaojieDataList(null, regionid);
	    List<SaojieData> list = saojiedatalist.getList();
	    int size = 0;
	    if(list!=null && !list.isEmpty()){
	      size = list.size();
	      saojiedatalist.setShopNum(size);
	    }
	    return saojiedatalist;
	  }
	  
	  
	  
	  
	  
	  /**
	   * 
	    * findOnePersonalRegion:一级区域 <br/> 
	    * 
	    * @author  
	    * @return 
	    * @since JDK 1.8
	   */
	  @RequestMapping(value = "/findOnePersonalRegion", method = RequestMethod.POST)
	  @ResponseBody
	  public ResponseEntity<List<RegionTree>> findOnePersonalRegion() {
	     List<RegionTree> listTreeVo =new ArrayList<RegionTree>();
	      Subject subject = SecurityUtils.getSubject();
	      User user=(User) subject.getPrincipal();
	      Manager manager = managerService.getById(user.getId());
	      listTreeVo.add(RegionUtil.getRegionTree(manager.getRegion()));
	    return new ResponseEntity<List<RegionTree>>(listTreeVo,HttpStatus.OK);
	  }
	  
	  
	  /**
	   * 
	    * findOneSaojieRegion:一级扫街区域
	    * @author Administrator 
	    * @param regionid
	    * @return 
	    * @since JDK 1.8
	   */
	  @RequestMapping(value = "/findOneSaojieRegion", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<OrganizationVo>> findOneSaojieRegion(String regionid) {
       List<OrganizationVo> listTreeVo =new ArrayList<OrganizationVo>();
       Region region =regionService.findListRegionbyid(regionid);
       List<Region> listRegion =regionService.findByRegion(region.getParent().getId());
       SaojieData saojiedata=null;
        for(Region reigon :listRegion){
          listTreeVo.add(getRegionVo(reigon,saojiedata));
        }
        
      return new ResponseEntity<List<OrganizationVo>>(listTreeVo,HttpStatus.OK);
    }
	  
	  
	  /**
	   * 
	    * findSaojieDataByid:扫街信息
	    * @author Administrator 
	    * @param id
	    * @return 
	    * @since JDK 1.8
	   */
	  @RequestMapping(value = "/findSaojieDataByid", method = RequestMethod.POST)
	  @ResponseBody
	  public ResponseEntity<List<OrganizationVo>> findSaojieDataByid(String id) {
	     List<OrganizationVo> listTreeVo =new ArrayList<OrganizationVo>();
	     List<SaojieData> list =saojieDateService.findByReion(regionService.findListRegionbyid(id));
	     Region region=null;
	     for(SaojieData saojiedata:list){
	       listTreeVo.add(getRegionVo(region, saojiedata));
	     }
	     return new ResponseEntity<List<OrganizationVo>>(listTreeVo,HttpStatus.OK);
	  }
	  
	  /**
	   * 
	    * dragSaojieData:拖转更改扫街、注册数据信息
	    * @author Administrator 
	    * @param id
	    * @param pid 
	    * @since JDK 1.8
	   */
	  @RequestMapping(value = "/dragSaojieData", method = RequestMethod.POST)
	  @ResponseBody
	  public String dragSaojieData(String id, String pid) {
	    SaojieData saojiedata=saojieDateService.findById(Long.parseLong(id));
	    Region region=regionService.findListRegionbyid(pid);
	    
	    SalesMan salesman= salesmanservice.findSaleamanByRegionId(region.getParent().getId());
	    if(null==salesman){
	    	return "false";
	    }
	    
	    saojiedata.setRegion(region);
	    saojieDateService.saveSaojieData(saojiedata);
	    if(null!=saojiedata.getRegistData()){
	      RegistData registdata=assessService.findRegistData(saojiedata.getRegistData().getId());
	      if(null!=registdata){
	        registdata.setRegion(region);
	        registdata.setSalesman(salesman);
	        assessService.saveRegistData(registdata);
	      }
	    }
	   return "true";
	  }
	  
	  /**
	   * 
	    * getRegionVo:区域扫街树
	    * @author Administrator 
	    * @param region
	    * @param saojiedata
	    * @return 
	    * @since JDK 1.8
	   */
	  private OrganizationVo getRegionVo(Region region,SaojieData saojiedata){
	    OrganizationVo vo=new OrganizationVo();
	    String iconUrl=null;
	    if(null!=region){
	      vo.setId(region.getId()+"");
	      vo.setName(region.getName());
	      if(saojieDateService.findByReion(region).size()>0){
	        vo.setIsParent("true");
	      }else{
	        vo.setIsParent("false");
	      }
	      iconUrl="/static/img/region/xian.png";
	    }else{
	      vo.setId(saojiedata.getId()+"");
	      vo.setName(saojiedata.getName());
	      vo.setIsParent("false");
	      iconUrl="/static/img/organization/jl.png";
	    }
    
    
       
      vo.setIcon(iconUrl);
      vo.setIconClose(iconUrl);
      vo.setIconOpen(iconUrl);
      vo.setOpen("true");
      vo.setpId("0");
      return vo;
  }
	  
}
