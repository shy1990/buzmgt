package com.wangge.buzmgt.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IncomeThreadPool {
  public static ExecutorService exServ = Executors.newFixedThreadPool(100);
}
