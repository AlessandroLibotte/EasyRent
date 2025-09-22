package main.persistence.memory;

import main.model.User;
import main.persistence.UserDao;

public class InMemoryUserDao extends InMemoryDao<String, User> implements UserDao {

    private static InMemoryUserDao instance;

    public static InMemoryUserDao getInstance(){
        if(instance == null){
            instance = new InMemoryUserDao();
        }
        return instance;
    }

    public String getKey(User user) {
        return user.getEmail();
    }


}
