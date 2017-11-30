package entities;

import java.time.LocalDateTime;

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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id; 
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name="item_id")
	private Item item; 
	private String content; 
	@Column(name="post_time")
	private LocalDateTime postTime; 
}
