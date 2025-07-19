package com.valkyrie.order_service.service;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.valkyrie.order_service.repository.OrderRepository;
import com.valkyrie.order_service.feign.ProductFeignController;
import com.valkyrie.order_service.model.Order;
import com.valkyrie.order_service.model.Product;
import com.valkyrie.order_service.model.ProductWrapper;
import com.valkyrie.order_service.model.Store;
import com.valkyrie.order_service.model.OrderWrapper;

@Service
public class OrderService {
    private OrderRepository repo;
    @Autowired
    private void setRepo(OrderRepository repo) {this.repo = repo;}

    private ProductFeignController feign;
    @Autowired
    private void setFeign(ProductFeignController feign) {this.feign = feign;}

    //SAVE-ORDER
    public Store<String> saveOrder(int productId, int quantity, String address, String username) {
        String uuid = UUID.randomUUID().toString();
        Product product = feign.productById(productId).getBody();

        if (product != null) {

            if (product.getQuantity() > quantity) {
                product = product.setQuantity(product.getQuantity() - quantity);
                feign.updateProduct(product.getId(), product);
            } else {
                return Store.initialize(HttpStatus.BAD_REQUEST, "Amount of Quantity order exceed the Stock");
            }

        }

        repo.save(new Order().setId(uuid).setQuantity(quantity).setAddress(address)
                            .setProducts(productId).setUsername(username));
        return repo.findById(uuid).orElse(null) == null?
                Store.initialize(HttpStatus.BAD_REQUEST, "Order not saved...") :
                Store.initialize(HttpStatus.ACCEPTED, "Order with ID = "+ uuid +" saved successfully...");
    }

    //UPDATE-ORDER
    public Store<String> updateOrder(String uuid, String address) {
        Order order = repo.findById(uuid).orElse(null);

        if (order == null) {
            return Store.initialize(HttpStatus.BAD_REQUEST, "There is no order with UUID = " + uuid);
        } else if (!order.getAddress().equals(address)) {
            String previousAddress = order.getAddress();
            repo.save(
                new Order().setId(uuid).setProducts(order.getProducts()).setQuantity(order.getQuantity())
                            .setAddress(address).setUsername(order.getUsername())
            );
            Order checkOrder = repo.findById(uuid).orElse(null);
            // System.out.println("checkOrder=" + checkOrder.toString());
            // System.out.println("order=" + order.toString() + "previous=" + previousAddress);
            return checkOrder != null && !checkOrder.getAddress().equals(previousAddress)?
                    Store.initialize(HttpStatus.ACCEPTED, "Order with UUID = " + uuid + " has been updated successfully...") : 
                    Store.initialize(HttpStatus.NOT_ACCEPTABLE, "Order with UUID = " + uuid + " not updated successfully...");
        }

        return Store.initialize(HttpStatus.BAD_REQUEST, "Update order not possible...");
    }

    //CANCEL-ORDER
    public Store<String> cancelOrder(String uuid) {
        Order order = repo.findById(uuid).orElse(null);

        if (order == null) {
            return Store.initialize(HttpStatus.OK, "Order with UUID = " + uuid + " Already been deleted");
        }

        Product product = feign.productById(order.getProducts()).getBody();

        if (product != null) {
            product = product.setQuantity(product.getQuantity() + order.getQuantity());
            feign.updateProduct(product.getId(), product);
        }

        repo.deleteById(uuid);
        return repo.findById(uuid).orElse(null) != null?
                Store.initialize(HttpStatus.BAD_REQUEST, "Currently it is not possible to cancel the order") : 
                Store.initialize(HttpStatus.OK, "Order with UUID = " + uuid + " has been deleted successfully...");
    }

    //DISPLAY-ORDER
    public Store<List<OrderWrapper>> displayOrder(String username) {
        List<Order> orders = repo.findAllByUsername(username);
        List<OrderWrapper> orderWrappers = new ArrayList<>();

        for (Order order : orders) {
            Product product = feign.productById(order.getProducts()).getBody();

            if (product == null) {return Store.initialize(HttpStatus.BAD_REQUEST, new ArrayList<>());}

            orderWrappers.add(
                new OrderWrapper().setId(order.getId()).setProducts(
                    new ProductWrapper().setName(product.getName())
                            .setBrand(product.getBrand()).setDescription(product.getDescription())
                            .setSpecification(product.getSpecification())
                ).setQuantity(order.getQuantity()).setAddress(order.getAddress())
            );
        }

        return Store.initialize(HttpStatus.OK, orderWrappers);
    } 
}
