public class ComplexMatrix {
    private final int rows;
    private final int cols;
    private final ComplexNumber[][] matrix;

    public ComplexMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.matrix = new ComplexNumber[rows][cols];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public ComplexNumber get(int r, int c) {
        return matrix[r][c];
    }

    public void set(int r, int c, ComplexNumber val) {
        matrix[r][c] = val;
    }

    public ComplexMatrix add(ComplexMatrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Размеры матриц должны совпадать для сложения!");
        }
        ComplexMatrix result = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, this.get(i, j).add(other.get(i, j)));
            }
        }
        return result;
    }

    public ComplexMatrix multiply(ComplexMatrix other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("Количество столбцов первой матрицы должно быть равно количеству строк второй!");
        }
        ComplexMatrix result = new ComplexMatrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                ComplexNumber sum = new ComplexNumber(0, 0);
                for (int k = 0; k < this.cols; k++) {
                    sum = sum.add(this.get(i, k).multiply(other.get(k, j)));
                }
                result.set(i, j, sum);
            }
        }
        return result;
    }

    public ComplexMatrix transpose() {
        ComplexMatrix result = new ComplexMatrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(j, i, this.get(i, j));
            }
        }
        return result;
    }

    public ComplexNumber determinant() {
        if (rows != cols) {
            throw new IllegalArgumentException("Определитель можно вычислить только для квадратной матрицы!");
        }
        return calculateDeterminant(this.matrix, rows);
    }

    private ComplexNumber calculateDeterminant(ComplexNumber[][] mat, int n) {
        if (n == 1) {
            return mat[0][0];
        }
        if (n == 2) {
            ComplexNumber a = mat[0][0].multiply(mat[1][1]);
            ComplexNumber b = mat[0][1].multiply(mat[1][0]);
            return a.subtract(b);
        }

        ComplexNumber det = new ComplexNumber(0, 0);
        for (int p = 0; p < n; p++) {
            ComplexNumber[][] subMatrix = new ComplexNumber[n - 1][n - 1];
            for (int i = 1; i < n; i++) {
                int colIndex = 0;
                for (int j = 0; j < n; j++) {
                    if (j == p) continue;
                    subMatrix[i - 1][colIndex] = mat[i][j];
                    colIndex++;
                }
            }
            ComplexNumber term = mat[0][p].multiply(calculateDeterminant(subMatrix, n - 1));
            if (p % 2 == 1) {
                det = det.subtract(term);
            } else {
                det = det.add(term);
            }
        }
        return det;
    }

    public ComplexMatrix inverse() {
        ComplexNumber det = determinant();
        if (Math.abs(det.getReal()) < 1e-9 && Math.abs(det.getImag()) < 1e-9) {
            throw new ArithmeticException("Матрица вырождена (определитель равен 0), обратной матрицы не существует!");
        }

        ComplexMatrix adj = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ComplexNumber[][] subMatrix = new ComplexNumber[rows - 1][cols - 1];
                int rIdx = 0;
                for (int r = 0; r < rows; r++) {
                    if (r == i) continue;
                    int cIdx = 0;
                    for (int c = 0; c < cols; c++) {
                        if (c == j) continue;
                        subMatrix[rIdx][cIdx] = matrix[r][c];
                        cIdx++;
                    }
                    rIdx++;
                }
                ComplexNumber minorDet = calculateDeterminant(subMatrix, rows - 1);
                if ((i + j) % 2 == 1) {
                    minorDet = new ComplexNumber(-minorDet.getReal(), -minorDet.getImag());
                }
                adj.set(j, i, minorDet); // Транспонируем сразу при записи
            }
        }

        ComplexMatrix result = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, adj.get(i, j).divide(det));
            }
        }
        return result;
    }

    public ComplexMatrix divide(ComplexMatrix other) {
        ComplexMatrix invOther = other.inverse();
        return this.multiply(invOther);
    }

    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("[" + matrix[i][j] + "]\t");
            }
            System.out.println();
        }
    }
}