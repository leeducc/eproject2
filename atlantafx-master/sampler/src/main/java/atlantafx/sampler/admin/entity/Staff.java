package atlantafx.sampler.admin.entity;

public class Staff {
    private String staffId;
    private String name;
    private String contactNumber;
    private String email;
    private String gender;
    private String role; // Add this line for role

    public Staff(String staffId, String name, String contactNumber, String email, String gender, String role) {
        this.staffId = staffId;
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
        this.gender = gender;
        this.role = role; // Set role
    }

    // Getters
    public String getStaffId() {
        return staffId;
    }

    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getRole() { // Add this method
        return role;
    }
}
