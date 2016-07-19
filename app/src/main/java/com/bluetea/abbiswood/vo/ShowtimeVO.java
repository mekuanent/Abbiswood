package com.bluetea.abbiswood.vo;

import java.util.List;

/**
 * Created by MEK on 6/26/2016.
 */
public class ShowtimeVO {

    public String cinema;
    public List<ST> showtimes;

    public static class ST{

        public String time, movie;

    }
}
