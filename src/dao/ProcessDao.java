package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Member;
import model.Process;
import util.Messages;

/**
 * @author Glaydston Veloso
 * @since 1.0.0
 * @date 08/09/2013
 * @mail glaydston.veloso@plansis.com.br
 */
public class ProcessDao {
	private final boolean lookup = true;
	private ConnectionFactory factory;
	private StringBuffer sql;

	public ProcessDao() {
		factory = new ConnectionFactory();
		factory.connect(lookup);
	}

	/**
	 * Set process in db
	 * 
	 * @param p
	 * @return String
	 */
	public String setProcess(Process p) {
		String message = Messages.INIT;
		sql = new StringBuffer();
		sql.append("INSERT INTO process (name, description, member) VALUES(?, ?, ?)");

		// Get connection from ConnectionFactory()
		Connection con = factory.returnConnection();
		try {
			// Prepare the sql to execute in ConnectionFactory
			PreparedStatement stmt = con.prepareStatement(sql.toString());

			stmt.setString(1, p.getName());
			stmt.setString(2, p.getDescription());
			stmt.setLong(3, p.getMember().getUid());

			factory.insertQuery(stmt);
			stmt.close();
			message = Messages.SCS02.replace("@", p.getName());
			
		} catch (SQLException e) {
			System.out.println("-> ConnectionFactory() > SQLException caught: " + e.getMessage());
			message = Messages.ERR01;
		} catch (Exception e) {
			System.out.println("-> ConnectionFactory() > Exception caught: " + e.getMessage());
			message = Messages.ERR02;
		} finally {
			factory.closeConnection();
		}

		return message;
	}
	/**
	 * Get all process in db
	 * @param name
	 * @return List<Process>
	 */
	public List<Process> getProcess() {
		sql = new StringBuffer();

		sql.append("SELECT uid, name, description, member FROM process");
		
		List<Process> process = new ArrayList<Process>();
		try {
			Process p = null;
			ResultSet rs = factory.executeQuery(sql);
			while (rs.next()) {
				p = new Process();				
				
				p.setUid(rs.getLong("uid"));
				p.setName(rs.getString("name"));	
				p.setDescription(rs.getString("description"));					
				p.setMember(new MemberDao().getMember(rs.getLong("member")));
				
				process.add(p);
			}

			rs.close();

		} catch (SQLException e) {
			System.out.println("-> ConnectionFactory() > SQLException caught: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("-> ConnectionFactory() > Exception caught: " + e.getMessage());
		} finally {
			factory.closeConnection();
		}
		return process;
	}
	
	/**
	 * Get all process from member in db
	 * @param name
	 * @return List<Process>
	 */
	public List<Process> getProcess(String name) {
		sql = new StringBuffer();
		
		sql.append("SELECT p.uid, p.name, p.description, m.uid, m.name, m.email ");
		sql.append("FROM process AS p INNER JOIN member AS m ON p.uid = m.uid   ");
		sql.append("WHERE m.name = '" + name + "'");
		
		List<Process> process = new ArrayList<Process>();
		try {
			Process p = null;
			Member m = null;
			ResultSet rs = factory.executeQuery(sql);
			while (rs.next()) {
				p = new Process();				
				p.setUid(rs.getLong("p.uid"));
				p.setName(rs.getString("p.name"));	
				p.setDescription(rs.getString("p.description"));	
				
				m = new Member();
				m.setUid(rs.getLong("m.uid"));
				m.setName(rs.getString("m.name"));
				m.setEmail(rs.getString("m.email"));
				p.setMember(m);
				
				process.add(p);
			}

			rs.close();

		} catch (SQLException e) {
			System.out.println("-> ConnectionFactory() > SQLException caught: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("-> ConnectionFactory() > Exception caught: " + e.getMessage());
		} finally {
			factory.closeConnection();
		}
		return process;
	}
	
	/**
	 * Get all process from member in db
	 * @param m
	 * @return List<Process>
	 */
	public List<Process> getProcess(Member m) {
		sql = new StringBuffer();
		
		sql.append("SELECT uid, name, description FROM process");
		sql.append("WHERE uid = " + m.getUid());
		
		List<Process> process = new ArrayList<Process>();
		try {
			Process p = null;
			ResultSet rs = factory.executeQuery(sql);
			while (rs.next()) {
				p = new Process();				
				p.setUid(rs.getLong("uid"));
				p.setName(rs.getString("name"));	
				p.setDescription(rs.getString("description"));	
				p.setMember(m);
				
				process.add(p);
			}

			rs.close();

		} catch (SQLException e) {
			System.out.println("-> ConnectionFactory() > SQLException caught: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("-> ConnectionFactory() > Exception caught: " + e.getMessage());
		} finally {
			factory.closeConnection();
		}
		return process;
	}
}
