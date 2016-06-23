package com.wangge.buzmgt.cash.repository;

import java.util.Date;
import java.util.List;

import com.wangge.buzmgt.cash.entity.MonthPunish;

public interface MonthPunishRepositoryCustom {

  List<MonthPunish> findAllISNotCash(Date minDate, Date maxDate);
}
