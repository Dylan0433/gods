package com.beyond.core.orm;

import java.io.Serializable;

/**
 * 
 * @author Dylan
 * @time 下午3:55:10
 */
public class Page<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * the number of very page
	 */
	private int pageSize = 0;
	
	/**
	 * which page we hold now
	 */
	private int currentPage = 0;
	
	/**
	 * 
	 */
	private int nextPage = 0;
	
	/**
	 * 
	 */
	private int prePage = 0;
	
	/**
	 * 
	 */
	private int totalPage = 0;
	
	/**
	 * 
	 */
	private T result = null;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public boolean hasPrePage(){
		return currentPage>0;
	}
	
	public boolean hasNextPage(){
		return totalPage>currentPage;
	}

	public void calculateTotalPage(int allCount){
		totalPage = allCount/pageSize;
	}
	
	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
	
}
