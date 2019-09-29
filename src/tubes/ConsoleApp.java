package tubes;

import static tubes.Console.*;

/**
 * Kelas ConsoleApp merupakan kelas utama untuk menjalankan program.
 */
public class ConsoleApp {
    /**
     * Prosedur main yang dijalankan program pertama kali.<br>
     * Prosedur ini menjalankan program dengan memulai dari state awal hygiene=0, energy=10, dan fun=0.<br>
     * Kemudian menerima input aksi dari pengguna sampai mencapai final state, kemudian keluar dari program.
     */
    public static void main(String[] args) {
        useColor();
        State state = new State();
        while (!state.isFinalState()) {
            outln(state.printString());
            outln();
            out("Masukkan aksi: " + normal);
            try {
                state.act(line());
            } catch (Exception e) {
                outln(red + "Error: " + e.getMessage());
            }
            outln();
        }
        outln(state.isDead() ? red + "ded" : green + "win");
    }
}
