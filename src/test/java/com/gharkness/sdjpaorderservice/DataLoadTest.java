package com.gharkness.sdjpaorderservice;

import com.gharkness.sdjpaorderservice.domain.*;
import com.gharkness.sdjpaorderservice.repositories.CustomerRepository;
import com.gharkness.sdjpaorderservice.repositories.OrderHeaderRepository;
import com.gharkness.sdjpaorderservice.repositories.ProductRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DataLoadTest {
    final String PRODUCT_D1 = "Product 1";
    final String PRODUCT_D2 = "Product 2";
    final String PRODUCT_D3 = "Product 3";

    final String TEST_CUSTOMER = "TEST CUSTOMER";

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    void testN_PlusOneProblem() {

        Customer customer = customerRepository.findCustomerByCustomerNameIgnoreCase(TEST_CUSTOMER).get();

        IntSummaryStatistics totalOrdered = orderHeaderRepository.findAllByCustomer(customer).stream()
                .flatMap(orderHeader -> orderHeader.getOrderLines().stream())
                .collect(Collectors.summarizingInt(OrderLine::getQuantityOrdered));

        System.out.println("Total ordered: " + totalOrdered.getSum());
    }

    @Test
    void testLazyVsEager() {
        OrderHeader orderHeader = orderHeaderRepository.findById(5L).get();

        System.out.println("Order ID is: " + orderHeader.getId());

        System.out.println("Customer name is: " + orderHeader.getCustomer().getCustomerName());
    }

    @Disabled
    @Test
    @Rollback(false)
    void testDataLoader() {
        List<Product> products = loadProducts();
        Customer customer = loadCustomers();

        int ordersToCreate = 10000;

        for (int i = 0; i < ordersToCreate; i++) {
            System.out.println("Creating order #: " + i);
            saveOrder(customer, products);
        }

        orderHeaderRepository.flush();
    }

    private OrderHeader saveOrder(Customer customer, List<Product> products) {
        Random random = new Random();

        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomer(customer);

        products.forEach(product -> {
           OrderLine orderLine = new OrderLine();
           orderLine.setProduct(product);
           orderLine.setQuantityOrdered(random.nextInt(20));
           orderHeader.addOrderLine(orderLine);
        });

        return orderHeaderRepository.save(orderHeader);
    }

    private Customer loadCustomers() {
        return getOrSaveCustomer(TEST_CUSTOMER);
    }

    private Customer getOrSaveCustomer(String customerName) {
        return customerRepository.findCustomerByCustomerNameIgnoreCase(customerName)
                .orElseGet(() -> {
                    Customer c1 = new Customer();
                    c1.setCustomerName(customerName);
                    c1.setEmail("test@example.com");
                    Address address = new Address();
                    address.setAddress("123 Main St");
                    address.setCity("New York");
                    address.setState("NY");
                    address.setZipCode("10001");
                    c1.setAddress(address);
                    return customerRepository.save(c1);
                });
    }

    private List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();

        products.add(getOrSaveProduct(PRODUCT_D1));
        products.add(getOrSaveProduct(PRODUCT_D2));
        products.add(getOrSaveProduct(PRODUCT_D3));

        return products;
    }

    private Product getOrSaveProduct(String description) {
        return productRepository.findByDescription(description)
                .orElseGet(() -> {
                   Product p1 = new Product();
                   p1.setDescription(description);
                   p1.setProductStatus(ProductStatus.NEW);
                   return productRepository.save(p1);
                });
    }
}
