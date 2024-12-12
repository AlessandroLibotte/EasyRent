package control;

import bean.AnnuncioBean;
import model.Annuncio;
import model.Immobile;
import model.Locatore;
import persistence.AnnuncioDao;
import persistence.DaoFactory;
import persistence.ImmobileDao;

import java.util.Vector;

public class AnnuncioController {

    private static AnnuncioController instance;

    private AnnuncioController() {}

    public static AnnuncioController getInstance() {
        if(instance == null) {
            instance = new AnnuncioController();
        }
        return instance;
    }

    public boolean creaAnnuncio(AnnuncioBean ab){

        AnnuncioDao annuncioDao = DaoFactory.getInstance().getAnnuncioDao();
        ImmobileDao immobileDao = DaoFactory.getInstance().getImmobileDao();

        if(annuncioDao.exists(ab.getTitolo())) return false;

        Annuncio ann = annuncioDao.create(ab.getTitolo());
        ann.setDescrizione(ab.getDescrizione());

        Immobile imm;
        if(immobileDao.exists(ab.getIndirizzo())){
            imm = immobileDao.load(ab.getIndirizzo());
        }
        else {
            imm = immobileDao.create(ab.getIndirizzo());
            imm.setServizi(ab.getServizi());
        }

        ann.setImmobile(imm);

        LoginController lc = LoginController.getInstance();

        Locatore loc = (Locatore) lc.getCurrentUser();
        Vector<Annuncio> anns = loc.getAnnunci();

        if (anns == null) {anns = new Vector<>();}

        anns.add(ann);

        loc.setAnnunci(anns);
        lc.setCurrentUser(loc);

        immobileDao.store(imm);
        annuncioDao.store(ann);

        return true;

    }

    public AnnuncioBean getCurrentUserAnnunci(){
        LoginController lc = LoginController.getInstance();
        Locatore loc = (Locatore) lc.getCurrentUser();
        Vector<Annuncio> anns = loc.getAnnunci();
        Vector<String> annTitls = new Vector<>();
        for(Annuncio ann : anns){
            annTitls.add(ann.getTitolo());
        }
        return new AnnuncioBean(annTitls);
    }

}
