package control;

import bean.AnnuncioBean;
import bean.PrenotazioneBean;
import model.Annuncio;
import model.Prenotazione;
import persistence.AnnuncioDao;
import persistence.DaoFactory;
import persistence.PrenotazioneDao;

import java.util.ArrayList;

public class PrenotazioneController {

    private static PrenotazioneController instance;

    private PrenotazioneController() {}

    public static PrenotazioneController getInstance() {
        if(instance == null) {
            instance = new PrenotazioneController();
        }
        return instance;
    }

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

        return new PrenotazioneBean(results);
    }

    public void Prenota(AnnuncioBean annBean, PrenotazioneBean prenBean){

        AnnuncioDao annDao = DaoFactory.getInstance().getAnnuncioDao();
        PrenotazioneDao prenDao = DaoFactory.getInstance().getPrenotazioneDao();

        Annuncio ann = annDao.load(annBean.getTitolo());

        ArrayList<Prenotazione> prens = (ArrayList<Prenotazione>) ann.getPrenotazioni();

        Prenotazione newPren = prenDao.create(LoginController.getInstance().getCurrentUser().getEmail());
        newPren.setStartDate(prenBean.getStartDate());
        newPren.setEndDate(prenBean.getEndDate());
        newPren.setNumOspiti(prenBean.getNumOspiti());

        prenDao.store(newPren);

        prens.add(newPren);

        ann.setPrenotazioni(prens);

        annDao.delete(ann.getTitolo());
        annDao.store(ann);
    }

}
