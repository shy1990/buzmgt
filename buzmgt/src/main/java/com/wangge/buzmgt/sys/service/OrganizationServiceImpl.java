package com.wangge.buzmgt.sys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangge.buzmgt.region.repository.RegionRepository;
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

  @Autowired
	private OrganizationRepository organRepository;
  @Autowired
  private RegionRepository regionRepository;
	@Override
	public List<OrganizationVo> getOrganListById(int id) {

        List<OrganizationVo> voList = new ArrayList<OrganizationVo>();
		Organization organ = organRepository.findOrganizationByIdOrderById(id);
		List<Organization> childrren =  organ.getChildren();
		for(Organization o : childrren){
				OrganizationVo vo = new OrganizationVo();
				vo.setId(o.getId()+"");
				vo.setName(o.getName());
				voList.add(vo);
				if(o.getChildren() != null){
				  List<Organization> childr =  o.getChildren();
					while(childr.size() > 0){
						for(Organization or : childr){
							OrganizationVo vor = new OrganizationVo();
							vor.setId(or.getId()+"");
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
	public Organization getOrganById(int id) {
		
		return  organRepository.findOrganizationByIdOrderById(id);
	}

  @Override
  public Organization addOrganization(Organization organ) {
    // TODO Auto-generated method stub
    
     return organRepository.save(organ);
  }

  @Override
  @Transactional 
  public void deleteOrganization(Organization organ) {
    organRepository.deleteOrganization(organ.getId());
  }
	
	
	
}
