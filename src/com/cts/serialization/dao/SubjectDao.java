package com.cts.serialization.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cts.serialization.pojo.Book;
import com.cts.serialization.pojo.Subject;
import com.cts.serialization.util.ConnectionUtils;

public class SubjectDao {

	public void addSubject(Subject subject) {
		PreparedStatement stmt = null;
		try {
			Connection con = ConnectionUtils.getConnection();
			stmt = con.prepareStatement("insert into subject values(?,?,?)");
			stmt.setLong(1, subject.getSubjectId());
			stmt.setString(2, subject.getSubtitle());
			stmt.setInt(3, subject.getDurationInHours());
			stmt.execute();
			if(subject.getReferences() != null && !subject.getReferences().isEmpty()) {
				stmt.close();
				stmt = con.prepareStatement("insert into subject_book values(?,?)");
				for(Book book : subject.getReferences()) {
					stmt.setLong(1, subject.getSubjectId());
					stmt.setLong(2, book.getBookId());
					stmt.addBatch();
				}
				stmt.executeBatch();
			}
			System.out.println("Subject added succesfully");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void deleteSubject(long id) {
		PreparedStatement stmt = null;
		int count;
		try {
			Connection con = ConnectionUtils.getConnection();
			stmt = con.prepareStatement("delete from subject_book where subjectid = ?");
			stmt.setLong(1, id);
			stmt.executeUpdate();
			stmt.close();
			stmt = con.prepareStatement("delete from subject where subjectid = ?");
			stmt.setLong(1, id);
			count = stmt.executeUpdate();
			if(count > 0) {
				System.out.println("Subject Deleted Successfully with id " + id);
			}
			else {
				System.out.println("Unable to delete Subject with id "+ id);
			}
		}
		catch(Exception e){
			System.out.println("Unable to delete Subject with id "+ id);	
			e.printStackTrace();
		}
		finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void searchSubject(long id) {
		PreparedStatement stmt = null;
		boolean found = false;
		ResultSet rs = null;
		try {
			Connection con = ConnectionUtils.getConnection();
			stmt = con.prepareStatement("select * from subject where subjectid = ?");
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			while(rs.next()){
				found = true;
				id  = rs.getLong("subjectid");
				String title = rs.getString("subtitle");
				int duration = rs.getInt("durationInHours");
				System.out.println("\nSubject Details");
				System.out.format("\n%10s %15s %15s", "Id", "Subtitle", "DurationInHours");
				System.out.format("\n%10d %15s %15d \n", id, title, duration);
			}
			if(found) {
				stmt.close();
				stmt = con.prepareStatement("select b.* from book b, subject_book sb \r\n" + 
						"where b.bookid = sb.bookid\r\n" + 
						"and sb.subjectid = ?");
				stmt.setLong(1, id);
				rs = stmt.executeQuery();
				int count = 0;
				while(rs.next()){
					id  = rs.getLong("bookId");
					double price = rs.getDouble("price");
					String title = rs.getString("title");
					int volume = rs.getInt("volume");
					Date publishDate = rs.getDate("publishDate");
					if(count ++ == 0) {
						System.out.println("\nReference Book Details");
						System.out.format("\n%10s %15s %15s %15s %15s", "Id", "Price", "Title", "Volume", "Publish Date");
					}
					System.out.format("\n%10d %15.2f %15s %15d %15s \n", id, price, title, volume, publishDate);
				}
			}
			if(!found) {
				System.out.println("Unable to find Subject with id "+ id);
			}
		}
		catch(Exception e) {
			System.out.println("Unable to find Book with id "+ id);	
			e.printStackTrace();
		}
		finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void sortSubjectBySubjectTitle() {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Subject> subjects = new ArrayList<>();
		try {
			Connection con = ConnectionUtils.getConnection();
			stmt = con.prepareStatement("select * from subject");
			rs = stmt.executeQuery();
			while(rs.next()){
				Subject subject = new Subject();
				subject.setSubjectId(rs.getLong("subjectid"));
				subject.setSubtitle(rs.getString("subtitle"));
				subject.setDurationInHours(rs.getInt("durationInHours"));
				subjects.add(subject);
			}
		}
		catch(Exception e) {
			System.out.println("Unable to Sort Subject By Subject Title");	
			e.printStackTrace();
		}
		finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		subjects.sort((Subject s1, Subject s2) -> s1.getSubtitle().compareTo(s2.getSubtitle()));
		if(subjects != null && !subjects.isEmpty()) {
			System.out.println("\nAvailable subjects sort by subtitle");
			System.out.format("\n%10s %15s %15s", "Id", "Subtitle", "DurationInHours");
			for(Subject subject : subjects) {
				System.out.format("\n%10d %15s %15d \n", subject.getSubjectId(), subject.getSubtitle(), subject.getDurationInHours());
			}
		}
	}
}
