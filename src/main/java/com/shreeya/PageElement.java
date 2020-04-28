package com.shreeya;

import org.openqa.selenium.By;

public class PageElement {
	private By by;
	private int waitTime;
	public PageElement(By by, int waitTime) {
		super();
		this.by = by;
		this.waitTime = waitTime;
	}
	public By getBy() {
		return by;
	}
	public int getWaitTime() {
		return waitTime;
	}
	
	
}
