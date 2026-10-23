package ServerPackage;

import sharedPackage.Operation;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {

    public static void main(String[] args) {
        try {
            ServerSocket serveur = new ServerSocket(1234);
            System.out.println("Je suis un serveur en attente de connexion d'un client");
            Socket s = serveur.accept();
            System.out.println("Un client est connecté");

            ObjectOutputStream sortie = new ObjectOutputStream(s.getOutputStream());
            sortie.flush();
            ObjectInputStream entree = new ObjectInputStream(s.getInputStream());

            Object recu = entree.readObject();

            while (recu instanceof Operation) {
                Operation calcul = (Operation) recu;

                double a = calcul.getOperande1();
                String op = String.valueOf(calcul.getOperateur());
                double b = calcul.getOperande2();

                System.out.println("Opération reçue : " + a + " " + op + " " + b);

                String reponse = calculer(a, op, b);

                sortie.writeObject(reponse);
                sortie.flush();

                System.out.println("Réponse envoyée : " + reponse);

                recu = entree.readObject();
            }

            System.out.println("Fin de la communication");
            s.close();
            serveur.close();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur côté serveur : " + e.getMessage());
        }
    }

    static String calculer(double a, String operateur, double b) {
        double r;

        switch (operateur) {
            case "+":
                r = a + b;
                break;
            case "-":
                r = a - b;
                break;
            case "*":
                r = a * b;
                break;
            case "/":
                if (b == 0) {
                    return "Erreur : division par zéro impossible";
                }
                r = a / b;
                break;
            default:
                return "Erreur : opérateur inconnu";
        }

        return a + " " + operateur + " " + b + " = " + r;
    }
}

