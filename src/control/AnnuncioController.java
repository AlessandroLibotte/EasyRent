package control;

import bean.AnnuncioBean;
import model.Annuncio;
import model.Immobile;
import model.Locatore;
import model.Prenotazione;
import persistence.AnnuncioDao;
import persistence.DaoFactory;
import persistence.ImmobileDao;
import persistence.UserDao;

import java.util.ArrayList;

public class AnnuncioController {

    public Immobile getCreateImmobile(String indirizzo) {

        ImmobileDao immobileDao = DaoFactory.getInstance().getImmobileDao();

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

        AnnuncioDao annuncioDao = DaoFactory.getInstance().getAnnuncioDao();
        ImmobileDao immobileDao = DaoFactory.getInstance().getImmobileDao();
        UserDao userDao = DaoFactory.getInstance().getUserDao();

        if(annuncioDao.exists(bean.getTitolo())) return false;

        Annuncio ann = annuncioDao.create(bean.getTitolo());
        ann.setDescrizione(bean.getDescrizione());

        Immobile imm = getCreateImmobile(bean.getIndirizzo());
        imm.setServizi(bean.getServizi());
        imm.setMaxOspiti(bean.getMaxOspiti());

        ann.setImmobile(imm);

        ann.setPrenotazioni(new ArrayList<>());

        Locatore loc = (Locatore) userDao.load(bean.getCurrentUser());
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

        //get daos
        AnnuncioDao annuncioDao = DaoFactory.getInstance().getAnnuncioDao();
        ImmobileDao immobileDao = DaoFactory.getInstance().getImmobileDao();
        UserDao userDao = DaoFactory.getInstance().getUserDao();

        //load old annuncio
        if(!annuncioDao.exists(bean.getOldTitolo())) return false;
        Annuncio ann = annuncioDao.load(bean.getOldTitolo());

        //remove old annuncio from memory
        annuncioDao.delete(bean.getOldTitolo());

        //get current user's annunci
        Locatore loc = (Locatore) userDao.load(bean.getCurrentUser());
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

    public AnnuncioBean getCurrentUserAnnunci(AnnuncioBean bean){

        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Locatore loc = (Locatore) userDao.load(bean.getCurrentUser());

        ArrayList<Annuncio> anns = (ArrayList<Annuncio>)loc.getAnnunci();
        if (anns == null) {return new AnnuncioBean(new ArrayList<>());}

        ArrayList<String> annTitls = new ArrayList<>();
        for(Annuncio ann : anns){
            annTitls.add(ann.getTitolo());
        }

        return new AnnuncioBean(annTitls);
    }

    public AnnuncioBean getAnnuncio(AnnuncioBean annBean){
        AnnuncioDao annuncioDao = DaoFactory.getInstance().getAnnuncioDao();
        Annuncio ann = annuncioDao.load(annBean.getTitolo());
        return new AnnuncioBean(annBean.getCurrentUser(), ann.getTitolo(), ann.getImmobile().getIndirizzo(), ann.getDescrizione(),
                ann.getImmobile().getServizi(), ann.getImmobile().getMaxOspiti());
    }

    public void eliminaAnnuncio(AnnuncioBean bean){

        AnnuncioDao annuncioDao = DaoFactory.getInstance().getAnnuncioDao();
        UserDao userDao = DaoFactory.getInstance().getUserDao();

        Locatore loc = (Locatore) userDao.load(bean.getTitolo());

        ArrayList<Annuncio> anns = (ArrayList<Annuncio>)loc.getAnnunci();

        anns.remove(annuncioDao.load(bean.getTitolo()));

        loc.setAnnunci(anns);
        userDao.store(loc);

        annuncioDao.delete(bean.getTitolo());

    }

    public AnnuncioBean getPrenotazioniAnnuncio(AnnuncioBean annBean){

        AnnuncioDao annuncioDao = DaoFactory.getInstance().getAnnuncioDao();

        Annuncio ann = annuncioDao.load(annBean.getTitolo());

        ArrayList<Prenotazione> prens = (ArrayList<Prenotazione>)ann.getPrenotazioni();
        if(prens == null) return new AnnuncioBean(new ArrayList<>());

        ArrayList<String> prenotatori = new ArrayList<>();

        for(Prenotazione pren : prens){
            prenotatori.add(pren.getPrenotante());
        }

        return new AnnuncioBean(prenotatori);

    }

}
