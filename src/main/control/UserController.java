package main.control;

import main.bean.LoginBean;
import main.bean.RegistrationBean;
import main.control.exceptions.InputException;
import main.control.exceptions.UserDoesNotExistException;
import main.model.Affittuario;
import main.model.Locatore;
import main.model.Role;
import main.model.User;
import main.persistence.DaoFactory;
import main.persistence.UserDao;

public class UserController {

    UserDao userDao = DaoFactory.getInstance().getUserDao();

    public RegistrationBean getUserInfo (LoginBean lb) {

        if (!userDao.exists(lb.getEmail())) throw new UserDoesNotExistException(lb.getEmail());

        User user = userDao.load(lb.getEmail());

        Role role;

        if (user instanceof Affittuario) role = Role.AFFITTUARIO;
        else if (user instanceof Locatore) role = Role.LOCATORE;
        else role = Role.INVALID;

        return new RegistrationBean(user.getNome(), user.getCognome(), user.getEmail(), "", user.getTelefono(), role);

    }

    public void editUserInfo(RegistrationBean rb) {

        if(!rb.isValid())  throw new InputException();
        if(!userDao.exists(rb.getEmail())) throw new UserDoesNotExistException(rb.getEmail());

        User user = userDao.load(rb.getEmail());

        user.setNome(rb.getNome());
        user.setCognome(rb.getCognome());
        user.setTelefono(rb.getTelefono());

        userDao.store(user);

    }

    public Role assertUser(LoginBean lb) {

        if (!userDao.exists(lb.getEmail())) return Role.INVALID;

        User user = userDao.load(lb.getEmail());

        if (user instanceof Affittuario) return Role.AFFITTUARIO;
        else if (user instanceof Locatore) return Role.LOCATORE;
        else return Role.INVALID;

    }

}
