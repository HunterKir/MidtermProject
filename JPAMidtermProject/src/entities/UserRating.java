package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_rating")	
public class UserRating {

@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private int id;

@ManyToOne
@JoinColumn(name="user_id")
private User user;

@ManyToOne
@JoinColumn(name="community_id")
private Community community;

private int rating;

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public Community getCommunity() {
	return community;
}

public void setCommunity(Community community) {
	this.community = community;
}

public int getRating() {
	return rating;
}

public void setRating(int rating) {
	this.rating = rating;
}

public int getId() {
	return id;
}

@Override
public String toString() {
	return "UserRating [id=" + id + ", user=" + user + ", community=" + community + ", rating=" + rating + "]";
}





	

	
	
}
