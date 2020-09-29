package Evidencia;
import java.io.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static String separador = FileSystems.getDefault().getSeparator();
    private static String filepath = String.format(
            "C:%sUsers%spc%sDesktop%sTecMilenio%s2020-08%sComputación en Java%sEntregables%sConsultorioClinico%sdb%s",
            separador, separador, separador, separador, separador, separador, separador, separador, separador, separador
    );
    private static String fileUsuarios = "usuarios.txt";
    private static String fileDoctores = "doctores.txt";
    private static String filePacientes= "pacientes.txt";
    private static String fileCitas = "citas.txt";
    private static String wait = "";
    private static Scanner scn = new Scanner(System.in);


    public static void main(String[] args) {
        boolean acceso = false;
        while(!acceso){
            System.out.println("Por favor ingrese su nombre de usuario y pulse la tecla 'Enter':");
            String user = scn.nextLine();
            String _userPass = userPass(user);
            if (_userPass.isEmpty()){
                System.out.println("El usuario no está registrado.");
            }else{
                while (!acceso){
                    System.out.println("Por favor ingrese su contraseña y pulse la tecla 'Enter':");
                    String pass = scn.nextLine();
                    if (pass.equals(_userPass)){
                        System.out.println("Credenciales correctas, accesando... \n");
                        wait = scn.nextLine();
                        acceso = true;
                    }else{
                        System.out.println("La contraseña es incorrecta. \n");
                    }
                }
            }
        }
    }


    private static String userPass(String user){
        String userPass = "";
        HashMap<String, String> usuarios = getInfoArchivo(fileUsuarios);
        if (usuarios.size() > 0){
            if (usuarios.containsKey(user)){
            userPass = usuarios.get(user);
            }
        }else{
            userPass = "";
        }
        return userPass;
    }

    private static HashMap<String, String> getInfoArchivo(String archivo){
        HashMap<String, String> registros = new HashMap<>();
        File file = new File(filepath + archivo);
        try {
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] temp = linea.split("\t");
                    try {
                        registros.put(temp[0], temp[1]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
            }else{
                String mensaje = "";

                if (archivo == "usuarios.txt"){
                    mensaje = "No existe el archivo de registro para usuarios. \n " +
                            "Se creará uno nuevo dejando un registro por default: \n " +
                            "Usuario: Admin \n " +
                            "Contraseña: 123456 \n";
                    BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                    bw.write("Admin\t123456");
                    bw.newLine();
                    bw.flush();
                }else{
                    mensaje = "No existe archivo de registro para " +
                            "'" + archivo.split(".")[0] + "'. \n" +
                            "Se creará uno nuevo. \n";
                    file.createNewFile();
                }
                System.out.println(mensaje);
                System.out.println("Archivo creado exitosamente");
            }
        }catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return registros;
    }
}
