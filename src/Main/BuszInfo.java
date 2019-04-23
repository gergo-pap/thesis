package Main;

public class BuszInfo {
    private int kapacitas;
    private boolean elsoAjtos;

    BuszInfo(int kapacitas, boolean elsoAjtos) {
        this.kapacitas = kapacitas;
        this.elsoAjtos = elsoAjtos;
    }

    int getKapacitas() {
        return kapacitas;
    }

    public boolean isElsoAjtos() {
        return elsoAjtos;
    }
}
