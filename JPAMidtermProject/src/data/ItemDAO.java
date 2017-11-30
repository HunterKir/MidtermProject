package data;

import entities.Item;

public interface ItemDAO {
	public Item postItem(); 
	public Item getItem();
	public Item deleteItem(); 
	public Item updateItem(); 
}
