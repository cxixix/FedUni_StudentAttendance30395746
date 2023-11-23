package au.edu.federation.itech3107.studentattendance30395746;

public class Course {
    private long id;
    private String courseName;
    private String teacherName;
    private String courseId;
    private String startTime;
    private String endTime;

    public Course() {
        // Default constructor
    }

    public Course(String courseName, String teacherName, String courseId, String startTime, String endTime) {
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.courseId = courseId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
