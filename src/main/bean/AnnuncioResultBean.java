package main.bean;

import java.util.List;

public class AnnuncioResultBean {

    private List<String> titoliAnnunci;
    private List<String> indirizziAnnunci;
    private List<Integer> votiAnnunci;
    private List<Double> prezziAnnunci;

    public AnnuncioResultBean(List<String> titoliAnnunci, List<String> indirizziAnnunci, List<Integer> votiAnnunci, List<Double> prezziAnnunci) {
        this.titoliAnnunci = titoliAnnunci;
        this.indirizziAnnunci = indirizziAnnunci;
        this.votiAnnunci = votiAnnunci;
        this.prezziAnnunci = prezziAnnunci;
    }

    public List<String> getTitoliAnnunci() {
        return titoliAnnunci;
    }

    public void setTitoliAnnunci(List<String> titoliAnnunci) {
        this.titoliAnnunci = titoliAnnunci;
    }

    public List<String> getIndirizziAnnunci() {
        return indirizziAnnunci;
    }

    public void setIndirizziAnnunci(List<String> indirizziAnnunci) {
        this.indirizziAnnunci = indirizziAnnunci;
    }

    public List<Integer> getVotiAnnunci() {
        return votiAnnunci;
    }

    public void setVotiAnnunci(List<Integer> votiAnnunci) {
        this.votiAnnunci = votiAnnunci;
    }

    public List<Double> getPrezziAnnunci() {
        return prezziAnnunci;
    }

    public void setPrezziAnnunci(List<Double> prezziAnnunci) {
        this.prezziAnnunci = prezziAnnunci;
    }
}
