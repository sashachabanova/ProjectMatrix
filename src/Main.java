import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Калькулятор Комплексных Матриц ===");
        while (true) {
            printMenu();
            int choice = readInt("Выберите пункт меню: ");
            if (choice == 0) {
                System.out.println("Завершение работы программы.");
                break;
            }
            try {
                handleChoice(choice);
            } catch (Exception e) {
                System.out.println("\n Ошибка: " + e.getMessage());
            }
            System.out.println("\n-----------------------------------\n");
        }
    }

    private static void printMenu() {
        System.out.println("1. Сложение матриц");
        System.out.println("2. Умножение матриц");
        System.out.println("3. Деление матриц (A * B^-1)");
        System.out.println("4. Транспонирование матрицы");
        System.out.println("5. Вычисление определителя");
        System.out.println("0. Выход");
    }

    private static void handleChoice(int choice) {
        switch (choice) {
            case 1 -> {
                System.out.println("\n--- Ввод первой матрицы ---");
                ComplexMatrix a = readMatrix();
                System.out.println("\n--- Ввод второй матрицы ---");
                ComplexMatrix b = readMatrix();
                System.out.println("\nРезультат сложения:");
                a.add(b).print();
            }
            case 2 -> {
                System.out.println("\n--- Ввод первой матрицы ---");
                ComplexMatrix a = readMatrix();
                System.out.println("\n--- Ввод второй матрицы ---");
                ComplexMatrix b = readMatrix();
                System.out.println("\nРезультат умножения:");
                a.multiply(b).print();
            }
            case 3 -> {
                System.out.println("\n--- Ввод первой матрицы (A) ---");
                ComplexMatrix a = readMatrix();
                System.out.println("\n--- Ввод второй матрицы (B) ---");
                ComplexMatrix b = readMatrix();
                System.out.println("\nРезультат деления (A / B):");
                a.divide(b).print();
            }
            case 4 -> {
                System.out.println("\n--- Ввод матрицы ---");
                ComplexMatrix m = readMatrix();
                System.out.println("\nТранспонированная матрица:");
                m.transpose().print();
            }
            case 5 -> {
                System.out.println("\n--- Ввод квадратной матрицы ---");
                ComplexMatrix m = readMatrix();
                System.out.println("\nОпределитель матрицы = " + m.determinant());
            }
            default -> System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }

    private static ComplexMatrix readMatrix() {
        int rows = readInt("Введите количество строк: ");
        int cols = readInt("Введите количество столбцов: ");
        ComplexMatrix matrix = new ComplexMatrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf("Элемент [%d][%d]:%n", i + 1, j + 1);
                double real = readDouble("  Вещественная часть: ");
                double imag = readDouble("  Мнимая часть: ");
                matrix.set(i, j, new ComplexNumber(real, imag));
            }
        }
        return matrix;
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            }
            System.out.println("Ошибка ввода! Введите целое число.");
            scanner.next();
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                return scanner.nextDouble();
            }
            System.out.println("Ошибка ввода! Введите число.");
            scanner.next();
        }
    }
}