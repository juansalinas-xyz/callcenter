package com.salinas.matias.juan;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.salinas.matias.juan.entity.Call;
import com.salinas.matias.juan.entity.Dispatcher;
import com.salinas.matias.juan.entity.Employee; 
import com.salinas.matias.juan.entity.EmployeeCategory;
import com.salinas.matias.juan.entity.EmployeeState;


class DispatcherTest {
	
	private static final Logger logger = LoggerFactory.getLogger(DispatcherTest.class);
	
	private Dispatcher dispatcher;
	
	@BeforeEach 
	void init(){
		this.dispatcher = new Dispatcher(new CopyOnWriteArrayList<Employee>());
	}
	
	@Test
	void dispatchTenCalls() {
		
		logger.info("Init proceso 10 llamadas ... ");
		
		Employee e1 = new Employee(EmployeeCategory.OPERATOR);
		e1.setState(EmployeeState.AVAILABLE);
		
		Employee e2 = new Employee(EmployeeCategory.OPERATOR);
		e2.setState(EmployeeState.AVAILABLE);
		
		Employee e3 = new Employee(EmployeeCategory.OPERATOR);
		e3.setState(EmployeeState.AVAILABLE);
		
		Employee e4 = new Employee(EmployeeCategory.OPERATOR);
		e4.setState(EmployeeState.AVAILABLE);
		
		Employee e5 = new Employee(EmployeeCategory.OPERATOR);
		e5.setState(EmployeeState.AVAILABLE);
		
		Employee e6 = new Employee(EmployeeCategory.OPERATOR);
		e6.setState(EmployeeState.AVAILABLE);
		
		Employee e7 = new Employee(EmployeeCategory.OPERATOR);
		e7.setState(EmployeeState.BUSY);
		 
		Employee e8 = new Employee(EmployeeCategory.OPERATOR);
		e8.setState(EmployeeState.BUSY);
		
		Employee e9 = new Employee(EmployeeCategory.SUPERVISOR);
		e9.setState(EmployeeState.BUSY);
		
		Employee e10 = new Employee(EmployeeCategory.DIRECTOR);
		e10.setState(EmployeeState.BUSY);
		
		List<Employee> employees = Arrays.asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10);
		
		this.dispatcher.setEmployeesList(employees);
		
		ConcurrentLinkedQueue<Call> callsQueue = new ConcurrentLinkedQueue<Call>();
		
		for(int i= 0; i < 10; i++) {
			callsQueue.add(new Call());
		}
		
		this.dispatcher.setCallsQueue(callsQueue);
		
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.execute(this.dispatcher);
		executorService.shutdown();
        while (!executorService.isTerminated()){ }
        
        logger.info("Fin proceso 10 llamadas ... ");
        
	}
	
}
