package data;

import java.util.List;

import entities.Community;

public interface CommunityDAO {
	public Community getCommunity(int id);
	public Community createCommunity(Community community);
	public Community updateCommunity(int id, Community community);
	public Community transferCommunityOwner(int uid, int cid);
	public Community deleteCommunity(int id);
	public List<Community> getCommunitybyDesc(String desc);
	public List<Community> getAllCommunities();
}
