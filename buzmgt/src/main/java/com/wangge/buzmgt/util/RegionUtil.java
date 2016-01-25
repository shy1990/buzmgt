package com.wangge.buzmgt.util;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.entity.Region.RegionType;
import com.wangge.buzmgt.region.vo.RegionTree;

public class RegionUtil {
	
	private static final String ONELEV="大区"; //除中国之外的级别
	private static final String TWOLEV="省"; 
	private static final String THREELEV="区"; 
	private static final String FOURLEV="市";
	private static final String FIRELEV="县";
	private static final String SIXLEV="镇";
	private static final String OTHER="其他";
	private static final String COUNTRY="国";
	/**
	 * 
	* @Title: getRegionTree 
	* @Description: 返回区域树对象（一个节点）
	* @param @param region
	* @param @return    设定文件 
	* @return Region    返回类型 
	* @throws
	 */
	public static RegionTree getRegionTree(Region region){
		RegionTree regionTree=new RegionTree();
		regionTree.setId(region.getId());
		regionTree.setName(region.getName());
		regionTree.setOpen("true");
		if (!region.getChildren().isEmpty() && region.getChildren().size() > 0) {
			regionTree.setIsParent("true");
		} else {
			regionTree.setIsParent("false");

		}
		String imgUrl=null;
		if (region.getType().getName().equals(ONELEV)) {
			imgUrl = "/static/img/region/quyu.png";
		} else if (region.getType().getName().equals(TWOLEV)) {
			imgUrl = "/static/img/region/sheng.png";
		} else if (region.getType().getName().equals(THREELEV)) {
			imgUrl = "/static/img/region/qu.png";
		} else if (region.getType().getName().equals(FOURLEV)) {
			imgUrl = "/static/img/region/shi.png";
		} else if (region.getType().getName().equals(FIRELEV)) {
			imgUrl = "/static/img/region/xian.png";
		} else if (region.getType().getName().equals(SIXLEV)) {
			imgUrl = "/static/img/region/zhen.png";
		} else if (region.getType().getName().equals(OTHER)) {
			regionTree.setRegiontype("false");
		}else if(region.getType().getName().equals(COUNTRY)){
		  imgUrl="/static/img/region/zhongguo.png";
		}
		regionTree.setIconOpen(imgUrl);
		regionTree.setIconClose(imgUrl);
		regionTree.setIcon(imgUrl);
		if(null!=region.getParent()){
	    regionTree.setpId(region.getParent().getId());
		}
		
		return regionTree;
	}
	
	/**
	 * 
	* @Title: getTYpe 
	* @Description: 获取区域类型
	* @param @param region
	* @param @return    设定文件 
	* @return RegionType    返回类型 
	* @throws
	 */
	public static RegionType getTYpe(Region region){
		RegionType type=RegionType.COUNTRY;
		if(region.getType().ordinal()==0){
			type=RegionType.PARGANA;
		}else if(region.getType().ordinal()==1){
			type=RegionType.PROVINCE;
		}else if(region.getType().ordinal()==2){
			type=RegionType.AREA;
		}else if(region.getType().ordinal()==3){
			type=RegionType.CITY;
		}else if(region.getType().ordinal()==4){
			type=RegionType.COUNTY;
		}else if(region.getType().ordinal()==5){
			type=RegionType.TOWN;
		}else{
			type=RegionType.OTHER;
		}
		return type;
	}
}
