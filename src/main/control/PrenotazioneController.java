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

    public PrenotazioneBean getPrenotazioni(PrenotazioneBean bean){

        User user = userDao.load(bean.getCurrentUser());

        List<Prenotazione> prens = new ArrayList<>();

        if (user instanceof  Affittuario) {
            prens = ((Affittuario)user).getPrenotazioni();
        }
        else if (user instanceof Locatore) {
            for (Annuncio ann : ((Locatore)user).getAnnunci()) prens.addAll(ann.getPrenotazioni());
        }

        List<String> resultPrenotanti = new ArrayList<>();
        List<String> resultTitles = new ArrayList<>();
        List<LocalDate> resultStartDates = new ArrayList<>();
        List<LocalDate> resultEndDates = new ArrayList<>();
        List<Integer> resultNumOspiti = new ArrayList<>();

        if(prens.isEmpty()) return new PrenotazioneBean(resultPrenotanti, resultTitles, resultStartDates, resultEndDates, resultNumOspiti);

        for (Prenotazione p : prens) {
            if(user instanceof Affittuario && p.getPrenotante().equals(user.getEmail())) {
                resultTitles.add(p.getTitoloAnnuncio());
                resultStartDates.add(p.getStartDate());
                resultEndDates.add(p.getEndDate());
                resultNumOspiti.add(p.getNumOspiti());
            }
            else if (user instanceof Locatore){
                resultPrenotanti.add(p.getPrenotante());
                resultTitles.add(p.getTitoloAnnuncio());
                resultStartDates.add(p.getStartDate());
                resultEndDates.add(p.getEndDate());
                resultNumOspiti.add(p.getNumOspiti());
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
        List<Prenotazione> prens = ann.getPrenotazioni();

        //create new reservation
        Prenotazione newPren = prenDao.create(ann.getTitolo());
        newPren.setStartDate(prenBean.getStartDate());
        newPren.setEndDate(prenBean.getEndDate());
        newPren.setNumOspiti(prenBean.getNumOspiti());
        newPren.setPrenotante(prenBean.getCurrentUser());

        // add reservation to reservation list
        prens.add(newPren);

        ann.setPrenotazioni(prens);

        //update current user's reservations
        Affittuario aff = (Affittuario) userDao.load(prenBean.getCurrentUser());
        List<Prenotazione> currPrens = aff.getPrenotazioni();
        if (currPrens == null) currPrens = new ArrayList<>();
        currPrens.add(newPren);
        aff.setPrenotazioni(currPrens);

        //store entities
        prenDao.store(newPren);
        annDao.store(ann);
        userDao.store(aff);

    }

}
