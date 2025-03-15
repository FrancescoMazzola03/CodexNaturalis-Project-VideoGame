package it.polimi.ingsw.EventHandling;

import java.io.Serializable;

/**
 * Enum that represents the type of event.
 */
public enum EventType implements Serializable {


        DRAW,
        CHOOSEOBJ,
        CREATEGAME,
        USERNAME,
        FLIP,
        PLACE,
        WAIT,
        CHAT,
        REFRESH,
        INVALIDUSER,
        NOJOIN,
        INVALIDPOS,
        NOCREATE,
        STARTERCARD,
        GETCREATORS,
        WANTOJOIN,
        REGISTER,
        CHOOSEPAWN,
        NOPAWN,
        GAMEENDED,
        QUITEVENT,
        TERMINATEEVENT,
        PING,
        PONG
    }


