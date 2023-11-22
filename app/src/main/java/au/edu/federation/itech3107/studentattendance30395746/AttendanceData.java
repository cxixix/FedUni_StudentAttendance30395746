package au.edu.federation.itech3107.studentattendance30395746;

public class AttendanceData {

    private String studentName;
    private String courseID;
    private String attendanceTime;
    private String status;

    public AttendanceData(String studentName, String courseID, String attendanceTime, String status) {
        this.studentName = studentName;
        this.courseID = courseID;
        this.attendanceTime = attendanceTime;
        this.status = status;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getAttendanceTime() {
        return attendanceTime;
    }

    public String getStatus() {
        return status;
    }
}

