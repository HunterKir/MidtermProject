package entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Size(min = 1, max = 45, message = "First name must contain 1 to 45 characters.")
	@Pattern(regexp="^[a-zA-Z]+$", message = "First name must not contain numbers or symbols.")
	@Column(name = "first_name")
	private String firstName;

	@Size(min = 1, max = 45, message = "Last name must contain 1 to 45 characters.")
	@Pattern(regexp="^[a-zA-Z]+$", message = "Last name must not contain numbers or symbols.")
	@Column(name = "last_name")
	private String lastName;

	@Size(min = 5, max = 45, message = "Username must contain 5 to 45 characters.")
	@Pattern(regexp="^[a-zA-Z0-9]+$", message = "Username must not contain symbols.")
	private String username;
	
	@Size(min = 5, max = 45, message = "Password must be 5 to 45 characters long.")
	@Pattern(regexp="^[a-zA-Z0-9]+$", message = "Password must not contain symbols.")
	private String password;

	private Boolean admin;

	@OneToMany(mappedBy="user",cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Item> itemsPosted;
	
	@OneToMany(mappedBy="owner",cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Community> ownedCommunities;

	@ManyToMany(	cascade = {CascadeType.PERSIST}, fetch=FetchType.EAGER)
	@JoinTable(name="user_community",
    joinColumns=@JoinColumn(name="user_id"),
    inverseJoinColumns=@JoinColumn(name="community_id"))
	private List<Community> communities;

	@OneToMany (mappedBy="user",cascade= {CascadeType.PERSIST,CascadeType.REMOVE})
	private List<Post> posts;
	
	@Transient
	private int totalItems;
	@Transient 
	private int itemsSold; 
	@Transient
	private double overallRating = 5.0;  
	@Transient
	private int ownedCommunitiesSize; 
	
	@Column(name="profile_views")
	private int profileViews;
	
	public int getProfileViews() {
		return profileViews;
	}
	@OneToMany(mappedBy = "user",cascade= {CascadeType.PERSIST,CascadeType.REMOVE})
	List<UserRating> ratings;
	
	public void setProfileViews(int profileViews) {
		this.profileViews = profileViews;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Item> getItemPosts() {
		return itemsPosted;
	}

	public void setItemPosts(List<Item> itemPosts) {
		this.itemsPosted = itemPosts;
	}



	public List<Item> getItemsPosted() {
		return itemsPosted;
	}

	public void setItemsPosted(List<Item> itemsPosted) {
		this.itemsPosted = itemsPosted;
	}

	public List<Community> getCommunities() {
		return communities;
	}

	public void setCommunities(List<Community> communities) {
		this.communities = communities;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}


	public List<Community> getOwnedCommunities() {
		return ownedCommunities;
	}

	public void setOwnedCommunities(List<Community> ownedCommunities) {
		this.ownedCommunities = ownedCommunities;
	}
	
	
	public List<UserRating> getRatings() {
		return ratings;
	}
	public double getOverallRating() {
		double total = 0; 
		double overallRating = 0; 
		
		for (UserRating r : ratings) {
			total += r.getRating(); 
		}
		overallRating = total / ratings.size(); 
		return overallRating;
	}
	public void setOverallRating(double overallRating) {
		this.overallRating = overallRating;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", password=" + password + ", admin=" + admin + ", itemsPosted=" + itemsPosted + ", ownedCommunities="
				+ ownedCommunities + ", communities=" + communities + ", posts=" + posts + ", profileViews="
				+ profileViews + "]";
	}

	public int getTotalItems() {
		int count = 0; 
		for (Item i : getItemsPosted()) {
			if(i.getActive()) {
				count++; 
			}
		}
		return count;
	}
	public int getOwnedCommunitiesSize() {
		this.ownedCommunitiesSize=0; 
		for (Community c : this.ownedCommunities) {
			this.ownedCommunitiesSize++;
		}
		return this.ownedCommunitiesSize; 
	}
	
	public int getItemsSold() {
		int count = 0; 
		for (Item i : getItemsPosted()) {
			if(i.isSold()) {
				count++; 
			}
		}
		return count;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
