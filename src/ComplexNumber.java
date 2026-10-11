public class ComplexNumber {
    private final double real;
    private final double imag;

    public ComplexNumber(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    public double getReal() {
        return real;
    }

    public double getImag() {
        return imag;
    }

    public ComplexNumber add(ComplexNumber other) {
        return new ComplexNumber(this.real + other.real, this.imag + other.imag);
    }

    public ComplexNumber subtract(ComplexNumber other) {
        return new ComplexNumber(this.real - other.real, this.imag - other.imag);
    }

    public ComplexNumber multiply(ComplexNumber other) {
        double r = this.real * other.real - this.imag * other.imag;
        double i = this.real * other.imag + this.imag * other.real;
        return new ComplexNumber(r, i);
    }

    public ComplexNumber divide(ComplexNumber other) {
        double denominator = other.real * other.real + other.imag * other.imag;
        if (Math.abs(denominator) < 1e-9) {
            throw new ArithmeticException("Деление на ноль (нулевое комплексное число)!");
        }
        double r = (this.real * other.real + this.imag * other.imag) / denominator;
        double i = (this.imag * other.real - this.real * other.imag) / denominator;
        return new ComplexNumber(r, i);
    }

    @Override
    public String toString() {
        if (Math.abs(imag) < 1e-9) {
            return String.format("%.2f", real);
        }
        if (Math.abs(real) < 1e-9) {
            return String.format("%.2fi", imag);
        }
        if (imag < 0) {
            return String.format("%.2f - %.2fi", real, Math.abs(imag));
        }
        return String.format("%.2f + %.2fi", real, imag);
    }
}