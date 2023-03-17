package org.example;

import static spark.Spark.*;
public class HelloWorld {
    public static void main(String[] args) {
        //keytool -genkeypair -alias ecikeypair -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore ecikeystore.p12 -validity 3650
        //API: secure(keystoreFilePath, keystorePassword, truststoreFilePath,truststorePassword);
        //keytool -export -keystore ./ecikeystore.p12 -alias ecikeypair -storetype PKCS12 -file ecicert.cer
        //keytool -import -storetype PKCS12 -file ./ecicert.cer -alias firstCA -keystore myTrustStore
        secure(getKeyStore(), getPwdKeyStore(), null, null);
        port(getPort());
        get("/hello", (req, res) -> "Hello Heroku");
    }

    private static String getPwdKeyStore() {
        if (System.getenv("PWDKEYSTORE") != null) {
            return (System.getenv("PWDKEYSTORE")).toString();
        }
        return "123456";
    }

    private static String getKeyStore() {
        if (System.getenv("KEYSTORE") != null) {
            return (System.getenv("KEYSTORE")).toString();
        }
        return "certificados/ecikeystore.p12";
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
