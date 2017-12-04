package data;

import java.util.List;

import entities.Community;
import entities.Item;
import entities.User;

public interface CommunityDAO {
	public Community getCommunity(int id);
	public Community createCommunity(Community community);
	public Community deleteCommunity(int id);
	public Community updateCommunityName(int id, Community community);
	public List<Item> getItems(int id);
	List<User> getUsers(int id);
}
