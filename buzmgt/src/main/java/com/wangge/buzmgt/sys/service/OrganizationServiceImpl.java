package com.wangge.buzmgt.sys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wangge.buzmgt.sys.entity.Organization;
import com.wangge.buzmgt.sys.repository.OrganizationRepository;
import com.wangge.buzmgt.sys.vo.OrganizationVo;
/**
 * 
* @ClassName: OrganizationServiceImpl
* @Description: TODO(这里用一句话描述这个类的作用)
* @author SongBaoZehn
* @date 2015年12月29日 下午1:10:32
*
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

	@Resource
	private OrganizationRepository organRepository;
	
	@Override
	public List<OrganizationVo> getOrganListById(Long id) {

        List<OrganizationVo> voList = new ArrayList<OrganizationVo>();
		Organization organ = organRepository.findOrganizationById(id);
		Set<Organization> childrren =  organ.getChildren();
		for(Organization o : childrren){
				OrganizationVo vo = new OrganizationVo();
				vo.setId(o.getId());
				vo.setName(o.getName());
				voList.add(vo);
				if(o.getChildren() != null){
					Set<Organization> childr =  o.getChildren();
					while(childr.size() > 0){
						for(Organization or : childr){
							OrganizationVo vor = new OrganizationVo();
							vor.setId(or.getId());
							vor.setName(or.getName());
							childr = or.getChildren();
							voList.add(vor);
						}
			        }
				}
		}
		return voList;
	}

	@Override
	public Organization getOrganById(long id) {
		
		return  organRepository.findOrganizationById(id);
	}
}
