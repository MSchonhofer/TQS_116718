package com.example.containers;

import com.example.containers.entity.Book;
import com.example.containers.repository.BookRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContainersApplicationTests {

	@Container
	public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:latest")
			.withUsername("duke")
			.withPassword("password")
			.withDatabaseName("books");

	@Autowired
	private BookRepository bookRepository;

	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.password", container::getPassword);
		registry.add("spring.datasource.username", container::getUsername);
	}
	@Test
	@Order(1)
	void createBook(){

		Book book = new Book();
		book.setTitle("Romeo and Julia");
		book.setAuthor("William Shakespeare");
		bookRepository.saveAndFlush(book);
	}

	@Test
	@Order(2)
	void updateBook() {

		Book book1 = new Book("Romeo and Julia", "William Shakespeare");
		bookRepository.saveAndFlush(book1);

		// Retrieve all books from the database
		List<Book> books = bookRepository.findAll();

		for (Book book : books) {
			if (book.getTitle().equals("Romeo and Julia")) {
				book.setTitle("Moby Dick");
				bookRepository.saveAndFlush(book);
			}
		}

		assertThat(books).hasSize(2);
		assertThat(books).extracting(Book::getTitle).contains("Moby Dick");
	}


	@Test
	@Order(3)
	public void testDeleteBook() {
		bookRepository.deleteAll();
		assertThat(bookRepository.count()).isEqualTo(0);
	}
}
