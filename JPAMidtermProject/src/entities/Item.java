package entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Item {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id; 
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name="user_id")
	private User user; 
	
	private String content; 
	
	@Column(name="post_time")
	private LocalDateTime postTime; 
	
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	private Double price; 
	
	@Size(min=5, max=100, message="Title must be 5 to 100 characters long.")
	@Pattern(regexp="^[a-zA-Z0-9 ]+$", message = "Title must not contain symbols.")
	private String title; 
	@ManyToOne
	@JoinColumn(name="community_id")
	private Community community; 
	
	@OneToMany(mappedBy="item")
	private List<Post> posts;
	
	private Boolean active;
	
	private boolean sold;
	
	
	public Item() {
		
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Double getPrice() {
		if (price == null) {
			price = 0.00;
		}
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Community getCommunity() {
		return community;
	}
	public void setCommunity(Community community) {
		this.community = community;
	}
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Boolean getActive() {
		return active;
	}


	public void setActive(Boolean active) {
		this.active = active;
	}


	public boolean isSold() {
		return sold;
	}


	public void setSold(boolean sold) {
		this.sold = sold;
	}


	@Override
	public String toString() {
		return "Item [id=" + id + ", user=" + user + ", content=" + content + ", postTime=" + postTime + ", category="
				+ category + ", price=" + price + ", title=" + title + ", community=" + community + "]";
	}
	
	
	
}
