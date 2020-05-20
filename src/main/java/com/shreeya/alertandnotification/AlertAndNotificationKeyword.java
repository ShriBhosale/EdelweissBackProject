package com.shreeya.alertandnotification;

import java.util.ArrayList;
import java.util.List;

public class AlertAndNotificationKeyword {

	List<String> keywordList;
	public AlertAndNotificationKeyword() {
		keywordList=new ArrayList<String>();
	}
	
	public List<String> keywordExecution() {
		keywordList.add("addAlert");
		keywordList.add("modifyAlert");
		keywordList.add("deleteAlert");
		return keywordList;
	}
}
