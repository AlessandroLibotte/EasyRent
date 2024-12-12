package control;

import bean.LoginBean;
import model.Affittuario;
import model.Locatore;
import model.User;
import persistence.DaoFactory;
import persistence.UserDao;

public class LoginController {

    private static LoginController instance;

    private User currentUser;

    private LoginController() {}

    public static LoginController getInstance() {
        if (instance == null) {
            instance = new LoginController();
        }
        return instance;
    }

    public int validate(LoginBean lb) {

        UserDao userDao = DaoFactory.getInstance().getUserDao();

        if(userDao.exists(lb.getEmail())) {

            User user = userDao.load(lb.getEmail());

            if (user != null && user.getPassword().equals(lb.getPassword())) {
                currentUser = user;
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

        UserDao userDao = DaoFactory.getInstance().getUserDao();

        if(!userDao.exists(lb.getEmail())){

            User user = userDao.create(lb.getEmail());

            if(lb.getRole() == 0){ // affittuario

                Affittuario aff = new Affittuario(user);

                aff.setNome(lb.getNome());
                aff.setCognome(lb.getCognome());
                aff.setPassword(lb.getPassword());
                aff.setTelefono(lb.getTelefono());

                userDao.store(aff);
                return true;
            } else if(lb.getRole() == 1) { // locatore

                Locatore loc = new Locatore(user);

                loc.setNome(lb.getNome());
                loc.setCognome(lb.getCognome());
                loc.setPassword(lb.getPassword());
                loc.setTelefono(lb.getTelefono());

                userDao.store(loc);
                return true;
            }

        }
        return false;
    }

}
