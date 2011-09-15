package rkutils;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class KjorSql {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException, IOException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(" beans.xml");

/*		
//        XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource("beans.xml"));    	
        PropertyPlaceholderConfigurer cfg = new PropertyPlaceholderConfigurer();
        cfg.setLocation(new ClassPathResource("db.properties"));
        cfg.postProcessBeanFactory(ctx);
*/

        JdbcSqlPoi jdbcSqlPoi = (JdbcSqlPoi)ctx.getBean("jdbcSqlPoi");
        
        String mldInd = "N";
        try {
            mldInd = args[0].trim();
          } // try
          catch (ArrayIndexOutOfBoundsException e) {}
        
        jdbcSqlPoi.kjorSql(mldInd);

	}

}
