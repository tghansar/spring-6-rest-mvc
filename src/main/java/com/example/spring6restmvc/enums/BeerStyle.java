package com.example.spring6restmvc.enums;

/**
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2023/09/21, Thu, 10:01
 **/

public enum BeerStyle {
    LAGER(1),
    PILSNER(2),
    STOUT(3),
    GOSE(4),
    PORTER(5),
    ALE(6),
    WHEAT(7),
    IPA(8),
    PALE_ALE(9),
    SAISON(10);

    private final int key;

    BeerStyle(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
