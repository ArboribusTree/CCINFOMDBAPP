/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HOAFS2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author ccslearner
 */
public class ResidentDAO {
    public Resident getUserById(int id){
        Resident resident = null;
        String query = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                resident = new Resident(rs.getInt("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resident;
    }
}
