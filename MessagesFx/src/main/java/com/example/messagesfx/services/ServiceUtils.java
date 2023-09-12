package com.example.messagesfx.services;

import javafx.beans.value.ObservableObjectValue;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.StringJoiner;
import java.util.zip.GZIPInputStream;

public class ServiceUtils {

        private static String token = null;
        private static String imageB64 = null;
        private static String nombre=null;
        private static String idUsu = null;

        public static String transformImage(String fileUrl) {

            //transforma la imagen en base 64
            String data = "";
            Path file = Paths.get(fileUrl);
            System.out.println("El filepath: " + file);
            byte[] bytes;
            try {
                bytes = Files.readAllBytes(file);
                //data = Base64.getEncoder().encodeToString(bytes);
                //data = new String(Base64.getEncoder().encode(bytes));
                data = new String(Base64.getMimeEncoder().encode(bytes));

            } catch (IOException ex) {
                System.err.println("Error getting bytes from " + file.toString());
            }

            return data;
        }

        public static File fromB64ToImage(String b64data, String filename) throws IOException {
            //decodifica la imagen
            byte[] data = Base64.getMimeDecoder().decode(b64data);

            //crea un objeto byteArray
            ByteArrayInputStream inStreambj = new ByteArrayInputStream(data);

            //el bufferimage recibe el objeto anterior
            BufferedImage newImage = ImageIO.read(inStreambj);

            //crear una imagen
            ImageIO.write(newImage, "jpg", new File("src/main/resources/images/" + filename));
            File resFile = new File("src/main/resources/images/" + filename);
            return resFile;

        }

        public static String getIdUsu() {
            return idUsu;
        }

        public static void setIdUsu(String idUsu) {
            ServiceUtils.idUsu = idUsu;
        }

    public static String getNombre() {
            return ServiceUtils.nombre;
        }

        public static void setNombre(String nombre) {
            ServiceUtils.nombre = nombre;
        }

        public static void setImageB64(String image) { ServiceUtils.imageB64 = image; }

        public static String getImageB64() {return ServiceUtils.imageB64;}

        public static void setToken(String token) {
            ServiceUtils.token = token;
        }
        public static void removeToken() {
            ServiceUtils.token = null;
        }
        public static String getCharset(String contentType) {
            for (String param : contentType.replace(" ", "").split(";")) {
                if (param.startsWith("charset=")) {
                    return param.split("=", 2)[1];
                }
            }
            return null; // Probably binary content
        }
        public static String getResponse(String url, String data, String method) {
            BufferedReader bufInput = null;
            StringJoiner result = new StringJoiner("\n");
            try {
                URL urlConn = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) urlConn.openConnection();
                conn.setReadTimeout(20000 /*milliseconds*/);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod(method);
                conn.setRequestProperty("Host", "localhost");
                conn.setRequestProperty("Connection", "keep-alive");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Origin", "http://localhost");
                conn.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
                conn.setRequestProperty("Accept-Language", "es-ES,es;q=0.8");
                conn.setRequestProperty("Accept-Charset", "UTF-8");
                conn.setRequestProperty("User-Agent", "Java");
// If set, send the authentication token
                if (token != null) {
                    conn.setRequestProperty("Authorization", "" + token);
                }
                if (data != null) {
                    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
                    conn.setDoOutput(true);
//Send request
                    DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                    wr.write(data.getBytes());
                    wr.flush();
                    wr.close();
                }

                String charset = getCharset(conn.getHeaderField("Content-Type"));
                if (charset != null) {
                    InputStream input = conn.getInputStream();
                    if ("gzip".equals(conn.getContentEncoding())) {
                        input = new GZIPInputStream(input);
                    }
                    bufInput = new BufferedReader(new InputStreamReader(input));
                    String line;
                    while ((line = bufInput.readLine()) != null) {
                        result.add(line);
                    }

                }
                //------
                if(conn.getResponseCode()>400 ){

                    throw new Exception(result.toString());

                }
                //------
            } catch (IOException e) {
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                if (bufInput != null) {
                    try {
                        bufInput.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result.toString();
        }

}
