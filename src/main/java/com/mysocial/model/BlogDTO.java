package com.mysocial.model;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * DTO class for transfering User data over json
 *
 */
public class BlogDTO implements Serializable{
	
	private String id;
	private String title;
	private String tags;
	private String content;
	
	public Blog toModel(){
    	Blog b = new Blog();
    	b.setTitle(title);
    	b.setTag(tags);
    	b.setContent(content);
    	return b;
    }
	
	@Override
	public String toString() {
		return "BlogDTO[title="+ title + ", tag= " + tags + ", Content= " + content +"]";
	}
	
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}
	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

}
