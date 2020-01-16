package com.educandoweb.course.config;

import java.time.Instant;
import java.util.Arrays;

import com.educandoweb.course.entities.Category;
import com.educandoweb.course.entities.Order;
import com.educandoweb.course.entities.OrderItem;
import com.educandoweb.course.entities.Payment;
import com.educandoweb.course.entities.Product;
import com.educandoweb.course.entities.User;
import com.educandoweb.course.entities.enums.OrderStatus;
import com.educandoweb.course.repositories.CategoryRepository;
import com.educandoweb.course.repositories.OrderItemRepository;
import com.educandoweb.course.repositories.OrderRepository;
import com.educandoweb.course.repositories.ProductRepository;
import com.educandoweb.course.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	private UserRepository userRepository;
	private OrderRepository orderRepository;
	private CategoryRepository categoryRepository;
	private ProductRepository productRepository;
	private OrderItemRepository orderItemRepository;

	@Autowired
	public TestConfig(UserRepository userRepository, OrderRepository orderRepository,
			CategoryRepository categoryRepository, ProductRepository productRepository,
			OrderItemRepository orderItemRepository) {
		this.userRepository = userRepository;
		this.orderRepository = orderRepository;
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
		this.orderItemRepository = orderItemRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		User user1 = new User(null, "Maria Brown", "maria@gmail.com", "988888888", "123456");
		User user2 = new User(null, "Alex Green", "alex@gmail.com", "977777777", "123456");

		Order order1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.WAITING_PAYMENT, user1, null);
		Order order2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"), OrderStatus.PAID, user2, null);
		Order order3 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"), OrderStatus.CANCELED, user1, null);

		Category cat1 = new Category(null, "Eletronics");
		Category cat2 = new Category(null, "Books");
		Category cat3 = new Category(null, "Computers");

		Product product1 = new Product(null, "Head First Java", "Java book for dummies", 130.0, "");
		Product product2 = new Product(null, "Smart TV", "Samsung 52'", 2600.0, "");
		Product product3 = new Product(null, "Macbook Pro", "Intel Core i9, 64 Gb, SSD 2TB", 15450.0, "");
		Product product4 = new Product(null, "KeyBoard", "RGB Mechanical Keyboard", 450.0, "");
		Product product5 = new Product(null, "Dart and Flutter", "Development for every platform", 89.0, "");

		userRepository.saveAll(Arrays.asList(user1, user2));
		orderRepository.saveAll(Arrays.asList(order1, order2, order3));
		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));
		productRepository.saveAll(Arrays.asList(product1, product2, product3, product4, product5));

		// creating many to many relationship

		product1.getCategories().add(cat2);
		product2.getCategories().add(cat1);
		product3.getCategories().add(cat1);
		product3.getCategories().add(cat3);
		product4.getCategories().add(cat1);
		product4.getCategories().add(cat3);
		product5.getCategories().add(cat2);

		productRepository.saveAll(Arrays.asList(product1, product2, product3, product4, product5));

		OrderItem orderItem1 = new OrderItem(order1, product1, 2, product1.getPrice());
		OrderItem orderItem2 = new OrderItem(order1, product3, 1, product3.getPrice());
		OrderItem orderItem3 = new OrderItem(order2, product3, 2, product3.getPrice());
		OrderItem orderItem4 = new OrderItem(order3, product5, 2, product5.getPrice());

		orderItemRepository.saveAll(Arrays.asList(orderItem1, orderItem2, orderItem3, orderItem4));

		Payment pay1 = new Payment(null, Instant.parse("2019-07-25T07:42:10Z"), order2);
		order2.setPayment(pay1);

		orderRepository.save(order2);
	}

}
