package entities;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ItemForm {
	@Pattern(regexp="^[^\\[\\];\\:{\\}\\\\\\/_\\<\\>]+$", message="Description cannot contain the following characters: [ ] ; : { } / \\ _ > < ")
	private String content; 
	
	@Size(min=5, max=100, message="Title must be 5 to 100 characters long.")
	@Pattern(regexp="^[a-zA-Z0-9 ]+$", message = "Title must not contain symbols.")
	private String title; 
	
	private Double price;
	
	private int cid;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	@Override
	public String toString() {
		return "ItemForm [content=" + content + ", title=" + title + ", price=" + price + "]";
	} 
	
	
}
