import java.util.Random;
import java.util.Scanner;

public class MatrixOperations {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MatrixUtils utils = new MatrixUtils(); // Створення екземпляра класу з методами

        // Номер залікової книжки
        int studentNumber = 13;

        // Обчислення C₅, C₇, C₁₁
        int C5 = studentNumber % 5;
        int C7 = studentNumber % 7;
        int C11 = studentNumber % 11;

        System.out.println("C₅ = " + C5);
        System.out.println("C₇ = " + C7);
        System.out.println("C₁₁ = " + C11);

        while (true) {
            try {
                // Введення розмірності матриць
                int rows = utils.getMatrixDimension(scanner, "рядків");
                int cols = utils.getMatrixDimension(scanner, "стовпців");

                // Вибір способу заповнення матриць
                System.out.println("Оберіть спосіб заповнення матриць:");
                System.out.println("1 - Автоматичне заповнення");
                System.out.println("2 - Ручне заповнення");
                int fillChoice = scanner.nextInt();

                float[][] A, B;

                if (fillChoice == 1) {
                    System.out.println("Автоматичне заповнення матриць цілими числами...");
                    A = utils.generateIntMatrix(rows, cols);
                    B = utils.generateIntMatrix(rows, cols);
                } else if (fillChoice == 2) {
                    System.out.println("Ручне заповнення матриці A (лише цілі числа):");
                    A = utils.manualFillMatrix(scanner, rows, cols);
                    System.out.println("Ручне заповнення матриці B (лише цілі числа):");
                    B = utils.manualFillMatrix(scanner, rows, cols);
                } else {
                    System.out.println("Невірний вибір. Завершення програми.");
                    return;
                }

                System.out.println("Матриця A:");
                utils.printMatrix(A);

                System.out.println("Матриця B:");
                utils.printMatrix(B);

                // Виконання побітового виключного ІЛИ
                System.out.println("Виконання операції побітового виключного АБО (C = A ⊕ B):");
                float[][] C = utils.bitwiseXOR(A, B);

                System.out.println("Результуюча матриця C:");
                utils.printMatrix(C);


                // Виконання дії залежно від C₁₁
                System.out.println("Дія: Обчислення суми найбільших елементів кожного стовпця матриці C.");
                float sumMax = utils.sumMaxInColumns(C);
                System.out.println("Сума найбільших елементів: " + sumMax);


                // Запит на повторення
                System.out.println("Бажаєте повторити виконання програми? (1 - так, 0 - ні)");
                int repeat = scanner.nextInt();
                if (repeat == 0) {
                    System.out.println("Завершення програми. Дякуємо!");
                    break;
                } else if (repeat != 1) {
                    System.out.println("Невірні дані. Завершення програми!");
                    break;
                }

            } catch (Exception e) {
                System.out.println("Сталася помилка: " + e.getMessage());
                scanner.nextLine(); // Очищення буфера
                break;
            }
        }
    }
}

class MatrixUtils {

    // Метод для отримання розмірності матриці
    public int getMatrixDimension(Scanner scanner, String dimensionName) {
        int dimension = 0;
        while (dimension <= 0) {
            try {
                System.out.printf("Введіть кількість %s матриці: ", dimensionName);
                dimension = scanner.nextInt();
                if (dimension <= 0) {
                    throw new IllegalArgumentException("Розмірність повинна бути більше 0.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка: " + e.getMessage());
                scanner.nextLine(); // Очищення буфера
            }
        }
        return dimension;
    }

    // Метод для автоматичного заповнення матриці цілими числами
    public float[][] generateIntMatrix(int rows, int cols) {
        Random random = new Random();
        float[][] matrix = new float[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(100); // Цілі числа від 0 до 99
            }
        }
        return matrix;
    }

    // Метод для ручного заповнення матриці
    public float[][] manualFillMatrix(Scanner scanner, int rows, int cols) {
        float[][] matrix = new float[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boolean valid = false;
                while (!valid) {
                    try {
                        System.out.printf("Введіть елемент [%d][%d] (лише ціле число): ", i, j);
                        float value = scanner.nextFloat();
                        if (value != (int) value) {
                            throw new IllegalArgumentException("Число повинно бути цілим.");
                        }
                        matrix[i][j] = value;
                        valid = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Помилка: " + e.getMessage());
                        scanner.nextLine(); // Очищення буфера
                    }
                }
            }
        }
        return matrix;
    }

    // Метод для побітового виключного АБО
    public float[][] bitwiseXOR(float[][] A, float[][] B) {
        int rows = A.length;
        int cols = A[0].length;
        float[][] result = new float[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = (float)((int) A[i][j] ^ (int) B[i][j]);
            }
        }
        return result;
    }

    // Метод для обчислення суми найбільших елементів кожного стовпця
    public float sumMaxInColumns(float[][] matrix) {
        int cols = matrix[0].length;
        int rows = matrix.length;
        float sum = 0;

        for (int j = 0; j < cols; j++) {
            float max = Float.MIN_VALUE;
            for (int i = 0; i < rows; i++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                }
            }
            sum += max;
        }
        return sum;
    }

    // Метод для друку матриці
    public void printMatrix(float[][] matrix) {
        for (float[] row : matrix) {
            for (float value : row) {
                System.out.printf("%8.2f", value);
            }
            System.out.println();
        }
    }
}
