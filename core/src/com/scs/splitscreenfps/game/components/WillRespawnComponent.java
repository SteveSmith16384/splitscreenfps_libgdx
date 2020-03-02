package com.scs.splitscreenfps.game.components;

public class WillRespawnComponent {

	public long respawn_time;
	
	public WillRespawnComponent() {
		this.respawn_time = System.currentTimeMillis() + 3000;
	}

}
