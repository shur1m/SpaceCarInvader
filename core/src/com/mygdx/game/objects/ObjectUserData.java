package com.mygdx.game.objects;

import com.mygdx.game.helper.ContactType;

public class ObjectUserData {
    ContactType type;

    public ObjectUserData(ContactType type) {
        this.type = type;
    }

    public ContactType getType() {
        return type;
    }
}
