package com.salinas.matias.juan.entity;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import com.salinas.matias.juan.Constants;

/**
 * 
 * @author Juan M. Salinas
 * 
 * Modela al objeto manager de llamadas. 
 * 
 *
 */
public class Dispatcher implements Runnable{

	private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);
	
	private static final Integer MAX_CALLS = 10;
	private Map<Integer, String> attendOrder;
	private List<Employee> employeesList; 
	private ConcurrentLinkedQueue<Call> callsQueue;
	private ExecutorService executorService;
	
	/**
	 * Construye el Dispatcher 
	 * @param employeesList: Lista de empleados.
	 */
	public Dispatcher(List<Employee> employeesList) {
		this.employeesList = Collections.synchronizedList(employeesList);
		this.callsQueue = new ConcurrentLinkedQueue<Call>();
		this.executorService = Executors.newFixedThreadPool(MAX_CALLS);
		this.setAttentionInstances();
	}
	
	/**
	 * Configura el orden de atencion por categoria del empleado desde un archivo .yaml
	 */
	private void setAttentionInstances() {
		Yaml yaml = new Yaml();	
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(Constants.CONFIG_ATTENTION_INSTANCES);
		this.attendOrder = Collections.synchronizedMap(new TreeMap<>());
		this.attendOrder = yaml.load(inputStream);
	}
	
	@Override
	public void run() {
		logger.info("Thread - Dispatcher : " + Thread.currentThread().getName() + " empezando ");
		while(!this.callsQueue.isEmpty()) {
			Call call = this.callsQueue.poll();
			this.dispatchCall(call);
			logger.info("-> " + Thread.currentThread().getName() + " dispatchCall: ( " + call.getDurationInSeconds() + " ) segundos ");
		}
		this.executorService.shutdown();
        while (!this.executorService.isTerminated()){ } 
	}
	
	/**
	 * Asigna llamadas a los empleados y van atendiendo.
	 * @param call
	 */
	private synchronized void dispatchCall(Call call) {
		Optional<Employee> optionalEmployee = this.findAvailableEmployee();
		if(optionalEmployee.isPresent()) {
			Employee employee = optionalEmployee.get();
			employee.addCall(call);
			this.executorService.execute(employee);
		}
	}
		
	/**
	 * Agrega una llamada a la cola de espera para ser atendida.
	 * @param call
	 */
	public void addCall(Call call) {
		this.callsQueue.add(call);
	}
	
	
	/** Busca un empleado disponible para atender la llamada: 
	 *  - En primer instancia debe ser atendida por un Operador. Si hay alguno disponible retorna  Optional<Employee> Operador. 
	 *  - Si todos los operadores se encuentran ocupados y hay algun supervisor disponible retorna  Optional<Employee> Supervisor. 
	 *  - Si todos los supervisores se encuentran ocupados y hay algun supervisor disponible retorna  Optional<Employee> Director.
	 *  - Si todos se encuentran ocupados retorna un Optional<Employee> vacio. 
	 *  
	 * @param employees
	 * @return Optional<Employee>
	 */	
	public Optional<Employee> findAvailableEmployee() {
		
		Optional<Employee> optionalEmployee = Optional.empty();
		List<Employee> availableEmployeeList = this.employeesList.stream().filter(e -> e.isAvailable()).collect(Collectors.toList());
		
		for (Entry<Integer, String> entry : this.attendOrder.entrySet()) {
			EmployeeCategory category = EmployeeCategory.valueOf(entry.getValue());
			Optional<Employee> optEmployee = availableEmployeeList.stream().filter(e -> e.getCategory().equals(category)).findAny();
			if(optEmployee.isPresent()){
				optionalEmployee = Optional.of(optEmployee.get());
				break;
			}
		}
		return optionalEmployee;
	}

	/* Getters an Setters */
	
	public List<Employee> getEmployeesList() {
		return employeesList;
	}

	public void setEmployeesList(List<Employee> employeesList) {
		this.employeesList = Collections.synchronizedList(employeesList);
	}

	public Map<Integer, String> getAttendOrder() {
		return attendOrder;
	}

	public void setAttendOrder(Map<Integer, String> attendOrder) {
		this.attendOrder = attendOrder;
	}

	public ConcurrentLinkedQueue<Call> getCallsQueue() {
		return callsQueue;
	}

	public void setCallsQueue(ConcurrentLinkedQueue<Call> callsQueue) {
		this.callsQueue = callsQueue;
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}
}
