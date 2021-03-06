package data;

import java.util.List;

import entities.Item;

public interface ItemDAO {
	public Item createItem(Item item); 
	public Item getItem(int id);
	public Item deleteItem(int id); 
	public Item updateItem(int id, Item item); 
	public List<Item> getItembyCatID(int id);
	public List<Item> getItembyPrice(double min, double max);
	public List<Item> getItembyDescription(String descrip);
	public List<Item> getItembyPastXdaysbyCommunity(int cid, int day);
	public Item changeActiveStatus (int id);
	public Item changeSoldStatus (int id);
	public List<Item> getAllInactiveItems();
}
