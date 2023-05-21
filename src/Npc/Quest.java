package Npc;

import GameScene.Item;

import java.util.ArrayList;

public class Quest {

    private String text;
    private Item requiredItem = null;

    public Quest(String _text, Object _item) {
        this.text = _text;

        if(_item != null && _item.getClass() == Item.class) {
            this.requiredItem = (Item) _item;
        }
    }
}
