/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.game;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class BtCommunication {

    private StreamConnectionNotifier scn=null;
    private DiscoveryAgent mDiscoveryAgent=null;
    private StreamConnection conn = null;
    private InputStream inputStream=null;
    private OutputStream outputStream=null;
    private UUID MYSERVICEUUID_UUID;
    private static final String myServiceName = "BlueChess";// Bluetooth Service name
    private static final String myServiceUUID = "8a02dc796f3141f1b83096cc0ac738cf";// Bluetooth Service UUID, anything
    private LocalDevice localDevice=null;
    private String connURL;
    private boolean connectionExists=false;

    public BtCommunication() {
        System.err.println("**********************BTCOMM CONSTRUCTOR");

            MYSERVICEUUID_UUID = new UUID(myServiceUUID, false);
            // Define the server connection URL
            connURL = "btspp://localhost:" + MYSERVICEUUID_UUID.toString() + ";" + "name=" + myServiceName + ";" + "authorize=true";
    }

    public StreamConnection waitForClient() {
        try {

            // Make the device discoverable, create the server and
            // wait for a connection
            localDevice = LocalDevice.getLocalDevice();
            localDevice.setDiscoverable(DiscoveryAgent.GIAC);
            // Create a server connection object to accept
            // a connection from a client
            scn = (StreamConnectionNotifier) Connector.open(connURL);
            // Accept a connection from the client
            conn = (StreamConnection) scn.acceptAndOpen();

            scn.close();
            //open the streams
            inputStream = conn.openInputStream();
            outputStream = conn.openOutputStream();
            connectionExists=true;
            //RemoteDevice remdev = RemoteDevice.getRemoteDevice(conn);
            //System.err.println("Remote device address: " + remdev.getBluetoothAddress());
            //System.err.println("Remote device name: " + remdev.getFriendlyName(true));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (conn != null) {
            return conn;
        } else {
            return null;
        }

    }

    public StreamConnection connectToServer() {

        try {

            MYSERVICEUUID_UUID = new UUID(myServiceUUID, false);
            mDiscoveryAgent = LocalDevice.getLocalDevice().getDiscoveryAgent();
            // select the server with MYSERVICEUUID_UUID
            connURL = mDiscoveryAgent.selectService(MYSERVICEUUID_UUID, ServiceRecord.AUTHENTICATE_NOENCRYPT, false);
            // open the connection
            conn = (StreamConnection) Connector.open(connURL);
            //open streams
            inputStream = conn.openInputStream();
            outputStream = conn.openOutputStream();
            connectionExists=true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (conn != null) {
            return conn;
        } else {
            return null;
        }
    }
    public void closeConnections()
    {
        try {
            Thread.sleep(200);
            inputStream.close();
            outputStream.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void writeMessage(String strToSend) {
        try {
            outputStream.write(strToSend.length());
            outputStream.flush();
            outputStream.write(strToSend.getBytes());
            outputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
public boolean connectionAlive()
{
    return connectionExists;
}
    public String readData() {
        String rxString = null;
        try {
            System.err.println("Read Data fn being executed");
            byte[] buffer = null;
            // throw an exception if length is not greater then 0

            int length = inputStream.read();
            if (length==-1) {
                connectionExists=false;
                 rxString="Lost";
                 return rxString;
  
            } else {
                //if data arrived
                buffer = new byte[length];
                length = 0;
                // Assemble data
                while (length != buffer.length) {
                    //read into buffer until the end
                    int ch = inputStream.read(buffer, length, buffer.length - length);
                    if (ch == -1) {
                        connectionExists=false;
                        rxString = "Lost";
                        return rxString;
                    }
                    length += ch;
                }
                //after finishing reading the buffer
               // if (buffer != null) {
                    //make string from buffer
                    rxString = new String(buffer);
                    System.err.println("Read Data :" + rxString);
               // }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return rxString;

    }

}