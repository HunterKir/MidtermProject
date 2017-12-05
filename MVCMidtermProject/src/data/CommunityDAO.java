package data;

import java.util.List;

import entities.Community;
import entities.Item;
import entities.User;

public interface CommunityDAO {
	public Community getCommunity(int id);
	public Community createCommunity(Community community, User owner);
	public Community deleteCommunity(int id);
	public Community updateCommunityName(int id, Community community);
	public List<Item> getItems(int id);
	public List<User> getUsers(int id);
	public List<Community> getAllCommunities();
	public List<Item> getItembyDescription(String descrip, User user, int groupId);
	List<User> getUserbyFirstOrLastName(String first, String last, User user, int groupId);

}
