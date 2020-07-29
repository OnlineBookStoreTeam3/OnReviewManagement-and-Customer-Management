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
class CustomerManagementApplicationTests {
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
	void testAddCustomerPositive() throws Exception {
		String url = "http://localhost:" + localServerPort + "/CustomerReview/addCustomer";
		Customer customer = new Customer(115, "Sneha", "snehaasaji@gmail.com", "sneha@780", 9676251906L, "11-3-2455",
				"hyderabad", "India", 598764L, LocalDate.now());
		ResponseEntity<String> response = testRestTemplate.postForEntity(url, customer, String.class);
		Assertions.assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	void testAddCustomerNegative() throws Exception {
		Customer customer = new Customer(10013, "aaa", "kumari@gmail.com", "praval123", 83099572L, "12-21", "Hyderabad",
				"India", 598764L, LocalDate.now());
		Customer c = customerService.createCustomer(customer);
		Assertions.assertEquals(c.getCustomerId(), customer.getCustomerId());

	}

	@Test
	void testUpdateCustomerPositive() throws Exception {
		Customer customer = new Customer(1004, "Krishna Vamshi", "mohanvamshi@gmail.com", "krishna@123", 8309957852L,
				"11-3-2455", "Hyderabad", "India", 598764L, LocalDate.now());
		Customer c = customerService.createCustomer(customer);
		Assertions.assertEquals(c.getCustomerId(), customer.getCustomerId());
	}

	@Test
	void testUpdateCustomerNegative() throws Exception {
		Customer customer = new Customer(1003, "Prav", "pravalikaa@gmail.com", "praval123", 83099572L, "13-2455",
				"Hyderabad", "India", 598764L, LocalDate.now());
		Customer c = customerService.createCustomer(customer);
		Assertions.assertEquals(c.getCustomerId(), customer.getCustomerId());
	}

	@Test
	void testDeleteCustomerPositive() throws Exception {
		String url = "http://localhost:" + localServerPort + "/CustomerReview/deleteCustomer/snehaasaji@gmail.com";
		ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
		Assertions.assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	void testDeleteCustomerNegative() throws Exception {
		String url = "http://localhost:" + localServerPort + "/CustomerReview/deleteCustomer/kuma@gmail.com";
		ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
		Assertions.assertEquals(404, response.getStatusCodeValue());

	}

	@Test
	void testGetCustomersPositive() throws Exception {
		List<Customer> customer = customerService.getCustomers();
		Assertions.assertEquals(true, customer.containsAll(customer));
	}

	@Test
	void testGetCustomersNegative() throws Exception {
		List<Customer> customer = customerService.getCustomers();
		Assertions.assertEquals(false, customer.isEmpty());
	}

	@Test
	void testGetCustomerByMailIdPositive() throws Exception {
		String url = "http://localhost:" + localServerPort + "/CustomerReview/getCustomer/kumari@gmail.com";
		ResponseEntity<Customer> customer = testRestTemplate.getForEntity(url, Customer.class);
		Assertions.assertEquals(200, customer.getStatusCodeValue());
	}

	@Test
	void testGetCustomerByMailIdNegative() throws Exception {
		String url = "http://localhost:" + localServerPort + "/CustomerReview/getCustomer/xyz@gmail.com";
		ResponseEntity<Customer> customer = testRestTemplate.getForEntity(url, Customer.class);
		Assertions.assertNotEquals(200, customer.getStatusCodeValue());
	}
}
