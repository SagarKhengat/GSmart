package sagar.khengat.gsmart.util;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

import sagar.khengat.gsmart.model.Area;
import sagar.khengat.gsmart.model.Cart;
import sagar.khengat.gsmart.model.Customer;
import sagar.khengat.gsmart.model.History;
import sagar.khengat.gsmart.model.Product;
import sagar.khengat.gsmart.model.Retailer;
import sagar.khengat.gsmart.model.Store;



/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil
{
	
	
	@SuppressWarnings("rawtypes")
	static Class[] classes = new Class[]{Area.class,Store.class,Customer.class, Retailer.class,Product.class, Cart.class, History.class};
	
	public static void main(String[] args) throws SQLException, IOException {
		writeConfigFile("ormlite_config.txt",classes);
	}

}
