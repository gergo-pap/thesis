package Main;

public class Beallitasok {

    private int utasKorMinTF;
    private int utasKorMaxTF;
    private int utasEgyenlegIgTF;
    private int utasJegyTF;
    private int utasBerletTF;


    public Beallitasok() {
        this.utasKorMinTF = 15;
        this.utasKorMaxTF = 99;
        this.utasEgyenlegIgTF = 50000;
        this.utasJegyTF = 50;
        this.utasBerletTF = 85;
    }

    public int getUtaskorMinTF() {
        return utasKorMinTF;
    }

    public void setUtaskorMinTF(int utasKorMinTF) {
        this.utasKorMinTF = utasKorMinTF;
    }

    public int getUtaskorMaxTF() {
        return utasKorMaxTF;
    }

    public void setUtaskorMaxTF(int utasKorMaxTF) {
        this.utasKorMaxTF = utasKorMaxTF;
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

