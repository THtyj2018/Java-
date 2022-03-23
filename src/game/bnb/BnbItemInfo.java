package game.bnb;

import java.util.HashMap;

import core.graphics.Drawable;
import core.math.Mathf;

public final class BnbItemInfo {
    String name;
    Drawable sprite;

    static final HashMap<String, BnbItemInfo> items = new HashMap<>();

    public static BnbItemInfo regisetrItemType(String name, Drawable sprite) {
        if (name == null || sprite == null) {
            return null;
        }
        BnbItemInfo item = new BnbItemInfo();
        item.name = name;
        item.sprite = sprite;
        if (items.putIfAbsent(name, item) == null) {
            return item;
        } else {
            return null;
        }
    }

    public static BnbItemInfo getItemType(String name) {
        return items.get(name);
    }

    public static BnbItemInfo getRandomItemType() {
        return ((BnbItemInfo) items.values().toArray()[Mathf.randInt() % items.size()]).getCopy();
    }

    BnbItemInfo getCopy() {
        BnbItemInfo item = new BnbItemInfo();
        item.name = name;
        item.sprite = sprite.getCopy();
        return item;
    }

    public String getName() {
        return name;
    }

    public Drawable getSprite() {
        return sprite;
    }
}