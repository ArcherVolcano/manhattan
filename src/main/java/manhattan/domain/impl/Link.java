package manhattan.domain.impl;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import manhattan.domain.AbstractEntity;

@Entity
@Table(name = "LINK")
@AttributeOverride(name = "id", column = @Column(name = "LINK_ID"))
public class Link extends AbstractEntity<Link> {
	
	private static final long serialVersionUID = 3378838374814146242L;
	
	private String link;
	private String name;
	private Boolean isGalleryItem;
	private Boolean isVideoItem;
	
	public Link() {
		this.isGalleryItem = false;
		this.isVideoItem = false;
	}
	
	@Column(name = "LINK", nullable = false)
	public String getLink() {
		return link;
	}
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	
	@Column(name = "IS_GALLERY_ITEM", nullable= false)
	public Boolean getIsGalleryItem() {
		return isGalleryItem;
	}
	
	@Column(name = "IS_VIDEO_ITEM", nullable = false)
	public Boolean getIsVideoItem() {
		return isVideoItem;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setIsVideoItem(Boolean isVideoItem) {
		this.isVideoItem = isVideoItem;
	}

	public void setIsGalleryItem(Boolean isGalleryItem) {
		this.isGalleryItem = isGalleryItem;
	}
	
	@Override
	public String toString() {
		return "Link [link=" + link + ", name=" + name + ", isGalleryItem=" + isGalleryItem + ", isVideoItem="
				+ isVideoItem + "]";
	}

	@Override
	public int compareTo(Link o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
