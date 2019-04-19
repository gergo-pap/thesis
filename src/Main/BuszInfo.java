package Main;

public class BuszInfo {
    private int kapacitas;
    private boolean elsoAjtos;

    public BuszInfo(int kapacitas, boolean elsoAjtos) {
        this.kapacitas = kapacitas;
        this.elsoAjtos = elsoAjtos;
    }

    public int getKapacitas() {
        return kapacitas;
    }

    public boolean isElsoAjtos() {
        return elsoAjtos;
    }
}
