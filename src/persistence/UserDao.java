package persistence;

import model.User;

public interface UserDao extends Dao<String, User>{

    User create(String email);

}
