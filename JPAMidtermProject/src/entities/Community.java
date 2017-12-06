package entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "community")
public class Community {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Size(min=5, max=100, message="Name must be 5 to 100 characters long.")
	@Pattern(regexp="^[a-zA-Z0-9 ]+$", message = "Name must not contain symbols.")
	private String name;

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name="owner_id")
	private User owner;
	
	@OneToMany(mappedBy="community",cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Item> items;
	
	@ManyToMany(mappedBy="communities",cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private List<User>members;
	
	@Pattern(regexp="^[^\\[\\];\\:{\\}\\\\\\/_\\<\\>]+$", message="Description cannot contain the following characters: [ ] ; : { } / \\ _ > < ")
	private String description;
	
	@OneToMany(mappedBy="community",cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private List<UserRating> ratings;
	
	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public List<UserRating> getRatings() {
		return ratings;
	}

	@Override
	public String toString() {
		return "Community [id=" + id + ", name=" + name + ", owner=" + owner + ", description=" + description + "]";
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
		Community other = (Community) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
