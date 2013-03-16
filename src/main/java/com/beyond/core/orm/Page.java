package com.beyond.core.orm;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

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
	private int pageSize = 20;
	
	/**
	 * which page we hold now
	 */
	private int currentPage = 1;
	
	/**
	 * 
	 */
	private int totalPages = 0;
	
	/**
	 * 
	 */
	private int totalCount=0;
	
	/**
	 * 
	 */
	private List<T> result = Collections.emptyList();

	public int getPageSize() {
		return pageSize;
	}

	public Page<T> setPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public Page<T> setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		return this;
	}

	public int getNextPage() {
		if(hasNextPage())
			return ++currentPage;
		return currentPage;
	}

	public int getPrePage() {
		if(hasPrePage())
			return --currentPage;
		return currentPage;
	}

	public int getTotalPages() {
		totalPages = totalCount/pageSize;
		if((totalCount%pageSize)>0)
			++totalPages;
		return totalPages;
	}
	
	public  int getFirstPage(){
		currentPage=1;
		return currentPage;
	}
	
	public int getLastPage(){
		currentPage=getTotalPages();
		return currentPage;
	}
	
	public boolean hasPrePage(){
		return currentPage>0;
	}
	
	public boolean hasNextPage(){
		return totalPages>currentPage;
	}
	
	public int getTotalCount() {
		return totalCount;
	}

	public Page<T> setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		return this;
	}


	public List<T> getResult() {
		return result;
	}

	public Page<T> setResult(List<T> result) {
		this.result = result;
		return this;
	}
	
	
}
