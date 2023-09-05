package dao;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import org.springframework.jdbc.core.*;
import vo.*;

public class NoticeDao {
	private JdbcTemplate jdbc;
	
	public NoticeDao(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}
}
