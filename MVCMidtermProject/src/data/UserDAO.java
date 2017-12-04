package data;

import java.util.List;

import entities.User;

public interface UserDAO {
	public User getUser(int id);
	public User createUser(User user); 
	public User updateUser(int id, User user); 
	public User deleteUser(int id); 
	public User getUserByUserName(String username); 
	public User getLoadedUser(String username);
	int getUserTotalPost(User user);
	List<User> getUserbyFirstOrLastName(String first, String last, User user); 
}
