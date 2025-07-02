package com.cts.application.views.myview;

import java.util.ArrayList;
import java.util.List;

import com.cts.application.data.CustomerEntity;
import com.cts.application.services.CustomerService;
import com.cts.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "CustomerView", layout = MainLayout.class)
@PageTitle("View Customer")
public class CustomerView extends Div {

	private TextField customerId = new TextField("Nomor Nasabah");
	private TextField customerName = new TextField("Nama Nasabah");
	private TextField customerGender = new TextField("Jenis Kelamin");
	private TextField customerPhone = new TextField("No Telephon");
	private TextArea customerAddress = new TextArea("Alamat");

	private Button saveButton = new Button("Save Customer");
	private Button readButton = new Button("List all data");
	
	private BeanValidationBinder<CustomerEntity> binder;
	private Grid<CustomerEntity> grid = new Grid<>(CustomerEntity.class, false);
	private List<CustomerEntity> customerList = new ArrayList<CustomerEntity>();
	
	private CustomerEntity entity;	
	private CustomerService service;
	
	public CustomerView(CustomerService service) {
		this.service = service;
		
		addClassName("hello-world-view");

		SplitLayout splitLayout = new SplitLayout();
		splitLayout.setSplitterPosition(30);
		splitLayout.addToPrimary(createForm());
		splitLayout.addToSecondary(createGridForm());
		
		dataBinding();
		readCustomer();
		
		add(splitLayout);
		saveButton.addClickListener(event->{
			saveCustomer();
		});
		readButton.addClickListener(event->{
			readCustomer();
		});
	}

	private void saveCustomer() {
		try {
			entity = new CustomerEntity();
			binder.writeBean(entity);
			service.saveCustomer(entity);
			
			customerList.add(entity);
			grid.getDataProvider().refreshAll();
			Notification.show("Data saved", 3000, com.vaadin.flow.component.notification.Notification.Position.MIDDLE);
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void readCustomer() {
		customerList = service.findAll();
		System.out.println("C.List.. "+customerList.size());
		grid.setItems(customerList);
		grid.getDataProvider().refreshAll();
	}
	
	private void dataBinding() {		
		binder = new BeanValidationBinder<CustomerEntity>(CustomerEntity.class);
		binder.bindInstanceFields(this);
	}
	
	private Component createForm() {
		Div div = new Div();
		div.getStyle().set("margin", "20px");

		FormLayout form = new FormLayout();
		form.setResponsiveSteps(new ResponsiveStep("0",1));
		form.add(customerId,customerName,customerGender,customerPhone,customerAddress);

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.add(saveButton, readButton);		
		buttonLayout.getStyle().set("margin-top", "30px");
		
		div.add(form);		
		div.add(buttonLayout);
		
		return div;
	}
	
	private Component createGridForm() {
		grid.addColumn(CustomerEntity::getCustomerId).setHeader("No Nasabah");
		grid.addColumn(CustomerEntity::getCustomerName).setHeader("Nama Nasabah");
		grid.addColumn(CustomerEntity::getCustomerGender).setHeader("Jenis Kelamin");
		grid.addColumn(CustomerEntity::getCustomerPhone).setHeader("Telphone");
		grid.addColumn(CustomerEntity::getCustomerAddress).setHeader("Alamat");
		grid.setItems(customerList);
		
		grid.setHeight("800px");
		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.asSingleSelect().addValueChangeListener(event -> {
			if (event.getValue() == null) {
				binder.readBean(null);
			} else {
				entity = event.getValue();
				binder.readBean(entity);
			}
		});

		Div wrapper = new Div();
//		wrapper.addClassName("grid-wrapper");
		wrapper.setWidthFull();
		wrapper.add(grid);
		
		return wrapper;
	}
}
