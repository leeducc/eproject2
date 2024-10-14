package atlantafx.sampler.page.components;

import atlantafx.sampler.configJDBC.dao.JDBCConnect; // Import your JDBCConnect class
import atlantafx.sampler.entity.Staff;
import atlantafx.sampler.page.OutlinePage;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public final class StaffListPage extends OutlinePage {
    public static final String NAME = "Staff List"; // Changed NAME to "Staff List"
    private TableView<Staff> table; // Changed type to Staff

    @Override
    public String getName() {
        return NAME;
    }

    public StaffListPage() {
        super();
        initialize();
    }

    private void initialize() {
        // Create columns for the staff table
        var col1 = new TableColumn<Staff, String>("Staff ID");
        col1.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStaffId()));

        var col2 = new TableColumn<Staff, String>("Name");
        col2.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));

        var col3 = new TableColumn<Staff, String>("Contact Number");
        col3.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getContactNumber()));

        var col4 = new TableColumn<Staff, String>("Email");
        col4.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));

        var col5 = new TableColumn<Staff, String>("Gender");
        col5.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getGender()));

        var col6 = new TableColumn<Staff, String>("Role");
        col6.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getRole())); // Role column

        // Create the table and add columns
        table = new TableView<>();
        table.getColumns().setAll(col1, col2, col3, col4, col5, col6); // Add the role column
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        // Pagination setup
        var pg = new Pagination(25, 0);
        pg.setMaxPageIndicatorCount(5);
        pg.setPageFactory(pageNum -> {
            // Fetch the staff for the current page
            List<Staff> staffList = fetchStaffFromDatabase(pageNum, 25); // Fetch staff for the current page
            table.getItems().setAll(staffList);
            return new StackPane(table);
        });

        // Add the pagination control to the layout
        getChildren().addAll(table, pg);
    }

    private List<Staff> fetchStaffFromDatabase(int pageNum, int itemsPerPage) {
        List<Staff> staffList = new ArrayList<>();
        String query = "SELECT s.staff_id, s.name, s.contact_number, s.email, s.gender, r.role_name " +
                "FROM staff s LEFT JOIN role r ON s.role_id = r.id " + // Join to get role name
                "LIMIT ? OFFSET ?";

        try (Connection conn = JDBCConnect.getJDBCConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the pagination parameters
            stmt.setInt(1, itemsPerPage);
            stmt.setInt(2, pageNum * itemsPerPage);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                staffList.add(new Staff(
                        rs.getString("staff_id"),
                        rs.getString("name"),
                        rs.getString("contact_number"),
                        rs.getString("email"),
                        rs.getString("gender"),
                        rs.getString("role_name") // Get the role name from the result set
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return staffList;
    }

}
