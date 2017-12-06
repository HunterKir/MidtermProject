package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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

private double rating;

@Transient 
private int ratingCount;

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

public double getRating() {
	return rating;
}

public void setRating(double rating) {
	this.rating = rating;
}

public int getId() {
	return id;
}

public int getRatingCount() {
	return ratingCount;
}

public void setRatingCount(int ratingCount) {
	this.ratingCount = ratingCount;
}

@Override
public String toString() {
	return "UserRating [id=" + id + ", user=" + user + ", community=" + community + ", rating=" + rating
			+ ", ratingCount=" + ratingCount + "]";
}








	

	
	
}
