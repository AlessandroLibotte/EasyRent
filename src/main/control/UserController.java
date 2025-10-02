package main.control;

import main.bean.LoginBean;
import main.model.Affittuario;
import main.model.Locatore;
import main.model.User;
import main.persistence.DaoFactory;
import main.persistence.UserDao;

public class UserController {

    UserDao userDao = DaoFactory.getInstance().getUserDao();

    public LoginBean getUserInfo (LoginBean lb) {

        if(userDao.exists(lb.getEmail())) {

            User user = userDao.load(lb.getEmail());

            int role = 0;

            if(user instanceof Affittuario) {
                role = 1;
            } else if (user instanceof Locatore) {
                role = 2;
            }

            return new LoginBean(user.getNome(), user.getCognome(), user.getEmail(), "", user.getTelefono(), role);

        }

        return new LoginBean("", "");

    }

    public void editUserInfo(LoginBean lb) {

        if(userDao.exists(lb.getEmail())) {

            User user = userDao.load(lb.getEmail());

            userDao.delete(lb.getEmail());

            user.setNome(lb.getNome());
            user.setCognome(lb.getCognome());
            user.setTelefono(lb.getTelefono());

            userDao.store(user);

        }

    }

    public int assertUser(LoginBean lb) {

        if(userDao.exists(lb.getEmail())) {

            User user = userDao.load(lb.getEmail());

            if (user instanceof Affittuario)
                return 1;
            else if (user instanceof Locatore)
                return 2;

        }

        return 0;

    }

}
