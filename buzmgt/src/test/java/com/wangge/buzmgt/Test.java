package com.wangge.buzmgt;

public class Test {

    public static void main(String args[]) {
    		System.out.println(args);
         if (args==null || new Test() {{Test.main(null);}}.equals(null)) {
        	 
                  System.out.print("Hello ");
         } else {
                  System.out.print("World！");
         }
    }
}