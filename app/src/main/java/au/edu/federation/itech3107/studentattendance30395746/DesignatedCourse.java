package au.edu.federation.itech3107.studentattendance30395746;

public class DesignatedCourse {
    private String userId;
    private String name;

    public DesignatedCourse(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
