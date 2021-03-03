package ncu.im3069.Group24.app;

import java.sql.*;
import java.time.LocalDateTime;

import org.json.*;

import ncu.im3069.Group24.app.Hotel;
import ncu.im3069.Group24.util.DBMgr;

public class HotelHelper {
    private HotelHelper() {
        
    }
    
    private static HotelHelper hh;
    private Connection conn = null;
    private PreparedStatement pres = null;
    private Connection conn2 = null;
	private PreparedStatement pres2 = null;
    
    public static HotelHelper getHelper() {
        /** Singleton檢查是否已經有HotelProductHelper物件，若無則new一個，若有則直接回傳 */
        if(hh == null) hh = new HotelHelper();
        
        return hh;
    }
    
    
    /**************************管理店家相關method****************************/ 
    
    
    /**
     * 建立該名店家至資料庫
     *
     * @param h 一名店家之Hotel物件
     * @return the JSON object 回傳SQL指令執行之結果
     */
    public JSONObject create(Hotel h) {
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "INSERT INTO `missa`.`tblhotel`(`hotel_name`, `hotel_email`, `hotel_password`, `hotel_modified`, `hotel_created`, `hotel_image`, `hotel_description`, `hotel_phone`, `hotel_country_id`, `hotel_city_id`, `hotel_address`)"
                    + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            /** 取得所需之參數 */
            String name = h.getName();
            String email = h.getEmail();
            String password = h.getPassword();
            String image = h.getImage();
            String description = h.getDescribe();
            String phone = h.getPhone();
            int country_id = h.getCountryId();
            int city_id = h.getCityId();           
            String address = h.getAddress();
                       
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, name);
            pres.setString(2, email);
            pres.setString(3, password);
            pres.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            pres.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            pres.setString(6, image);
            pres.setString(7, description);
            pres.setString(8, phone);
            pres.setInt(9, country_id);
            pres.setInt(10, city_id);
            pres.setString(11, address);
            
            /** 執行新增之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();
            
            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(pres, conn);
        }

        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);

        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("time", duration);
        response.put("row", row);

        return response;
    }
    
    
    /**
     * 更新一名店家店家資料
     *
     * @param h 一名店家之Hotel物件
     * @return the JSONObject 回傳SQL指令執行結果與執行之資料
     */
    public JSONObject update(Hotel h) {
        /** 紀錄回傳之資料 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "Update `missa`.`tblhotel` SET `hotel_name` = ? , `hotel_password` = ?"
            		+ ", `hotel_phone` = ?, `hotel_address` = ?, `hotel_description` = ?, `hotel_image` = ?"
            		+ ", `hotel_country_id` = ?, `hotel_city_id` = ?, `hotel_modified` = ? WHERE `hotel_email` = ?";
            /** 取得所需之參數 */
            String name = h.getName();
            String email = h.getEmail();
            String password = h.getPassword();
            String phone = h.getPhone();
            String address = h.getAddress();
            String description = h.getDescribe();
            String image = h.getImage();
            int country_id = h.getCountryId();
            int city_id = h.getCityId();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, name);
            pres.setString(2, password);
            pres.setString(3, phone);
            pres.setString(4, address);
            pres.setString(5, description);
            pres.setString(6, image);
            pres.setInt(7, country_id);
            pres.setInt(8, city_id);
            pres.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
            pres.setString(10, email);
            /** 執行更新之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(pres, conn);
        }
        
        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
        
        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
    
    /**
     * 透過店家編號（ID）刪除店家
     *
     * @param id 店家編號
     * @return the JSONObject 回傳SQL執行結果
     */
    public JSONObject deleteByID(int id) {
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            
            /** SQL指令 */
            String sql = "DELETE FROM `missa`.`tblhotel` WHERE `hotel_id` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, id);
            /** 執行刪除之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }

        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
        
        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);

        return response;
    }
    
    
    /**
     * 檢查該名店家之電子郵件信箱是否重複註冊
     *
     * @param p 一名店家之Product物件
     * @return boolean 若重複註冊回傳False，若該信箱不存在則回傳True
     */
    public boolean checkDuplicate(Hotel h){
        /** 紀錄SQL總行數，若為「-1」代表資料庫檢索尚未完成 */
        int row = -1;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT count(*) FROM `missa`.`tblhotel` WHERE `hotel_email` = ?";
            
            /** 取得所需之參數 */
            String email = h.getEmail();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, email);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 讓指標移往最後一列，取得目前有幾行在資料庫內 */
            rs.next();
            row = rs.getInt("count(*)");
            System.out.print(row);

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }
        
