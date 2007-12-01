/*
 * Copyright 2003-2004 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

/*
 * @test
 * @bug 4097826
 * @summary SOCKS support inadequate
 * @run main/timeout=40/othervm -DsocksProxyHost=nonexistant ProxyCons
 */

import java.net.*;
public class ProxyCons {
    class Server extends Thread {
        ServerSocket server;
        Server (ServerSocket server) {
            super ();
            this.server = server;
        }
        public void run () {
            try {
                Socket s = server.accept ();
                while (!finished ()) {
                    Thread.sleep (500);
                }
            } catch (Exception e) {
            }
        }
        boolean isFinished = false;

        synchronized boolean finished () {
            return (isFinished);
        }
        synchronized void done () {
            isFinished = true;
        }
    }

    public ProxyCons() {
    }

    void test() {
        try {
            ServerSocket ss = new ServerSocket();
            ss.bind(new InetSocketAddress(0));
            Server s = new Server(ss);
            s.start();
            Socket sock = new Socket(Proxy.NO_PROXY);
            sock.connect(new InetSocketAddress("localhost", ss.getLocalPort()));
            s.done();
            sock.close();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ProxyCons c = new ProxyCons();
        c.test();
    }
}
