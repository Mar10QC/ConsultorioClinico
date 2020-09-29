package Evidencia;
import java.io.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static final String separador = FileSystems.getDefault().getSeparator();
    private static final String filepath = String.format(
            "C:%sUsers%spc%sDesktop%sTecMilenio%s2020-08%sComputación en Java%sEntregables%sConsultorioClinico%sdb%s",
            separador, separador, separador, separador, separador, separador, separador, separador, separador, separador
    );
    private static final String fileUsuarios = "usuarios.txt";
    private static final String fileDoctores = "doctores.txt";
    private static final String filePacientes= "pacientes.txt";
    private static final String fileCitas = "citas.txt";
    private static String wait = "";
    private static final Scanner scn = new Scanner(System.in);


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
        String resultPrincipal;
        boolean salirPrincipal = false;
        while(!salirPrincipal){
            System.out.println("Digite el número correspondiente a la opción deseada y presione 'Enter': \n" +
                    "1)Doctores\n" +
                    "2)Pacientes\n" +
                    "3)Citas\n" +
                    "4)Salir");
            resultPrincipal = scn.nextLine();
            switch (resultPrincipal) {
                case "1":
                    String resultDoctores = "";
                    boolean salirDoctores = false;
                    while(!salirDoctores){
                        listElements(getInfoArchivo(fileDoctores));
                        System.out.println("Digite el número correspondiente a la opción deseada y presione 'Enter': \n" +
                                "1)Agregar doctor\n" +
                                "2)Salir al menú pricipal\n");
                        resultDoctores = scn.nextLine();
                        switch (resultDoctores){
                            case "1":
                                String nombre;
                                String especialidad;
                                boolean salirDoctores2 = false;
                                while (!salirDoctores2){
                                    System.out.println("Ingrese el nombre del doctor y presione 'Enter': \n");
                                    nombre = scn.nextLine();
                                    if (nombre.isEmpty()){
                                        System.out.println("El campo nombre no debe ir vacío");
                                    }else {
                                        while (!salirDoctores2) {
                                            System.out.println("Ingrese la especialidad del doctor y presione 'Enter': \n");
                                            especialidad = scn.nextLine();
                                            if (especialidad.isEmpty()) {
                                                System.out.println("El campo especialidad no debe ir vacío");
                                            } else {
                                                Doctor doctor = new Doctor(getInfoArchivo(fileDoctores).size(), nombre, especialidad);
                                                saveElement(fileDoctores, doctor, null, null);
                                                System.out.println("Registro guardado correctamente");
                                                salirDoctores2 = true;
                                            }
                                        }
                                    }
                                }
                                break;
                            case "2":
                                salirDoctores = true;
                                break;
                        }
                    }

                    break;
                case "2":

                    break;
                case "3":

                    break;
                case "4":
                    salirPrincipal = true;
                    break;
            }
        }

        //saveElement(getInfoArchivo(fileDoctores), null, null, null);
    }

    private static void listElements(HashMap<String, String> registros){

    }

    private  static void saveElement(String archivo, Doctor doctor, Paciente paciente, Cita cita){
        HashMap<String, String> registros = getInfoArchivo(archivo);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filepath + archivo, true));
            if (cita != null){
                bw.write(cita.id_cita + "\t" + cita.id_doctor + "," + cita.id_paciente + "," +
                        cita.fechaHora + "," + cita.motivo);
            }else if (doctor != null){
                bw.write(doctor.id_doctor + "\t" + doctor.nombre + "," + doctor.especialidad);
            }else if (paciente != null){
                bw.write(paciente.id_paciente + "\t" + paciente.nombre);
            }
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
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
                    String[] nomArchivo = archivo.split(".");
                    mensaje = "No existe archivo de registro para " +
                            "'" + archivo + "'. \n" +
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