        /** 
         * 判斷是否已經有一筆該電子郵件信箱之資料
         * 若無一筆則回傳False，否則回傳True 
         */
        return (row == 0) ? false : true;
    }

	public JSONObject getLogin(String email, String password) {
		  /** 新建一個 Hotel物件之 h 變數，用於紀錄每一位查詢回之會員資料 */
        Hotel h = null;
        /** 用於儲存檢索回應之店家，以JSONArray方式儲存 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `missa`.`tblhotel` WHERE `hotel_email`= ? AND `hotel_password`= ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, email);
            pres.setString(2, password);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            /** 正確來說資料庫只會有一筆該會員編號之資料，因此其實可以不用使用 while 迴圈 */
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;
                
                /** 將 ResultSet 之資料取出 */
                int h_id = rs.getInt("hotel_id");
                String name = rs.getString("hotel_name");
                String image = rs.getString("hotel_image");
                String description = rs.getString("hotel_description");
                String phone = rs.getString("hotel_phone");
                int countryid = rs.getInt("hotel_country_id");
                int cityid = rs.getInt("hotel_city_id");
                String address = rs.getString("hotel_address");
                /** 將每一筆店家資料產生一名新HotelMember物件 */
                h = new Hotel(h_id, email,password, name, image, description, phone, countryid, cityid, address, null, null);
                /** 取出該名店家之資料並封裝至 JSONsonArray 內 */
                jsa.put(h.getData());
            }
            
        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }
        
        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
        if(jsa.toString().equals("[]")) {
        	return null;
        }
        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
	}
	  /**
     * 取得該名店家之更新時間
     *
     * @param h 一名店家之Hotel物件
     * @return the JSON object 回傳該名店家之更新時間（以JSONObject進行封裝）
     */
    public JSONObject getTimesStatus(Hotel h) {
        /** 用於儲存該名店家所檢索之更新時間與店家組別之資料 */
        JSONObject jso = new JSONObject();
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `missa`.`tblhotel` WHERE `hotel_id` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, h.getID());
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            /** 正確來說資料庫只會有一筆該電子郵件之資料，因此其實可以不用使用 while迴圈 */
            while(rs.next()) {
                /** 將 ResultSet 之資料取出 */
                String modifiedTimes = rs.getString("hotel_modified");
                String createdTimes = rs.getString("hotel_created");
                
                /** 將其封裝至JSONObject資料 */
                jso.put("modifiedTimes", modifiedTimes);
                jso.put("createdTimes", createdTimes);
                
            }
            
        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }

        return jso;
    }
    
 
 
    

   /**************************檢視店家相關method****************************/ 
    
    public JSONObject getAll() {
    	System.out.println("enter Class ProductHelper method getAll");
        /** 新建一個 Product 物件之 p 變數，用於紀錄每一位查詢回之商品資料 */
    	Hotel h = null;
        /** 用於儲存所有檢索回之商品，以JSONArray方式儲存 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 : 取得所有店家資料*/
            String sql = "SELECT * FROM `missa`.`tblhotel`";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;
                
                /** 將 ResultSet 之資料取出 */
                int hotel_id = rs.getInt("hotel_id");
                String hotel_email = rs.getString("hotel_email");
                String hotel_name = rs.getString("hotel_name");
                String hotel_image = rs.getString("hotel_image");
                Date hotel_modified = rs.getDate("hotel_modified");
                Date hotel_created = rs.getDate("hotel_created");
                String hotel_phone = rs.getString("hotel_phone");
                String hotel_description = rs.getString("hotel_description");
                int hotel_country_id = rs.getInt("hotel_country_id");
                int hotel_city_id = rs.getInt("hotel_city_id");
                String hotel_address = rs.getString("hotel_address");
                
                /*****************取得店家國家名稱****************/
                String hotel_country_name = getCountryName(hotel_country_id);
                //System.out.println(hotel_country_name);
                String hotel_city_name = getCityName(hotel_city_id);
                //System.out.println(hotel_city_name);
                Double hotel_lowest_price = getLowestPrice(hotel_id);
                //System.out.println(hotel_lowest_price);
                /*
                String countryname_sql = "SELECT `country_name` " + 
                		"FROM `missa`.`tblcountry`" + 
                		"WHERE `country_id` = ?";
                pres = conn.prepareStatement(countryname_sql);
                pres.setInt(1, hotel_country_id);
                rs_countryname = pres.executeQuery();

                exexcute_sql = pres.toString();
                System.out.println(exexcute_sql);
                while(rs_countryname.next()) {
                hotel_country_name = rs_countryname.getString("country_name");
                System.out.println(hotel_country_name);
                }
                */
                /*
                新建sql取得店家城市名稱
                String cityname_sql = "SELECT `city_name` " + 
                		"FROM `missa`.`tblcity`" + 
                		"WHERE `city_id` = ?";
                pres = conn.prepareStatement(cityname_sql);
                pres.setInt(1, hotel_city_id);
                rs_cityname = pres.executeQuery();

                exexcute_sql = pres.toString();
                System.out.println(exexcute_sql);
                String hotel_city_name = rs_cityname.getString("city_name");
                新建sql取得房間最低價
                String lowestprice_sql = "SELECT min(cast(room_price as decimal(9,2))) " + 
                		"FROM `missa`.`tblroom`" + 
                		"WHERE `hotel_id` = ?";
                pres = conn.prepareStatement(lowestprice_sql);
                pres.setInt(1, hotel_id);
                rs_lowestprice = pres.executeQuery();

                exexcute_sql = pres.toString();
                System.out.println(exexcute_sql);
                Double hotel_lowest_price = rs_lowestprice.getDouble("min(cast(room_price as decimal(9,2)))");
        */       
                /****************************************************/
                /** 將每一筆商品資料產生一名新Product物件 */
                //System.out.println(hotel_id);
                //System.out.println(hotel_name);
                //System.out.println(hotel_image);
                //System.out.println(hotel_country_id);
                //System.out.println(hotel_city_id);
                //System.out.println(hotel_country_name);
                //System.out.println(hotel_city_name);
                //System.out.println(hotel_lowest_price);
                h = new Hotel(hotel_id, hotel_email, hotel_name, hotel_image, hotel_description, hotel_phone,
                		hotel_modified, hotel_created, hotel_country_name, hotel_city_name, hotel_address, hotel_lowest_price);
                /** 取出該項商品之資料並封裝至 JSONsonArray 內 */
                jsa.put(h.getSearchData());
            }
            
            

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }
        
        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
        
        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);
        System.out.println("response.put sucessfully");
        
        return response;
    }
    
    /** 使用國家編號取得店家國家名稱*/
    public String getCountryName(int hotel_country_id) {
    	System.out.println("enter Class ProductHelper method getCountryName");
    	 
    	
    	String exexcute_sql = "";
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs_countryname = null;
        String hotel_country_name="";
        
        try {
        	  	
            /** 取得資料庫之連線 */
            conn2 = DBMgr.getConnection();
            /** SQL指令 : 取得所有店家資料*/
            String countryname_sql = "SELECT `country_name` " + 
            		"FROM `missa`.`tblcountry`" + 
            		"WHERE `country_id` = ?";
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres2 = conn2.prepareStatement(countryname_sql);
            pres2.setInt(1, hotel_country_id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs_countryname = pres2.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres2.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs_countryname.next()) {              
                /** 將 ResultSet 之資料取出 */
                hotel_country_name = rs_countryname.getString("country_name");
            }
                       

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs_countryname, pres2, conn2);
        }
              
        return hotel_country_name;
    }
    
    /** 使用國家名稱取得店家國家編號*/
    public int getByCountryId(String hotel_country_name) {
    	System.out.println("enter Class ProductHelper method getCountryId");
    	 
    	
    	String exexcute_sql = "";
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs_countryId = null;
        int hotel_country_id=0;
        
        try {
        	  	
            /** 取得資料庫之連線 */
            conn2 = DBMgr.getConnection();
            /** SQL指令 : 取得所有店家資料*/
            String countryId_sql = "SELECT `country_id` " + 
            		"FROM `missa`.`tblcountry`" + 
            		"WHERE `country_name` = ?";
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres2 = conn2.prepareStatement(countryId_sql);
            pres2.setString(1, hotel_country_name);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs_countryId = pres2.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres2.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs_countryId.next()) {              
                /** 將 ResultSet 之資料取出 */
                hotel_country_id = rs_countryId.getInt("country_id");
            }
                       

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs_countryId, pres2, conn2);
        }
              
        return hotel_country_id;
    }
    
    /** 使用城市編號取得店家城市名稱*/
    public String getCityName(int hotel_city_id) {
    	System.out.println("enter Class ProductHelper method getCityName");
    	 
    	
    	String exexcute_sql = "";
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs_cityname = null;
        String hotel_city_name="";
        
        try {
        	  	
            /** 取得資料庫之連線 */
            conn2 = DBMgr.getConnection();
            /** SQL指令 : 取得所有店家資料*/
            String cityname_sql = "SELECT `city_name` " + 
            		"FROM `missa`.`tblcity`" + 
            		"WHERE `city_id` = ?";
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres2 = conn2.prepareStatement(cityname_sql);
            pres2.setInt(1, hotel_city_id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs_cityname = pres2.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres2.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs_cityname.next()) {              
                /** 將 ResultSet 之資料取出 */
                hotel_city_name = rs_cityname.getString("city_name");
            }
                       

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs_cityname, pres2, conn2);
        }
              
        return hotel_city_name;
    }
    
    
    /** 使用城市名稱取得店家城市編號*/
    public int getByCityId(String hotel_city_name) {
    	System.out.println("enter Class ProductHelper method getCityId");
    	 
    	
    	String exexcute_sql = "";
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs_cityId = null;
        int hotel_city_id=0;
        
        try {
        	  	
            /** 取得資料庫之連線 */
            conn2 = DBMgr.getConnection();
            /** SQL指令 : 取得所有店家資料*/
            String cityId_sql = "SELECT `city_id` " + 
            		"FROM `missa`.`tblcity`" + 
            		"WHERE `city_name` = ?";
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres2 = conn2.prepareStatement(cityId_sql);
            pres2.setString(1, hotel_city_name);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs_cityId = pres2.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres2.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs_cityId.next()) {              
                /** 將 ResultSet 之資料取出 */
                hotel_city_id = rs_cityId.getInt("city_id");
            }
                       

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs_cityId, pres2, conn2);
        }
              
        return hotel_city_id;
    }
    
    /** 使用店家編號取得店家最低房間價格*/
    public Double getLowestPrice(int hotel_id) {
    	System.out.println("enter Class ProductHelper method getLowestPrice");
    	 
    	
    	String exexcute_sql = "";
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs_lowestprice = null;
        Double hotel_lowest_price = 0.0;
        
        try {
        	  	
            /** 取得資料庫之連線 */
            conn2 = DBMgr.getConnection();
            /** SQL指令 : 取得所有店家資料*/
            String lowestprice_sql = "SELECT min(cast(room_price as decimal(9,2))) " + 
            		"FROM `missa`.`tblroom`" + 
            		"WHERE `hotel_id` = ?";
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres2 = conn2.prepareStatement(lowestprice_sql);
            pres2.setInt(1, hotel_id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs_lowestprice = pres2.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres2.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs_lowestprice.next()) {              
                /** 將 ResultSet 之資料取出 */
                hotel_lowest_price = rs_lowestprice.getDouble("min(cast(room_price as decimal(9,2)))");
            }
                       

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs_lowestprice, pres2, conn2);
        }
              
        return hotel_lowest_price;
    }
    
    

    public JSONObject getById(String id) {
    	System.out.println("enter HotelHelper method getById");
        /** 新建一個 Product 物件之 m 變數，用於紀錄每一位查詢回之商品資料 */
        Hotel h = null;
        /** 用於儲存所有檢索回之會員，以JSONArray方式儲存 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `missa`.`tblHotel` WHERE `hotel_id` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            pres.setString(1, id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {
                /** 將 ResultSet 之資料取出 */
                row += 1;
                
               
                int hotel_id = rs.getInt("hotel_id");
                String name = rs.getString("hotel_name");
                String email = rs.getString("hotel_email");
                String password = rs.getString("hotel_password");
                String image = rs.getString("hotel_image");
                String description = rs.getString("hotel_description");
                String phone = rs.getString("hotel_phone");
                int country_id = rs.getInt("hotel_country_id");
                int city_id = rs.getInt("hotel_city_id");
                String address = rs.getString("hotel_address");
                Date modified_time = rs.getDate("hotel_modified");
                Date created_time = rs.getDate("hotel_created");


                h = new Hotel(hotel_id, email, password, name, image, description, phone, country_id, city_id, address, modified_time, created_time);

                JSONObject jso_hotel = h.getData();
                int id_int = Integer.parseInt(id);
                jso_hotel.put("room", getRoomByHotelId(id_int));
                jsa.put(jso_hotel);
            }
            
            
            System.err.println(jsa.toString());

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }

        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
        
        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }


