package streams;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    OrderService ordersService = new OrderService();


    @BeforeEach
    public void init() {


        Product p1 = new Product("Tv", "IT", 2000);
        Product p2 = new Product("Laptop", "IT", 2400);
        Product p3 = new Product("Phone", "IT", 400);
        Product p4 = new Product("Lord of The Rings", "Book", 20);
        Product p5 = new Product("Harry Potter Collection", "Book", 120);

        Order o1 = new Order("pending", LocalDate.of(2021, 6, 7));
        o1.addProduct(p1);
        o1.addProduct(p2);
        o1.addProduct(p5);

        Order o2 = new Order("on delivery", LocalDate.of(2021, 6, 1));
        o2.addProduct(p3);
        o2.addProduct(p1);
        o2.addProduct(p2);

        ordersService.saveOrder(o1);
        ordersService.saveOrder(o2);

    }

    @Test
    void countOrderByStatus() {

        Long result = ordersService.countOrdersByStatus("pending");
        assertEquals(1, result);
    }

    @Test
    void countOrderByStatusWithZero() {

        Long result = ordersService.countOrdersByStatus("pend");
        assertEquals(0, result);
    }

    @Test
    void collectOrderWithCategoryTest() {
        List<Order> orders = ordersService.collectOrdersWithProductCategory("Book");

        assertThat(orders)
                .hasSize(1);

    }

    @Test
    void productOverAmountPrice() {
        List<Product> result = ordersService.productOverAmountPrice(1900);

        assertThat(result)
                .hasSize(2)
                .extracting(Product::getName)
                .containsExactly("Tv", "Laptop");

        assertEquals(2, result.size());
        assertEquals("Tv", result.get(0).getName());

    }

    @Test
    void productOverAmountPriceOver2500() {
        List<Product> result = ordersService.productOverAmountPrice(2500);

        assertEquals(0, result.size());
    }

    @Test
    void testSumProductsPriceBetweenTime() {
        double result = ordersService.sumProductsPriceBetweenTime(LocalDate.of(2021, 5, 1),
                LocalDate.of(2021, 6, 8));
        assertEquals(9320d, result, 0.01);
    }

    @Test
    void testSumProductsPriceBetweenTime2() {
        double result = ordersService.sumProductsPriceBetweenTime(LocalDate.of(2021, 6, 6),
                LocalDate.of(2021, 6, 8));
        assertEquals(4520d, result, 0.01);
    }

    @Test
    void findProductByName() {
        Optional<Product> result = ordersService.findProductByName("Tv");
        assertTrue(result.isPresent());
        assertEquals("Tv", result.get().getName());
    }

    @Test
    void findProductByNameNotFound() {
        Optional<Product> result = ordersService.findProductByName("Tv2");
        assertTrue(result.isEmpty());
    }

    @Test
    void findOrderMostExpensive() {
        Optional<Order> result = ordersService.findOrderMostExpensive();
        assertTrue(result.isPresent());

        assertThat(result.get().getProducts())
                .hasSize(3)
                .extracting(Product::getName)
                .contains("Tv", "Laptop", "Harry Potter Collection");


    }

}