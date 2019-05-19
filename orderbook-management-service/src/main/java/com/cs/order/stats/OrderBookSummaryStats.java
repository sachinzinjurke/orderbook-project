package com.cs.order.stats;

public class OrderBookSummaryStats {
	
	private Long totalOrderBookQuantityCount;
	private Integer smallestOrderInBook;
	private Integer biggestOrderInBook;
	private Long totalOrderCount;
	private Integer earliestOrderEntry;
	private Integer lastOrderEntry;
	
	public Long getTotalOrderBookQuantityCount() {
		return totalOrderBookQuantityCount;
	}
	public void setTotalOrderBookQuantityCount(Long totalOrderBookQuantityCount) {
		this.totalOrderBookQuantityCount = totalOrderBookQuantityCount;
	}
	public Long getTotalOrderCount() {
		return totalOrderCount;
	}
	public void setTotalOrderCount(Long totalOrderCount) {
		this.totalOrderCount = totalOrderCount;
	}
	public Integer getSmallestOrderInBook() {
		return smallestOrderInBook;
	}
	public void setSmallestOrderInBook(Integer smallestOrderInBook) {
		this.smallestOrderInBook = smallestOrderInBook;
	}
	public Integer getBiggestOrderInBook() {
		return biggestOrderInBook;
	}
	public void setBiggestOrderInBook(Integer biggestOrderInBook) {
		this.biggestOrderInBook = biggestOrderInBook;
	}
	@Override
	public String toString() {
		return "OrderBookSummaryStats [totalOrderBookQuantityCount=" + totalOrderBookQuantityCount
				+ ", smallestOrderInBook=" + smallestOrderInBook + ", biggestOrderInBook=" + biggestOrderInBook
				+ ", totalOrderCount=" + totalOrderCount + "]";
	}
	
	
}
