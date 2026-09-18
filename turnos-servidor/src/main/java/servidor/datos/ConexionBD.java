/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package servidor.datos;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConexionBD {

    private static ConexionBD instancia;
    private final MysqlDataSource dataSource;

    private ConexionBD() {
        dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://TU_HOST:3306/turnos_app?useSSL=false&serverTimezone=UTC");
        dataSource.setUser("TU_USUARIO");
        dataSource.setPassword("TU_CLAVE");
    }

    public static synchronized ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    /** Cada llamada abre una conexión nueva; nunca se comparte un Connection entre hilos. */
    public Connection nuevaConexion() throws SQLException {
        return dataSource.getConnection();
    }
}