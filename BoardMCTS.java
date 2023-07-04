
//import java.util.LinkedList;
import java.util.PriorityQueue;

//7 lado; 6 altura

public class BoardMCTS implements Comparable<BoardMCTS> {
    Board board;
    boolean max;
    int v;
    int n;
    BoardMCTS parent;
    PriorityQueue<BoardMCTS> childs;

    BoardMCTS(Board b, boolean m, BoardMCTS p) {
        board = b;
        max = m;
        v = 0;
        n = 0;
        parent = p;
        childs = new PriorityQueue<>();
    }

    public double ucb() {
        return v / (double) n + Math.sqrt(2) * Math.sqrt(Math.log(parent.n) / (double) n);
    }

    public int compareTo(BoardMCTS b) {
        return Double.compare(b.ucb(), ucb());
    }

    public BoardMCTS sellect() {
        if (childs.isEmpty()) {
            return this;
        }
        return childs.peek().sellect();
    }

    public int expand() {
        int count = 0;
        for (int j = 0; j < 7; j++) {
            if (board.canInsert(j)) {
                Board bo = board.insert(j);
                bo.changePlayer();
                BoardMCTS b = new BoardMCTS(bo, !max, this);
                childs.add(b);
                count++;
            }
        }
        return count;
    }

    public int rollout(Tree tree, Atributo[] atributos, int[][] bd) {
        Board bo = board.copy();
        if (max) {
            int c = bo.randommove();
            bo = bo.insert(c);
            bo.changePlayer();
        }
        String[] test = board.prepare_for_tree();
        int[] new_line = new int[atributos.length - 1];
        for (int i = 0; i < atributos.length - 1; i++) {
            if (!atributos[i].valores.contains(test[i]))
                atributos[i].valores.add(test[i]);
            new_line[i] = atributos[i].valores.indexOf(test[i]);
        }
        String result = tree.predict(atributos, new_line, bd);
        if (result.equals("loss")) {
            return 1;
        }
        if (result.equals("win")) {
            return 2;
        }
        return 3;
    }

    public void backpropagation(int winner) {
        n++;
        if (max && winner == 2) {
            v++;
        }
        if (!max && winner == 1) {
            v++;
        }
        if (parent == null) {
            return;
        }
        parent.backpropagation(winner);
    }

    public void reset() {
        PriorityQueue<BoardMCTS> filhos = new PriorityQueue<>();
        while (!childs.isEmpty()) {
            filhos.add(childs.poll());
        }
        childs = filhos;
        if (parent == null) {
            return;
        }
        parent.reset();
    }

    public Board montecarlo(Tree tree, Atributo[] atributos, int[][] bd) {
        int count = 0;
        long start = System.currentTimeMillis();
        for (int k = 0; k < 50000; k++) {
            BoardMCTS b = sellect();
            if (b.board.gameOver() != 0) {
                b.backpropagation(b.board.gameOver());
            } else {
                count += b.expand();
                PriorityQueue<BoardMCTS> filhos = new PriorityQueue<>();
                while (!b.childs.isEmpty()) {
                    BoardMCTS bo = b.childs.poll();
                    int a = bo.rollout(tree, atributos, bd);
                    bo.backpropagation(a);
                    filhos.add(bo);
                }
                b.childs = filhos;
            }
            b.reset();
        }
        Board b = childs.peek().board;
        int max = 0;
        while (!childs.isEmpty()) {
            BoardMCTS filho = childs.poll();
            if (filho.n > max) {
                max = filho.n;
                b = filho.board;
            }
        }
        long finish = System.currentTimeMillis();
        System.out.println("count = " + count);
        System.out.println("time = " + (finish - start) / 1000.0);
        return b;
    }

}
