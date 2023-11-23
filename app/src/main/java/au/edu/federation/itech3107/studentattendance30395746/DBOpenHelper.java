package au.edu.federation.itech3107.studentattendance30395746;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create database user database table

        String sql_user = "CREATE TABLE user (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE," +
                "userid TEXT NOT NULL," +
                "name TEXT NOT NULL," +
                "password TEXT NOT NULL, " +
                "type TEXT NOT NULL)";

        db.execSQL(sql_user);
        //Create Administrator
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("userid", "1");
        contentValues2.put("name", "admin");
        contentValues2.put("password", "admin");
        contentValues2.put("type", "admin");
        db.insert("user", null, contentValues2);


        String sql_course = "CREATE TABLE course (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE," +//
                "coursename TEXT," +
                "teachername TEXT, " +
                "course_id TEXT NOT NULL, " +
                "course_start_time TEXT, " +
                "course_end_time TEXT " +
                ")";
        db.execSQL(sql_course);


        String sql_record = "CREATE TABLE record (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE," +//
                "student_id TEXT," +
                "student_name TEXT," +
                "course_id TEXT, " +
                "attendancetime TEXT, " +
                "status TEXT " +
                ")";
        db.execSQL(sql_record);

        String sql_schedule = "CREATE TABLE schedule (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE," +//
                "course_id TEXT," +
                "coursename TEXT," +
                "year TEXT, " +
                "month TEXT, " +
                "day TEXT " +
                ")";
        db.execSQL(sql_schedule);

        String sql_DesignatedCourses = "CREATE TABLE designatedcourses (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE," +
                "course_id TEXT," +
                "coursename TEXT," +
                "userid TEXT, " +
                "name TEXT, " +
                "type TEXT " +
                ")";
        db.execSQL(sql_DesignatedCourses);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @SuppressLint("Range")
    public List<Course> getAllCourses() {
        List<Course> courseList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("course", null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Course course = new Course();
                course.setId(cursor.getLong(cursor.getColumnIndex("id")));
                course.setCourseName(cursor.getString(cursor.getColumnIndex("coursename")));
                course.setTeacherName(cursor.getString(cursor.getColumnIndex("teachername")));
                course.setCourseId(cursor.getString(cursor.getColumnIndex("course_id")));
                course.setStartTime(cursor.getString(cursor.getColumnIndex("course_start_time")));
                course.setEndTime(cursor.getString(cursor.getColumnIndex("course_end_time")));

                courseList.add(course);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return courseList;
    }

    @SuppressLint("Range")
    public List<Course> getAllTeacherCourses(String currentTeacherName) {
        List<Course> courseList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "teachername = ?";
        String[] selectionArgs = {currentTeacherName};

        Cursor cursor = db.query("course", null, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Course course = new Course();
                course.setId(cursor.getLong(cursor.getColumnIndex("id")));
                course.setCourseName(cursor.getString(cursor.getColumnIndex("coursename")));
                course.setTeacherName(cursor.getString(cursor.getColumnIndex("teachername")));
                course.setCourseId(cursor.getString(cursor.getColumnIndex("course_id")));
                course.setStartTime(cursor.getString(cursor.getColumnIndex("course_start_time")));
                course.setEndTime(cursor.getString(cursor.getColumnIndex("course_end_time")));

                courseList.add(course);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return courseList;
    }

    @SuppressLint("Range")
    public List<User> getTeachers() {
        List<User> userList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("user", null, "type=?", new String[]{"teacher"}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setUserId(cursor.getString(cursor.getColumnIndex("userid")));
                user.setName(cursor.getString(cursor.getColumnIndex("name")));
                user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                user.setType(cursor.getString(cursor.getColumnIndex("type")));

                userList.add(user);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return userList;
    }

    public boolean changePassword(String userId, String oldPassword, String newPassword, String confirmPassword) {
        // Verify that the original password matches
        if (validateOldPassword(userId, oldPassword)) {
            // Verify that the new password and confirmation password match
            if (newPassword.equals(confirmPassword)) {
                SQLiteDatabase db = this.getWritableDatabase();

                // Update Password
                ContentValues values = new ContentValues();
                values.put("password", newPassword);

                int rowsAffected = db.update("user", values, "userid = ?", new String[]{userId});

                // Close the database connection
                db.close();

                // Returns whether the update was successful or not
                return rowsAffected > 0;
            }
        }
        // The original password does not match or the new password does not match the confirmation password, return update failure
        return false;
    }

    // A private helper method for verifying the original password
    private boolean validateOldPassword(String userId, String oldPassword) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Verify that the original password matches
        Cursor cursor = db.query("user", new String[]{"password"}, "userid = ? AND password = ?", new String[]{userId, oldPassword}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Close Cursor
            cursor.close();

            // Close the database connection
            db.close();

            // Returns whether the original password matches
            return true;
        } else {
            // Close Cursor
            if (cursor != null) {
                cursor.close();
            }

            // Close the database connection
            db.close();

            // Return the original password does not match
            return false;
        }
    }

    public ArrayList<CalendarDate> getAllDates(String courseNumber, String courseName) {
        ArrayList<CalendarDate> datesList = new ArrayList<>();
        String selectQuery = "SELECT * FROM schedule WHERE course_id = ? AND coursename = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{courseNumber, courseName});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int year = cursor.getInt(cursor.getColumnIndex("year"));
                @SuppressLint("Range") int month = cursor.getInt(cursor.getColumnIndex("month"));
                @SuppressLint("Range") int day = cursor.getInt(cursor.getColumnIndex("day"));
                datesList.add(new CalendarDate(year, month, day));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return datesList;
    }

    // Assuming that the DBOpenHelper class has a method named getAllCourses()
    public List<Course> getAllFilteredCourses(String userId, String userName) {
        List<Course> courseList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Assuming "designatedcourses" is the table name and "course" is another table name.
        String selectQuery = "SELECT designatedcourses.coursename, designatedcourses.course_id, " +
                "course.teachername, course.course_start_time, course.course_end_time " +
                "FROM designatedcourses " +
                "INNER JOIN course ON designatedcourses.course_id = course.course_id " +
                "WHERE designatedcourses.userid = ? AND designatedcourses.name = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{userId, userName});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int Id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String courseId = cursor.getString(cursor.getColumnIndex("course_id"));
                @SuppressLint("Range") String courseName = cursor.getString(cursor.getColumnIndex("coursename"));
                @SuppressLint("Range") String teacherName = cursor.getString(cursor.getColumnIndex("teachername"));
                @SuppressLint("Range") String startTime = cursor.getString(cursor.getColumnIndex("course_start_time"));
                @SuppressLint("Range") String endTime = cursor.getString(cursor.getColumnIndex("course_end_time"));

                // Create a Course object and add it to the list
                Course course = new Course(courseId, courseName, teacherName, startTime, endTime);
                courseList.add(course);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return courseList;
    }

    @SuppressLint("Range")
    public List<Course> getAllCoursesStudent(String userId, String userName) {
        List<Course> courseList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT designatedcourses.id,designatedcourses.coursename, designatedcourses.course_id, " +
                "course.teachername, course.course_start_time, course.course_end_time " +
                "FROM designatedcourses " +
                "INNER JOIN course ON designatedcourses.course_id = course.course_id " +
                "WHERE designatedcourses.userid = ? AND designatedcourses.name = ? AND designatedcourses.type = 'student'";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{userId, userName});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Course course = new Course();
                course.setId(cursor.getLong(cursor.getColumnIndex("id")));
                course.setCourseId(cursor.getString(cursor.getColumnIndex("course_id")));
                course.setCourseName(cursor.getString(cursor.getColumnIndex("coursename")));
                course.setTeacherName(cursor.getString(cursor.getColumnIndex("teachername")));
                course.setStartTime(cursor.getString(cursor.getColumnIndex("course_start_time")));
                course.setEndTime(cursor.getString(cursor.getColumnIndex("course_end_time")));

                courseList.add(course);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return courseList;
    }

    public boolean addCourse(String courseName, String teacherName, String courseId, String startTime, String endTime) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("coursename", courseName);
        values.put("teachername", teacherName);
        values.put("course_id", courseId);
        values.put("course_start_time", startTime);
        values.put("course_end_time", endTime);

        long rowId = db.insert("course", null, values);

        db.close();

        return rowId != -1;
    }

    public List<DesignatedCourse> getDesignatedCourses() {
        List<DesignatedCourse> designatedCourses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                "user_id",
                "name"
        };

        Cursor cursor = db.query("designatedcourses", projection, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String userId = cursor.getString(cursor.getColumnIndex("user_id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));

                DesignatedCourse course = new DesignatedCourse(userId, name);
                designatedCourses.add(course);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();

        return designatedCourses;
    }
}
