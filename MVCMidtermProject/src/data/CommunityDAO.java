package data;

import entities.Community;

public interface CommunityDAO {
	public Community getCommunity(int id);
	public Community createCommunity(Community community);
	public Community updateCommunity(int id);
	public Community deleteCommunity(int id);
}
