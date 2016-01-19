package com.wangge.buzmgt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.repository.RegionRepository;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.region.vo.RegionTree;
import com.wangge.buzmgt.sys.entity.Organization;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.repository.OrganizationRepository;
import com.wangge.buzmgt.sys.repository.UserRepository;
import com.wangge.buzmgt.sys.service.OrganizationService;
import com.wangge.buzmgt.sys.service.UserService;
import com.wangge.buzmgt.sys.vo.OrganizationVo;
import com.wangge.buzmgt.teammember.entity.Manager;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.entity.SalesMan.SalesmanStatus;
import com.wangge.buzmgt.teammember.service.ManagerService;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.buzmgt.util.RegionUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuzmgtApplication.class)
@WebAppConfiguration
public class BuzmgtApplicationTests {
	@Resource
	private UserService userService;
	@Resource
	private UserRepository userRepository;
	@Resource
	private RegionService regionService;
	@Resource
	private RegionRepository regionRepostory;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private OrganizationRepository OrganRepository;
	@Resource
	private ManagerService managerService;
    @Resource
	private SalesManService salesManService;
	@Test
	@Transactional
	public void contextLoads() {
		
		Organization type = OrganRepository.findOne(1L);
		Set<Organization> childrren =  type.getChildren();
		
 		List<OrganizationVo> types = new ArrayList<OrganizationVo>();
 		//types.add(type);
 			for(Organization o : childrren){
 				OrganizationVo vo = new OrganizationVo();
 				vo.setId(o.getId());
 				vo.setName(o.getName());
 				types.add(vo);
 				if(o.getChildren() != null){
 					Set<Organization> childr =  o.getChildren();
 					while(childr.size() > 0){
 						for(Organization or : childr){
 							OrganizationVo vor = new OrganizationVo();
 							vor.setId(or.getId());
 							vor.setName(or.getName());
 							childr = or.getChildren();
 							types.add(vor);
 						}
 					
 				}
 				//vo.setOpen("false");
 				
 				
 			}
 		   //  types.add(parent);
 		  //   parent =  parent.getParent();
 		}
 		for(OrganizationVo r : types){
 			System.out.println("id=="+r.getId()+"name==="+r.getName());
 		}
	}
	@Test
	//@Transactional
	public void testOrgan(){
		String[] num = {"A","B","C","D","E","F"} ;
		SimpleDateFormat formatter = new SimpleDateFormat("MMdd");
		String time = formatter.format(new Date());
		String uid = "370105";
		String userId = "";
		String regionId = "370105";
		String name = "服务站经理";
		//User user = userRepository.findUserById("370105");
		
		if(name.equals("服务站经理")){
			List<User> uList = salesManService.findByReginId(regionId);
			if(uList.size() > 0){
				for(int i=0;i<uList.size();i++){
					System.out.println("<<======>>"+uList.get(i));
					for(int j=0;j<uList.size();j++){
						userId += num[i]+uid+time+"0";
						System.out.println("======>>"+userId);
						break;
					}
				}
			}else{
				for(int j=0;j<num.length;j++){
					userId += num[0]+uid+time+"0";
					System.out.println("======>>"+userId);
					break;
				}
			}
		}else{
			List<Manager> uList = managerService.findByReginId(regionId);
			if(uList.size() > 0){
				for(int i=0;i<uList.size();i++){
					System.out.println("<<======>>"+uList.get(i));
					for(int j=0;j<uList.size();j++){
						userId += num[i]+uid+time+"0";
						System.out.println("======>>"+userId);
						break;
					}
				}
			}else{
				for(int j=0;j<num.length;j++){
					userId += num[0]+uid+time+"0";
					System.out.println("======>>"+userId);
					break;
				}
			}
		}
		
		//System.out.println("=========="+user.getOrganization().getId());
	//	System.out.println("d======="+time);
		
		
	}
	
	@Test
	public  void test(){
	  
	 User user  = userService.getById("1");
	 System.out.println("================="+user.getId()+">>>>>"+user.getUsername());
	 
	}
	
