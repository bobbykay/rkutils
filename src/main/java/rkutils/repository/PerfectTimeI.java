//: PerfectTimeI.java
// The PerfectTime remote interface
package rkutils.repository;
import java.rmi.*;

interface PerfectTimeI extends Remote {
  long getPerfectTime() throws RemoteException;
} ///:~ 
