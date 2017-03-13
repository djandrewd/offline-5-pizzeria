package ua.goit.offline5.dao.annotations;

public class TestClassA {
    @Precision(param = 5, trailingZeros = 3)
    private double a;

    public static void main(String[] args) throws Exception {
        TestClassA a1 = new TestClassA();
        a1.a = 10;
        System.out.println(Printer.print(a1));
    }
}
