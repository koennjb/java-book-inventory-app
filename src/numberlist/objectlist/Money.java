package numberlist.objectlist;

/**
 * This class defines a money object that has both dollars and cents. Whenever a
 * new Money object is created, added or subtracted from, the dollars
 * (multiplied by 100) and cents are added together. The sum is modded by 100 to
 * get cents, and divided by 100 to get dollars. the add and subtract methods
 * both return new Money objects, so they are immutable.
 *
 * @author Koenn Becker
 * @version 1.0.0 - 4/7/2018
 */
public final class Money implements Copiable {

    private long dollars;
    private byte cents;

    /**
     * This is the default constructor for Money, which initializes both the
     * dollars and cents numbers to 0.
     */
    public Money() {

        dollars = 0;
        cents = 0;

    }

    /**
     * This is the overloaded constructor for Money, which initializes both the
     * dollars and cents numbers to the passed parameters.
     *
     * @param dollars a long number for dollars
     * @param cents a byte for the cents ($0.50) is input as "50"
     */
    public Money(long dollars, byte cents) {

        long temp;
        temp = (dollars * 100) + cents;

        this.cents = (byte) (temp % 100);
        this.dollars = temp / 100;

    }

    /**
     * This method returns the dollars in a money object.
     *
     * @return long - the dollars in a Money object
     */
    public long getDollars() {

        return dollars;

    }

    /**
     * This method returns the cents in a money object.
     *
     * @return byte - the cents in a Money object
     */
    public byte getCents() {

        return cents;

    }

    /**
     * This method adds two Money objects together and returns a new Money
     * object containing the sum. If a money object containing 1 dollar and 99
     * cents is added to a number containing 2 dollars and negative 4 cents, the
     * resulting Money object will have 0 dollars and 75 cents.
     *
     * @param other the Money object to add
     * @return Money - the sum of current money object plus the passed
     * parameter.
     */
    public Money add(Money other) {

        long temp = dollars * 100;
        temp += cents;
        temp += other.getDollars() * 100;
        temp += other.getCents();

        Money result = new Money((temp / 100), (byte) (temp % 100));
        return result;

    }

    /**
     * This method subtracts two Money objects and returns a new Money object
     * containing the sum. If a money object containing 1 dollar and 50 cents is
     * subtracted by Money containing 0 dollars and 75 cents, the resulting
     * Money object will have 0 dollars and 75 cents.
     *
     * @param other the Money object to subtract by
     * @return Money - the sum of current money object minus the passed
     * parameter.
     */
    public Money subtract(Money other) {

        long temp = dollars * 100;
        temp += cents;
        temp -= other.getDollars() * 100;
        temp -= other.getCents();

        Money result = new Money((temp / 100), (byte) (temp % 100));
        return result;

    }

    /**
     * This method is to display a Money object in the correct format using the
     * dollar sign. If there is less than 0 dollars it is displayed with a
     * negative sign before the dollar sign.
     *
     * @return String - The dollars in cents with a dollar sign before it.
     */
    @Override
    public String toString() {

        String result = "";

        if (dollars < 0 || cents < 0) {
            result = "-$" + Math.abs(dollars) + "." + Math.abs(cents);
        } else {
            result = "$" + dollars + "." + String.format("%02d", cents);
        }

        return result;
    }

    /**
     * This method makes a deep copy of a Money object and returns it.
     *
     * @return Money - a deep copy of the Money object.
     */
    @Override
    public Money deepCopy() {
        return new Money(dollars, cents);
    }

}
