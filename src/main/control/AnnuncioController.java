package main.control;

import main.bean.AnnuncioBean;
import main.bean.PrenotazioneBean;
import main.model.Annuncio;
import main.model.Immobile;
import main.model.Locatore;
import main.model.Prenotazione;
import main.persistence.AnnuncioDao;
import main.persistence.DaoFactory;
import main.persistence.ImmobileDao;
import main.persistence.UserDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AnnuncioController {

    AnnuncioDao annuncioDao = DaoFactory.getInstance().getAnnuncioDao();
    ImmobileDao immobileDao = DaoFactory.getInstance().getImmobileDao();
    UserDao userDao = DaoFactory.getInstance().getUserDao();

    private Immobile getCreateImmobile(String indirizzo) {

        Immobile imm;
        if(immobileDao.exists(indirizzo)){
            imm = immobileDao.load(indirizzo);
        }
        else {
            imm = immobileDao.create(indirizzo);
        }

        return imm;
    }

    public boolean creaAnnuncio(AnnuncioBean bean){

        if(annuncioDao.exists(bean.getTitolo())) return false;

        Annuncio ann = annuncioDao.create(bean.getTitolo());
        ann.setOwner(bean.getOwner());
        ann.setDescrizione(bean.getDescrizione());

        Immobile imm = getCreateImmobile(bean.getIndirizzo());
        imm.setServizi(bean.getServizi());
        imm.setMaxOspiti(bean.getMaxOspiti());

        ann.setImmobile(imm);

        ann.setPrezzoPerNotte(bean.getPrice());

        ann.setPrenotazioni(new ArrayList<>());

        Locatore loc = (Locatore) userDao.load(bean.getOwner());
        ArrayList<Annuncio> anns = (ArrayList<Annuncio>) loc.getAnnunci();

        if (anns == null) {anns = new ArrayList<>();}

        anns.add(ann);

        loc.setAnnunci(anns);

        userDao.store(loc);
        immobileDao.store(imm);
        annuncioDao.store(ann);

        return true;

    }

    public boolean modifcaAnnuncio(AnnuncioBean bean){

        //load old annuncio
        if(!annuncioDao.exists(bean.getOldTitolo())) return false;
        Annuncio ann = annuncioDao.load(bean.getOldTitolo());

        //remove old annuncio from memory
        annuncioDao.delete(bean.getOldTitolo());

        //get current user's annunci
        Locatore loc = (Locatore) userDao.load(bean.getOwner());
        ArrayList<Annuncio> anns = (ArrayList<Annuncio>)loc.getAnnunci();

        //remove old annuncio from current user's annunci
        anns.remove(ann);

        //set new annuncio's parameters
        ann.setTitolo(bean.getTitolo());
        ann.setDescrizione(bean.getDescrizione());

        Immobile imm = getCreateImmobile(bean.getIndirizzo());
        imm.setServizi(bean.getServizi());
        imm.setMaxOspiti(bean.getMaxOspiti());

        ann.setImmobile(imm);

        //add new annuncio to the user's annunci
        anns.add(ann);
        loc.setAnnunci(anns);

        // store modified annuncio and immobile
        userDao.store(loc);
        immobileDao.store(imm);
        annuncioDao.store(ann);

        return true;

    }

    public AnnuncioBean getAllAnnunci(AnnuncioBean bean){

        Locatore loc = (Locatore) userDao.load(bean.getOwner());

        List<Annuncio> anns = loc.getAnnunci();
        if (anns == null || anns.isEmpty()) {return new AnnuncioBean(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());}

        List<String> annTitles = new ArrayList<>();
        List<String> annIndirizzi = new ArrayList<>();
        List<Integer> annVoti = new ArrayList<>();
        List<Double> annPrezzi = new ArrayList<>();

        for(Annuncio ann : anns){
            annTitles.add(ann.getTitolo());
            annIndirizzi.add(ann.getImmobile().getIndirizzo());
            annVoti.add(ann.getVoto());
            annPrezzi.add(ann.getPrezzoPerNotte());
        }

        return new AnnuncioBean(annTitles, annIndirizzi, annVoti, annPrezzi);
    }

    public AnnuncioBean getAnnuncio(AnnuncioBean annBean){

        Annuncio ann = annuncioDao.load(annBean.getTitolo());

        List<String> prenotanti = new ArrayList<>();

        for(Prenotazione p: ann.getPrenotazioni()){
            prenotanti.add(p.getPrenotante());
        }

        return new AnnuncioBean(ann.getOwner(), ann.getTitolo(), ann.getImmobile().getIndirizzo(),
                ann.getDescrizione(), ann.getImmobile().getServizi(), ann.getImmobile().getMaxOspiti(), ann.getPrezzoPerNotte(), ann.getVoto(), prenotanti);

    }

    public void eliminaAnnuncio(AnnuncioBean bean){

        Locatore loc = (Locatore) userDao.load(bean.getTitolo());

        ArrayList<Annuncio> anns = (ArrayList<Annuncio>)loc.getAnnunci();

        anns.remove(annuncioDao.load(bean.getTitolo()));

        loc.setAnnunci(anns);
        userDao.store(loc);

        annuncioDao.delete(bean.getTitolo());

    }

    public PrenotazioneBean getPrenotazioniAnnuncio(AnnuncioBean annBean){

        Annuncio ann = annuncioDao.load(annBean.getTitolo());

        List<Prenotazione> prens = ann.getPrenotazioni();
        if(prens == null) return new PrenotazioneBean(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        List<String> prenotanti = new ArrayList<>();
        List<LocalDate> dateInizio = new ArrayList<>();
        List<LocalDate> dateFine = new ArrayList<>();
        List<Integer> numeroOspiti = new ArrayList<>();

        for(Prenotazione pren : prens){
            prenotanti.add(pren.getPrenotante());
            dateInizio.add(pren.getStartDate());
            dateFine.add(pren.getEndDate());
            numeroOspiti.add(pren.getNumOspiti());
        }

        return new PrenotazioneBean(prenotanti, new ArrayList<>(), dateInizio, dateFine, numeroOspiti);

    }

    public PrenotazioneBean getPrenotazioneInfo(AnnuncioBean annBean){

        Annuncio ann = annuncioDao.load(annBean.getTitolo());

        for(Prenotazione pren : ann.getPrenotazioni()){
            if(pren.getPrenotante().equals(annBean.getOwner())) return new PrenotazioneBean(pren);
        }

        return null;
    }

}
