package tubes;

import static tubes.Console.*;

public class ConsoleApp {
    public static void main(String[] args) {
        State state = new State();
        while (!state.isFinalState()) {
            outln(state.printString());
            state.act(line());
        }
        outln(state.isDead() ? red + "ded" : green + "win");
    }
}
