package com.wangge.buzmgt.sys.service;

import java.util.List;

import com.wangge.buzmgt.sys.entity.Organization;
import com.wangge.buzmgt.sys.vo.OrganizationVo;

/**
 * 
* @ClassName: OrganzitionService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author SongBaoZhen
* @date 2015年12月29日 下午1:08:10
*
 */
public interface OrganizationService {

	public List<OrganizationVo> getOrganListById(int id);

	public Organization getOrganById(int id);
	
	public Organization addOrganization(Organization organ);
	
	public void deleteOrganization(Organization organ);
	
}
