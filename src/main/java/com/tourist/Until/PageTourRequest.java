package com.tourist.Until;

public class PageTourRequest {
 private int page;
 private int size;
public PageTourRequest(int page, int size) {
	super();
	this.page = page;
	this.size = size;
}
public PageTourRequest() {
	super();
	// TODO Auto-generated constructor stub
}
public int getPage() {
	return page;
}
public void setPage(int page) {
	this.page = page;
}
public int getSize() {
	return size;
}
public void setSize(int size) {
	this.size = size;
}
 
 
}
