package control;

import bean.AnnuncioBean;
import bean.PrenotazioneBean;
import model.Affittuario;
import model.Annuncio;
import model.Prenotazione;
import persistence.AnnuncioDao;
import persistence.DaoFactory;
import persistence.PrenotazioneDao;

import java.util.ArrayList;

public class PrenotazioneController {

    public PrenotazioneBean searchAnnunci(PrenotazioneBean bean) {

        AnnuncioDao annDao = DaoFactory.getInstance().getAnnuncioDao();

        ArrayList<Annuncio> annunci = (ArrayList<Annuncio>) annDao.loadAll();

        ArrayList<String> results = new ArrayList<>();

        for (Annuncio ann : annunci) {
            if (ann.getImmobile().getIndirizzo().contains(bean.getLocalita())){
                if(bean.getNumOspiti() <= ann.getImmobile().getMaxOspiti()) {
                    if (!ann.getPrenotazioni().isEmpty()) {
                        for (Prenotazione p : ann.getPrenotazioni()) {
                            if (p.getEndDate().isBefore(bean.getStartDate())) results.add(ann.getTitolo());
                        }
                    } else results.add(ann.getTitolo());
                }
            }
        }

        bean.setSearchResults(results);

        return bean;
    }

    public PrenotazioneBean getPrenotazioni(){

        Affittuario currentUser = (Affittuario)LoginController.getInstance().getCurrentUser();

        ArrayList<Annuncio> prens = currentUser.getPrenotazioni();

        ArrayList<String> results = new ArrayList<>();

        if(prens == null || prens.isEmpty()) return new PrenotazioneBean(results);

        for (Annuncio ann : prens) {
            for (Prenotazione p : ann.getPrenotazioni()) {
                if(p.getPrenotante().equals(currentUser.getEmail())) {
                    results.add(ann.getTitolo());
                }
            }
        }

        return new PrenotazioneBean(results);

    }

    public PrenotazioneBean getPrenotazioneInfo(AnnuncioBean annBean){

        AnnuncioDao annDao = DaoFactory.getInstance().getAnnuncioDao();
        Affittuario currentUser = (Affittuario)LoginController.getInstance().getCurrentUser();

        Annuncio ann = annDao.load(annBean.getTitolo());

        ArrayList<Prenotazione> prens = (ArrayList<Prenotazione>)ann.getPrenotazioni();

        for(Prenotazione p : prens) {
            if(p.getPrenotante().equals(currentUser.getEmail())) return new PrenotazioneBean(p);
        }

        return null;

    }

    public void prenota(AnnuncioBean annBean, PrenotazioneBean prenBean){

        // Get Daos
        AnnuncioDao annDao = DaoFactory.getInstance().getAnnuncioDao();
        PrenotazioneDao prenDao = DaoFactory.getInstance().getPrenotazioneDao();

        //Load target annuncio
        Annuncio ann = annDao.load(annBean.getTitolo());

        //get the already present reservations
        ArrayList<Prenotazione> prens = (ArrayList<Prenotazione>) ann.getPrenotazioni();

        //create new reservation
        Prenotazione newPren = prenDao.create(LoginController.getInstance().getCurrentUser().getEmail());
        newPren.setStartDate(prenBean.getStartDate());
        newPren.setEndDate(prenBean.getEndDate());
        newPren.setNumOspiti(prenBean.getNumOspiti());

        prenDao.store(newPren);

        // add reservation to reservation list
        prens.add(newPren);

        ann.setPrenotazioni(prens);

        //update current user's reservations
        Affittuario aff = (Affittuario) LoginController.getInstance().getCurrentUser();
        ArrayList<Annuncio> currPrens = aff.getPrenotazioni();
        if (currPrens == null) currPrens = new ArrayList<>();
        currPrens.add(ann);
        aff.setPrenotazioni(currPrens);

        annDao.delete(ann.getTitolo());
        annDao.store(ann);
    }

}
