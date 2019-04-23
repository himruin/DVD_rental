package DVD_rental;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

// Implementing the remote interface 
public class Implementation implements Interface {

	// *------------------
	// creation of collections of data

	// input data
	List<String> film_name = new ArrayList<String>();
	List<String> film_count = new ArrayList<String>();
	List<String> film_format = new ArrayList<String>();

	// updated count when film is rented
	List<String> newFilm_count = new ArrayList<String>();

	// user's films data
	List<String> myFilm_format = new ArrayList<String>();
	List<String> myFilm_name = new ArrayList<String>();
	List<String> myFilm_time = new ArrayList<String>();

	// data after change
	List<String> combinedData = new ArrayList<String>();
	List<String> myData = new ArrayList<String>();

	// Implementation of the Interface methods

	public void readFilms() {

		film_name.clear();
		film_count.clear();
		film_format.clear();
		File file = new File("data/film_list.txt");
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] split_data = line.split(";");

				film_name.add(split_data[0]);
				film_count.add(split_data[1]);
				film_format.add(split_data[2]);

			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void readMyFilms() {
		File file = new File("data/my_film_list.txt");
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] split_data = line.split(";");

				myFilm_name.add(split_data[0]);
				myFilm_time.add(split_data[1]);
				myFilm_format.add(split_data[2]);

			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void combinedMyData() {
		myData.clear();
		for (int i = 0; i < myFilm_name.size(); i++) {
			myData.add(myFilm_name.get(i));
			myData.add(myFilm_time.get(i));
			myData.add(myFilm_format.get(i));
		}

	}

	public void combinedNewData() {
		combinedData.clear();
		for (int i = 0; i < film_name.size(); i++) {
			combinedData.add(film_name.get(i));
			if (newFilm_count.size() < 1) {
				combinedData.add(film_count.get(i));
			} else {
				combinedData.add(newFilm_count.get(i));
			}
			combinedData.add(film_format.get(i));
		}

	}

	public void writeToFilms() throws IOException {
		FileWriter writer = null;
		try {
			writer = new FileWriter("data/film_list.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		int counter = 0;

		for (String str : combinedData) {

			counter++;
			writer.write(str);
			if ((counter % 3 == 0)) {
				writer.write("\r\n");
			} else
				writer.write(";");
		}
		writer.close();
	}

	public void writeToMyFilms() throws IOException {
		FileWriter writer = null;
		try {
			writer = new FileWriter("data/my_film_list.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		int counter = 0;
		for (String str : myData_Value()) {
			counter++;
			writer.write(str);
			if ((counter % 3 == 0))
				writer.write("\r\n");
			else
				writer.write(";");
		}
		writer.close();
	}

	// *---------

	// single operations

	public List<String> filmName_Value() {
		return film_name;
	}

	public List<String> newFilmCount_Value() {
		return newFilm_count;
	}

	public List<String> filmCount_Value() {
		return film_count;
	}

	public List<String> filmFormat_Value() {
		return film_format;
	}

	public List<String> myFilmFormat_Value() {
		return myFilm_format;
	}

	public List<String> myFilmName_Value() {
		return myFilm_name;
	}

	public List<String> myFilmTime_Value() {
		return myFilm_time;
	}

	public List<String> combinedData_Value() {
		return combinedData;
	}

	public List<String> myData_Value() {
		return myData;
	}

	// *-----------
	// clears

	public List<String> filmName_Clear() {
		film_name.clear();
		return film_name;
	}

	public List<String> newFilmCount_Clear() {
		newFilm_count.clear();
		return newFilm_count;
	}

	public List<String> filmCount_Clear() {
		film_count.clear();
		return film_count;
	}

	public List<String> filmFormat_Clear() {
		film_format.clear();
		return film_format;
	}

	public List<String> myFilmFormat_Clear() {
		myFilm_format.clear();
		return myFilm_format;
	}

	public List<String> myFilmName_Clear() {
		myFilm_name.clear();
		return myFilm_name;
	}

	public List<String> myFilmTime_Clear() {
		myFilm_time.clear();
		return myFilm_time;
	}

	public List<String> combinedData_Clear() {
		combinedData.clear();
		return combinedData;
	}

	public List<String> myData_Clear() {
		film_name.clear();
		return myData;
	}

	// *--------------
	// adds

	public List<String> newFilmCount_Add(String s) {
		newFilm_count.add(s);
		return newFilm_count;
	}

	public List<String> newFilmCount_AddLess(String s) {
		newFilm_count.add(String.valueOf(Integer.valueOf(s) - 1));
		return newFilm_count;
	}

	public List<String> myFilmName_Add(String s) {
		myFilm_name.add(s);
		return myFilm_name;
	}

	public List<String> myFilmFormat_Add(String s) {
		myFilm_format.add(s);
		return myFilm_format;
	}

	public List<String> myFilmTime_Add(String s) {
		myFilm_time.add(s);
		return myFilm_time;
	}

}
