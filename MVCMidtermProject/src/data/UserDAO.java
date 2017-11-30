package data;

import entities.User;

public interface UserDAO {
	public User getUser(int id);
	public User createUser(User user); 
	public User updateUser(int id); 
	public User deleteUser(int id); 
}
