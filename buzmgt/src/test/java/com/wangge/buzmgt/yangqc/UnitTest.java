package com.wangge.buzmgt.yangqc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.wangge.buzmgt.util.DateUtil;

public class UnitTest {
  @Test
  public void testDate() {
    System.out.println(DateUtil.getPreMonth(new Date(), 2));
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date smdate = null;
    try {
      smdate = sdf.parse("2016-08-04");
      int between = DateUtil.getDayBetweenNextMonth(smdate);
      
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }
  
  @Test
  public void testdouble() {
   double d=10D/30D;
   System.out.println(String.format("%.2f", d));
  }
  @Test
  public void testSort() {
   List<Integer> alist=new ArrayList<Integer>(Arrays.asList(1,7,8,4,2,4));
   alist.sort((a1,a2)-> a1-a2);
   System.out.println(alist);
  }
 
}
