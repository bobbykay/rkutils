package rkutils.repository;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.StatementCallback;

public class GeneriskQueryDaoImpl implements GeneriskQueryDao {
	JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		System.out.println(jdbcTemplate.getDataSource().toString());
	}
	
	public List getAlleRader(String sqlStatement, int antallKolonner) {

		RowMapper mapper = new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				ResultSetMetaData rsmd = rs.getMetaData();
//				int numberOfColumns = rsmd.getColumnCount();
				int antallKolonner = rsmd.getColumnCount();
				
				Object[] o = new Object[antallKolonner];

				for (int j = 0; j < antallKolonner; j++) {
					o[j] = rs.getString(j+1);
				}
				
				return o;
				
/*				return new Object(rs.getInt("EVENT_ID"),
					rs.getString("title"),
					rs.getDate("EVENT_DATE"));
*/					
			}
		};
		return jdbcTemplate.query(sqlStatement, mapper);		
	}


}

