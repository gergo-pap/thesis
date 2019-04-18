package Main;

public class Beallitasok {

    private Integer utasKorMinTF;
    private Integer utasKorMaxTF;
    private Integer utasEgyenlegIgTF;
    private Integer utasJegyTF;
    private Integer utasBerletTF;


    public Beallitasok() {
        this.utasKorMinTF = 15;
        this.utasKorMaxTF = 99;
        this.utasEgyenlegIgTF = 50000;
        this.utasJegyTF = 50;
        this.utasBerletTF = 85;
    }

    public Integer getUtaskorMinTF() {
        return utasKorMinTF;
    }

    public void setUtaskorMinTF(Integer utasKorMinTF) {
        this.utasKorMinTF = utasKorMinTF;
    }

    public Integer getUtaskorMaxTF() {
        return utasKorMaxTF;
    }

    public void setUtaskorMaxTF(Integer utasKorMaxTF) {
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

