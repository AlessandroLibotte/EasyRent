package main.control;

import main.bean.AnnuncioBean;
import main.bean.LoginBean;
import main.bean.PrenotazioneBean;
import main.control.exceptions.NoAvailableAnnunciException;
import main.control.exceptions.UserDoesNotExistException;
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
    UserController userController = new UserController();

    public PrenotazioneBean getPrenotazioni(PrenotazioneBean bean){

        if(!userDao.exists(bean.getCurrentUser())) throw new UserDoesNotExistException(bean.getCurrentUser());

        User user = userDao.load(bean.getCurrentUser());

        List<Prenotazione> prens = new ArrayList<>();

        Role role = userController.assertUser(new LoginBean(bean.getCurrentUser()));

        switch (role) {
            case Role.AFFITTUARIO -> prens = ((Affittuario)user).getPrenotazioni();
            case Role.LOCATORE -> {
                for (Annuncio ann : ((Locatore)user).getAnnunci()) prens.addAll(ann.getPrenotazioni());
            }
            case Role.INVALID -> throw new UserDoesNotExistException(bean.getCurrentUser());
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

        if (!userDao.exists(annBean.getOwner())) throw new UserDoesNotExistException(annBean.getOwner());

        Affittuario currentUser = (Affittuario) userDao.load(annBean.getOwner());

        if(!annDao.exists(annBean.getTitolo())) throw new NoAvailableAnnunciException();
        Annuncio ann = annDao.load(annBean.getTitolo());

        ArrayList<Prenotazione> prens = (ArrayList<Prenotazione>)ann.getPrenotazioni();

        for(Prenotazione p : prens) {
            if(p.getPrenotante().equals(currentUser.getEmail())) return new PrenotazioneBean(p);
        }

        return null;

    }

    public void prenota(AnnuncioBean annBean, PrenotazioneBean prenBean){

        //Load target annuncio
        if(!annDao.exists(annBean.getTitolo())) throw new NoAvailableAnnunciException();
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
        if(!userDao.exists(prenBean.getCurrentUser())) throw new  UserDoesNotExistException(prenBean.getCurrentUser());
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
