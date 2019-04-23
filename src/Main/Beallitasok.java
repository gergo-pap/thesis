package Main;

public class Beallitasok {

    private Integer utasKorMinTF;
    private Integer utasKorMaxTF;
    private Integer utasEgyenlegIgTF;
    private Integer utasJegyTF;
    private Integer utasBerletTF;


    Beallitasok() {
        this.utasKorMinTF = 15;
        this.utasKorMaxTF = 99;
        this.utasEgyenlegIgTF = 50000;
        this.utasJegyTF = 50;
        this.utasBerletTF = 85;
    }

    public Integer getUtasKorMinTF() {
        return utasKorMinTF;
    }

    public void setUtasKorMinTF(Integer utasKorMinTF) {
        this.utasKorMinTF = utasKorMinTF;
    }

    public Integer getUtasKorMaxTF() {
        return utasKorMaxTF;
    }

    public void setUtasKorMaxTF(Integer utasKorMaxTF) {
        this.utasKorMaxTF = utasKorMaxTF;
    }

    public Integer getUtasEgyenlegIgTF() {
        return utasEgyenlegIgTF;
    }

    public void setUtasEgyenlegIgTF(Integer utasEgyenlegIgTF) {
        this.utasEgyenlegIgTF = utasEgyenlegIgTF;
    }

    public Integer getUtasJegyTF() {
        return utasJegyTF;
    }

    public void setUtasJegyTF(Integer utasJegyTF) {
        this.utasJegyTF = utasJegyTF;
    }

    public Integer getUtasBerletTF() {
        return utasBerletTF;
    }

    public void setUtasBerletTF(Integer utasBerletTF) {
        this.utasBerletTF = utasBerletTF;
    }
}

