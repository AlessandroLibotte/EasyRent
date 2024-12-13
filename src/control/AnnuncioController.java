package control;

import bean.AnnuncioBean;
import model.Annuncio;
import model.Immobile;
import model.Locatore;
import persistence.AnnuncioDao;
import persistence.DaoFactory;
import persistence.ImmobileDao;

import java.util.ArrayList;

public class AnnuncioController {

    private static AnnuncioController instance;

    private AnnuncioController() {}

    public static AnnuncioController getInstance() {
        if(instance == null) {
            instance = new AnnuncioController();
        }
        return instance;
    }

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

    public boolean creaAnnuncio(AnnuncioBean ab){

        AnnuncioDao annuncioDao = DaoFactory.getInstance().getAnnuncioDao();
        ImmobileDao immobileDao = DaoFactory.getInstance().getImmobileDao();

        if(annuncioDao.exists(ab.getTitolo())) return false;

        Annuncio ann = annuncioDao.create(ab.getTitolo());
        ann.setDescrizione(ab.getDescrizione());

        Immobile imm = getCreateImmobile(ab.getIndirizzo());
        imm.setServizi(ab.getServizi());

        ann.setImmobile(imm);

        LoginController lc = LoginController.getInstance();

        Locatore loc = (Locatore) lc.getCurrentUser();
        ArrayList<Annuncio> anns = loc.getAnnunci();

        if (anns == null) {anns = new ArrayList<>();}

        anns.add(ann);

        loc.setAnnunci(anns);
        lc.setCurrentUser(loc);

        immobileDao.store(imm);
        annuncioDao.store(ann);

        return true;

    }

    public boolean modifcaAnnuncio(AnnuncioBean ab){

        //get daos
        AnnuncioDao annuncioDao = DaoFactory.getInstance().getAnnuncioDao();
        ImmobileDao immobileDao = DaoFactory.getInstance().getImmobileDao();

        //load old annuncio
        Annuncio ann = annuncioDao.load(ab.getTitolo());

        //remove old annuncio from memory
        annuncioDao.delete(ab.getTitolo());

        //get current user's annunci
        LoginController lc = LoginController.getInstance();
        Locatore loc = (Locatore) lc.getCurrentUser();
        ArrayList<Annuncio> anns = loc.getAnnunci();

        //remove old annuncio from current user's annunci
        anns.remove(ann);

        //set new annuncio's parameters
        ann.setTitolo(ab.getNewTitolo());
        ann.setDescrizione(ab.getDescrizione());

        Immobile imm = getCreateImmobile(ab.getIndirizzo());
        imm.setServizi(ab.getServizi());

        ann.setImmobile(imm);

        //add new annuncio to the user's annunci
        anns.add(ann);
        loc.setAnnunci(anns);
        //update user
        lc.setCurrentUser(loc);

        // store modified annuncio and immobile
        immobileDao.store(imm);
        annuncioDao.store(ann);

        return true;

    }

    public AnnuncioBean getCurrentUserAnnunci(){

        LoginController lc = LoginController.getInstance();
        Locatore loc = (Locatore) lc.getCurrentUser();

        ArrayList<Annuncio> anns = loc.getAnnunci();
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
        return new AnnuncioBean(ann.getTitolo(), ann.getImmobile().getIndirizzo(), ann.getDescrizione(),
                ann.getImmobile().getServizi());
    }

    public void eliminaAnnuncio(AnnuncioBean annBean){

    }

}
