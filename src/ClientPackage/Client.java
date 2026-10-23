
package ClientPackage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {

    static final Pattern format = Pattern.compile(
            "^\\s*(-?\\d+(?:\\.\\d+)?)\\s*([+\\-*/])\\s*(-?\\d+(?:\\.\\d+)?)\\s*$");

    public static void main(String[] args) {
        try {
            System.out.println("Je suis un client pas encore connecté…");
            Socket s = new Socket("localhost", 1234);
            System.out.println("Je suis un client connecté");

            PrintWriter ecrire = new PrintWriter(s.getOutputStream(), true);
            BufferedReader lire = new BufferedReader(new InputStreamReader(s.getInputStream()));
            Scanner clavier = new Scanner(System.in);

            while (true) {
                System.out.print("Donnez une opération  ou quit : ");
                String operation = clavier.nextLine().trim();

                if (operation.equals("quit")) {
                    break;
                }

                Matcher resultat = format.matcher(operation);

                if (!resultat.matches()) {
                    System.out.println("Syntaxe invalide, rien n'est envoyé.");
                    continue;
                }

                ecrire.println(resultat.group(1));
                ecrire.println(resultat.group(2));
                ecrire.println(resultat.group(3));

                String reponse = lire.readLine();
                System.out.println("Résultat : " + reponse);
            }

            ecrire.println("quit");
            System.out.println("Fin de la communication");
            clavier.close();
            s.close();

        } catch (IOException e) {
            System.out.println("Erreur côté client : " + e.getMessage());
        }
    }
}
