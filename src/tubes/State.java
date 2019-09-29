package tubes;

import tubes.Console.*;

public class State {
    public static final int MAX_VALUE = 15,
                            MIN_VALUE = 0;
    private static final RuntimeException NOT_VALID_EXCEPTION = new RuntimeException("Aksi tidak valid");
    public int h, e, f;
    public State() {
        this(0, 10, 0);
    }
    
    public State(int h, int e, int f) {
        this.h = h;
        this.e = e;
        this.f = f;
    }

    public void act(String input) {
        try {
            String arg = input.split(" ")[0];
            String argNext = input.length() > arg.length()+1 ? input.substring(arg.length()+1) : "";
            switch (arg) {
                case "tidur":
                    switch (argNext) {
                        case "siang":
                            this.add(0, 10, 0);
                            break;
                        case "malam":
                            this.add(0, 15, 0);
                            break;
                        default:
                            throw NOT_VALID_EXCEPTION;
                    }
                    break;
                case "makan":
                    switch (argNext) {
                        case "hamburger":
                            this.add(0, 5, 0);
                            break;
                        case "pizza":
                            this.add(0, 10, 0);
                            break;
                        case "steak and beans":
                            this.add(0, 15, 0);
                            break;
                        default:
                            throw NOT_VALID_EXCEPTION;
                    }
                    break;
                case "minum":
                    switch (argNext) {
                        case "air":
                            this.add(-5, 0, 0);
                            break;
                        case "kopi":
                            this.add(-10, 5, 0);
                            break;
                        case "jus":
                            this.add(-5, 10, 0);
                            break;
                        default:
                            throw NOT_VALID_EXCEPTION;
                    }
                    break;
                case "buang":
                    switch (argNext) {
                        case "air kecil":
                            this.add(5, 0, 0);
                            break;
                        case "air besar":
                            this.add(10, -5, 0);
                            break;
                        default:
                            throw NOT_VALID_EXCEPTION;
                    }
                    break;
                case "bersosialisasi ke kafe":
                    this.add(-5, -10, 15);
                    break;
                case "bermain media sosial":
                    this.add(0, -10, 10);
                    break;
                case "bermain komputer":
                    this.add(0, -10, 15);
                    break;
                case "mandi":
                    this.add(15, -5, 0);
                    break;
                case "cuci tangan":
                    this.add(5, 0, 0);
                    break;
                case "mendengarkan musik di radio":
                    this.add(0, -5, 10);
                    break;
                case "membaca":
                    switch (argNext) {
                        case "koran":
                            this.add(0, -5, 5);
                            break;
                        case "novel":
                            this.add(0, -5, 10);
                            break;
                        default:
                            throw NOT_VALID_EXCEPTION;
                    }
                    break;
                default:
                    throw NOT_VALID_EXCEPTION;
            }
        } catch (Exception _e) {
            outln("Aksi tidak ada.");
        }
    }

    @Override
    public String toString() {
        return String.format("<%d,%d,%d>", this.h, this.e, this.f);
    }

    public String printString() {
        return "Hygiene = " + this.h + "\nEnergy = " + this.e + "\nFun = " + this.f;
    }

    public boolean isFinalState() {
        return this.isDead() || this.isFull();
    }

    public boolean isDead() {
        return this.h == MIN_VALUE && this.e == MIN_VALUE && this.f == MIN_VALUE;
    }

    public boolean isFull() {
        return this.h == MAX_VALUE && this.e == MAX_VALUE && this.f == MAX_VALUE;
    }

    public void add(int h, int e, int f) {
        if (this.h+h > MAX_VALUE || this.e+e > MAX_VALUE || this.f+f > MAX_VALUE ||
            this.h+h < MIN_VALUE || this.e+e < MIN_VALUE || this.f+f < MIN_VALUE)
            throw NOT_VALID_EXCEPTION;
        this.h += h;
        this.e += e;
        this.f += f;
    }

    public void addHygiene(int h) {
        this.add(h, 0, 0);
    }

    public void addEnergy(int e) {
        this.add(0, e, 0);
    }
    
    public void addFun(int f) {
        this.add(0, 0, f);
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
}