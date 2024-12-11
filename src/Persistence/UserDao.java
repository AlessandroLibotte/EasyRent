package Persistence;

import Model.User;

public interface UserDao extends Dao<String, User>{

    User create(String email);

}
