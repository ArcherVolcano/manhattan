package manhattan.domain;

import java.io.Serializable;

public interface GenericEntity extends Serializable {

	public abstract Long getId();
	public abstract void setId(Long id);
	
}
