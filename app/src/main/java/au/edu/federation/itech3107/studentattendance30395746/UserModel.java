package au.edu.federation.itech3107.studentattendance30395746;

// UserModel.java

public class UserModel {
    private String name;
    private String userId;
    private boolean isSelected;
    private String status;
    private String courseId;

    public UserModel(String name, String userId,String status) {
        this.name = name;
        this.userId = userId;
        this.isSelected = false; // Unchecked by default
        this.status = status;
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
