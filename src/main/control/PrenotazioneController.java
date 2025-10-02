package main.control;

import main.bean.AnnuncioBean;
import main.bean.PrenotazioneBean;
import main.model.*;
import main.persistence.AnnuncioDao;
import main.persistence.DaoFactory;
import main.persistence.PrenotazioneDao;
import main.persistence.UserDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrenotazioneController {

    AnnuncioDao annDao = DaoFactory.getInstance().getAnnuncioDao();
    UserDao userDao = DaoFactory.getInstance().getUserDao();
    PrenotazioneDao prenDao = DaoFactory.getInstance().getPrenotazioneDao();

    public AnnuncioBean searchAnnunci(PrenotazioneBean bean) {

        List<Annuncio> annunci = annDao.loadAll();

        List<String> resultsTitolo = new ArrayList<>();
        List<String> resultsIndirizzo = new ArrayList<>();
        List<Double> resultsPrezzo = new ArrayList<>();
        List<Integer> resultsVoto = new ArrayList<>();

        for (Annuncio ann : annunci) {
            if (ann.getImmobile().getIndirizzo().contains(bean.getLocalita())) {
                if (bean.getNumOspiti() <= ann.getImmobile().getMaxOspiti()) {
                    if (!ann.getPrenotazioni().isEmpty() && bean.getStartDate() != null && bean.getEndDate() != null) {
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
            }
        }

        return new AnnuncioBean(resultsTitolo, resultsIndirizzo, resultsVoto, resultsPrezzo);
    }

    public PrenotazioneBean getPrenotazioni(PrenotazioneBean bean){

        User user = userDao.load(bean.getCurrentUser());

        List<Annuncio> prens = null;

        if (user instanceof  Affittuario) {
            prens = ((Affittuario)user).getPrenotazioni();
        }
        else if (user instanceof Locatore) {
            prens = ((Locatore)user).getAnnunci();
        }

        List<String> resultPrenotanti = new ArrayList<>();
        List<String> resultTitles = new ArrayList<>();
        List<LocalDate> resultStartDates = new ArrayList<>();
        List<LocalDate> resultEndDates = new ArrayList<>();
        List<Integer> resultNumOspiti = new ArrayList<>();

        if(prens == null || prens.isEmpty()) return new PrenotazioneBean(resultPrenotanti, resultTitles, resultStartDates, resultEndDates, resultNumOspiti);

        for (Annuncio ann : prens) {
            for (Prenotazione p : ann.getPrenotazioni()) {
                if(user instanceof Affittuario && p.getPrenotante().equals(user.getEmail())) {
                    resultTitles.add(ann.getTitolo());
                    resultStartDates.add(p.getStartDate());
                    resultEndDates.add(p.getEndDate());
                    resultNumOspiti.add(p.getNumOspiti());
                }
                else if (user instanceof Locatore){
                    System.out.println(p.getPrenotante());
                    resultPrenotanti.add(p.getPrenotante());
                    resultTitles.add(ann.getTitolo());
                    resultStartDates.add(p.getStartDate());
                    resultEndDates.add(p.getEndDate());
                    resultNumOspiti.add(p.getNumOspiti());
                }
            }
        }

        return new PrenotazioneBean(resultPrenotanti, resultTitles, resultStartDates, resultEndDates, resultNumOspiti);

    }

    public PrenotazioneBean getPrenotazioneInfo(AnnuncioBean annBean){

        Affittuario currentUser = (Affittuario) userDao.load(annBean.getOwner());

        Annuncio ann = annDao.load(annBean.getTitolo());

        ArrayList<Prenotazione> prens = (ArrayList<Prenotazione>)ann.getPrenotazioni();

        for(Prenotazione p : prens) {
            if(p.getPrenotante().equals(currentUser.getEmail())) return new PrenotazioneBean(p);
        }

        return null;

    }

    public void prenota(AnnuncioBean annBean, PrenotazioneBean prenBean){

        //Load target annuncio
        Annuncio ann = annDao.load(annBean.getTitolo());

        //get the already present reservations
        ArrayList<Prenotazione> prens = (ArrayList<Prenotazione>) ann.getPrenotazioni();

        //create new reservation
        Prenotazione newPren = prenDao.create(prenBean.getCurrentUser());
        newPren.setStartDate(prenBean.getStartDate());
        newPren.setEndDate(prenBean.getEndDate());
        newPren.setNumOspiti(prenBean.getNumOspiti());

        prenDao.store(newPren);

        // add reservation to reservation list
        prens.add(newPren);

        ann.setPrenotazioni(prens);

        //update current user's reservations
        Affittuario aff = (Affittuario) userDao.load(prenBean.getCurrentUser());
        ArrayList<Annuncio> currPrens = (ArrayList<Annuncio>) aff.getPrenotazioni();
        if (currPrens == null) currPrens = new ArrayList<>();
        currPrens.add(ann);
        aff.setPrenotazioni(currPrens);

        //store entities
        annDao.delete(ann.getTitolo());
        annDao.store(ann);
        userDao.store(aff);

    }

}