/** 使用店家編號取得店家所有房間*/
public JSONArray getRoomByHotelId(int hotel_id) {
	System.out.println("enter Class ProductHelper method getRoomByHotelId");
	 
	
	String exexcute_sql = "";
    /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
    ResultSet rs_roomList = null;
    JSONArray jsa_room = new JSONArray();
    Room r = null;
    
    try {
    	  	
        /** 取得資料庫之連線 */
        conn2 = DBMgr.getConnection();
        /** SQL指令 : 取得店家的所有房間資料*/
        String roomList_sql = "SELECT * " + 
        		"FROM `missa`.`tblroom`" + 
        		"WHERE `hotel_id` = ?";
        /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
        pres2 = conn2.prepareStatement(roomList_sql);
        pres2.setInt(1, hotel_id);
        /** 執行查詢之SQL指令並記錄其回傳之資料 */
        rs_roomList = pres2.executeQuery();

        /** 紀錄真實執行的SQL指令，並印出 **/
        exexcute_sql = pres2.toString();
        System.out.println(exexcute_sql);
        
        /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
        while(rs_roomList.next()) {              
            /** 將 ResultSet 之資料取出 */
            int room_id = rs_roomList.getInt("room_id");
            String room_image = rs_roomList.getString("room_image");
            String room_name = rs_roomList.getString("room_name");
            Double room_price = rs_roomList.getDouble("room_price");
            System.out.println(room_name);
            
            
            r = new Room(room_id, hotel_id, room_name, room_price, room_image);
            JSONObject jso_room = r.getData();
            jso_room.put("type", "room");
            
            
            jsa_room.put(jso_room);
        }
        System.err.println(jsa_room.toString());

    } catch (SQLException e) {
        /** 印出JDBC SQL指令錯誤 **/
        System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
    } catch (Exception e) {
        /** 若錯誤則印出錯誤訊息 */
        e.printStackTrace();
    } finally {
        /** 關閉連線並釋放所有資料庫相關之資源 **/
        DBMgr.close(rs_roomList, pres2, conn2);
    }
          
    return jsa_room;
}


