package com.mygdx.game.objects;

import com.mygdx.game.helper.ContactType;

/**
 * Holds the user data of an in-game object. Information held in an
 * ObjectUserData is accessible to the GameContactListener, and can be used
 * to determine what to do upon collision between two objects.
 */
public class ObjectUserData {
    private ContactType type;

    /**
     * The constructor of ObjectUserData.
     * @param type The ContactType of this user data.
     */
    public ObjectUserData(ContactType type) {
        this.type = type;
    }

    /**
     * Returns the ContactType of this user data.
     * @return The ContactType of this user data.
     */
    public ContactType getType() {
        return type;
    }
}
