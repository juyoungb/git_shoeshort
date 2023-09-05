package dao;

import java.util.*;
import java.sql.*;
import javax.sql.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import vo.*;

public class AdMainDao {
	private JdbcTemplate jdbc;
	public AdMainDao(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}
	
}
