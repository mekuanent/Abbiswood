package com.bluetea.abbiswood.vo;

/**
 * Created by MEK on 6/27/2016.
 */
public class MenuItemVO {

    public static int DIRECTION_LEFT = 1;
    public static int DIRECTION_RIGHT = 2;

    public static int PAGE_HOME = 3;
    public static int PAGE_VOTED = 4;
    public static int PAGE_NON_VOTED = 5;

    public int direction;
    public String name;
    public Long id;
    public int page,size;


    public MenuItemVO(int direction, int page,Long id) {
        this.direction = direction;
        this.page = page;
        this.id = id;
    }

    public MenuItemVO(int direction, int page,Long id, int size) {
        this.direction = direction;
        this.page = page;
        this.id = id;
        this.size = size;
    }

    public MenuItemVO(int direction, String name) {
        this.direction = direction;
        this.name = name;
    }
    public MenuItemVO(int direction, String name, int size) {
        this.direction = direction;
        this.name = name;
        this.size = size;
    }
}
