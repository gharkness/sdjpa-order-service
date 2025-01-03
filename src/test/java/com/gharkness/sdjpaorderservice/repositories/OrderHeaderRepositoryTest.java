package com.gharkness.sdjpaorderservice.repositories;
import com.gharkness.sdjpaorderservice.domain.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderHeaderRepositoryTest {

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    OrderApprovalRepository orderApprovalRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    Product product;

    @BeforeEach
    void setUp() {
        Product newProduct = new Product();
        newProduct.setDescription("test product");
        newProduct.setProductStatus(ProductStatus.NEW);
        product = productRepository.saveAndFlush(newProduct);
    }

    @Test
    void testSaveOrderWithLine() {
        OrderHeader orderHeader = new OrderHeader();
        Customer customer = new Customer();
        customer.setCustomerName("New Customer");
        Customer savedCustomer = customerRepository.save(customer);

        orderHeader.setCustomer(savedCustomer);

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(5);
        orderLine.setProduct(product);

        orderHeader.addOrderLine(orderLine);

        OrderApproval approval = new OrderApproval();
        approval.setApprovedBy("me");

        orderHeader.setOrderApproval(approval);

        OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);

        orderHeaderRepository.flush();

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());
        assertNotNull(savedOrder.getOrderLines());
        assertEquals(savedOrder.getOrderLines().size(), 1);

        OrderHeader fetchedOrder = orderHeaderRepository.getById(savedOrder.getId());

        assertNotNull(fetchedOrder);
        assertEquals(fetchedOrder.getOrderLines().size(), 1);
    }

    @Test
    void testSaveOrder() {
        OrderHeader orderHeader = new OrderHeader();
        Customer customer = new Customer();
        customer.setCustomerName("New Customer");
        customer.setPhone("012345678");

        Address address = new Address();
        address.setCity("012345678");
        customer.setAddress(address);

        Customer savedCustomer = customerRepository.save(customer);

        orderHeader.setCustomer(savedCustomer);
        OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());

        OrderHeader fetchedOrder = orderHeaderRepository.getById(savedOrder.getId());

        assertNotNull(fetchedOrder);
        assertNotNull(fetchedOrder.getId());
        assertNotNull(fetchedOrder.getCreatedDate());
        assertNotNull(fetchedOrder.getLastModifiedDate());
    }


    @Test
    void testDeleteCascade() {
        OrderHeader orderHeader = new OrderHeader();
        Customer customer = new Customer();
        customer.setCustomerName("new Customer");
        orderHeader.setCustomer(customerRepository.save(customer));

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(3);
        orderLine.setProduct(product);

        OrderApproval approval = new OrderApproval();
        approval.setApprovedBy("me");
        orderHeader.setOrderApproval(approval);

        orderHeader.addOrderLine(orderLine);
        OrderHeader savedOrder = orderHeaderRepository.saveAndFlush(orderHeader);

        System.out.println("order saved and flushed");

        orderHeaderRepository.deleteById(savedOrder.getId());
        orderHeaderRepository.flush();

        assertThrows(EntityNotFoundException.class, () -> {
           OrderHeader fetchedOrder = orderHeaderRepository.getById(savedOrder.getId());

           //   entity doesn't throw exception until we try and access the object
           fetchedOrder.getOrderStatus();
        });
    }

    @Test
    void testSaveOrderWithInvalidCustomerName() {
        OrderHeader orderHeader = new OrderHeader();
        Customer customer = new Customer();
        customer.setCustomerName("This customer name is way too long and should trigger a validation error because it exceeds the maximum length allowed by the @Size annotation");

        assertThrows(ConstraintViolationException.class, () -> {
            orderHeader.setCustomer(customerRepository.save(customer));
            orderHeaderRepository.saveAndFlush(orderHeader);
        });
    }

    @Test
    void testSaveOrderWithInvalidAddress() {
        OrderHeader orderHeader = new OrderHeader();
        Customer customer = new Customer();
        customer.setCustomerName("Valid Customer");
        orderHeader.setCustomer(customerRepository.save(customer));

        Address invalidAddress = new Address();
        invalidAddress.setCity("This city name is way too long and should trigger a validation error because it exceeds the maximum length allowed by the @Size annotation");

        assertThrows(ConstraintViolationException.class, () -> {
            orderHeader.setShippingAddress(invalidAddress);
            orderHeaderRepository.saveAndFlush(orderHeader);
        });
    }


}
