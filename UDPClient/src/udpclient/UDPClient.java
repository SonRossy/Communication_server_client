/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpclient;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

class UDPClient {
     
    public static void main(String args[]) throws Exception
    {
       int messageNumber=1;
       
        
       
        BufferedReader inFromUser =
            new BufferedReader(new InputStreamReader
                (System.in));
        DatagramSocket clientSocket = new DatagramSocket();//port # is assigned by OS to the client
        InetAddress IPAddress = 
            InetAddress.getByName("localhost");
        byte[] receiveData = new byte[1024];
        
        while(messageNumber<11)
        {
        String sentence = inFromUser.readLine();
        byte[] sendData = sentence.getBytes();
        DatagramPacket sendPacket =
            new DatagramPacket(sendData, sendData.length, 
                               IPAddress, 9876); //data with server's IP and server's port #
        DatagramPacket receivePacket =
                new DatagramPacket(receiveData,
                                   receiveData.length);
        clientSocket.setSoTimeout(1000);
        long startTime=System.currentTimeMillis();
        clientSocket.send(sendPacket);
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date dateobj = new Date();
        System.out.println("ping number: "+messageNumber+" "+"current time: "+df.format(dateobj));
         
            try{
                clientSocket.receive(receivePacket);
                long endTime =System.currentTimeMillis();//time that packet is recieved
                // we still need to catch the exception and retry
            String modifiedSentence =
                new String(receivePacket.getData(),
                           0,
                           receivePacket.getLength());
            System.out.println("Recieve FROM SERVER:" +
                               modifiedSentence+" elapsed time: "+(endTime-startTime)*1000+" microseconds\n");
            
            }catch(Exception e)
            {
                System.out.println("time out");
            }
            messageNumber++;
        }
    clientSocket.close();
}
}

    


