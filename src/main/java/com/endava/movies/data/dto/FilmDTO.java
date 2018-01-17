package com.endava.movies.data.dto;

import java.text.DecimalFormat;

public class FilmDTO {

	private static DecimalFormat df2 = new DecimalFormat(".##");

	private int idFilm;
	private String title;
	private int year;
	private float rating;

	public FilmDTO(String title, int year, float rating) {
		this.title = title;
		this.year = year;
		this.rating = Float.parseFloat(df2.format(rating));
	}

	@Override
	public String toString() {
		return " Movie [title=" + title + "]";
	}

	public FilmDTO() {
	}

	public FilmDTO(int idFilm, String title, int year, float rating) {
		this.idFilm = idFilm;
		this.title = title;
		this.year = year;
		this.rating = Float.parseFloat(df2.format(rating));
	}

	public void setRating(float rating) {
		this.rating = Float.parseFloat(df2.format(rating));
	}

	public int getIdFilm() {
		return idFilm;
	}

	public String getTitle() {
		return title;
	}

	public int getYear() {
		return year;
	}

	public float getRating() {
		return rating;
	}

	public void setIdFilm(int idFilm) {
		this.idFilm = idFilm;
	}

	public void setTitle(String tit) {
		title = tit;
	}

	public void setYear(int y) {
		year = y;
	}

}
