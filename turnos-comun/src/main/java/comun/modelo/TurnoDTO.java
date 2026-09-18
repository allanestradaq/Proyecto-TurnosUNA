/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comun.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TurnoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String codigo;
    private String servicioCodigo;
    private String estado;
    private LocalDateTime fechaGeneracion;
    private Integer numeroVentanilla;

    public TurnoDTO(int id, String codigo, String servicioCodigo, String estado) {
        this.id = id;
        this.codigo = codigo;
        this.servicioCodigo = servicioCodigo;
        this.estado = estado;
    }

    public int getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getServicioCodigo() { return servicioCodigo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Integer getNumeroVentanilla() { return numeroVentanilla; }
    public void setNumeroVentanilla(Integer numeroVentanilla) { this.numeroVentanilla = numeroVentanilla; }
}
