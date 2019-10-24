package wjx.skill.vo;


import wjx.skill.model.Catalog;

import javax.validation.Valid;
import java.io.Serializable;

public class CatalogVO implements Serializable {
 
	private static final long serialVersionUID = 1L;
	private String username;
	@Valid
	private Catalog catalog;
	
	public CatalogVO() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

}