	@Test
	@Transactional
	public void testPage(){
	  int page = 0;
	  Region r = new Region();
    r.setId("370105");
	  SalesMan a = new SalesMan();
	 
	// a.setSalesmanStatus(SalesmanStatus.SAOJIE);
	  //a.setTruename("王五");
	 //  a.setRegion(r);
	 // a.setJobNum("2016010602");
	  Page<SalesMan> user  = salesManService.getSalesmanList(a,page);
	  
	  System.out.println("======================"+user.getNumber());
	  System.out.println("========================="+user.getSize());
	  System.out.println("========================="+user.getTotalPages());
	  System.out.println("========================="+user.getTotalPages()*user.getSize());
	  
	  for( SalesMan s : user.getContent()){
	    System.out.println("==========name==="+s.getTruename());
	  /* System.out.println("==========org==="+s.getUser().getOrganization().getName());
	     System.out.println("==========reg==="+s.getRegion().getName());
	    System.out.println("==========status==="+s.getSalesmanStatus());
*/	  }
	    
	}
	@Test
	public void test2(){
	  System.out.println("============="+SalesmanStatus.SAOJIE);
	}
	@Test
	public void testRegion(){
	      String id  = "370105";
	      Collection<Region> children = regionRepostory.findOne("0").getChildren();
	       List<RegionTree> listRegionTree = new ArrayList<RegionTree>();
	      // Region r = regionRepostory.findOne(id);
	       String ptree = "0,1,370000,370004,370100";
	       String[] strArr=ptree.split(",");//把字符串分割成一个数组  
	       listRegionTree =createTree(listRegionTree,children, strArr);
	       
	       
	      for(RegionTree r : listRegionTree){
	        System.out.println("======================"+r.getName());
	      }
	      
	}
	
  private List<RegionTree> createTree(List<RegionTree> listRegionTree, Collection<Region> children, String[] strArr){
  
    //Region r = regionRepostory.findOne(id);
    
    //   strArr.sort();//排序  
 /*for ( var i in strArr) {
   if (strArr[i] != tempStr) {
     result.push(strArr[i]);
   } else {
     continue;
   }
 }*/
	for(Region region : children){
      listRegionTree.add(RegionUtil.getRegionTree(region));
    if(strArr != null && strArr.length > 0 ){
      /*for ( int i = 0;i< strArr.length; i++) {
        if(!strArr[i].equals("0")){
          Region r = regionRepostory.findOne(strArr[i]);
          System.out.println("=================="+strArr[i]);
          if (r.getParent().getId().equals(region.getId()) ) {
            createTree(listRegionTree, region.getChildren(),strArr);
          } else {
            continue;
          }
        }
      if(ptree.contains(region.getId())){
        // children = region.getChildren();
         createTree(listRegionTree, region.getChildren(),id);
       }
    }*/
      createTree(listRegionTree, creteChildTree(region, strArr),strArr);
      }
     
    }
	  return listRegionTree;
	}
  
  private  Collection<Region> creteChildTree(Region region,String[] strArr){
    Collection<Region> children = new HashSet<Region>();
    for ( int i = 0;i< strArr.length; i++) {
     // if(!strArr[i].equals("0")){
       // Region r = regionRepostory.findOne(strArr[i]);
        if (strArr[i].equals(region.getId()) ) {
          //creteChildTree(region.get,strArr);
          System.out.println("=================="+strArr[i]);
          System.out.println("==================>>>"+region.getId());
          children  = region.getChildren();
        } else {
          continue;
        }
     }
  // }
    return children;
}
  @Test
  public void testSalesman(){
     SalesMan  s = salesManService.findById("C37010501060");
    System.out.println("================="+s.getTruename());
    System.out.println("================="+s.getId());
    System.out.println("================="+s.getMobile()); 
    System.out.println("================="+s.getSalesmanStatus()); 
    
  }
}
