package entities;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne (cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne (cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name = "item_id")
	private Item item;
	
	private String content;
	
	@Column(name = "post_time")
	private LocalDateTime postTime;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getPostTime() {
		return postTime;
	}

	public void setPostTime(LocalDateTime postTime) {
		this.postTime = postTime;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", user=" + user + ", item=" + item + ", content=" + content + ", postTime="
				+ postTime + "]";
	}

	
}
