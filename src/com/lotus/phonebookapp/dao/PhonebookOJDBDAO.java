package com.lotus.phonebookapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.lotus.phonebookapp.beans.Company;
import com.lotus.phonebookapp.beans.Contact;

public class PhonebookOJDBDAO implements PhonebookDAO {
	private static final PhonebookOJDBDAO INSTANCE = new PhonebookOJDBDAO();

	public static PhonebookOJDBDAO getInstance() {
		return INSTANCE;
	}

	private PhonebookOJDBDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(
					"An error occured during database connection");
		}
	}

	private static Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(
				"jdbc:oracle:thin:@localhost:1522:xe", "ernest", "password");
		connection.setAutoCommit(false);
		return connection;
	}

	
	@Override
	public List<Contact> contactList() {
		List<Contact> contacts = new ArrayList<Contact>();
		Connection connection = null;
		Statement statement = null;

		try {
			connection = getConnection();
			statement = connection.createStatement();
			String sqlStatement = "SELECT * FROM contacts";
			ResultSet rs = statement.executeQuery(sqlStatement);

			while (rs.next()) {
				contacts.add(getContactFromResult(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Database error.");
		} finally {

			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					throw new RuntimeException("Unable to close statement");
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new RuntimeException("Unable to close connection");
				}
			}
		}

		return contacts;
	}

	
	@Override
	public Company getCompanyByContact(Contact contact) {
		List<Company> companies = companyList();
		for (Company comp : companies) {
			if (comp.getCompanyId() == contact.getCompanyId()) {
				return comp;
			}
		}

		return null;
	}

	private  List<Company> companyList() {
		List<Company> companies = new ArrayList<Company>();
		Connection connection = null;
		Statement statement = null;

		try {
			connection = getConnection();
			statement = connection.createStatement();
			String sqlStatement = "SELECT * FROM companies";
			ResultSet rs = statement.executeQuery(sqlStatement);

			while (rs.next()) {
				companies.add(getCompanyFromResult(rs));
			}
		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			e.printStackTrace();
			throw new RuntimeException("An error occured in the database");
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					throw new RuntimeException("Unable to close statement");
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new RuntimeException("Unable to close connection");
				}
			}
		}

		return companies;
	}

	
	@Override
	public Contact getByName(String contactName) {
		Connection connection = null;
		Statement statement = null;
		Contact contact = null;
		
		try {
			connection = getConnection();
			statement = connection.createStatement();
			String sqlStatement = "Select * from contacts where name = '"+contactName+"'";
			statement.executeQuery("alter session set NLS_COMP=ANSI");
			statement.executeQuery("alter session set NLS_SORT=BINARY_CI");
			ResultSet rs = statement.executeQuery(sqlStatement);
			if(rs.next()) {
				contact = getContactFromResult(rs);
			}
		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			e.printStackTrace();
			throw new RuntimeException("An error occured in the database");
		}finally{
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					throw new RuntimeException("Unable to close statement");
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new RuntimeException("Unable to close connection");
				}
			}
		}
		return contact;
	}

	
	@Override
	public List<Contact> search(String keyword) {
		List<Contact> contacts = new ArrayList<Contact>();
		Connection connection = null;
		Statement statement = null;

		try {
			connection = getConnection();
			statement = connection.createStatement();
			statement.execute("alter session set NLS_COMP=ANSI");
			statement.execute("alter session set NLS_SORT=BINARY_CI");
			statement.execute("ALTER SESSION SET NLS_COMP=LINGUISTIC");
			String sqlStatement = "SELECT * FROM contacts where name like '%"+keyword+"%'";
			ResultSet rs = statement.executeQuery(sqlStatement);
			while (rs.next()) {
				contacts.add(getContactFromResult(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Database error.");
		} finally {

			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					throw new RuntimeException("Unable to close statement");
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new RuntimeException("Unable to close connection");
				}
			}
		}

		return contacts;
	}

	
	@Override
	public void delete(String contactName) {
		Connection connection = null;
		Statement statement = null;
		
		try {
			connection = getConnection();
			statement = connection.createStatement();
			statement.execute("alter session set NLS_COMP=ANSI");
			statement.execute("alter session set NLS_SORT=BINARY_CI");
			String sqlStatement = "DELETE FROM contacts where name = '"+contactName+"'";
			statement.executeUpdate(sqlStatement);
			connection.commit();
		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			e.printStackTrace();
			throw new RuntimeException("An error occured in the database");
		}finally{
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					throw new RuntimeException("Unable to close statement");
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new RuntimeException("Unable to close connection");
				}
			}
		}
	}

	
	@Override
	public void update(String contactName, String newNumber) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		

		try {
			connection = getConnection();
			doCaseInsensitive(connection);
			preparedStatement = connection
					.prepareStatement("UPDATE contacts SET contact_number = ? WHERE name = ?");
			preparedStatement.setString(1, newNumber);
			preparedStatement.setString(2, contactName);
			preparedStatement.executeUpdate();
			connection.commit();
			System.out.println("Contact Updated");
		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			e.printStackTrace();
			throw new RuntimeException("An error occured in the database");
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					throw new RuntimeException("Unable to close statement");
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new RuntimeException("Unable to close connection");
				}
			}
		}
	}

	private void doCaseInsensitive(Connection connection) throws SQLException {
		Statement sstatement = connection.createStatement();
		sstatement.execute("alter session set NLS_COMP=ANSI");
		sstatement.execute("alter session set NLS_SORT=BINARY_CI");
	}

	
	@Override
	public void create(Contact contact) {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = getConnection();
			statement = connection
					.prepareStatement("insert into contacts (id, name, birthday, vipYN, company_id,contact_number) values (contacts_seq.nextval,?,?,?,?,?)");
			 
			statement.setString(1, contact.getContactName());
			Date sqlBirthday = null;
			if (contact.getContactBirthday() != null) {
				sqlBirthday = new Date(contact.getContactBirthday().getTime());
			}
			statement.setDate(2, sqlBirthday);
			statement.setBoolean(3, contact.isVIP());
			statement.setLong(4, contact.getCompanyId());
			long sqlContactNumber = Long.parseLong(contact.getContactNumber());
			statement.setLong(5, sqlContactNumber);
			doCaseInsensitive(connection);
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {

			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			e.printStackTrace();
			throw new RuntimeException("An error occured in the database");
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					throw new RuntimeException("Unable to close statement");
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new RuntimeException("Unable to close connection");
				}
			}
		}
	}

	private Contact getContactFromResult(ResultSet resultSet)
			throws SQLException {
		
		long contactId = resultSet.getLong("ID");
		String contactName = resultSet.getString("NAME");
		String contactNumber = resultSet.getString("CONTACT_NUMBER");
		Date contactBirthday = resultSet.getDate("BIRTHDAY");
		boolean isVIP = resultSet.getBoolean("VIPYN");
		long companyId = resultSet.getLong("COMPANY_ID");
		Contact contact = new Contact.Builder(contactName, contactNumber,
				isVIP, companyId).birthday(contactBirthday).id(contactId)
				.build();

		return contact;

	}

	private static Company getCompanyFromResult(ResultSet resultSet)
			throws SQLException {

		long companyId = resultSet.getLong("ID");
		String companyName = resultSet.getString("COMPANY_NAME");
		String companyCode = resultSet.getString("CODE");
		String companyDescription = resultSet.getString("DESCRIPTION");

		Company company = new Company.Builder(companyId, companyName,
				companyCode, companyDescription).build();

		return company;

	}

}
