package com.salinas.matias.juan.entity;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
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
 * Modela al objeto manager de llamadas que recibe el Call Center. 
 * 
 *
 */
public class Dispatcher implements Runnable{

	
	private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);
	
	private static final Integer MAX_CALLS = 10;
	private Map<Integer, String> attendOrder;
	private CopyOnWriteArrayList<Employee> employeesList; 
	private ConcurrentLinkedQueue<Call> callsQueue;
	private ExecutorService executorService;
	
	/**
	 * Construye el Dispatcher 
	 * @param employeesList: Lista de empleados CopyOnWriteArrayList (thread-safe)
	 */
	public Dispatcher(CopyOnWriteArrayList<Employee> employeesList) {
		this.employeesList = employeesList;
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
		while(!this.callsQueue.isEmpty()) {
			Call call = this.callsQueue.poll();
			this.dispatchCall(call);
		}
		this.processCalls();
	}
	
	/**
	 * Agrega una llamada a la cola de espera para ser atendida.
	 * @param call
	 */
	public void addCall(Call call) {
		this.callsQueue.add(call);
	}
	
	/**
	 * Asigna llamadas a los empleados.
	 * @param call
	 */
	private synchronized void dispatchCall(Call call) {
		Optional<Employee> optionalEmployee = this.find(this.employeesList);
		if(optionalEmployee.isPresent()) {
			Employee employee = optionalEmployee.get();
			employee.addCall(call);
		}
	}
	
	/**
	 * Los empleados procesan sus respectivas llamadas asignadas.
	 */
	private void processCalls() {
		for(Employee employee : this.employeesList) {
			this.executorService.execute(employee);
		}	
	}
	
	/** Busca un empleado disponible para atender la llamada: 
	 *  - En primer instancia debe ser atendida por un Operador. Si hay alguno disponible retorna  Optional<Employee> Operador. 
	 *  - Si todos los operadores se encuentran ocupados y hay algun supervisor disponible retorna  Optional<Employee> Supervisor. 
	 *  - Si todos los supervisores se encuentran ocupados y hay algun supervisor disponible retorna  Optional<Employee> Director.
	 *  - Si todos se encuentran ocupados retorna un Optional<Employee> vacio. 
	 * @param employees
	 * @return Optional<Employee>
	 */	
	public Optional<Employee> find(Collection<Employee> employees) {

		Optional<Employee> optionalEmployee = Optional.empty(); 
		List<Employee> availableEmployeeList = employees.stream().filter(e -> e.isAvailable()).collect(Collectors.toList());

		logger.info("Cantidad availableEmployeeList: " + availableEmployeeList.size());

		for (Entry<Integer, String> entry : this.attendOrder.entrySet()) {
			
			logger.info("Orden : " + entry.getKey() + ": " + entry.getValue());
			EmployeeCategory category = EmployeeCategory.valueOf(entry.getValue());
			
			Optional<Employee> optEmployee = availableEmployeeList.stream().filter(e -> e.getCategory().equals(category)).findAny();

			if(optEmployee.isPresent()){
				logger.info(optEmployee.get().toString() + "  Libre ");
				optionalEmployee = Optional.of(optEmployee.get());
				break;
			}
		}
		return optionalEmployee;
	}
}
