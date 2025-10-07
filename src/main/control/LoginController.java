package main.control;

import main.bean.LoginBean;
import main.bean.RegistrationBean;
import main.model.*;
import main.persistence.DaoFactory;
import main.persistence.UserDao;

import java.util.ArrayList;

public class LoginController {

    UserDao userDao = DaoFactory.getInstance().getUserDao();

    public Role validate(LoginBean lb) {
        if(!userDao.exists(lb.getEmail())) return Role.INVALID;

        User user = userDao.load(lb.getEmail());
        if(user == null || !user.checkPassword(lb.getPassword())) return Role.INVALID;

        if(user instanceof Affittuario) return Role.AFFITTUARIO;
        else return Role.LOCATORE;
    }

    public RegisterResult register(RegistrationBean rb) {

        if(!rb.isValidPwd()) return RegisterResult.INVALID;

        if(userDao.exists(rb.getEmail())) return RegisterResult.EXISTS;

        User user = userDao.create(rb.getEmail());

        if(rb.getRole() == Role.AFFITTUARIO){

            Affittuario aff = new Affittuario(user);

            aff.setNome(rb.getNome());
            aff.setCognome(rb.getCognome());
            aff.setPassword(rb.getPassword());
            aff.setTelefono(rb.getTelefono());
            aff.setPrenotazioni(new ArrayList<>());

            userDao.store(aff);
            return RegisterResult.SUCCESS;

        } else if(rb.getRole() == Role.LOCATORE) {

            Locatore loc = new Locatore(user);

            loc.setNome(rb.getNome());
            loc.setCognome(rb.getCognome());
            loc.setPassword(rb.getPassword());
            loc.setTelefono(rb.getTelefono());
            loc.setAnnunci(new ArrayList<>());

            userDao.store(loc);
            return RegisterResult.SUCCESS;

        } else return RegisterResult.INVALID;

    }

}
