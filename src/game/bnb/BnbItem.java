package game.bnb;

import core.math.IntVector2;

public class BnbItem {
    final BnbMap bnbmap;
    final IntVector2 coord;
    final BnbItemInfo item;

    BnbItem(IntVector2 coord, BnbItemInfo itemtype, BnbMap map) {
        this.bnbmap = map;
        this.coord = coord;
        this.item = itemtype;
        map.getNode(coord).item = this;
    }
}