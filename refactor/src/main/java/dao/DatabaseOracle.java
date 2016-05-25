package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Datasource implementation using Hikari JDBC connection pool.
 * @see <a href="https://github.com/brettwooldridge/HikariCP">HikariCP</a>
 */
public class DatabaseOracle {
	private static DataSource dataSource;

	static {
		HikariConfig config = new HikariConfig();
		//optional property. Add specific driver to libs and specify below if necessary
		//config.setDriverClassName("oracle.jdbc.ThinDriver");
		config.setJdbcUrl("jdbc:oracle:thin:@prod:1521:DB_NAME");
		config.setUsername("admin");
		config.setPassword("beefhead");
		config.setMaximumPoolSize(18);
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "50");
		dataSource = new HikariDataSource(config);
	}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

}
