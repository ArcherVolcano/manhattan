package manhattan.domain.impl;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

import manhattan.domain.AbstractEntity;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "ABOUT_ID"))
public class About extends AbstractEntity<About> {
	
	private static final long serialVersionUID = 2250897750127386144L;	
	private String turkishInfo;
	
	private String englishInfo;
	
	@Column(name = "ENGLISH_INFO", length=1500)
	public String getEnglishInfo() {
		return englishInfo;
	}
	
	@Column(name = "TURKISH_INFO", nullable = false, length=1500)
	public String getTurkishInfo() {
		return turkishInfo;
	}

	public void setEnglishInfo(String englishInfo) {
		this.englishInfo = englishInfo;
	}
	
	public void setTurkishInfo(String turkishInfo) {
		this.turkishInfo = turkishInfo;
	}
	
	@Override
	public int compareTo(About o) {
		// TODO Auto-generated method stub
		return 0;
	}


}
