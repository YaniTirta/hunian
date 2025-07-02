package com.cts.application.data;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Customer")
public class CustomerEntity implements Serializable{

	@Id
	@Column(name="CustomerId", nullable=false, length=5)
	private String customerId;
	
	@Column(name="CustomerName", nullable=false, length=50)
	private String customerName;

	@Column(name="CustomerGender", nullable=false, length=10)
	private String customerGender;
	
	@Column(name="CustomerPhone", nullable=false, length=13)
	private String customerPhone;

	@Column(name="CustomerAddress", nullable=false, length=50)
	private String customerAddress;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerGender() {
		return customerGender;
	}

	public void setCustomerGender(String customerGender) {
		this.customerGender = customerGender;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

}
