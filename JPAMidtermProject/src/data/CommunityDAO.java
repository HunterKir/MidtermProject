package data;

import entities.Community;

public interface CommunityDAO {
	public Community getCommunity();
	public Community createCommunity();
	public Community updateCommunity();
	public Community deleteCommunity();
}
