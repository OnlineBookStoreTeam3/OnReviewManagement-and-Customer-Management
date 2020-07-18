package com.cap.management;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.cap.management.entities.Customer;
import com.cap.management.service.CustomerServiceImpl;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerManagementApplicationTests {
	@Autowired
	TestRestTemplate testRestTemplate;

	public void setTestRestTemplate(TestRestTemplate testRestTemplate) {
		this.testRestTemplate = testRestTemplate;
	}

	@LocalServerPort
	int localServerPort;

	@Autowired
	CustomerServiceImpl customerService;

	@Test
	public void testAddCustomerPositive() throws Exception {
		String url = "http://localhost:" + localServerPort + "/CustomerReview/addCustomer";
		Customer customer = new Customer(100, "Mohan", "mohanvamshi@gmail.com", "mohan@143", 9676251906L,
				"11-3-2455", "hyderabad", "India", 598764L, LocalDate.now());
		ResponseEntity<String> response = testRestTemplate.postForEntity(url, customer, String.class);
		Assertions.assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	public void testAddCustomerNegative() throws Exception {
		Customer customer = new Customer(10010, null, "pravalikaa@gmail.com", "praval123", 83099572L, "13-2455",
				"Hyderabad", "India", 598764L, LocalDate.now());
		Customer c = customerService.createCustomer(customer);
		Assertions.assertEquals(c.getCustomerId(), customer.getCustomerId());

	}

	@Test
	public void testUpdateCustomerPositive() throws Exception {
		Customer customer = new Customer(100, "Pravalika", "pravalikajakka@gmail.com", "pravalika123", 8309957852L,
				"11-3-2455", "Hyderabad", "India", 598764L, LocalDate.now());
		Customer c = customerService.createCustomer(customer);
		Assertions.assertEquals(c.getCustomerId(), customer.getCustomerId());
	}

	@Test
	public void testUpdateCustomerNegative() throws Exception {
		Customer customer = new Customer(10010, "Prav", "pravalikaa@gmail.com", "praval123", 83099572L, "13-2455",
				"Hyderabad", "India", 598764L, LocalDate.now());
		Customer c = customerService.createCustomer(customer);
		Assertions.assertEquals(c.getCustomerId(), customer.getCustomerId());
	}

	@Test
	public void testDeleteCustomerPositive() throws Exception {
		String url = "http://localhost:" + localServerPort + "/CustomerReview/deleteCustomer/pravalikajakka@gmail.com";
		ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
		Assertions.assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	public void testDeleteCustomerNegative() throws Exception {
		String url = "http://localhost:" + localServerPort + "/CustomerReview/deleteCustomer/asa@gmail.com";
		ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
		Assertions.assertEquals(500, response.getStatusCodeValue());

		
	}

	@Test
	public void testGetCustomersPositive() throws Exception {
		List<Customer> customer = customerService.getCustomers();
		Assertions.assertEquals(true, customer.containsAll(customer));
	}

	@Test
	public void testGetCustomersNegative() throws Exception {
		List<Customer> customer = customerService.getCustomers();
		Assertions.assertEquals(false, customer.isEmpty());
	}
}
