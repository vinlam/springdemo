package com.event;

import com.entity.User;

public class FaceEvent extends BaseEvent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FaceEvent(User user) {
        super(user);
    }

    public FaceEvent(Object source, User user){
        super(source,user);
    }
}
