package dao;

import java.util.*;
import java.sql.*;
import javax.sql.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import vo.*;

public class WalkingDao {
	private JdbcTemplate jdbc;
	
	public WalkingDao(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}

}
