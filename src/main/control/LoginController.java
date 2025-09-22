package main.control;

import main.bean.LoginBean;
import main.model.Affittuario;
import main.model.Locatore;
import main.model.User;
import main.persistence.DaoFactory;
import main.persistence.UserDao;

import java.util.ArrayList;

public class LoginController {

    UserDao userDao = DaoFactory.getInstance().getUserDao();

    public int validate(LoginBean lb) {

        if(userDao.exists(lb.getEmail())) {

            User user = userDao.load(lb.getEmail());

            if (user != null && user.getPassword().equals(lb.getPassword())) {
                if(user instanceof Affittuario) {
                    return 1;
                } else if (user instanceof Locatore) {
                    return 2;
                }
            }

        }

        return 0;
    }

    public boolean register(LoginBean lb) {

        if(!userDao.exists(lb.getEmail())){

            User user = userDao.create(lb.getEmail());

            if(lb.getRole() == 0){ // affittuario

                Affittuario aff = new Affittuario(user);

                aff.setNome(lb.getNome());
                aff.setCognome(lb.getCognome());
                aff.setPassword(lb.getPassword());
                aff.setTelefono(lb.getTelefono());
                aff.setPrenotazioni(new ArrayList<>());

                userDao.store(aff);
                return true;
            } else if(lb.getRole() == 1) { // locatore

                Locatore loc = new Locatore(user);

                loc.setNome(lb.getNome());
                loc.setCognome(lb.getCognome());
                loc.setPassword(lb.getPassword());
                loc.setTelefono(lb.getTelefono());
                loc.setAnnunci(new ArrayList<>());

                userDao.store(loc);
                return true;
            }

        }
        return false;
    }

}
