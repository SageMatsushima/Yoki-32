package edu.brown.cs.rhill6.mockaroo;

/**
 * Class for storing mock data for a single person.
 */
public class MockPerson {
  private String firstName, lastName, dateTime, emailAddress, gender, streetAddress;

  /**
   * Constructor to fill out all fields of MockPerson.
   * @param first String for first name
   * @param last String for last name
   * @param time String for date time
   * @param email String for email address
   * @param gen String for gender
   * @param street String for street address
   */
  public MockPerson(String first, String last, String time,
                    String email, String gen, String street) {
    firstName = first;
    lastName = last;
    dateTime = time;
    emailAddress = email;
    gender = gen;
    streetAddress = street;
  }

  @Override
  public String toString() {
    return lastName + ", " + firstName;
  }
}
