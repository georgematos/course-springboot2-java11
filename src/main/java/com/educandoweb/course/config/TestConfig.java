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
		User u1 = new User(null, "Maria Brown", "maria@gmail.com", "988888888", "123456");
		User u2 = new User(null, "Alex Green", "alex@gmail.com", "977777777", "123456");

		Order o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.WAITING_PAYMENT, u1, null);
		Order o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"), OrderStatus.PAID, u2, null);
		Order o3 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"), OrderStatus.CANCELED, u1, null);

		Category cat1 = new Category(null, "Eletronics");
		Category cat2 = new Category(null, "Books");
		Category cat3 = new Category(null, "Computers");

		Product p1 = new Product(null, "Head First Java", "Java book for dummies", 130.0, "");
		Product p2 = new Product(null, "Smart TV", "Samsung 52'", 2600.0, "");
		Product p3 = new Product(null, "Macbook Pro", "Intel Core i9, 64 Gb, SSD 2TB", 15450.0, "");
		Product p4 = new Product(null, "KeyBoard", "RGB Mechanical Keyboard", 450.0, "");
		Product p5 = new Product(null, "Dart and Flutter", "Development for every platform", 89.0, "");

		userRepository.saveAll(Arrays.asList(u1, u2));
		orderRepository.saveAll(Arrays.asList(o1, o2, o3));
		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

		// creating many to many relationship

		p1.getCategories().add(cat2);
		p2.getCategories().add(cat1);
		p3.getCategories().add(cat1);
		p3.getCategories().add(cat3);
		p4.getCategories().add(cat1);
		p4.getCategories().add(cat3);
		p5.getCategories().add(cat2);

		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

		OrderItem orderItem1 = new OrderItem(o1, p1, 2, p1.getPrice());
		OrderItem orderItem2 = new OrderItem(o1, p3, 1, p4.getPrice());
		OrderItem orderItem3 = new OrderItem(o2, p3, 2, p1.getPrice());
		OrderItem orderItem4 = new OrderItem(o3, p5, 2, p5.getPrice());

		orderItemRepository.saveAll(Arrays.asList(orderItem1, orderItem2, orderItem3, orderItem4));

		Payment pay1 = new Payment(null, Instant.parse("2019-07-25T07:42:10Z"), o2);
		o2.setPayment(pay1);

		orderRepository.save(o2);
	}

}
