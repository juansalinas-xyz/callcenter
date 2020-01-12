package com.salinas.matias.juan;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.salinas.matias.juan.entity.Dispatcher;
import com.salinas.matias.juan.entity.Employee;
import com.salinas.matias.juan.entity.EmployeeCategory;
import com.salinas.matias.juan.entity.EmployeeState;


class DispatcherTest {
	
	private Dispatcher assignStrategy;
	
	@BeforeEach void init(){
		this.assignStrategy = new Dispatcher(new CopyOnWriteArrayList<Employee>());
	}
	
	@Test
	void findOperatortest() {
	
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
		
		Collection<Employee> employees = Arrays.asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10);
		
		Optional<Employee> optEmployee = this.assignStrategy.find(employees);
		
		assertTrue("Es un operador", optEmployee.isPresent());
		
	}
	
	@Test
	void findSupervisortest() {
		
		Employee e1 = new Employee(EmployeeCategory.OPERATOR);
		e1.setState(EmployeeState.BUSY);
		
		Employee e2 = new Employee(EmployeeCategory.OPERATOR);
		e2.setState(EmployeeState.BUSY);
		
		Employee e3 = new Employee(EmployeeCategory.OPERATOR);
		e3.setState(EmployeeState.BUSY);
		
		Employee e4 = new Employee(EmployeeCategory.OPERATOR);
		e4.setState(EmployeeState.BUSY);
		
		Employee e5 = new Employee(EmployeeCategory.OPERATOR);
		e5.setState(EmployeeState.BUSY);
		
		Employee e6 = new Employee(EmployeeCategory.OPERATOR);
		e6.setState(EmployeeState.BUSY);
		
		Employee e7 = new Employee(EmployeeCategory.OPERATOR);
		e7.setState(EmployeeState.BUSY);
		
		Employee e8 = new Employee(EmployeeCategory.OPERATOR);
		e8.setState(EmployeeState.BUSY);
		
		Employee e9 = new Employee(EmployeeCategory.SUPERVISOR);
		e9.setState(EmployeeState.AVAILABLE);
		
		Employee e10 = new Employee(EmployeeCategory.DIRECTOR);
		e10.setState(EmployeeState.BUSY);
		
		Collection<Employee> employees = Arrays.asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10);
		
		Optional<Employee> optEmployee = this.assignStrategy.find(employees);
		
		assertTrue("Es un Supervisor", optEmployee.isPresent());
	}
	
	@Test
	void findDirectortest() {
		
		Employee e1 = new Employee(EmployeeCategory.OPERATOR);
		e1.setState(EmployeeState.BUSY);
		
		Employee e2 = new Employee(EmployeeCategory.OPERATOR);
		e2.setState(EmployeeState.BUSY);
		
		Employee e3 = new Employee(EmployeeCategory.OPERATOR);
		e3.setState(EmployeeState.BUSY);
		
		Employee e4 = new Employee(EmployeeCategory.OPERATOR);
		e4.setState(EmployeeState.BUSY);
		
		Employee e5 = new Employee(EmployeeCategory.OPERATOR);
		e5.setState(EmployeeState.BUSY);
		
		Employee e6 = new Employee(EmployeeCategory.OPERATOR);
		e6.setState(EmployeeState.BUSY);
		
		Employee e7 = new Employee(EmployeeCategory.OPERATOR);
		e7.setState(EmployeeState.BUSY);
		
		Employee e8 = new Employee(EmployeeCategory.OPERATOR);
		e8.setState(EmployeeState.BUSY);
		
		Employee e9 = new Employee(EmployeeCategory.SUPERVISOR);
		e9.setState(EmployeeState.BUSY);
		
		Employee e10 = new Employee(EmployeeCategory.DIRECTOR);
		e10.setState(EmployeeState.AVAILABLE);
		
		Collection<Employee> employees = Arrays.asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10);
		
		Optional<Employee> optEmployee = this.assignStrategy.find(employees);
		
		assertTrue("Es un Director", optEmployee.isPresent());
	}
	
	@Test
	void findNonetest() {
		
		Employee e1 = new Employee(EmployeeCategory.OPERATOR);
		e1.setState(EmployeeState.BUSY);
		
		Employee e2 = new Employee(EmployeeCategory.OPERATOR);
		e2.setState(EmployeeState.BUSY);
		
		Employee e3 = new Employee(EmployeeCategory.OPERATOR);
		e3.setState(EmployeeState.BUSY);
		
		Employee e4 = new Employee(EmployeeCategory.OPERATOR);
		e4.setState(EmployeeState.BUSY);
		
		Employee e5 = new Employee(EmployeeCategory.OPERATOR);
		e5.setState(EmployeeState.BUSY);
		
		Employee e6 = new Employee(EmployeeCategory.OPERATOR);
		e6.setState(EmployeeState.BUSY);
		
		Employee e7 = new Employee(EmployeeCategory.OPERATOR);
		e7.setState(EmployeeState.BUSY);
		
		Employee e8 = new Employee(EmployeeCategory.OPERATOR);
		e8.setState(EmployeeState.BUSY);
		
		Employee e9 = new Employee(EmployeeCategory.SUPERVISOR);
		e9.setState(EmployeeState.BUSY);
		
		Employee e10 = new Employee(EmployeeCategory.DIRECTOR);
		e10.setState(EmployeeState.BUSY);
		
		Collection<Employee> employees = Arrays.asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10);
		
		Optional<Employee> optEmployee = this.assignStrategy.find(employees);
		
		assertFalse("Sin disponibilidad", optEmployee.isPresent());
	}
	
}
