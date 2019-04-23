package DVD_rental;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server extends Implementation {
	public Server() {
	}

	public static void main(String args[]) {
		try {
			// Instantiating the implementation class
			Implementation obj = new Implementation();

			// Exporting the object of implementation class (
			Interface stub = (Interface) UnicastRemoteObject.exportObject(obj, 0);

			// Binding the remote object (stub) in the registry
			Registry registry = null;
			try {
				registry = LocateRegistry.createRegistry(1099);
				registry.list();
			} catch (RemoteException e) {
				registry = LocateRegistry.getRegistry(1099);
			}

			registry.bind("Interface", stub);
			System.err.println("Server is ready");

		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}