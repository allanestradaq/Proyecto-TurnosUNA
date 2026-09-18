/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package servidor.datos;

import java.sql.*;

public class ServicioDAO {

    public int obtenerIdPorCodigo(char codigo) throws SQLException {
        String sql = "SELECT id FROM servicio WHERE codigo = ?";
        try (Connection con = ConexionBD.getInstancia().nuevaConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, String.valueOf(codigo));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }
}