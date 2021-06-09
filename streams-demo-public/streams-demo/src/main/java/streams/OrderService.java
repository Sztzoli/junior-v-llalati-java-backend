package streams;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class OrderService {

    private List<Order> orders = new ArrayList<>();


    public void saveOrder(Order order) {
        orders.add(order);
    }

    public long countOrdersByStatus(String status) {
        return orders.stream()
                .filter(order -> order.getStatus().equals(status))
                .count();
    }

    public List<Order> collectOrdersWithProductCategory(String category) {
        return orders.stream().
                filter(order -> order.getProducts()
                        .stream()
                        .anyMatch(product -> product.getCategory().equals(category)))
                .collect(Collectors.toList());

    }

    public List<Product> productOverAmountPrice(int amount) {
        return orders.stream()
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getPrice() > amount)
                .distinct()
                .collect(Collectors.toList());
    }

    public double sumProductsPriceBetweenTime(LocalDate start, LocalDate end) {
        return orders.stream()
                .filter(order -> order.getOrderDate().isAfter(start) && order.getOrderDate().isBefore(end))
                .flatMap(order -> order.getProducts().stream())
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public Optional<Product> findProductByName(String name) {
        return orders.stream()
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getName().equals(name))
                .findFirst();
    }

    public Optional<Order> findOrderMostExpensive() {
        return orders.stream().max((o1, o2) -> {
            double o1d = o1.getProducts().stream().mapToDouble(Product::getPrice).max().getAsDouble();
            double o2d = o2.getProducts().stream().mapToDouble(Product::getPrice).max().getAsDouble();
            return (int) (o1d - o2d);
        });
    }

}
