import java.util.Scanner;
import java.util.LinkedList;

//7 lado; 6 altura

public class Board {
    int player;
    int[][] board;

    Board() {
        player = 1;
        board = new int[6][7];
    }

    public void read(Scanner in) {
        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = in.nextInt();
            }
        }
    }

    public void print() {
        System.out.println("-------------------------------");
        System.out.println("Player : " + player);
        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                System.out.print("| ");
                if (board[i][j] == 0) {
                    System.out.print("-");
                }
                if (board[i][j] == 1) {
                    System.out.print("X");
                }
                if (board[i][j] == 2) {
                    System.out.print("O");
                }
                System.out.print(" ");
            }
            System.out.println("|");
        }
        System.out.println("  1   2   3   4   5   6   7");
        System.out.println("-------------------------------");
    }

    public Board copy() {
        Board b = new Board();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                b.board[i][j] = board[i][j];
            }
        }
        b.player = player;
        return b;
    }

    public String[] prepare_for_tree() {
        String[] res = new String[42];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[j][i] == 0) {
                    res[i * 6 + j] = "b";
                } else if (board[j][i] == 1) {
                    res[i * 6 + j] = "o";
                } else if (board[j][i] == 2) {
                    res[i * 6 + j] = "x";
                }
            }
        }
        return res;
    }

    public int column() {
        for (int j = 0; j < 7; j++) {
            for (int i = 0; i < 3; i++) {
                if (board[i][j] != 0 && board[i][j] == board[i + 1][j] && board[i][j] == board[i + 2][j]
                        && board[i][j] == board[i + 3][j]) {
                    return board[i][j];
                }
            }
        }
        return 0;
    }

    public int line() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] != 0 && board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2]
                        && board[i][j] == board[i][j + 3]) {
                    return board[i][j];
                }
            }
        }
        return 0;
    }

    public int diagonal1() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] != 0 && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2]
                        && board[i][j] == board[i + 3][j + 3]) {
                    return board[i][j];
                }
            }
        }
        return 0;
    }

    public int diagonal2() {
        for (int i = 0; i < 3; i++) {
            for (int j = 3; j < 7; j++) {
                if (board[i][j] != 0 && board[i][j] == board[i + 1][j - 1] && board[i][j] == board[i + 2][j - 2]
                        && board[i][j] == board[i + 3][j - 3]) {
                    return board[i][j];
                }
            }
        }
        return 0;
    }

    public int heuristic() {
        int count = 0;
        int p1 = 0;
        int p2 = 0;
        if (gameOver() == 1) {
            return -512;
        }
        if (gameOver() == 2) {
            return 512;
        }
        if (gameOver() == 3) {
            return 0;
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                p1 = 0;
                p2 = 0;
                for (int k = 0; k < 4; k++) {
                    if (board[i][j + k] == 1) {
                        p1++;
                    } else if (board[i][j + k] == 2) {
                        p2++;
                    }
                }
                if (p1 == 3 && p2 == 0) {
                    count -= 50;
                } else if (p1 == 2 && p2 == 0) {
                    count -= 10;
                } else if (p1 == 1 && p2 == 0) {
                    count -= 1;
                } else if (p1 == 0 && p2 == 1) {
                    count += 1;
                } else if (p1 == 0 && p2 == 2) {
                    count += 10;
                } else if (p1 == 0 && p2 == 3) {
                    count += 50;
                }
            }
        }
        for (int j = 0; j < 7; j++) {
            for (int i = 0; i < 3; i++) {
                p1 = 0;
                p2 = 0;
                for (int k = 0; k < 4; k++) {
                    if (board[i + k][j] == 1) {
                        p1++;
                    } else if (board[i + k][j] == 2) {
                        p2++;
                    }
                }
                if (p1 == 3 && p2 == 0) {
                    count -= 50;
                } else if (p1 == 2 && p2 == 0) {
                    count -= 10;
                } else if (p1 == 1 && p2 == 0) {
                    count -= 1;
                } else if (p1 == 0 && p2 == 1) {
                    count += 1;
                } else if (p1 == 0 && p2 == 2) {
                    count += 10;
                } else if (p1 == 0 && p2 == 3) {
                    count += 50;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                p1 = 0;
                p2 = 0;
                for (int k = 0; k < 4; k++) {
                    if (board[i + k][j + k] == 1) {
                        p1++;
                    } else if (board[i + k][j + k] == 2) {
                        p2++;
                    }
                }
                if (p1 == 3 && p2 == 0) {
                    count -= 50;
                } else if (p1 == 2 && p2 == 0) {
                    count -= 10;
                } else if (p1 == 1 && p2 == 0) {
                    count -= 1;
                } else if (p1 == 0 && p2 == 1) {
                    count += 1;
                } else if (p1 == 0 && p2 == 2) {
                    count += 10;
                } else if (p1 == 0 && p2 == 3) {
                    count += 50;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 3; j < 7; j++) {
                p1 = 0;
                p2 = 0;
                for (int k = 0; k < 4; k++) {
                    if (board[i + k][j - k] == 1) {
                        p1++;
                    } else if (board[i + k][j - k] == 2) {
                        p2++;
                    }
                }
                if (p1 == 3 && p2 == 0) {
                    count -= 50;
                } else if (p1 == 2 && p2 == 0) {
                    count -= 10;
                } else if (p1 == 1 && p2 == 0) {
                    count -= 1;
                } else if (p1 == 0 && p2 == 1) {
                    count += 1;
                } else if (p1 == 0 && p2 == 2) {
                    count += 10;
                } else if (p1 == 0 && p2 == 3) {
                    count += 50;
                }
            }
        }
        return count;
    }

    public boolean isFull() {
        for (int j = 0; j < 7; j++) {
            if (board[5][j] == 0) {
                return false;
            }
        }
        return true;
    }

    public int gameOver() {
        if (line() != 0) {
            return line();
        }
        if (column() != 0) {
            return column();
        }
        if (diagonal1() != 0) {
            return diagonal1();
        }
        if (diagonal2() != 0) {
            return diagonal2();
        }
        if (isFull()) {
            return 3;
        }
        return 0;
    }

    public boolean canInsert(int coord) {
        if (coord < 0 || coord > 6) {
            return false;
        }
        return board[5][coord] == 0;
    }

    public Board insert(int coord) {
        Board b = new Board();
        b = copy();
        for (int k = 0; k < 6; k++) {
            if (b.board[k][coord] == 0) {
                b.board[k][coord] = player;
                break;
            }
        }
        return b;
    }

    public void changePlayer() {
        player = -player + 3;
    }

    public int[] minimax(int depth, boolean max, int[] count) {
        int res[] = new int[2];
        count[0]++;
        if (depth == 0 || gameOver() != 0) {
            res[0] = -1;
            res[1] = heuristic();
            if (res[1] == 512) {
                res[1] += depth;
            }
            if (res[1] == -512) {
                res[1] -= depth;
            }
            return res;
        }
        int jogada = 0;
        if (max) {
            int maxv = Integer.MIN_VALUE;
            for (int j = 0; j < 7; j++) {
                if (canInsert(j)) {
                    Board b = insert(j);
                    b.changePlayer();
                    int value = b.minimax(depth - 1, false, count)[1];
                    if (value > maxv) {
                        maxv = value;
                        jogada = j;
                    }
                }
            }
            res[0] = jogada;
            res[1] = maxv;
            return res;
        } else {
            int minv = Integer.MAX_VALUE;
            for (int j = 0; j < 7; j++) {
                if (canInsert(j)) {
                    Board b = insert(j);
                    b.changePlayer();
                    int value = b.minimax(depth - 1, true, count)[1];
                    if (value < minv) {
                        minv = value;
                        jogada = j;
                    }
                }
            }
            res[0] = jogada;
            res[1] = minv;
            return res;
        }

    }

    public int[] minimaxalphabeta(int depth, boolean max, int alpha, int beta, int[] count) {
        int res[] = new int[2];
        count[0]++;
        if (depth == 0 || gameOver() != 0) {
            res[0] = -1;
            res[1] = heuristic();
            if (res[1] == 512) {
                res[1] += depth;
            }
            if (res[1] == -512) {
                res[1] -= depth;
            }
            return res;
        }
        int jogada = 0;
        if (max) {
            int maxv = Integer.MIN_VALUE;
            for (int j = 0; j < 7; j++) {
                if (canInsert(j)) {
                    Board b = insert(j);
                    b.changePlayer();
                    int value = b.minimaxalphabeta(depth - 1, false, alpha, beta, count)[1];
                    if (value > maxv) {
                        maxv = value;
                        jogada = j;
                    }
                    if (value >= beta) {
                        break;
                    }
                    alpha = Math.max(alpha, value);
                }
            }
            res[0] = jogada;
            res[1] = maxv;
            return res;
        } else {
            int minv = Integer.MAX_VALUE;
            for (int j = 0; j < 7; j++) {
                if (canInsert(j)) {
                    Board b = insert(j);
                    b.changePlayer();
                    int value = b.minimaxalphabeta(depth - 1, true, alpha, beta, count)[1];
                    if (value < minv) {
                        minv = value;
                        jogada = j;
                    }
                    if (value <= alpha) {
                        break;
                    }
                    beta = Math.min(beta, value);
                }
            }
            res[0] = jogada;
            res[1] = minv;
            return res;
        }

    }

    public LinkedList<Integer> possiblemoves() {
        LinkedList<Integer> lista = new LinkedList<>();
        for (int i = 0; i < 7; i++) {
            if (canInsert(i)) {
                lista.add(i);
            }
        }
        return lista;
    }

    public int randommove() {
        LinkedList<Integer> lista = possiblemoves();
        int a = (int) (Math.random() * lista.size());
        return lista.get(a);
    }
}
