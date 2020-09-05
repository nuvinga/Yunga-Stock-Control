package stockControlWithFxml;

import java.sql.*;

public class StaffVerifier {

    private String id;
    private String name;
    private String username;
    private String password;
    private String designationCode;

    public StaffVerifier(ResultSet resultSet){
        try {
            this.id = resultSet.getString("staffId");
            this.name = resultSet.getString("staffName");
            this.username = resultSet.getString("staffUsername");;
            this.password = resultSet.getString("staffPassword");;
            this.designationCode = resultSet.getString("staffDesignationCode");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getVerification(String getUsername, String getPassword){
        if (getUsername.equals(username)) {
            if (getPassword.equals(password)) {
                if (designationCode.equals("CHAIR") || designationCode.equals("DIR") || designationCode.equals("MAN")) {
                    return "grant-top";
                } else if (designationCode.equals("EXE") || designationCode.equals("TYP") ||
                        designationCode.equals("CASH")) {
                    return "grant-low";
                } else {
                    return "grant-null";
                }
            }else{
                return "error-password";
            }
        }else{
            return "error-username";
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDesignationCode() {
        return designationCode;
    }

}
