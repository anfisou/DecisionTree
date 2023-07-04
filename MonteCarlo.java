import java.util.Scanner;

public class MonteCarlo {
    public static void run(Tree tree, Atributo[] atributos, int[][] bd) {
        Scanner in = new Scanner(System.in);
        Board b = new Board();
        b.player = 2;
        int jogada = 0;
        while (b.gameOver() == 0) {
            b.print();
            if (b.player == 1) {
                boolean valid = false;
                while (!valid) {
                    jogada = in.nextInt() - 1;
                    if (b.canInsert(jogada)) {
                        valid = true;
                    } else {
                        System.out.println("A jogada nao e valida");
                    }
                }
                b = b.insert(jogada);
                b.changePlayer();
            } else {
                BoardMCTS bo = new BoardMCTS(b.copy(), false, null);
                b = bo.montecarlo(tree, atributos, bd).copy();
            }
        }
        
        b.print();
        if (b.gameOver() == 1) {
            System.out.println("Ganhou jogador 1");
        }
        if (b.gameOver() == 2) {
            System.out.println("Ganhou jogador 2");
        }
        if (b.gameOver() == 3) {
            System.out.println("Empate");
        }
        in.close();
    }
}
