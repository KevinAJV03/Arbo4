import java.util.Scanner;

public class TresEnRaya {

    static char[] t = {' ',' ',' ',' ',' ',' ',' ',' ',' '}; // tablero 1D
    static final char YO = 'X';
    static final char PC = 'O';

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean turnoYo = true;

        while (!termino()) {
            mostrar();

            if (turnoYo) {
                int pos;
                do {
                    System.out.print("Elige una casilla (1-9): ");
                    pos = sc.nextInt() - 1;
                } while (pos < 0 || pos > 8 || t[pos] != ' ');

                t[pos] = YO;
            } else {
                int mejor = mejorMovimiento(); // IA usando minimax
                t[mejor] = PC;
                System.out.println("La PC jugó en: " + (mejor + 1));
            }

            turnoYo = !turnoYo;
        }

        mostrar();
        char g = ganador();
        if (g == YO) System.out.println("Ganaste.");
        else if (g == PC) System.out.println("Ganó la PC.");
        else System.out.println("Empate.");
    }

    // busca la mejor jugada probando cada casilla libre
    static int mejorMovimiento() {
        int mejorPos = -1;
        int mejorValor = -999;

        for (int i = 0; i < 9; i++) {
            if (t[i] == ' ') {
                t[i] = PC; // pruebo jugar aquí
                int valor = minimax(false); // ahora le tocaría a la persona
                t[i] = ' '; // deshago

                if (valor > mejorValor) {
                    mejorValor = valor;
                    mejorPos = i;
                }
            }
        }
        return mejorPos;
    }

    // minimax simple:
    // si gana PC => 1
    // si gana persona => -1
    // empate => 0
    static int minimax(boolean turnoPC) {
        if (termino()) {
            char g = ganador();
            if (g == PC) return 1;
            if (g == YO) return -1;
            return 0;
        }

        if (turnoPC) {
            int mejor = -999;
            for (int i = 0; i < 9; i++) {
                if (t[i] == ' ') {
                    t[i] = PC;
                    mejor = Math.max(mejor, minimax(false));
                    t[i] = ' ';
                }
            }
            return mejor;
        } else {
            int mejor = 999;
            for (int i = 0; i < 9; i++) {
                if (t[i] == ' ') {
                    t[i] = YO;
                    mejor = Math.min(mejor, minimax(true));
                    t[i] = ' ';
                }
            }
            return mejor;
        }
    }

    // revisa ganador
    static char ganador() {
        int[][] l = {
                {0,1,2},{3,4,5},{6,7,8},
                {0,3,6},{1,4,7},{2,5,8},
                {0,4,8},{2,4,6}
        };

        for (int i = 0; i < l.length; i++) {
            int a = l[i][0], b = l[i][1], c = l[i][2];
            if (t[a] != ' ' && t[a] == t[b] && t[b] == t[c]) return t[a];
        }
        return ' '; // nadie
    }

    // termina si hay ganador o ya no hay espacios
    static boolean termino() {
        if (ganador() != ' ') return true;
        for (int i = 0; i < 9; i++) {
            if (t[i] == ' ') return false;
        }
        return true;
    }

    static void mostrar() {
        System.out.println();
        for (int i = 0; i < 9; i++) {
            char c = (t[i] == ' ') ? (char)('1' + i) : t[i];
            System.out.print(" " + c + " ");
            if (i % 3 != 2) System.out.print("|");
            if (i % 3 == 2 && i != 8) System.out.println("\n-----------");
        }
        System.out.println("\n");
    }
}

