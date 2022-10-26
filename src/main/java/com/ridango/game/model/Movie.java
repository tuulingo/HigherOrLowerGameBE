package com.ridango.game.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;

@Entity
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger ID;
    private String ORIGINAL_TITLE;
    private String OVERVIEW;
    private BigDecimal POPULARITY;
    private Date RELEASE_DATE;
    private BigInteger REVENUE;
    private BigDecimal RUNTIME;
    private String TAGLINE;
    private String TITLE;
    private BigDecimal VOTE_AVERAGE;
    private Integer VOTE_COUNT;

    public Movie() {}

    public BigInteger getID() {
        return ID;
    }

    public void setID(BigInteger ID) {
        this.ID = ID;
    }

    public String getORIGINAL_TITLE() {
        return ORIGINAL_TITLE;
    }

    public void setORIGINAL_TITLE(String ORIGINAL_TITLE) {
        this.ORIGINAL_TITLE = ORIGINAL_TITLE;
    }

    public String getOVERVIEW() {
        return OVERVIEW;
    }

    public void setOVERVIEW(String OVERVIEW) {
        this.OVERVIEW = OVERVIEW;
    }

    public BigDecimal getPOPULARITY() {
        return POPULARITY;
    }

    public void setPOPULARITY(BigDecimal POPULARITY) {
        this.POPULARITY = POPULARITY;
    }

    public Date getRELEASE_DATE() {
        return RELEASE_DATE;
    }

    public void setRELEASE_DATE(Date RELEASE_DATE) {
        this.RELEASE_DATE = RELEASE_DATE;
    }

    public BigInteger getREVENUE() {
        return REVENUE;
    }

    public void setREVENUE(BigInteger REVENUE) {
        this.REVENUE = REVENUE;
    }

    public BigDecimal getRUNTIME() {
        return RUNTIME;
    }

    public void setRUNTIME(BigDecimal RUNTIME) {
        this.RUNTIME = RUNTIME;
    }

    public String getTAGLINE() {
        return TAGLINE;
    }

    public void setTAGLINE(String TAGLINE) {
        this.TAGLINE = TAGLINE;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public BigDecimal getVOTE_AVERAGE() {
        return VOTE_AVERAGE;
    }

    public void setVOTE_AVERAGE(BigDecimal VOTE_AVERAGE) {
        this.VOTE_AVERAGE = VOTE_AVERAGE;
    }

    public Integer getVOTE_COUNT() {
        return VOTE_COUNT;
    }

    public void setVOTE_COUNT(Integer VOTE_COUNT) {
        this.VOTE_COUNT = VOTE_COUNT;
    }

    public Movie(String original_title, String overview, BigDecimal popularity, Date release_date, BigInteger revenue, BigDecimal runtime, String tagline, String title, BigDecimal vote_average, Integer vote_count) {
        ORIGINAL_TITLE = original_title;
        OVERVIEW = overview;
        POPULARITY = popularity;
        RELEASE_DATE = release_date;
        REVENUE = revenue;
        RUNTIME = runtime;
        TAGLINE = tagline;
        TITLE = title;
        VOTE_AVERAGE = vote_average;
        VOTE_COUNT = vote_count;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "ID=" + ID +
                ", ORIGINAL_TITLE='" + ORIGINAL_TITLE + '\'' +
                ", OVERVIEW='" + OVERVIEW + '\'' +
                ", POPULARITY=" + POPULARITY +
                ", RELEASE_DATE=" + RELEASE_DATE +
                ", REVENUE=" + REVENUE +
                ", RUNTIME=" + RUNTIME +
                ", TAGLINE='" + TAGLINE + '\'' +
                ", TITLE='" + TITLE + '\'' +
                ", VOTE_AVERAGE=" + VOTE_AVERAGE +
                ", VOTE_COUNT=" + VOTE_COUNT +
                '}';
    }
}
