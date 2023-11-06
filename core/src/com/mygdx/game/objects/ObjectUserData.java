package com.mygdx.game.objects;

import com.mygdx.game.helper.ContactType;

public class ObjectUserData {
    private ContactType type;

    public ObjectUserData(ContactType type) {
        this.type = type;
    }

    public ContactType getType() {
        return type;
    }
}
