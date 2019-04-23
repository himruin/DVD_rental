package DVD_rental;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Interface extends Remote {

	// operations on data

	public void readFilms() throws RemoteException, IOException;

	public void readMyFilms() throws RemoteException, IOException;

	public void combinedNewData() throws RemoteException;

	public void combinedMyData() throws RemoteException;

	public void writeToFilms() throws RemoteException, IOException;

	public void writeToMyFilms() throws RemoteException, IOException;

	
	// single operations
	public List<String> filmName_Value() throws RemoteException;

	public List<String> newFilmCount_Value() throws RemoteException;

	public List<String> filmCount_Value() throws RemoteException;

	public List<String> filmFormat_Value() throws RemoteException;

	public List<String> myFilmFormat_Value() throws RemoteException;

	public List<String> myFilmName_Value() throws RemoteException;

	public List<String> myFilmTime_Value() throws RemoteException;

	public List<String> combinedData_Value() throws RemoteException;

	public List<String> myData_Value() throws RemoteException;

	public List<String> filmName_Clear() throws RemoteException;

	public List<String> newFilmCount_Clear() throws RemoteException;

	public List<String> filmCount_Clear() throws RemoteException;

	public List<String> filmFormat_Clear() throws RemoteException;

	public List<String> myFilmFormat_Clear() throws RemoteException;

	public List<String> myFilmName_Clear() throws RemoteException;

	public List<String> myFilmTime_Clear() throws RemoteException;

	public List<String> combinedData_Clear() throws RemoteException;

	public List<String> myData_Clear() throws RemoteException;

	public List<String> newFilmCount_Add(String s) throws RemoteException;

	public List<String> newFilmCount_AddLess(String s) throws RemoteException;

	public List<String> myFilmName_Add(String s) throws RemoteException;

	public List<String> myFilmFormat_Add(String s) throws RemoteException;

	public List<String> myFilmTime_Add(String s) throws RemoteException;
}