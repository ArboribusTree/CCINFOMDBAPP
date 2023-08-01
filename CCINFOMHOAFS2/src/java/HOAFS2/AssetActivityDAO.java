package HOAFS2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssetActivityDAO {

    public AssetActivity getAssetActivityById(AssetActivityId id) {
        AssetActivity assetActivity = null;
        String query = "SELECT * FROM asset_activity WHERE asset_id = ? AND activity_date = ?";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id.getAssetId());
            stmt.setDate(2, Date.valueOf(id.getActivityDate()));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                assetActivity = new AssetActivity();
                assetActivity.setId(id);
                assetActivity.setActivityDescription(rs.getString("activity_description"));
                assetActivity.setTentStart(rs.getDate("tent_start").toLocalDate());
                assetActivity.setTentEnd(rs.getDate("tent_end").toLocalDate());
                assetActivity.setActStart(rs.getDate("act_start").toLocalDate());
                assetActivity.setActEnd(rs.getDate("act_end").toLocalDate());
                assetActivity.setCost(rs.getBigDecimal("cost"));
                assetActivity.setStatus(rs.getString("status"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assetActivity;
    }

    public void saveOrUpdateAssetActivity(AssetActivity assetActivity) {
        String query = "INSERT INTO asset_activity (asset_id, activity_date, activity_description, tent_start, tent_end, act_start, act_end, cost, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE activity_description = VALUES(activity_description), tent_start = VALUES(tent_start), " +
                "tent_end = VALUES(tent_end), act_start = VALUES(act_start), act_end = VALUES(act_end), cost = VALUES(cost), status = VALUES(status)";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            AssetActivityId id = assetActivity.getId();
            stmt.setInt(1, id.getAssetId());
            stmt.setDate(2, Date.valueOf(id.getActivityDate()));
            stmt.setString(3, assetActivity.getActivityDescription());
            stmt.setDate(4, Date.valueOf(assetActivity.getTentStart()));
            stmt.setDate(5, Date.valueOf(assetActivity.getTentEnd()));
            stmt.setDate(6, Date.valueOf(assetActivity.getActStart()));
            stmt.setDate(7, Date.valueOf(assetActivity.getActEnd()));
            stmt.setBigDecimal(8, assetActivity.getCost());
            stmt.setString(9, assetActivity.getStatus());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAssetActivity(AssetActivity assetActivity) {
        String query = "DELETE FROM asset_activity WHERE asset_id = ? AND activity_date = ?";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            AssetActivityId id = assetActivity.getId();
            stmt.setInt(1, id.getAssetId());
            stmt.setDate(2, Date.valueOf(id.getActivityDate()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<AssetActivity> getAllAssetActivities() {
        List<AssetActivity> assetActivities = new ArrayList<>();
        String query = "SELECT * FROM asset_activity";

        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                AssetActivity assetActivity = new AssetActivity();
                AssetActivityId id = new AssetActivityId(rs.getInt("asset_id"), rs.getDate("activity_date").toLocalDate());
                assetActivity.setId(id);
                assetActivity.setActivityDescription(rs.getString("activity_description"));
                assetActivity.setTentStart(rs.getDate("tent_start").toLocalDate());
                assetActivity.setTentEnd(rs.getDate("tent_end").toLocalDate());
                assetActivity.setActStart(rs.getDate("act_start").toLocalDate());
                assetActivity.setActEnd(rs.getDate("act_end").toLocalDate());
                assetActivity.setCost(rs.getBigDecimal("cost"));
                assetActivity.setStatus(rs.getString("status"));

                assetActivities.add(assetActivity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assetActivities;
    }
}