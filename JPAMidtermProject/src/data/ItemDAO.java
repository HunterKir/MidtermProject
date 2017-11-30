package data;

import entities.Item;

public interface ItemDAO {
	public Item postItem(Item item); 
	public Item getItem(int id);
	public Item deleteItem(int id); 
	public Item updateItem(int id); 
}
