package tubes;

import static tubes.Console.*;

/**
 * Kelas State sebagai obyek state yang mempunyai properti <code>h</code> untuk nilai hygiene, <code>e</code> untuk nilai energy, dan <code>f</code> untuk nilai fun.
 */
public class State {
    public static final int MAX_VALUE = 15,
                            MIN_VALUE = 0;
    public static final RuntimeException NOT_VALID_EXCEPTION = new RuntimeException("Aksi tidak valid."),
                                         NOT_FOUND_EXCEPTION = new RuntimeException("Aksi tidak ada.");
    public int h, e, f;

    /**
     * Konstruktor State dengan nilai hygiene=0, energy=10, fun=0.
     */
    public State() {
        this(0, 10, 0);
    }
    
    /**
     * Konstruktor State.
     */
    public State(int h, int e, int f) {
        this.h = h;
        this.e = e;
        this.f = f;
    }

    /**
     * Prosedur melakukan aksi dengan input aksi berbentuk string.
     */
    public State act(String input) {
        input = input.toLowerCase();
        String arg = input.split(" ")[0];
        String argNext = input.length() > arg.length()+1 ? input.substring(arg.length()+1) : "";
        switch (arg) {
            case "tidur":
                switch (argNext) {
                    case "siang":
                        return this.add(0, 10, 0);
                    case "malam":
                        return this.add(0, 15, 0);
                    default:
                        throw NOT_FOUND_EXCEPTION;
                }
            case "makan":
                switch (argNext) {
                    case "hamburger":
                        return this.add(0, 5, 0);
                    case "pizza":
                        return this.add(0, 10, 0);
                    case "steak and beans":
                        return this.add(0, 15, 0);
                    default:
                        throw NOT_FOUND_EXCEPTION;
                }
            case "minum":
                switch (argNext) {
                    case "air":
                        return this.add(-5, 0, 0);
                    case "kopi":
                        return this.add(-10, 5, 0);
                    case "jus":
                        return this.add(-5, 10, 0);
                    default:
                        throw NOT_FOUND_EXCEPTION;
                }
            case "buang":
                switch (argNext) {
                    case "air kecil":
                        return this.add(5, 0, 0);
                    case "air besar":
                        return this.add(10, -5, 0);
                    default:
                        throw NOT_FOUND_EXCEPTION;
                }
            case "membaca":
                switch (argNext) {
                    case "koran":
                        return this.add(0, -5, 5);
                    case "novel":
                        return this.add(0, -5, 10);
                    default:
                        throw NOT_FOUND_EXCEPTION;
                }
            default:
                switch (input) {
                    case "bersosialisasi ke kafe":
                        return this.add(-5, -10, 15);
                    case "bermain media sosial":
                        return this.add(0, -10, 10);
                    case "bermain komputer":
                        return this.add(0, -10, 15);
                    case "mandi":
                        return this.add(15, -5, 0);
                    case "cuci tangan":
                        return this.add(5, 0, 0);
                    case "mendengarkan musik di radio":
                        return this.add(0, -5, 10);
                    default:
                        throw NOT_FOUND_EXCEPTION;
                }
        }
    }

    /**
     * Fungsi yang menghasilkan bentuk state dalam format string &lthygiene,energy,fun&gt.
     */
    @Override
    public String toString() {
        return String.format("<%d,%d,%d>", this.h, this.e, this.f);
    }

    /**
     * Fungsi yang menghasilkan string output informasi status hygiene, energy, dan fun.
     */
    public String printString() {
        return green + "Hygiene = " + yellow + this.h + green + "\nEnergy = " + yellow + this.e + green + "\nFun = " + yellow + this.f;
    }

    /**
     * Fungsi yang menghasilkan true apabila fungsi isDead atau isFull adalah true.
     */
    public boolean isFinalState() {
        return this.isDead() || this.isFull();
    }

    /**
     * Fungsi yang menghasilkan true apabila nilai hygiene, energy, dan fun adalah 0.
     */
    public boolean isDead() {
        return this.h == MIN_VALUE && this.e == MIN_VALUE && this.f == MIN_VALUE;
    }

    /**
     * Fungsi yang menghasilkan true apabila nilai hygiene, energy, dan fun adalah 15.
     */
    public boolean isFull() {
        return this.h == MAX_VALUE && this.e == MAX_VALUE && this.f == MAX_VALUE;
    }

    /**
     * Fungsi untuk menambahkan nilai hygiene, energy, dan fun.
     */
    public State add(int h, int e, int f) {
        if (this.h+h > MAX_VALUE || this.e+e > MAX_VALUE || this.f+f > MAX_VALUE ||
            this.h+h < MIN_VALUE || this.e+e < MIN_VALUE || this.f+f < MIN_VALUE ||
            isFinalState())
            throw NOT_VALID_EXCEPTION;
        return new State(this.h+h, this.e+e, this.f+f);
    }

    public State clone() {
        return new State(this.h, this.e, this.f);
    }

    public static State parse(String str) {
        RuntimeException ex = new RuntimeException("Can't parse " + str + " to a State!");
        if (!str.startsWith("<") || !str.endsWith(">"))
            throw ex;
        str = str.substring(1, str.length()-1);
        String[] strs = str.split(",");
        if (strs.length < 3)
            throw ex;
        try {
            return new State(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]), Integer.parseInt(strs[2]));
        } catch (Exception _e) {
            throw ex;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + e;
        result = prime * result + f;
        result = prime * result + h;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        State other = (State) obj;
        if (e != other.e)
            return false;
        if (f != other.f)
            return false;
        if (h != other.h)
            return false;
        return true;
    }

}