package Evidencia;

public class Cita {
    int id_cita;
    int id_doctor;
    int id_paciente;
    String fechaHora;
    String motivo;

    public Cita(int id_cita, int id_doctor, int id_paciente, String fechaHora, String motivo){
        this.id_cita = id_cita;
        this.id_doctor = id_doctor;
        this.id_paciente = id_paciente;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
    }
}
