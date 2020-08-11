import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

public class SudokuBoard {
    int[][] board = new int[9][9];

    public SudokuBoard(String filePath) {
        File inputFile = new File(filePath);

        try (Scanner scanner = new Scanner(inputFile)) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    board[i][j] = scanner.nextInt();
                }
            }
        } catch (Exception e) {

        }
    }

    public SudokuBoard(SudokuBoard otherBoard) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = otherBoard.board[i][j];
            }
        }
    }

     public void printBoard() {
        System.out.println("-------------------------------------");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != 0) {
                    System.out.print("| " + board[i][j] + " ");
                } else {
                    System.out.print("|  "  + " ");
                }
            }
            System.out.println("|");
        }
        System.out.println("-------------------------------------");

     }

     public boolean isSolved() {
        boolean[] check = new boolean[9];

        // Check rows
        for (int i = 0; i < 9; i ++) {
            for (int k = 0; k < 9; k ++) check[k] = false;
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) return false;
                check[board[i][j]-1] = true;
            }
            for (int k = 0; k < 9; k ++) {
                if (!check[k]) return false;
            }
        }

        //Check Columns
         for (int i = 0; i < 9; i ++) {
             for (int k = 0; k < 9; k ++) check[k] = false;
             for (int j = 0; j < 9; j ++) {
                 if (board[j][i] == 0) return false;
                 check[board[j][i]-1] = true;
             }
             for (int k = 0; k < 9; k ++) {
                 if (!check[k]) return false;
             }
         }

         //Check Sub Boards
         for (int i = 0; i < 9; i += 3) {
             for (int k = 0; k < 9; k ++) check[k] = false;
             for (int j = 0; j < 9; j += 3) {
                 if (board[i][j] == 0 || board[i][j+1] == 0 || board[i][j+2] == 0) return false;
                 if (board[i+1][j] == 0 || board[i+1][j+1] == 0 || board[i+1][j+2] == 0) return false;
                 if (board[i+2][j] == 0 || board[i+2][j+1] == 0 || board[i+2][j+2] == 0) return false;
                 check[board[i][j]-1] = true;
                 check[board[i][j+1]-1] = true;
                 check[board[i][j+2]-1] = true;
                 check[board[i+1][j]-1] = true;
                 check[board[i+1][j+1]-1] = true;
                 check[board[i+1][j+2]-1] = true;
                 check[board[i+2][j]-1] = true;
                 check[board[i+2][j+1]-1] = true;
                 check[board[i+2][j+2]-1] = true;
             }
             for (int k = 0; k < 9; k ++) {
                 if (!check[k]) return false;
             }
         }

        return true;
     }

     public BoardField findBestEmptyField() {
         LinkedList<BoardField> fieldList = new LinkedList<>();
         for (int i = 0; i < 9; i++) {
             for (int j = 0; j < 9; j++) {
                 if (board[i][j] == 0) fieldList.add(new BoardField(j, i));
             }
         }
         for (BoardField f : fieldList) {
             boolean[] isCandidate = new boolean[9];
             for (int i = 0; i < 9; i++) isCandidate[i] = true;
             // Check rows for candidates
             for (int i = 0; i < 9; i++) {
                 if (board[f.y][i] != 0) isCandidate[board[f.y][i] - 1] = false;
             }
             // Check columns for candidates
             for (int i = 0; i < 9; i++) {
                 if (board[i][f.x] != 0) isCandidate[board[i][f.x] - 1] = false;
             }
             // Check sub Board for candidates
             int x = f.x;
             int y = f.y;
             if (f.y % 3 == 0) {
                 if (f.x % 3 == 0) {
                     if (board[y + 1][x + 1] != 0) isCandidate[board[y + 1][x + 1] - 1] = false;
                     if (board[y + 1][x + 2] != 0) isCandidate[board[y + 1][x + 2] - 1] = false;
                     if (board[y + 2][x + 1] != 0) isCandidate[board[y + 2][x + 1] - 1] = false;
                     if (board[y + 2][x + 2] != 0) isCandidate[board[y + 2][x + 2] - 1] = false;
                 } else if (f.x % 3 == 1) {
                     if (board[y + 1][x - 1] != 0) isCandidate[board[y + 1][x - 1] - 1] = false;
                     if (board[y + 1][x + 1] != 0) isCandidate[board[y + 1][x + 1] - 1] = false;
                     if (board[y + 2][x - 1] != 0) isCandidate[board[y + 2][x - 1] - 1] = false;
                     if (board[y + 2][x + 1] != 0) isCandidate[board[y + 2][x + 1] - 1] = false;
                 } else {
                     if (board[y + 1][x - 2] != 0) isCandidate[board[y + 1][x - 2] - 1] = false;
                     if (board[y + 1][x - 1] != 0) isCandidate[board[y + 1][x - 1] - 1] = false;
                     if (board[y + 2][x - 2] != 0) isCandidate[board[y + 2][x - 2] - 1] = false;
                     if (board[y + 2][x - 1] != 0) isCandidate[board[y + 2][x - 1] - 1] = false;
                 }
             } else if (f.y % 3 == 1) {
                 if (f.x % 3 == 0) {
                     if (board[y - 1][x + 1] != 0) isCandidate[board[y - 1][x + 1] - 1] = false;
                     if (board[y + 1][x + 1] != 0) isCandidate[board[y + 1][x + 1] - 1] = false;
                     if (board[y - 1][x + 2] != 0) isCandidate[board[y - 1][x + 2] - 1] = false;
                     if (board[y + 1][x + 2] != 0) isCandidate[board[y + 1][x + 2] - 1] = false;
                 } else if (f.x % 3 == 1) {
                     if (board[y - 1][x - 1] != 0) isCandidate[board[y - 1][x - 1] - 1] = false;
                     if (board[y + 1][x - 1] != 0) isCandidate[board[y + 1][x - 1] - 1] = false;
                     if (board[y - 1][x + 1] != 0) isCandidate[board[y - 1][x + 1] - 1] = false;
                     if (board[y + 1][x + 1] != 0) isCandidate[board[y + 1][x + 1] - 1] = false;
                 } else {
                     if (board[y - 1][x - 2] != 0) isCandidate[board[y - 1][x - 2] - 1] = false;
                     if (board[y - 1][x - 1] != 0) isCandidate[board[y - 1][x - 1] - 1] = false;
                     if (board[y + 1][x - 2] != 0) isCandidate[board[y + 1][x - 2] - 1] = false;
                     if (board[y + 1][x - 1] != 0) isCandidate[board[y + 1][x - 1] - 1] = false;
                 }
             } else {
                 if (f.x % 3 == 0) {
                     if (board[y - 1][x + 1] != 0) isCandidate[board[y - 1][x + 1] - 1] = false;
                     if (board[y - 1][x + 2] != 0) isCandidate[board[y - 1][x + 2] - 1] = false;
                     if (board[y - 2][x + 1] != 0) isCandidate[board[y - 2][x + 1] - 1] = false;
                     if (board[y - 2][x + 2] != 0) isCandidate[board[y - 2][x + 2] - 1] = false;
                 } else if (f.x % 3 == 1) {
                     if (board[y - 1][x - 1] != 0) isCandidate[board[y - 1][x - 1] - 1] = false;
                     if (board[y - 2][x - 1] != 0) isCandidate[board[y - 2][x - 1] - 1] = false;
                     if (board[y - 1][x + 1] != 0) isCandidate[board[y - 1][x + 1] - 1] = false;
                     if (board[y - 2][x + 1] != 0) isCandidate[board[y - 2][x + 1] - 1] = false;
                 } else {
                     if (board[y - 1][x - 2] != 0) isCandidate[board[y - 1][x - 2] - 1] = false;
                     if (board[y - 1][x - 1] != 0) isCandidate[board[y - 1][x - 1] - 1] = false;
                     if (board[y - 2][x - 2] != 0) isCandidate[board[y - 2][x - 2] - 1] = false;
                     if (board[y - 2][x - 1] != 0) isCandidate[board[y - 2][x - 1] - 1] = false;
                 }
             }

             int numberOfCandidates = 0;
             for (int i = 0; i < 9; i++) {
                 if(isCandidate[i]) numberOfCandidates++;
             }
             f.candidates = new int[numberOfCandidates];
             int counter = 0;
             for (int i = 0; i < 9; i++) {
                    if (isCandidate[i]) {
                        f.candidates[counter] = i + 1;
                        counter++;
                    }
             }
         }

         if (fieldList.isEmpty()) return null;
         BoardField bestField = fieldList.get(0);
         for (BoardField f : fieldList) {
             if (f.candidates.length < bestField.candidates.length) bestField = f;
         }
         return bestField;
        }
    }
