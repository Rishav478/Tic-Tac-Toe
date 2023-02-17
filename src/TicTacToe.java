import java.util.Scanner;

enum Cell {
    X, O, EMPTY
}

interface Player {
    Cell mark();
    int move();
}

class HumanPlayer implements Player {
    private final Cell mark;
    private final Scanner sc = new Scanner(System.in);

    public HumanPlayer(Cell mark) {
        this.mark = mark;
    }

    @Override
    public Cell mark() {
        return mark;
    }

    @Override
    public int move() {
        System.out.print("Enter your move (1-9): ");
        int move = sc.nextInt();
        if (move < 1 || move > 9) {
            throw new IllegalArgumentException("Invalid move. Try again.");
        }
        return move - 1;
    }
}

abstract class AIPlayer implements Player {
    protected final Cell mark;

    public AIPlayer(Cell mark) {
        this.mark = mark;
    }
    @Override
    public Cell mark() {
        return mark;
    }
}

class TicTacToe {
    private final Cell[] board = new Cell[9];
    private Player currentPlayer;
    private Player p1;
    private Player p2;

    public TicTacToe(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        for (int i = 0; i < 9; i++) {
            board[i] = Cell.EMPTY;
        }
        currentPlayer = p1;
    }

    public void play() {
        while (true) {
            int move = currentPlayer.move();
            board[move] = currentPlayer.mark();
            displayBoard();
            if (isWinningMove(move)) {
                System.out.println(currentPlayer.mark() + " wins!");
                break;
            } else if (isDraw()) {
                System.out.println("It's a draw.");
                break;
            } else {
                currentPlayer = (currentPlayer == p1) ? p2 : p1;
            }
        }
    }
    private void displayBoard() {
        System.out.println();
        for (int i = 0; i < 9; i++) {
            System.out.print(board[i] + " ");
            if (i % 3 == 2) {
                System.out.println();
            }
        }
        System.out.println();
    }

    private boolean isWinningMove(int move) {
        int row = move / 3;
        int col = move % 3;

        // check row
        if (board[row * 3] == board[row * 3 + 1] && board[row * 3 + 1] == board[row * 3 + 2]) {
            return true;
        }

        // check column
        if (board[col] == board[col + 3] && board[col + 3] == board[col + 6]) {
            return true;
        }

        // check diagonal
        if (move % 2 == 0) {
            if ((move == 0 && board[0] == board[4] && board[4] == board[8]) ||
                    (move == 2 && board[2] == board[4] && board[4] == board[6]))            {
                return true;
            }
        }
        return false;
    }

    private boolean isDraw() {
        for (int i = 0; i < 9; i++) {
            if (board[i] == Cell.EMPTY) {
                return false;
            }
        }
        return true;
    }
}
class TicTacToeGame {
    public static void main(String[] args) {
        Player p1 = new HumanPlayer(Cell.X);
        Player p2 = new HumanPlayer(Cell.O);
        TicTacToe game = new TicTacToe(p1, p2);
        game.play();
    }
}
