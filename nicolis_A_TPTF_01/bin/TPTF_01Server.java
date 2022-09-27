package nicolis_A_TPTF_01.bin;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

class TPTF_01Server {

        public static void main(String[] args) {
                try {
                        DatagramSocket sck = new DatagramSocket(2000);
                        DatagramPacket p_send, p_rece;
                        byte[] recBuf = new byte[512];
                        p_rece = new DatagramPacket(recBuf, recBuf.length);

                        while (true){
                                sck.receive(p_rece);
                                p_send = new DatagramPacket(p_rece.getData(),
                                        p_rece.getLength(),
                                        p_rece.getSocketAddress());

                                sck.send(p_send);
                        }

                } catch (IOException e) {
                        throw new RuntimeException(e);
                }

        }
}

//byte[] mex="filecorto".getBytes();
//            byte[] request = new byte[100];
//            request[0]=0;
//            request[1]=1;
//            int j=2;
//
//            for (int k=0;k<mex.length;k++){
//                request[j++]=mex[k];
//            }
//
//            request[j++]=0;
//            mex="octet".getBytes();
//            for (int k=0;k<mex.length;k++){
//                request[j++]=mex[k];
//            }
//
//            request[j++]=0;
//            // j lunghezza del pacchetto