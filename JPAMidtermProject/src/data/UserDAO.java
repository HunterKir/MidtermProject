package data;

import java.util.List;

import entities.Post;
import entities.User;

public interface UserDAO {
	public User getUser(int id);
	public User createUser(User user); 
	public User updateUser(int id, User user); 
	public User deleteUser(int id);
	public List<Post> getPostHistorybyUid(int uid); 
}
