package com.salinas.matias.juan;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.salinas.matias.juan.entity.Call;
import com.salinas.matias.juan.entity.Employee;
import com.salinas.matias.juan.entity.EmployeeCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmployeeTest {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeTest.class);

	private ExecutorService executorService;
	
	
	@BeforeEach
	public void init() {
		this.executorService = Executors.newFixedThreadPool(10); 
	}
	
	@Test
	public void processCalls() {
		
		logger.info("Init ProcessCalls ");
		
		for(Employee e : this.emlpoyees()) {
			this.executorService.execute(e);
		}
		this.executorService.shutdown();
        while (!this.executorService.isTerminated()){ }
        
        logger.info("Fin ProcessCalls ");
	}
	
	
	private List<Employee> emlpoyees(){
		
		List<Employee> employeeList = new ArrayList<Employee>();
		
		for (int i = 0; i < 10 ; i++) {
			Employee emp = new Employee(EmployeeCategory.OPERATOR);
			emp.setName("Nombre: " + i);
			emp.setAvailable();
			emp.addCall(new Call());
			emp.addCall(new Call());
			emp.addCall(new Call());
			employeeList.add(emp);
		}
		
		return employeeList; 
	}
}
