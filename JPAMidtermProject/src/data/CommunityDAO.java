package data;

import entities.Community;

public interface CommunityDAO {
	public Community getCommunitybyId( int id);
	public Community createCommunity();
	public Community updateCommunity();
	public Community deleteCommunity();
}
