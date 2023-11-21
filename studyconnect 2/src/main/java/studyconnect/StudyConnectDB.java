package studyconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StudyConnectDB {

	// Returns all the courses from the Courses database table. If there's an error, return null
	public static ArrayList<String> getCourses() {
		String query = "SELECT courseID FROM Courses";
		Connection conn = createConnection();
		ResultSet rs = executeQuery(conn, query);
		ArrayList<String> courses = null;
		
		// If null, no ResultSet due to error
		if (rs == null) {
			return null;
		}
		
		try {
			courses = new ArrayList<>();
			while (rs.next()) {
				String courseID = rs.getString("courseID");
				courses.add(courseID);
			}
			
			if (rs != null) {
				rs.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception ex) {
			System.out.println ("Exception: " + ex.getMessage());
		}
		
		return courses;
	}
	
	
	// Retrieves user information from the Users table from the database and creates a User object if a user with that email exists, otherwise returns null
	public static User getUser(String email) {
		String query = "SELECT * FROM Users WHERE Users.email = \"" + email + "\"";
		Connection conn = createConnection();
		ResultSet rs = executeQuery(conn, query);
		
		// If null, no ResultSet due to error
		if (rs == null) {
			return null;
		}
		
		User user = null;
		
		try {
			if (rs.next()) {
				// User with that email is found
				user = new User();
				user.setUserID((rs.getInt("userID")));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setPassword(rs.getString("password"));
			}
			
			if (rs != null) {
				rs.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception ex) {
			System.out.println ("Exception: " + ex.getMessage());
		}
		
		return user;
	}
	
	// TO-DO 
	// Retrieves study group information from the StudyGroups table from the database and creates a User object if a user with that email exists, otherwise returns null
	public static User getStudyGroup(int studyGroupID) {
		return null;
	}
	
	// Inserts a given user into the Users table in the database and returns true if successful, otherwise false
	public static boolean addUser(User user) {
		String query = "INSERT INTO Users (firstName, lastName, email, phone, password) VALUES (\"" + user.getFirstName() + "\", \""
				+ user.getLastName() + "\", \"" + user.getEmail() + "\", \"" + user.getPhone() + "\", \"" + user.getPassword() + "\")";
		System.out.println("executing query: " + query);
		Connection conn = createConnection();
		boolean res = executeUpdate(conn, query); // Returns true if successfully added, false otherwise
		try {
	 	 conn.close();
   	    } catch (Exception ex) {
   	    	
		}
		return res;
	}
	
	
	// Inserts a given study group into the StudyGroups table in the database and returns true if successful, otherwise false
	public static boolean addStudyGroup(StudyGroups studyGroup) {
		String query = "INSERT INTO StudyGroups (courseID, hostID, location, time, day) VALUES (\"" + studyGroup.getCourseID() + "\", \""
				+ studyGroup.getHostID() + "\", \"" + studyGroup.getLocation() + "\", \"" + studyGroup.getTime() + "\", \"" + studyGroup.getDay() + "\")";
		System.out.println("executing query: " + query);
		Connection conn = createConnection();
		boolean res = executeUpdate(conn, query); // Returns true if successfully added, false otherwise
		try {
	 	 conn.close();
   	    } catch (Exception ex) {
   	    	
		}
		return res;
	}
	
	
	// Executes an sql query. If successful, it returns the ResultSet otherwise it returns null.
	public static ResultSet executeQuery(Connection conn, String sqlQuery) {
		if (conn == null) return null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sqlQuery);
			rs = ps.executeQuery();
			return rs;
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} finally {
			/*try {
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}*/
		}
		return null;
	}
	
	public static Connection createConnection() {
		Connection conn = null;
		try {
			// EDIT THIS: 
			conn = DriverManager.getConnection("jdbc:mysql://localhost/studyconnect?user=root&password=");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return conn;
	}
	
	public static boolean executeUpdate(Connection conn, String sqlQuery) {
		if (conn == null) return false;
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sqlQuery);
			ps.executeUpdate();
			return true;
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} finally {
			/*try {
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}*/
		}
		return false;
	}
}