package sagar.khengat.gsmart.util;

import android.content.Context;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sagar.khengat.gsmart.R;
import sagar.khengat.gsmart.model.Area;
import sagar.khengat.gsmart.model.Cart;
import sagar.khengat.gsmart.model.Customer;
import sagar.khengat.gsmart.model.History;
import sagar.khengat.gsmart.model.Product;
import sagar.khengat.gsmart.model.Retailer;
import sagar.khengat.gsmart.model.Store;



public class DatabaseHandler {
	private static final String TAG = "DatabaseHandler";

	RuntimeExceptionDao<Area, Integer> areaDao;
	RuntimeExceptionDao<Store, Integer> storeDao;

	private RuntimeExceptionDao<Customer, Integer> customerDao;
	private RuntimeExceptionDao<Retailer, Integer> retailerDao;
	private RuntimeExceptionDao<Product, Integer> productDao;
	private RuntimeExceptionDao<Cart, Integer> cartDao;
	private RuntimeExceptionDao<History, Integer> historyDao;


	private DatabaseHelper databaseHelper;

	private Context context;

	public DatabaseHandler() {

	}

	public DatabaseHandler(Context context) {
		this.context = context;
		initElements();
	}

	public void initElements() {


		areaDao = getHelper().getAreaDao();

		storeDao = getHelper().getStoreDao();

		customerDao = getHelper().getCustomerDao();
		retailerDao = getHelper().getRetailerDao();
		productDao = getHelper().getProductDao();
		cartDao = getHelper().getCartDao();
		historyDao = getHelper().getHistoryDao();




	}




