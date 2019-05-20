package io.github.shanerwu.refactoring.ch1;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CustomerTest {

    private Movie regularMovie;
    private Movie childrenMovie;
    private Movie newReleaseMovie;

    @Before
    public void setUp() {
        regularMovie = new Movie("regular", Movie.REGULAR);
        childrenMovie = new Movie("children", Movie.CHILDRENS);
        newReleaseMovie = new Movie("newRelease", Movie.NEW_RELEASE);
    }

    @Test
    public void ReturnEqualString_IfRentRegularMovieLessThan2Days() {
        Customer tom = new Customer("Tom");
        tom.addRental(new Rental(regularMovie, 1));

        String returnString = getRentalInfo(tom, regularMovie)
                + getCalculatingInfo(2.0, 2.0, 1);
        assertEquals(returnString, tom.statement());
    }

    @Test
    public void ReturnEqualString_IfRentRegularMovieGreaterThan2Days() {
        Customer tom = new Customer("Tom");
        tom.addRental(new Rental(regularMovie, 3));

        String expected = getRentalInfo(tom, regularMovie)
                + getCalculatingInfo(3.5, 3.5, 1);
        assertEquals(expected, tom.statement());
    }

    @Test
    public void ReturnEqualString_IfRentChildMovieLessThan3Days() {
        Customer tom = new Customer("Tom");
        tom.addRental(new Rental(childrenMovie, 2));

        String expected = getRentalInfo(tom, childrenMovie)
                + getCalculatingInfo(1.5, 1.5, 1);
        assertEquals(expected, tom.statement());
    }

    @Test
    public void ReturnEqualString_IfRentChildMovieGreaterThan3Days() {
        Customer tom = new Customer("Tom");
        tom.addRental(new Rental(childrenMovie, 4));

        String expected = getRentalInfo(tom, childrenMovie)
                + getCalculatingInfo(3.0, 3.0, 1);
        assertEquals(expected, tom.statement());
    }

    @Test
    public void ReturnEqualString_IfRentNewReleaseMovieLessThan2Days() {
        Customer tom = new Customer("Tom");
        tom.addRental(new Rental(newReleaseMovie, 3));

        String expected = getRentalInfo(tom, newReleaseMovie)
                + getCalculatingInfo(9.0, 9.0, 2);
        assertEquals(expected, tom.statement());
    }

    @Test
    public void ReturnEqualString_IfRentNewReleaseMovieGreaterThan2Days() {
        Customer tom = new Customer("Tom");
        tom.addRental(new Rental(newReleaseMovie, 3));

        String expected = getRentalInfo(tom, newReleaseMovie)
                + getCalculatingInfo(9.0, 9.0, 2);
        assertEquals(expected, tom.statement());
    }

    private String getRentalInfo(Customer customer, Movie movie) {
        return "Rental Record for " + customer.getName()
                + "\n\t" + movie.getTitle() + "\t";
    }

    private String getCalculatingInfo(double thisAmount, double totalAmount, int points) {
        return thisAmount + "\nAmount owed is " + totalAmount +
                "\nYou earned " + points + " frequent renter points";
    }

}