package data;

import java.util.List;

import entities.UserRating;

public interface RatingDAO {
	public UserRating getRating(int id);
	public UserRating getRatingbyCidAndUid(int cid,int uid);
	public UserRating createRating(UserRating rating, int cid, int uid);	 
	public UserRating updateRating(int id, int score); 
	public UserRating deleteRating(int id);
	public List<UserRating> getallRatingsbyCommunity(int cid);
	
}