	private DatabaseHelper getHelper() {
		databaseHelper = null;
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(context,
					DatabaseHelper.class);
		}
		return databaseHelper;
	}

	/**
	 * This method to check user exist or not
	 *
	 * @param email
	 * @return true/false
	 */
	public boolean checkCustomer(String email) {
		boolean b = false;
		List <Customer> mListAllStores = fnGetAllCustomer();
		try {
			QueryBuilder < Customer, Integer > qb = customerDao.queryBuilder();

			for (Customer user:
					mListAllStores) {

				if (user.getEmail().equals(email))
				{
					b = true;
				}
				else
				{

				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return b;
	}



	/**
	 * This method to check user exist or not
	 *
	 * @param email
	 * @return true/false
	 */
	public boolean checkRetailer(String email) {
		boolean b = false;
		List <Retailer> mListAllStores = fnGetAllRetailer();
		try {
			QueryBuilder < Retailer, Integer > qb = retailerDao.queryBuilder();

			for (Retailer user:
					mListAllStores) {

				if (user.getName().equals(email))
				{
					b = true;
				}
				else
				{

				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return b;
	}

	public boolean checkStore(String email) {
		boolean b = false;
		List <Store> mListAllStores = fnGetAllStore();
		try {
			QueryBuilder < Store, Integer > qb = storeDao.queryBuilder();

			for (Store user:
					mListAllStores) {

				if (user.getStoreName().equals(email))
				{
					b = true;
				}
				else
				{

				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return b;
	}

	public boolean checkArea(Area email) {
		boolean b = false;
		List <Area> mListAllStores = fnGetAllArea();
		try {
			QueryBuilder < Area, Integer > qb = areaDao.queryBuilder();

			for (Area user:
					mListAllStores) {

				if (user.getAreaName().equals(email.getAreaName()))
				{
					b = true;
				}
				else
				{

				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return b;
	}


	/**
	 * This method to check user exist or not
	 *
	 * @param email
	 * @param password
	 * @return true/false
	 */
	public boolean checkRetailer(String email, String password) {

		boolean b = false;
		List <Retailer> mListAllStores = fnGetAllRetailer();
		try {
			QueryBuilder < Retailer, Integer > qb = retailerDao.queryBuilder();

			for (Retailer user:
					mListAllStores) {

				if (user.getName().equals(email) && user.getPassword().equals(password))
				{
					b = true;
				}
				else
				{

				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return b;
	}

	public boolean checkRetailerContactNo(String username, String contactno) {

		boolean b = false;
		List <Retailer> mListAllStores = fnGetAllRetailer();
		try {
			QueryBuilder < Retailer, Integer > qb = retailerDao.queryBuilder();

			for (Retailer user:
					mListAllStores) {

				if (user.getName().equals(username) && user.getMobno().equals(contactno))
				{
					b = true;
				}
				else
				{

				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return b;
	}

	/**
	 * This method to check user exist or not
	 *
	 * @param email
	 * @param password
	 * @return true/false
	 */
	public boolean checkCustomer(String email, String password) {

		boolean b = false;
		List <Customer> mListAllStores = fnGetAllCustomer();
		try {
			QueryBuilder < Customer, Integer > qb = customerDao.queryBuilder();

			for (Customer user:
					mListAllStores) {

				if (user.getEmail().equals(email) && user.getPassword().equals(password))
				{
					b = true;
				}
				else
				{

				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return b;
	}


	public void addCustomer(Customer user) {
		try
		{
			customerDao.create( user );
			Toast.makeText(context, context.getString(R.string.success_message), Toast.LENGTH_LONG).show();

		} catch(OutOfMemoryError e) {
			e.printStackTrace();
			Toast.makeText( context, "Problem in memory allocation. Please free some memory space and try again.", Toast.LENGTH_LONG ).show();
		} catch(Exception e) {
			Toast.makeText( context, "Problem in adding User. Please try again.", Toast.LENGTH_LONG ).show();

			e.printStackTrace();
		}
	}


	public void addRetailer(Retailer user) {
		try
		{
			retailerDao.create( user );
			Toast.makeText(context, context.getString(R.string.success_message), Toast.LENGTH_LONG).show();

		} catch(OutOfMemoryError e) {
			e.printStackTrace();
			Toast.makeText( context, "Problem in memory allocation. Please free some memory space and try again.", Toast.LENGTH_LONG ).show();
		} catch(Exception e) {
			Toast.makeText( context, "Problem in adding User. Please try again.", Toast.LENGTH_LONG ).show();

			e.printStackTrace();
		}
	}


	public List<Store> fnGetAllStore() {
		List< Store > mListIndustry = new ArrayList<>();

		try {
			QueryBuilder< Store, Integer > queryBuilder = storeDao.queryBuilder();
			PreparedQuery< Store > preparedQuery = null;
			preparedQuery = queryBuilder.prepare();
			mListIndustry = storeDao.query( preparedQuery );
		} catch ( SQLException e ) {
			e.printStackTrace();
		} catch(OutOfMemoryError e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}

		return mListIndustry;
	}



	public List<Customer> fnGetAllCustomer() {
		List< Customer > mListIndustry = new ArrayList<>();

		try {
			QueryBuilder< Customer, Integer > queryBuilder = customerDao.queryBuilder();
			PreparedQuery< Customer > preparedQuery = null;
			preparedQuery = queryBuilder.prepare();
			mListIndustry = customerDao.query( preparedQuery );
		} catch ( SQLException e ) {
			e.printStackTrace();
		} catch(OutOfMemoryError e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}

		return mListIndustry;
	}


	public List<Retailer> fnGetAllRetailer() {
		List< Retailer > mListIndustry = new ArrayList<>();

		try {
			QueryBuilder< Retailer, Integer > queryBuilder = retailerDao.queryBuilder();
			PreparedQuery< Retailer > preparedQuery = null;
			preparedQuery = queryBuilder.prepare();
			mListIndustry = retailerDao.query( preparedQuery );
		} catch ( SQLException e ) {
			e.printStackTrace();
		} catch(OutOfMemoryError e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}

		return mListIndustry;
	}



	public List<History> fnGetAllHistory() {
		List< History > mListIndustry = new ArrayList<>();

		try {
			QueryBuilder< History, Integer > queryBuilder = historyDao.queryBuilder();
			PreparedQuery< History > preparedQuery = null;
			preparedQuery = queryBuilder.prepare();
			mListIndustry = historyDao.query( preparedQuery );
		} catch ( SQLException e ) {
			e.printStackTrace();
		} catch(OutOfMemoryError e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}

		return mListIndustry;
	}

	public List<Product> fnGetAllProduct() {
		List< Product > mListIndustry = new ArrayList<>();

		try {
			QueryBuilder< Product, Integer > queryBuilder = productDao.queryBuilder();
			PreparedQuery< Product > preparedQuery = null;
			preparedQuery = queryBuilder.prepare();
			mListIndustry = productDao.query( preparedQuery );
		} catch ( SQLException e ) {
			e.printStackTrace();
		} catch(OutOfMemoryError e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}

		return mListIndustry;
	}



	public List<Cart> fnGetAllCart() {
		List< Cart > mListIndustry = new ArrayList<>();

		try {
			QueryBuilder< Cart, Integer > queryBuilder = cartDao.queryBuilder();
			PreparedQuery< Cart > preparedQuery = null;
			preparedQuery = queryBuilder.prepare();
			mListIndustry = cartDao.query( preparedQuery );
		} catch ( SQLException e ) {
			e.printStackTrace();
		} catch(OutOfMemoryError e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}

		return mListIndustry;
	}



	public List<Area> fnGetAllArea() {
		List< Area > mListIndustry = new ArrayList<>();

		try {
			QueryBuilder< Area, Integer > queryBuilder = areaDao.queryBuilder();
			PreparedQuery< Area > preparedQuery = null;
			preparedQuery = queryBuilder.prepare();
			mListIndustry = areaDao.query( preparedQuery );
		} catch ( SQLException e ) {
			e.printStackTrace();
		} catch(OutOfMemoryError e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return mListIndustry;
	}


	public void addStore(Store store) {
		try
		{
			storeDao.create( store );
		} catch(OutOfMemoryError e) {
			e.printStackTrace();
			Toast.makeText( context, "Problem in memory allocation. Please free some memory space and try again.", Toast.LENGTH_LONG ).show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	public void addArea(Area area) {
		try
		{
			areaDao.create( area );
		} catch(OutOfMemoryError e) {
			e.printStackTrace();
			Toast.makeText( context, "Problem in memory allocation. Please free some memory space and try again.", Toast.LENGTH_LONG ).show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	public List< Store> fnGetStoreInArea( Area area ) {
		List <Store> mListStores = new ArrayList<>();
		List <Store> mListAllStores = fnGetAllStore();

		try {
//			QueryBuilder < Store, Integer > qb = storeDao.queryBuilder();
//			Where<Store, Integer> where = qb.where();
//
//			where.like( "areaId", area.getAreaId() );//.or().like("customerPrintAs", "%"+nameToSearch+"%");
//
//
//
//			// It filters only data present in DB fetched at the time of sync.
//			PreparedQuery < Store> pq = where.prepare();
//			mListStores = storeDao.query( pq );


			for (Store store : mListAllStores)
			{
				if(store.getArea().getAreaId()==area.getAreaId())
				{
					mListStores.add(store);
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return mListStores;
	}

	public void addProduct(Product product) {
		try
		{
			productDao.create( product );
		} catch(OutOfMemoryError e) {
			e.printStackTrace();
			Toast.makeText( context, "Problem in memory allocation. Please free some memory space and try again.", Toast.LENGTH_LONG ).show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void addToCart(Cart product) {
		try
		{
			cartDao.create( product );
			Toast.makeText( context, "Product Added in Cart Successfully.", Toast.LENGTH_LONG ).show();
		} catch(OutOfMemoryError e) {
			e.printStackTrace();
			Toast.makeText( context, "Problem in memory allocation. Please free some memory space and try again.", Toast.LENGTH_LONG ).show();
		} catch(Exception e) {
			Toast.makeText( context, "Something went wrong. Please try again.", Toast.LENGTH_LONG ).show();
			e.printStackTrace();
		}
	}


	public int fnGetCartCount(Store store)
	{
		int i = 0;


		List <Cart> mListStores = new ArrayList<>();
		List <Cart> mListAllStores = fnGetAllCart();

		try {
//			QueryBuilder < Store, Integer > qb = storeDao.queryBuilder();
//			Where<Store, Integer> where = qb.where();
//
//			where.like( "areaId", area.getAreaId() );//.or().like("customerPrintAs", "%"+nameToSearch+"%");
//
//
//
//			// It filters only data present in DB fetched at the time of sync.
//			PreparedQuery < Store> pq = where.prepare();
//			mListStores = storeDao.query( pq );


			for (Cart cart : mListAllStores)
			{
				if(cart.getStore().getStoreId()==store.getStoreId())
				{
					mListStores.add(cart);
				}
			}

			if(mListStores.size()!=0) {
				i = mListStores.size();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}


		return i;
	}


	public List<Cart> fnGetAllCart(Store store)
	{

		List <Cart> mListStores = new ArrayList<>();
		List <Cart> mListAllStores = fnGetAllCart();

		try {
//			QueryBuilder < Store, Integer > qb = storeDao.queryBuilder();
//			Where<Store, Integer> where = qb.where();
//
//			where.like( "areaId", area.getAreaId() );//.or().like("customerPrintAs", "%"+nameToSearch+"%");
//
//
//
//			// It filters only data present in DB fetched at the time of sync.
//			PreparedQuery < Store> pq = where.prepare();
//			mListStores = storeDao.query( pq );


			for (Cart cart : mListAllStores)
			{
				if(cart.getStore().getStoreId()==store.getStoreId())
				{
					mListStores.add(cart);
				}
			}


		} catch (Exception e) {

			e.printStackTrace();
		}


		return mListStores;
	}



	public void deleteCartitem(Cart cart) {
		try
		{
			cartDao.delete(cart);
			Toast.makeText(context, "Product Deleted Successfully", Toast.LENGTH_LONG).show();

		}
		catch(OutOfMemoryError e)
		{
			e.printStackTrace();
			Toast.makeText(context, "Problem in memory allocation. Please free some memory space and try again.", Toast.LENGTH_LONG).show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	public void fnDeleteAllCart(Store store)
	{

		List <Cart> mListStores = new ArrayList<>();
		List <Cart> mListAllStores = fnGetAllCart();

		try {
//			QueryBuilder < Store, Integer > qb = storeDao.queryBuilder();
//			Where<Store, Integer> where = qb.where();
//
//			where.like( "areaId", area.getAreaId() );//.or().like("customerPrintAs", "%"+nameToSearch+"%");
//
//
//
//			// It filters only data present in DB fetched at the time of sync.
//			PreparedQuery < Store> pq = where.prepare();
//			mListStores = storeDao.query( pq );


			for (Cart cart : mListAllStores)
			{
				if(cart.getStore().getStoreId()==store.getStoreId())
				{
					cartDao.delete(cart);
				}
			}


		} catch (Exception e) {

			e.printStackTrace();
		}



	}



	public List<History> fnGetAllHistory(Store store)
	{

		List <History> mListStores = new ArrayList<>();
		List <History> mListAllStores = fnGetAllHistory();

		try {
//			QueryBuilder < Store, Integer > qb = storeDao.queryBuilder();
//			Where<Store, Integer> where = qb.where();
//
//			where.like( "areaId", area.getAreaId() );//.or().like("customerPrintAs", "%"+nameToSearch+"%");
//
//
//
//			// It filters only data present in DB fetched at the time of sync.
//			PreparedQuery < Store> pq = where.prepare();
//			mListStores = storeDao.query( pq );


			for (History event : mListAllStores) {
				boolean isFound = false;
				// check if the event name exists in noRepeat
				for (History e : mListStores) {
					if (e.getProductName().equals(event.getProductName()) && e.getProductBrand().equals(event.getProductBrand()))
					isFound = true;
					break;
				}
				if (!isFound) mListStores.add(event);
			}


//			for (History cart : mListAllStores)
//			{
//
//				mListStores.add(cart);
//
//				for(History carta : mListStores)
//				{
//
//					if (  cart.getProductName().equals(carta.getProductName()) && cart.getProductBrand().equals(carta.getProductBrand()))
//					{
//						mListAllStores.remove(carta);
//					}
//				}
//			}


		} catch (Exception e) {

			e.printStackTrace();
		}


		return mListStores;
	}



	public void addProductHistory(History product) {
		try
		{
			historyDao.createIfNotExists( product );
		} catch(OutOfMemoryError e) {
			e.printStackTrace();
			Toast.makeText( context, "Problem in memory allocation. Please free some memory space and try again.", Toast.LENGTH_LONG ).show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Product fnGetProductFromCart(Cart cart)
	{



		List <Product> mListAllStores = fnGetAllProduct();

		try {
//			QueryBuilder < Store, Integer > qb = storeDao.queryBuilder();
//			Where<Store, Integer> where = qb.where();
//
//			where.like( "areaId", area.getAreaId() );//.or().like("customerPrintAs", "%"+nameToSearch+"%");
//
//
//
//			// It filters only data present in DB fetched at the time of sync.
//			PreparedQuery < Store> pq = where.prepare();
//			mListStores = storeDao.query( pq );


			for (Product product : mListAllStores)
			{
					if(product.getProductId() == cart.getProductCartId())
					{
						return product;
					}
			}


		} catch (Exception e) {

			e.printStackTrace();
		}


		return null;
	}


	public Cart fnGetCartFromCartHistory(History cart)
	{



		List <Cart> mListAllStores = fnGetAllCart();

		try {
//			QueryBuilder < Store, Integer > qb = storeDao.queryBuilder();
//			Where<Store, Integer> where = qb.where();
//
//			where.like( "areaId", area.getAreaId() );//.or().like("customerPrintAs", "%"+nameToSearch+"%");
//
//
//
//			// It filters only data present in DB fetched at the time of sync.
//			PreparedQuery < Store> pq = where.prepare();
//			mListStores = storeDao.query( pq );


			for (Cart product : mListAllStores)
			{
				if(product.getProductCartId() == cart.getProductCartId())
				{
					return product;
				}
			}


		} catch (Exception e) {

			e.printStackTrace();
		}


		return null;
	}


	public void updateCustomerPassword(String Email, String Password)
	{
		try
		{
			UpdateBuilder<Customer, Integer> updateBuilder = customerDao.updateBuilder();
			updateBuilder.where().eq("email",Email);
			updateBuilder.updateColumnValue("password",Password);
			updateBuilder.update();
			Toast.makeText( context, "Password changed Successfully...", Toast.LENGTH_LONG ).show();
		} catch(OutOfMemoryError e)
		{
			e.printStackTrace();
			Toast.makeText( context, "Problem in updating password try again.", Toast.LENGTH_LONG ).show();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			Toast.makeText( context, "Problem in updating password try again.", Toast.LENGTH_LONG ).show();

		}
	}

	public void updateRetailerPassword(String Email, String Password)
	{
		try
		{
			UpdateBuilder<Retailer, Integer> updateBuilder = retailerDao.updateBuilder();
			updateBuilder.where().eq("email",Email);
			updateBuilder.updateColumnValue("password",Password);
			updateBuilder.update();
			Toast.makeText( context, "Password changed Successfully...", Toast.LENGTH_LONG ).show();
		} catch(OutOfMemoryError e)
		{
			e.printStackTrace();
			Toast.makeText( context, "Problem in updating password try again.", Toast.LENGTH_LONG ).show();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			Toast.makeText( context, "Problem in updating password try again.", Toast.LENGTH_LONG ).show();

		}
	}
}