package tubes;

import static tubes.Console.*;

import java.io.*;
import java.util.*;

/**
 * Kelas ConsoleApp merupakan kelas utama untuk menjalankan program.
 */
public class ConsoleApp {
    private ConsoleApp() {}
    /**
     * Prosedur main yang dijalankan program pertama kali.<br>
     * Prosedur ini menjalankan program dengan memulai dari state awal hygiene=0, energy=10, dan fun=0.<br>
     * Kemudian menerima input aksi dari pengguna sampai mencapai final state, kemudian keluar dari program.
     */
    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equalsIgnoreCase("gencsv")) {
            gencsv();
            return;
        }
        useColor();
        State state = new State();
        while (!state.isFinalState()) {
            outln(state.printString());
            outln();
            out("Masukkan aksi: " + normal);
            try {
                state = state.act(line());
            } catch (Exception e) {
                outln(red + "Error: " + e.getMessage());
            }
            outln();
        }
        outln(yellow + "Final state! " + (state.isDead() ? red : green) + state);
    }

    private static String[] actList = {
        "Tidur Siang", "Tidur Malam",
        "Makan Hamburger", "Makan Pizza", "Makan Steak and Beans",
        "Minum Air", "Minum Kopi", "Minum Jus",
        "Buang Air Kecil", "Buang Air Besar",
        "Bersosialisasi ke Kafe", "Bermain Media Sosial", "Bermain Komputer",
        "Mandi", "Cuci Tangan", "Mendengarkan Musik di Radio",
        "Membaca Koran", "Membaca Novel"
    };
    private static void gencsv() throws Exception {
        State q0 = new State();
        Queue<State> q = new LinkedList<>();
        List<List<String>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        res.get(0).add("State");
        for (String s : actList)
            res.get(0).add(s);
        HashSet<State> done = new HashSet<>();
        q.add(q0);
        done.add(q0);
        while (!q.isEmpty()) {
            State now = q.poll();
            List<String> list = new ArrayList<>();
            list.add("\"" + (now.equals(q0) ? "→" : now.isFinalState() ? "*" : "") + now.toString() + "\"");
            for (int i = 0; i < actList.length; i++) {
                State next = null;
                try {
                    next = now.act(actList[i]);
                } catch (RuntimeException e) {
                    if (e.equals(State.NOT_FOUND_EXCEPTION)) {
                        outln(actList[i]);
                        throw e;
                    }
                }
                list.add(next == null ? "∅" : ("\"" + next.toString() + "\""));
                if (next == null)
                    continue;
                if (!done.contains(next)) {
                    done.add(next);
                    q.add(next);
                }
            }
            res.add(list);
        }
        FileWriter writer = new FileWriter(new File("generated_dfa.csv"));
        for (int i = 0; i < res.size(); i++) {
            if (i > 0)
                writer.append("\n");
            for (int j = 0; j < res.get(i).size(); j++) {
                if (j > 0)
                    writer.append(",");
                writer.append(res.get(i).get(j));
            }
        }
        writer.close();
    }
}
