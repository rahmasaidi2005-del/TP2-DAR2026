package ClientPackage;

import sharedPackage.Operation;
import java.io.*;
import java.net.Socket;
import java.util.Locale;
import java.util.Scanner;

public class client2 {

    public static void main(String[] args) {
        try {
            System.out.println("Je suis un client pas encore connecté…");
            Socket s = new Socket("localhost", 1234);
            System.out.println("Je suis un client connecté");

            ObjectOutputStream sortie = new ObjectOutputStream(s.getOutputStream());
            sortie.flush();
            ObjectInputStream entree = new ObjectInputStream(s.getInputStream());

            Scanner clavier = new Scanner(System.in);
            clavier.useLocale(Locale.US);

            String choix;

            do {
                System.out.print("Premier opérande : ");
                double a = clavier.nextDouble();

                String op;

                do {
                    System.out.print("Opérateur (+ - * /) : ");
                    op = clavier.next();
                } while (!op.matches("[+\\-*/]"));

                System.out.print("Deuxième opérande : ");
                double b = clavier.nextDouble();

                Operation calcul = new Operation(a, op.charAt(0), b);
                sortie.writeObject(calcul);

                String reponse = (String) entree.readObject();
                System.out.println("Résultat : " + reponse);

                System.out.print("Autre calcul ? (o/n) : ");
                choix = clavier.next();

            } while (choix.equalsIgnoreCase("o"));

            sortie.writeObject("FIN");
            System.out.println("Fin de la communication");

            clavier.close();
            s.close();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur côté client : " + e.getMessage());
        }
    }
}

