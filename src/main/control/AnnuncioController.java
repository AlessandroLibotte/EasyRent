package main.control;

import main.bean.AnnuncioBean;
import main.bean.AnnuncioResultBean;
import main.bean.PrenotazioneBean;
import main.control.exceptions.*;
import main.model.Annuncio;
import main.model.Immobile;
import main.model.Locatore;
import main.model.Prenotazione;
import main.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AnnuncioController {

    private final AnnuncioDao annuncioDao = DaoFactory.getInstance().getAnnuncioDao();
    private final ImmobileDao immobileDao = DaoFactory.getInstance().getImmobileDao();
    private final UserDao userDao = DaoFactory.getInstance().getUserDao();
    private final PrenotazioneDao prenotazioneDao = DaoFactory.getInstance().getPrenotazioneDao();


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

    public void creaAnnuncio(AnnuncioBean bean) throws AnnuncioExistsException {

        if(bean.isNotValid()) throw new InputException();
        if(annuncioDao.exists(bean.getTitolo())) throw new AnnuncioExistsException(bean.getTitolo());

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

    }

    public AnnuncioResultBean searchAnnunci(PrenotazioneBean bean) {

        if(!bean.isValid()) throw new InputException();

        List<Annuncio> annunci = annuncioDao.loadAll();

        if (annunci.isEmpty()) throw new NoAvailableAnnunciException();

        return getSearchResults(annunci, bean);

    }

    private AnnuncioResultBean getSearchResults(List<Annuncio> annunci, PrenotazioneBean bean) {

        List<String> resultsTitolo = new ArrayList<>();
        List<String> resultsIndirizzo = new ArrayList<>();
        List<Double> resultsPrezzo = new ArrayList<>();
        List<Integer> resultsVoto = new ArrayList<>();

        for (Annuncio ann : annunci) {

            if (!ann.getImmobile().getIndirizzo().contains(bean.getLocalita()) || bean.getNumOspiti() > ann.getImmobile().getMaxOspiti()) continue;

            if (!ann.getPrenotazioni().isEmpty()) {
                for (Prenotazione p : ann.getPrenotazioni()) {
                    if (p.getEndDate().isBefore(bean.getStartDate())) {
                        resultsTitolo.add(ann.getTitolo());
                        resultsIndirizzo.add(ann.getImmobile().getIndirizzo());
                        resultsPrezzo.add(ann.getPrezzoPerNotte());
                        resultsVoto.add(ann.getVoto());
                    }
                }
            } else {
                resultsTitolo.add(ann.getTitolo());
                resultsIndirizzo.add(ann.getImmobile().getIndirizzo());
                resultsPrezzo.add(ann.getPrezzoPerNotte());
                resultsVoto.add(ann.getVoto());
            }

        }

        return new AnnuncioResultBean(resultsTitolo, resultsIndirizzo, resultsVoto, resultsPrezzo);

    }

    public AnnuncioResultBean getAllAnnunci(AnnuncioBean bean){

        if (!userDao.exists(bean.getOwner())) throw new UserDoesNotExistException(bean.getOwner());

        Locatore loc = (Locatore) userDao.load(bean.getOwner());

        List<Annuncio> anns = loc.getAnnunci();
        if (anns == null || anns.isEmpty()) throw new NoAvailableAnnunciException();

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

        return new AnnuncioResultBean(annTitles, annIndirizzi, annVoti, annPrezzi);
    }

    public AnnuncioBean getAnnuncio(AnnuncioBean annBean){

        if (!annuncioDao.exists(annBean.getTitolo())) throw new NoAvailableAnnunciException();

        Annuncio ann = annuncioDao.load(annBean.getTitolo());

        List<String> prenotanti = new ArrayList<>();

        for(Prenotazione p: ann.getPrenotazioni()){
            prenotanti.add(p.getPrenotante());
        }

        AnnuncioBean result = new AnnuncioBean(ann.getTitolo());

        result.setOwner(ann.getOwner());
        result.setIndirizzo(ann.getImmobile().getIndirizzo());
        result.setDescrizione(ann.getDescrizione());
        result.setServizi(ann.getImmobile().getServizi());
        result.setMaxOspiti(ann.getImmobile().getMaxOspiti());
        result.setPrice(ann.getPrezzoPerNotte());
        result.setVoto(ann.getVoto());
        result.setPrenotanti(prenotanti);

        return result;
    }

    public void eliminaAnnuncio(AnnuncioBean bean){

        if (!userDao.exists(bean.getOwner())) throw new UserDoesNotExistException(bean.getOwner());

        Locatore loc = (Locatore) userDao.load(bean.getOwner());

        if (!annuncioDao.exists(bean.getTitolo())) throw new NoAvailableAnnunciException();

        Annuncio ann = annuncioDao.load(bean.getTitolo());

        List<Annuncio> anns = loc.getAnnunci();

        anns.remove(ann);

        loc.setAnnunci(anns);
        userDao.store(loc);

        for(Prenotazione p: ann.getPrenotazioni()) prenotazioneDao.delete(p.getId());
        annuncioDao.delete(bean.getTitolo());
        immobileDao.delete(ann.getImmobile().getIndirizzo());

    }

    public PrenotazioneBean getPrenotazioniAnnuncio(AnnuncioBean annBean){

        if(!annuncioDao.exists(annBean.getTitolo())) throw new  NoAvailableAnnunciException();

        Annuncio ann = annuncioDao.load(annBean.getTitolo());

        List<Prenotazione> prens = ann.getPrenotazioni();

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

        if(!annuncioDao.exists(annBean.getTitolo())) throw new  NoAvailableAnnunciException();

        Annuncio ann = annuncioDao.load(annBean.getTitolo());

        for(Prenotazione pren : ann.getPrenotazioni()){
            if(pren.getPrenotante().equals(annBean.getOwner())) return new PrenotazioneBean(pren);
        }

        return null;
    }

}
