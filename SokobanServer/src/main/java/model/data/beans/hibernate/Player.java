package model.data.beans.hibernate;

import java.io.Serializable;

import javax.persistence.*;

@Entity(name="Players")
public class Player implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	private String playerName;

	public Player() {
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Player(String playerName) {
		this.playerName = playerName;
	}
}