/**
 *	多載
 * 	取得該名店家之國家與店家之城市
 *
 * @param h 一名店家之Hotel物件
 * @return the JSON object 回傳該名店家之更新時間（以JSONObject進行封裝）
 */
public JSONObject getCountryCity(Hotel h) {
	 /** 用於儲存該名店家所檢索之店家國家及城市之資料 */
    JSONObject jso = new JSONObject();
    /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
    ResultSet rs = null;

    try {
        /** 取得資料庫之連線 */
        conn = DBMgr.getConnection();
        
        
        if(h.getCityId() == 0 && h.getCountryId() == 0) {
        	/** SQL指令 */
            String countrysql = "SELECT * FROM `missa`.`tblcountry` WHERE `country_name` = ? LIMIT 1;";
            String citysql = "SELECT * FROM `missa`.`tblcity` WHERE `city_name` = ? LIMIT 1;";
            
            /**取得國家編號*/
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(countrysql);
            pres.setString(1, h.getCountryName());
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            
            rs = pres.executeQuery();
            while(rs.next()) {
            	/** 將 ResultSet 之資料取出 */
                int country_id = rs.getInt("country_id");
                /** 將其封裝至JSONObject資料 */
                jso.put("country_id", country_id);
               
            }
            /**取得城市編號*/
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(citysql);
            pres.setString(1, h.getCityName());
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();
            while(rs.next()) {
            	/** 將 ResultSet 之資料取出 */
                int city_id = rs.getInt("city_id");
                       
                /** 將其封裝至JSONObject資料 */
                jso.put("city_id", city_id);
            }
            /**查詢*/
        }else {
        	/** SQL指令 */
            String countrysql = "SELECT * FROM `missa`.`tblcountry` WHERE `country_id` = ? LIMIT 1;";
            String citysql =  "SELECT * FROM `missa`.`tblcity` WHERE `city_id` = ? LIMIT 1;";
            
            /**取得國家資料*/
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(countrysql);
            pres.setInt(1, h.getCountryId());
            
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();
         
            while(rs.next()) {
                /** 將 ResultSet 之資料取出 */
                String country = rs.getString("country_name");
                    
                /** 將其封裝至JSONObject資料 */
                jso.put("country", country);
            }
            
            /**取得城市資料*/
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(citysql);
            pres.setInt(1, h.getCityId());
            
            
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();
            while(rs.next()) {
                /** 將 ResultSet 之資料取出 */
                String city = rs.getString("city_name");
                /** 將其封裝至JSONObject資料 */
                jso.put("city", city);
            }
        }
        return jso;
        
    } catch (SQLException e) {
        /** 印出JDBC SQL指令錯誤 **/
        System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
    } catch (Exception e) {
        /** 若錯誤則印出錯誤訊息 */
        e.printStackTrace();
    } finally {
        /** 關閉連線並釋放所有資料庫相關之資源 **/
        DBMgr.close(rs, pres, conn);
    }

    return jso;
}
 /**
 *	多載
 * 	取得所有國家及城市
 *
 * @return the JSON object 回傳該名店家之更新時間（以JSONObject進行封裝）
 */
	public JSONObject getCountryCity() {
		System.err.println("hh getCountryCity");
		/** 用於儲存該名店家所檢索之店家國家及城市之資料 */
		JSONObject jso = new JSONObject();
		/** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
		ResultSet rs = null;
		/** 記錄實際執行之SQL指令 */
		String exexcute_sql = "";
    
		try {
			/** 取得資料庫之連線 */
			conn = DBMgr.getConnection();
			/** SQL指令 */
			String sql = "";
			StringBuilder[] country = null;
			StringBuilder[] city = null;
			int length;
        
			/**取得資料庫所有的國家*/
			sql = "SELECT * FROM `missa`.`tblcountry`;";
			/** 將參數回填至SQL指令當中 */
			pres = conn.prepareStatement(sql);
        
			/** 執行更新之SQL指令 */
			rs = pres.executeQuery();
        	/**取得結果集的長度*/
			rs.last();
			length = rs.getRow()+1;
			rs.first();
        
			country = new StringBuilder[length];
			city = new StringBuilder[length];
			while(rs.next()) {
				/** 將 ResultSet 之資料取出 */
				int countryid = rs.getInt("country_id"); 
				String countryname = rs.getString("country_name");
            
				city[countryid-1] = new StringBuilder();
				country[countryid-1] = new StringBuilder();
                System.out.println(countryname);
				/** 將其用StringBuilder組合起來 */
				String countryString = "{\"countryname\":\""+countryname+"\",\"city\":[";
				country[countryid-1].append(countryString);
			}
			System.out.println(country.toString());
			/** 紀錄真實執行的SQL指令，並印出 **/
			exexcute_sql = pres.toString();
			System.out.println(exexcute_sql);
        
			/**取得資料庫所有的城市*/
			sql = "SELECT * FROM `missa`.`tblcity`;";
			/** 將參數回填至SQL指令當中 */
			pres = conn.prepareStatement(sql);
        
			/** 執行更新之SQL指令 */
			rs = pres.executeQuery();
			/**取得結果集的長度*/
        
			while(rs.next()) {
				/** 將 ResultSet 之資料取出 */
				int countryid = rs.getInt("country_id"); 
				String cityname = rs.getString("city_name");
				System.out.println(cityname);
				String cityString = "\""+cityname+"\",";
				if(country[countryid-1]==null)
					continue;
				else
					/** 將其用StringBuilder組合起來 */
					country[countryid-1].append(cityString);
			}
			System.out.println(city.toString());
        
			/** 紀錄真實執行的SQL指令，並印出 **/
			exexcute_sql = pres.toString();
			System.out.println(exexcute_sql);
			for(int i=0;i<length;i++) {
				if(country[i]==null)
					continue;
        	
				String countryString = country[i].toString();
				countryString = countryString.substring(0,countryString.length()-1)+"]}";
				jso.put( Integer.toString(i),countryString );
			}
       
        
		} catch (SQLException e) {
			/** 印出JDBC SQL指令錯誤 **/
			System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			/** 若錯誤則印出錯誤訊息 */
			e.printStackTrace();
		} finally {
			/** 關閉連線並釋放所有資料庫相關之資源 **/
			DBMgr.close(pres, conn);
		}
		return jso;
	}

}

