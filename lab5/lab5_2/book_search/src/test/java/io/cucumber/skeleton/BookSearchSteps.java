package io.cucumber.skeleton;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookSearchSteps {
    Library library = new Library();
    List<Book> result = new ArrayList<Book>();

    @Given("+book with the title {string}, written by {string}, published in {int} {word} {int}")
    public void addNewBook(final String title, final String author, final int day, final String month, final int year) {
        //Date published = iso8601Date(day, month, year);
        Book book = new Book(title, author, published);
        library.addBook(book);
    }

    @When("the customer searches for books published between {int} and {int}")
    public void setSearchParameters(final Date from, final Date to) {
        result = library.findBooks(from, to);
    }
    @Then("{int} books should have been found")
    public void verifyAmountOfBooksFound(final int booksFound) {
        assertEquals(result.size(), booksFound);
    }

    @Then("Book {int} should have the title {string}")
    public void verifyBookAtPosition(final int position, final String title) {
        assertEquals(result.get(position - 1).getTitle(), title);
    }
}
