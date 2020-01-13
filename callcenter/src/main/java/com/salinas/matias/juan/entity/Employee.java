package com.salinas.matias.juan.entity;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Juan M. Salinas
 * 
 * Modela a un empleado. 
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
		logger.info("Thread Employee : " + Thread.currentThread().getName() + " empezando ");
		while(!this.callsQueue.isEmpty()){
			logger.info("Cantidad de llamadas : " + this.callsQueue.size());	
			Call call = this.callsQueue.poll();
			this.setBusy();
			logger.info("-> "+ "Thread Employee : " + Thread.currentThread().getName() + " (Ocupado) atiende llamada de ( "+ call.getDurationInSeconds()+" ) segundos, " + "Cantidad de llamadas restantes : " + this.callsQueue.size());
			try {
				TimeUnit.SECONDS.sleep(call.getDurationInSeconds());
				this.setAvailable();
				logger.info("-> " + Thread.currentThread().getName()  + " disponible ");
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
			}
		}		
	}
}
