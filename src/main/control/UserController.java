package main.control;

import main.bean.LoginBean;
import main.bean.RegistrationBean;
import main.model.Affittuario;
import main.model.Locatore;
import main.model.Role;
import main.model.User;
import main.persistence.DaoFactory;
import main.persistence.UserDao;

public class UserController {

    UserDao userDao = DaoFactory.getInstance().getUserDao();

    public RegistrationBean getUserInfo (LoginBean lb) {

        if(!userDao.exists(lb.getEmail())) return null;

        User user = userDao.load(lb.getEmail());

        Role role;

        if (user instanceof Affittuario) role = Role.AFFITTUARIO;
        else if (user instanceof Locatore) role = Role.LOCATORE;
        else role = Role.INVALID;

        return new RegistrationBean(user.getNome(), user.getCognome(), user.getEmail(), "", user.getTelefono(), role);

    }

    public boolean editUserInfo(RegistrationBean rb) {

        if(!userDao.exists(rb.getEmail()) || !rb.isValid()) return false;

        User user = userDao.load(rb.getEmail());

        userDao.delete(rb.getEmail());

        user.setNome(rb.getNome());
        user.setCognome(rb.getCognome());
        user.setTelefono(rb.getTelefono());

        userDao.store(user);

        return true;
    }

    public Role assertUser(LoginBean lb) {

        if(!userDao.exists(lb.getEmail())) return Role.INVALID;

        User user = userDao.load(lb.getEmail());

        if (user instanceof Affittuario) return Role.AFFITTUARIO;
        else if (user instanceof Locatore) return Role.LOCATORE;
        else return Role.INVALID;

    }

}
