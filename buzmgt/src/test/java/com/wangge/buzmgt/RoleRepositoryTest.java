package com.wangge.buzmgt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import com.wangge.buzmgt.BuzmgtApplication;
import com.wangge.buzmgt.region.repository.RegionRepository;
import com.wangge.buzmgt.sys.repository.ResourceRepository;
import com.wangge.buzmgt.sys.service.ResourceService;


//@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuzmgtApplication.class)
public class RoleRepositoryTest {
	@Autowired
	private ResourceService rs;
	@Autowired
	private ResourceRepository rr;
	@Autowired
  private RegionRepository res;
//	@Test
//	@Transactional
//	public void testQueryRes() {
//		Set<Menu> menus = 	rs.getAllMenus();
//		for(Menu m:menus){
//			System.out.println(m.getName());
//		}
//	}
//	
//	@Test
//	@Transactional
//	public void testQueryMenus() {
//		List<Menu> menus = rs.getMenusByUsername("caozhaoyang");
//		for(Menu m:menus){
//			System.out.println(m.getName());
//		}
//	}
//	@Test
//	@Transactional
//	public void testDelRes() {
//		rr.delete(360448L);
////		rs.delResource(360448L);
//	}
	//@Test
	private void queryRegion(){
	  System.out.println(res.findOne("370209").getName());
	}
}
