package data;

import java.util.List;

import entities.Community;
import entities.Item;
import entities.User;

public interface ItemDAO {
	public Item createItem(Item item, User user, int id, int categoryId); 
	public Item getItem(int id);
	public Item deleteItem(int id); 
	public Item updateItem(int id, Item item); 
	public List<Item> getItembyCatID(int id);
	public List<Item> getItembyPrice(double min, double max);
	public List<Item> getAllItemsInAllCommunitiesByDescription(String descrip, User user);
	public Item changeActiveStatus (int id);
	public Item changeSoldStatus (int id);
	List<Item> getAllItemsInAllCommunitiesByUser(User user);
	List<Item> getAllItemsInAllCommunitiesByUserLimit10(User user);
}
