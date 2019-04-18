package Main;

public class Beallitasok {

    private int utaskorMinTF;
    private int utaskorMaxTF;
    private int utasEgyenlegIgTF;
    private int utasJegyTF;
    private int utasBerletTF;


    public Beallitasok() {
        this.utaskorMinTF = 15;
        this.utaskorMaxTF = 99;
        this.utasEgyenlegIgTF = 50000;
        this.utasJegyTF = 50;
        this.utasBerletTF = 85;
    }

    public int getUtaskorMinTF() {
        return utaskorMinTF;
    }

    public void setUtaskorMinTF(int utaskorMinTF) {
        this.utaskorMinTF = utaskorMinTF;
    }

    public int getUtaskorMaxTF() {
        return utaskorMaxTF;
    }

    public void setUtaskorMaxTF(int utaskorMaxTF) {
        this.utaskorMaxTF = utaskorMaxTF;
    }

    public int getUtasEgyenlegIgTF() {
        return utasEgyenlegIgTF;
    }

    public void setUtasEgyenlegIgTF(int utasEgyenlegIgTF) {
        this.utasEgyenlegIgTF = utasEgyenlegIgTF;
    }

    public int getUtasJegyTF() {
        return utasJegyTF;
    }

    public void setUtasJegyTF(int utasJegyTF) {
        this.utasJegyTF = utasJegyTF;
    }

    public int getUtasBerletTF() {
        return utasBerletTF;
    }

    public void setUtasBerletTF(int utasBerletTF) {
        this.utasBerletTF = utasBerletTF;
    }
}

