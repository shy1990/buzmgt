package com.wangge.buzmgt.region.web;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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

import com.wangge.buzmgt.manager.entity.Manager;
import com.wangge.buzmgt.manager.service.ManagerService;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.region.vo.RegionTree;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.service.UserService;
import com.wangge.buzmgt.sys.vo.RegionVo;
import com.wangge.buzmgt.util.RegionUtil;

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
	public ResponseEntity<RegionTree> addRegion(String pid,String name) {
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
		
		Region region=regionService.findListRegionbyid(pid);
		Long maxid=Long.parseLong(id);
		Region newRegion=new Region(String.valueOf(maxid),name,RegionUtil.getTYpe(region));
		newRegion.setParent(region);
		regionService.saveRegion(newRegion);
		logger.debug(region);
		return new ResponseEntity<Region>(newRegion,HttpStatus.OK);
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
		Region newRegion=new Region(region.getId(),region.getName(),RegionUtil.getTYpe(regionService.findListRegionbyid(pid)));
		newRegion.setParent(region);
		newRegion.setParent(regionService.findListRegionbyid(pid));
		regionService.saveRegion(newRegion);
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
		if(region.getChildren().size()>0){
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
			listRegion.add(reg);
		}
		String name=regionName;
		String pcoordinates=null;
		if(null!=region.getCoordinates()){
		  name=region.getParent().getName();
		  pcoordinates=region.getCoordinates();
		}
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
	public boolean addPoints(String  points,String parentid,String name) {
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
					if (maxid < Integer.parseInt(reg.getId())) {
						maxid = Long.parseLong(reg.getId());
					}
				}
			} else {
				maxid = Long.parseLong(pid + "00");
			}
		}
		return maxid;
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
	public ResponseEntity<List<RegionVo>> getRegionById(String id,HttpServletRequest request){
		String regionId = "0";
		if(id != null && !"".equals(id)){
			regionId = id;
		}else{
			Subject subject = SecurityUtils.getSubject();
			User user=(User) subject.getPrincipal();
			Manager manager = managerService.getById(user.getId());
			regionId = String.valueOf(manager.getRegion().getId());
		}
		   List<RegionVo> regionList = regionService.getRegionByPid(regionId);
		return new ResponseEntity<List<RegionVo>>(regionList,HttpStatus.OK);
	}
	
}
