/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import ClientSide.observerThread;
import ServerSide.linkServer;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class QuoteClient {
    public static void main(String[] args) throws IOException {

        System.out.println("To transfert file press 9,  To stop the application press 8");
        Thread terminalThread = new observerThread();
        terminalThread.start();


        byte[] message;
        applicationClient applayer= new applicationClient();
        transportClient transportLayer = new transportClient();
        linkClient datalink =new linkClient();
        Packet[] transmission = transportLayer.creerTrame(applayer.creationMessage(terminalThread.getName2()),terminalThread.getAdr2(),layer.getFilename());
        datalink.CRC(transmission,args[0]);

        if (args.length != 1) {
            System.out.println("Usage: java QuoteClient <hostname>");
            return;
        }
        byte[] buf = new byte[256];
        // get a datagram socket
        DatagramSocket socket = new DatagramSocket(25501);
        String header = "ceci est un test denvoie";
        Charset charset = StandardCharsets.US_ASCII;
        buf = charset.encode(header).array();

        // send request
        InetAddress address = InetAddress.getByName(args[0]);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 25500);
        socket.send(packet);
        // get response
        buf = new byte[256];
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        byte[] sortie = new byte[packet.getLength()-4];
        System.arraycopy(buf, 0,sortie,0,sortie.length);

        String received = new String(sortie, 0, sortie.length);
        System.out.println("reussi voici le message de retour : " + received);

        socket.close();
    }
}