package numberlist.objectlist;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * This class defines a complex number object. Each complex number has a real
 * and a imaginary part, which are treated as separate numbers. The add and
 * subtract methods both return new Complex objects, so they are immutable.
 *
 * @author Koenn Becker
 * @version 1.0.0 - 4/6/2018
 */
public final class Complex implements Copiable {

    private double real;
    private double imaginary;

    /**
     * This is the default constructor for Complex, which initializes both the
     * real and imaginary numbers to 0
     */
    public Complex() {

        real = 0;
        imaginary = 0;

    }

    /**
     * This is the overloaded constructor for Complex, which initializes both
     * the real and imaginary numbers to the parameters that get passed to it.
     *
     * @param real Double - the real part of a complex number
     * @param imaginary Double - the imaginary part of a complex number
     */
    public Complex(double real, double imaginary) {

        this.real = real;
        this.imaginary = imaginary;

    }

    /**
     * This method returns the real part of a complex number.
     *
     * @return double - the real part of a whole number
     */
    public double getReal() {

        return real;
    }

    /**
     * This method returns the imaginary part of a complex
     *
     * @return double - the imaginary part of a whole number
     */
    public double getImaginary() {

        return imaginary;

    }

    /**
     * This method adds two complex numbers together. the real and imaginary
     * numbers are combined separately and then returned as a new Complex
     * object.
     *
     * @param other the Complex object to add
     * @return Complex - the sum of current complex object plus the passed
     * parameter.
     */
    public Complex add(Complex other) {

        double realTemp = real + other.getReal();
        double imaginaryTemp = imaginary + other.getImaginary();

        return new Complex(realTemp, imaginaryTemp);

    }

    /**
     * This method subtracts two complex numbers. the real and imaginary numbers
     * are subtracted separately and then returned as a new Complex object.
     *
     * @param other the Complex object to subtract
     * @return Complex - the sum of current complex object minus the passed
     * parameter.
     */
    public Complex subtract(Complex other) {

        double realTemp = real - other.getReal();
        double imaginaryTemp = imaginary - other.getImaginary();

        return new Complex(realTemp, imaginaryTemp);

    }

    /**
     * This method is used to display the Complex number in correct form with
     * real and imaginary parts. The if statements are used to detect whether a
     * "+" or "-" should be shown in between numbers or if there is only a real
     * or imaginary part.
     *
     * @return String - a string with the real and imaginary parts both
     * displayed in correct form.
     */
    @Override
    public String toString() {

        String result = "";

        BigDecimal bdReal = new BigDecimal(real);
        BigDecimal bdImaginary = new BigDecimal(imaginary);

        bdReal = bdReal.round(new MathContext(4));
        bdImaginary = bdImaginary.round(new MathContext(4));

        real = bdReal.doubleValue();
        imaginary = bdImaginary.doubleValue();

        if (real != 0 && imaginary > 0) {
            result = real + " + " + imaginary + "i";
        } else if (real != 0 && imaginary < 0) {
            result = real + " - " + Math.abs(imaginary) + "i";
        } else if (real == 0 && imaginary != 0) {
            result = imaginary + "i";
        } else if (imaginary == 0 && real != 0) {
            result = Double.toString(real);
        } else if (real == 0 && imaginary == 0) {
            result = "0.0";
        }

        return result;
    }

    /**
     * This method makes a deep copy of a Complex object and returns it.
     *
     * @return Complex - a deep copy of the Complex object.
     */
    @Override
    public Complex deepCopy() {
        return new Complex(real, imaginary);
    }

}
