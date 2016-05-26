package com.wangge.buzmgt.monthTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import com.wangge.buzmgt.monthTask.entity.MonthOdersData;
import com.wangge.buzmgt.monthTask.entity.MonthTask;
import com.wangge.buzmgt.monthTask.entity.MonthTaskSub;
import com.wangge.buzmgt.monthTask.repository.MonthOrdersDataRepository;

@RepositoryEventHandler
public class DataEventHandler {
	@Autowired
	private MonthOrdersDataRepository monthRep;

	@HandleBeforeCreate
	public void handlePersonCreate(MonthTask monthTask) {
		MonthOdersData orda = monthRep.findFirst1bySalesmanOrRegionId("",
				monthTask.getRegionid(), monthTask.getMonth());
		if (null != orda) {
			orda.setUsed(1);
			monthRep.save(orda);
			monthTask.setMonthData(orda);
		}
	}

	@HandleBeforeSave
	public void handleProfileSave(MonthTaskSub monthTasksub) {
		// … you can now deal with Profile in a type-safe way
	}
}