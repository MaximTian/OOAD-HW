// package src.main.java.tickets;
package tickets.controller;

import tickets.DBMSOperation;
import tickets.datamodel.*;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private static Connection dbmsConn = DBMSOperation.getDBConnection();
	private PreparedStatement sql;
	private ResultSet res;

	@RequestMapping(value="/register", method = RequestMethod.POST, consumes="application/json")
	public @ResponseBody Response register(@RequestBody User user) {
		System.out.print(" { register: " + user.getUsername());
		Response response = new Response();
		try {
			sql = dbmsConn.prepareStatement("insert into user_Info values(?,?,?,?,?)");
			sql.setString(1, user.getUsername());
			sql.setString(2, user.getPassword());
			sql.setInt(3, user.getGender());
			sql.setString(4, user.getPhone());
			sql.setString(5, user.getTags());
			sql.executeUpdate();

			response.setStatus(true);
			response.setMessage("Successfully registered!");
		} catch (Exception e) {
			response.setStatus(false);
			response.setMessage("username exists! error inserting into database");
			// e.printStackTrace();
		}
		System.out.println(";  Status: " + response.getStatus() + " }");
		return response;
	}


    @RequestMapping(value="/login", method = RequestMethod.POST)
    public @ResponseBody Response login(@RequestBody User user) {
        System.out.print(" { login: " + user.getUsername());
		Response response = new Response();
		try {
		    // query database for user
			String query = String.format("select * from user_Info where username=\"%s\";",
				user.getUsername());
			sql = dbmsConn.prepareStatement(query);
			res = sql.executeQuery();
			
			// if username exists in database
			if (res.next()) {
				String password = res.getString("password");
				// if password is right
				if (password.equals(user.getPassword())) {
					response.setStatus(true);
					response.setMessage("login Successfully");
				}
				else {
					response.setStatus(false);
					response.setMessage("password invalid");
				}
			}
			// username not exists in database
			else {
				response.setStatus(false);
				response.setMessage("username not exist");
			}
		} catch (Exception e) {
			System.out.println("error query");
			e.printStackTrace();
		}
		System.out.println(";  Status: " + response.getStatus() + " }");
        return response;
    }
}

