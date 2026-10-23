package ServerPackage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try {
            ServerSocket serveur = new ServerSocket(1234);
            System.out.println("Je suis un serveur en attente de connexion d'un client");
            Socket s = serveur.accept();
            System.out.println("Un client est connecté");

            InputStream entree = s.getInputStream();
            BufferedReader lire = new BufferedReader(new InputStreamReader(entree));
            OutputStream sortie = s.getOutputStream();
            PrintWriter ecrire = new PrintWriter(sortie, true);

            String a = lire.readLine();

            while (a != null && !a.equals("quit")) {
                String op = lire.readLine();
                String b = lire.readLine();

                System.out.println("Opération reçue : " + a + " " + op + " " + b);

                String reponse;

                try {
                    reponse = calculer(Double.parseDouble(a), op, Double.parseDouble(b));
                } catch (NumberFormatException e) {
                    reponse = "Erreur : opérande invalide";
                }

                ecrire.println(reponse);
                System.out.println("Réponse envoyée : " + reponse);

                a = lire.readLine();
            }

            System.out.println("Fin de la communication");
            s.close();
            serveur.close();

        } catch (IOException e) {
            System.out.println("Erreur côté serveur : " + e.getMessage());
        }
    }

    static String calculer(double a, String op, double b) {
        double resultat;

        switch (op) {
            case "+":
                resultat = a + b;
                break;
            case "-":
                resultat = a - b;
                break;
            case "*":
                resultat = a * b;
                break;
            case "/":
                if (b == 0) {
                    return "Erreur : division par zéro impossible";
                }
                resultat = a / b;
                break;
            default:
                return "Erreur : opérateur inconnu";
        }

        return a + " " + op + " " + b + " = " + resultat;
    }

    static String formater(double nombre) {
        if (nombre == Math.rint(nombre) && !Double.isInfinite(nombre)) {
            return String.valueOf((long) nombre);
        }

        return String.valueOf(nombre);
    }
}

