public class SudokuSolver {

    public SudokuBoard solve(SudokuBoard sudokuBoard) {
        if (sudokuBoard.isSolved()) return sudokuBoard;
        BoardField bestField = sudokuBoard.findBestEmptyField();
        if (bestField != null) {
            for (int i = 0; i < bestField.candidates.length; i++) {
                SudokuBoard newSudokuBoard = new SudokuBoard(sudokuBoard);
                newSudokuBoard.board[bestField.y][bestField.x] = bestField.candidates[i];
                SudokuBoard checkBoard = solve(newSudokuBoard);
                if (checkBoard.isSolved()) return checkBoard;
            }
        }
        return sudokuBoard;
    }

    public static void main(String[] args) {
        SudokuBoard sudokuBoard = new SudokuBoard("/home/viet/viet.Programmierung/Java/SudokuSolver/src/input");
        System.out.println("UNSOLVED BOARD: ");
        sudokuBoard.printBoard();
        SudokuSolver solver = new SudokuSolver();
        SudokuBoard solvedBoard = solver.solve(sudokuBoard);
        System.out.println();
        System.out.println("SOLUTION: ");
        solvedBoard.printBoard();
    }
}
