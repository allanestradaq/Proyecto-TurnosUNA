/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package servidor.datos;

import comun.modelo.TurnoDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurnoDAO {

    public int insertarTurno(String codigo, int servicioId) throws SQLException {
        String sql = "INSERT INTO turno (codigo, servicio_id) VALUES (?, ?)";
        try (Connection con = ConexionBD.getInstancia().nuevaConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, codigo);
            ps.setInt(2, servicioId);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    /** Devuelve el turno EN_ESPERA más antiguo entre los servicios permitidos, o null si no hay. */
    public TurnoDTO obtenerSiguienteEnEspera(List<Integer> serviciosPermitidos) throws SQLException {
        String placeholders = String.join(",", serviciosPermitidos.stream().map(i -> "?").toList());
        String sql = "SELECT t.id, t.codigo, s.codigo AS servicio_codigo, t.estado " +
                     "FROM turno t JOIN servicio s ON t.servicio_id = s.id " +
                     "WHERE t.estado = 'EN_ESPERA' AND t.servicio_id IN (" + placeholders + ") " +
                     "ORDER BY t.fecha_generacion ASC LIMIT 1";

        try (Connection con = ConexionBD.getInstancia().nuevaConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < serviciosPermitidos.size(); i++) {
                ps.setInt(i + 1, serviciosPermitidos.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new TurnoDTO(rs.getInt("id"), rs.getString("codigo"),
                                         rs.getString("servicio_codigo"), rs.getString("estado"));
                }
            }
        }
        return null;
    }

    public void marcarLlamado(int turnoId, int ventanillaId, int funcionarioId) throws SQLException {
        String sql = "UPDATE turno SET estado='LLAMADO', hora_llamado=NOW(), " +
                     "ventanilla_id=?, funcionario_id=? WHERE id=?";
        try (Connection con = ConexionBD.getInstancia().nuevaConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ventanillaId);
            ps.setInt(2, funcionarioId);
            ps.setInt(3, turnoId);
            ps.executeUpdate();
        }
    }

    public void marcarFinalizado(int turnoId) throws SQLException {
        String sql = "UPDATE turno SET estado='FINALIZADO', hora_fin_atencion=NOW() WHERE id=?";
        try (Connection con = ConexionBD.getInstancia().nuevaConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, turnoId);
            ps.executeUpdate();
        }
    }

    /** Cuenta cuántos turnos existen para un servicio, usado para numerar el siguiente código. */
    public int contarTurnosDelServicio(int servicioId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM turno WHERE servicio_id = ?";
        try (Connection con = ConexionBD.getInstancia().nuevaConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, servicioId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }
}