package tubes;

public class State {
    public int h, e, f;
    public State() {
        this(0, 10, 0);
    }
    
    public State(int h, int e, int f) {
        this.h = h;
        this.e = e;
        this.f = f;
    }

    @Override
    public String toString() {
        return String.format("<%d,%d,%d>", this.h, this.e, this.f);
    }

    public void add(int h, int e, int f) {
        
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