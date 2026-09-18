/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comun.protocolo;

import java.io.Serializable;

public class Mensaje implements Serializable {
    private static final long serialVersionUID = 1L;

    private final TipoMensaje tipo;
    private final Object contenido;

    public Mensaje(TipoMensaje tipo, Object contenido) {
        this.tipo = tipo;
        this.contenido = contenido;
    }

    public TipoMensaje getTipo() { return tipo; }
    public Object getContenido() { return contenido; }
}