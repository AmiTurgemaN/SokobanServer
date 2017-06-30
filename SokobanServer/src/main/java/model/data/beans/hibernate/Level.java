package model.data.beans.hibernate;

import java.io.Serializable;

import javax.persistence.*;

@Entity(name="Levels")
public class Level implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	private String levelName;

	public Level() {
	}

	public Level(String levelName) {
		this.levelName = levelName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
	
}
