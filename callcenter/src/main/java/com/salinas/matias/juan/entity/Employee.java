package com.salinas.matias.juan.entity;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Juan M. Salinas
 *
 * Modela a un empleado
 *
 */
public class Employee implements Runnable{

	private static final Logger logger = LoggerFactory.getLogger(Employee.class);
	
	private String name;
	private String surname; 
	private EmployeeState state;
	private EmployeeCategory category;
	private ConcurrentLinkedDeque<Call> callsQueue;
	
	public Employee(EmployeeCategory cateogry) {
		this.category = cateogry;
		this.callsQueue = new ConcurrentLinkedDeque<>();
	}
	
	public Boolean isAvailable() {
		return this.state == EmployeeState.AVAILABLE;
	}

	public EmployeeState getState() {
		return state;
	}

	public void setState(EmployeeState state) {
		this.state = state;
	}

	public EmployeeCategory getCategory() {
		return category;
	}

	public void setCategory(EmployeeCategory category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public synchronized void setBusy(){
		this.state = EmployeeState.BUSY; 
	}
	
	public synchronized void setAvailable(){
		this.state = EmployeeState.AVAILABLE; 
	}
	
	public void addCall(Call call) {
		this.callsQueue.add(call);
	}
	
	@Override
	public void run() {
		while(!this.callsQueue.isEmpty()){
			Call call = this.callsQueue.poll();
			this.attend(call);
		}		
	}	
	
	/**
	 * Metodo mediante el cual el empleado atiende la llamada.
	 * @param call
	 */
	private synchronized void attend(Call call) {
		this.setBusy();
		try {
			logger.info("-> " + this.name + " ocupado ");
			TimeUnit.SECONDS.sleep(call.getDurationInSeconds());
			this.setAvailable();
			logger.info("-> " + this.name + " disponible ");
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}
	}
}
