package user;
import Address.*;

public class User {
    private int userID;
    private String userName;
    private String password;
    private String role;
    private String email;
    private Address address;
    private String phone;

    // Constructor, getters, and setters

    public User() {
    }

    public User(int userID, String userName, String password, String role, String email, Address address, String phone) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

	public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

    @Override
    public String toString() {
        return "User [userID=" + userID + ", userName=" + userName + ", password=" + password + ", role=" + role
                + ", email=" + email + ", address=" + address + ", phone=" + phone + "]";
    }
}
