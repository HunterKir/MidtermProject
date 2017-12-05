package data;

import entities.Post;

public interface PostDAO {
	public Post getPost(int id);
	public Post createPost(Post post, int iid, int uid);	 
	public Post updatePost(int id, Post post); 
	public Post deletePost(int id);
	
	
}
