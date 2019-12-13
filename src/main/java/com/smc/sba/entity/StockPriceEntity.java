package com.smc.sba.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "stockprice")
public class StockPriceEntity extends AuditEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer priceid;

	@Column(name = "companycode")
	private String companyCode;

	@Column(name = "stockexchange")
	private String stockExchange;

	@Column(name = "currentprice")
	private BigDecimal currentPrice;

	@Column(name = "date")
	private String date;

	@Column(name = "time")
	private String time;

	@Column(name = "datetime")
	private LocalDateTime dateTime;


	public StockPriceEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getPriceid() {
		return priceid;
	}

	public void setPriceid(Integer priceid) {
		this.priceid = priceid;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getStockExchange() {
		return stockExchange;
	}

	public void setStockExchange(String stockExchange) {
		this.stockExchange = stockExchange;
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
}
