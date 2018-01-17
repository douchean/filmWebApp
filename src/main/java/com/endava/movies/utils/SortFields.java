package com.endava.movies.utils;

public class SortFields {

	public static int film(String field) {
		if (field == null)
			return 0;
		switch (field) {
		case " title":
			return 1;
		case "title":
			return 1;
		case "-title":
			return 2;
		case " year":
			return 3;
		case "year":
			return 4;
		case "-year":
			return 4;
		case " rating":
			return 5;
		case "rating":
			return 6;
		case "-rating":
			return 6;
		default:
			return 0;
		}
	}

	public static int human(String field) {
		if (field == null)
			return 0;
		switch (field) {
		case " name":
			return 1;
		case "name":
			return 1;
		case "-name":
			return 2;
		case " birth":
			return 3;
		case "birth":
			return 4;
		case "-birth":
			return 4;
		default:
			return 0;
		}
	}

}
