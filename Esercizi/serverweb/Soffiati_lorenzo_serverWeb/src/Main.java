import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Base64;

public class Main {

    //-------------------------------------------- Metodi --------------------------------------------------------------
    /**
     * creazione dell'heaer del pachetto da inviare
     * @param length    greandezza del file da inviare
     * @return          l'header
     */
    public static String creaHeader(long length){

        StringBuilder header = new StringBuilder();
        header.append("HTTP/1.1 200 OK \n");
        header.append("Date: ").append(LocalTime.now()).append("\n");
        header.append("Content-Length: ").append(length).append("\n");
        header.append("Content-Type: text/html \n\n");

        return  header.toString();
    }

    /**
     * invio dell pacchetto con i file ma non le immagini
     * @param pagina        nome della pagina o file richiesto
     * @param scrittura     variabile per poter inviare il pachetto
     * @return              true se il pachetto è stato inviato false se la pagina non esiste
     */
    public static boolean inviaFile(String pagina, PrintWriter scrittura) throws IOException {

        // Completamento path dei file
        pagina = "sito" + pagina;

        // Controllo se la pagina esiste
        File file = new File(pagina);

        if(file.exists()) {
            // Invio header
            scrittura.println(creaHeader(file.length()));

            // Invio html
            BufferedReader html = new BufferedReader(new FileReader(pagina));
            String riga;
            while((riga = html.readLine()) != null){
                scrittura.println(riga);
            }
            return true;
        }
        return false;
    }

    /**
     * invio dell pacchetto con le immagini ma non i file
     * @param foto          Indirizzo della foto
     * @param scrittura     variabile per poter inviare il pachetto
     */
    public static boolean inviaImage(String foto, PrintWriter scrittura) throws IOException {

        // Completamento path della immagine
        foto = "sito" + foto;

        // Controllo se la pagina esiste
        File file = new File(foto);

        if(file.exists()) {
            // Invio header
            scrittura.println(creaHeader(file.length()));

            // Invio immgine
            InputStream inputStream = new FileInputStream(foto);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            
            byte[] fine;
            byte[] image = new byte[8192]; //8192 || foto.length()
            int bytesRead;
            
            while ((bytesRead = inputStream.read()) != -1) {
                output.write(image, 0, bytesRead);
                System.out.println(bytesRead);
            }

            fine = output.toByteArray();
            String base64 = Base64.getEncoder().encodeToString(fine);

            scrittura.println(base64);

            return true;
        }

        return false;
    }


    //-------------------------------------------- Main ----------------------------------------------------------------
    public static void main(String... args) throws IOException {
        //---------- Variabili ----------
        int porta = 2000;
        ServerSocket serverSocketsoket = new ServerSocket(porta);
        Socket clientSoket;

        BufferedReader lettura;
        PrintWriter scrittura;

        String riga;
        String[] elementi;
        String[] estensioni;
        String pagina = "";

        boolean image = false;
        boolean ris = false;

        //---------- Code ---------------

        while (true) {
            image = false;

            // Connessione client
            clientSoket = serverSocketsoket.accept();
            lettura = new BufferedReader(new InputStreamReader(clientSoket.getInputStream()));
            scrittura = new PrintWriter(new OutputStreamWriter(clientSoket.getOutputStream()), true);

            // Lettura headere richiesta file
            do {
                // Dividere i vari elementi
                riga = lettura.readLine();

                if(riga != null) {
                    elementi = riga.split(" ");

                    // Prende il nomee del file richiesto
                    if (elementi[0].equals("GET")) {
                        pagina = elementi[1];

                        // Se richiesta la pagina principale prendi index.html
                        if (pagina.toString().equals("/")) pagina = "/index";
                    }

                    // Prende la tipologia del file
                    if (elementi[0].equals("Accept:")) {
                        estensioni = elementi[1].split(",");

                        if (estensioni[0].equals("text/html")) pagina += ".html";

                        if(estensioni[0].split("/")[0].equals("image")) image = true;
                    }
                }
            } while (!riga.equals(""));

            // Invia file richiesto e se non esiste manda pagina errore pag 404
            if (image == false && !inviaFile(pagina, scrittura)) {
                if(pagina.split("\\.")[1].equals("html")){
                    pagina = "/404.html";
                    inviaFile(pagina, scrittura);
                }
            }

            if(image){
                ris = inviaImage(pagina, scrittura);
            }

            // Log di sistema
            System.out.println(pagina);
            if(image) System.out.println("immagine inviata: " + ris);
            System.out.print("\n");
        }
    }
}

