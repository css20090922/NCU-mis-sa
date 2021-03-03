package ncu.im3069.Group24.app;

import java.sql.*;
import java.util.*;
import java.sql.Date;

import org.json.*;

import ncu.im3069.Group24.util.DBMgr;

public class OrderHelper {
    
	private OrderHelper() {
    }
	
    private static OrderHelper oh;
    private Connection conn = null;
    private PreparedStatement pres = null;
    
    
    public static OrderHelper getHelper() {
        if(oh == null) oh = new OrderHelper();
        
        return oh;
    }
    
    public JSONObject create(Order o) {
        /** 記錄實際執行之SQL指令 */
        String execute_sql = "";
        
        /*
        long id = -1;
        JSONArray opa = new JSONArray();
        */
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
           
            /** SQL指令 */
            String sql = "INSERT INTO `missa`.`tblorder`(`member_id`, `room_id`, `room_amount`, `total_price`, `checkin_date`, `checkout_date`, `customer`, `paid_status`, `created`, `hotel_id`, `room_name`, `room_price`, `room_image`)"
                    + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            System.out.print("Inserting");
            
            /** 取得所需之參數 */
            int member_id = o.getMemberID();
            int room_id = o.getRoomID();
            int room_amount = o.getRoomAmount();
            int total_price = o.getTotalPrice();
            Date checkin = o.getCheckin();
            Date checkout = o.getCheckout();
            String customer = o.getCustomer();
            int paid_status = o.getPaidStatus();
            Date created = o.getCreated();
            int hotel_id = o.getHotelID();
            String room_name = o.getRoomName();
            double room_price = o.getRoomPrice();
            String room_img = o.getRoomImage();
            
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, member_id);
            pres.setInt(2, room_id);
            pres.setInt(3, room_amount);
            pres.setInt(4, total_price);
            pres.setDate(5, checkin);
            pres.setDate(6, checkout);
            pres.setString(7, customer);
            pres.setInt(8, paid_status);
            pres.setDate(9, created);
            pres.setInt(10, hotel_id);
            pres.setString(11, room_name);
            pres.setDouble(12, room_price);
            pres.setString(13, room_img);
            
            /** 執行新增之SQL指令並記錄影響之行數 */
            pres.executeUpdate();
            
            /** 紀錄真實執行的SQL指令，並印出 **/
            execute_sql = pres.toString();
            System.out.println(execute_sql);
            
            /*ResultSet rs = pres.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getLong(1);
                /**ArrayList<OrderItem> opd = order.getOrderProduct(); 
                opa = oph.createByList(id, opd);
            }*/
        
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

        /** 將SQL指令，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        //response.put("order_id", id);
        //response.put("order_product_id", opa);
        response.put("sql", execute_sql);
        
        return response;
    }
    
    
    /**
     * 取回所有訂單資料
     *
     * @return the JSONObject 回傳SQL執行結果與自資料庫取回之所有資料
     */
    public JSONObject getAll() {
        Order o = null;
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String execute_sql = "";
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
            String sql = "SELECT * FROM `missa`.`tblorder`";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            execute_sql = pres.toString();
            System.out.println(execute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;
                
                /** 將 ResultSet 之資料取出 */
                int order_id = rs.getInt("order_id");
                int member_id = rs.getInt("member_id");
                int room_id = rs.getInt("room_id");
                int room_amount = rs.getInt("room_amount");
                int total_price = rs.getInt("total_price");
                Date checkin = rs.getDate("checkin_date");
                Date checkout = rs.getDate("checkout_date");
                String customer = rs.getString("customer");
                int paid_status = rs.getInt("paid_status");
                Date created = rs.getDate("created");
                int hotel_id = rs.getInt("hotel_id");
                String room_name = rs.getString("room_name");
                double room_price = rs.getDouble("room_price");
                String room_image = rs.getString("room_image");
                
                /** 將每一筆商品資料產生一名新Order物件 */
                o = new Order(order_id, member_id, room_id, room_amount, total_price, checkin, checkout, customer, paid_status, created, hotel_id, room_name, room_price, room_image);
                /** 取出該項商品之資料並封裝至 JSONsonArray 內 */
                jsa.put(o.getData());
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
        response.put("sql", execute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
    
    public JSONObject getById(int id) {
        JSONObject data = new JSONObject();
        Order o = null;
        /** 記錄實際執行之SQL指令 */
        String execute_sql = "";
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
            String sql = "SELECT * FROM `missa`.`tblorder` WHERE `order_id` = ?";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            execute_sql = pres.toString();
            System.out.println(execute_sql);
            
            while(rs.next()){         
            	/** 將 ResultSet 之資料取出 */
                int order_id = rs.getInt("order_id");
                int member_id = rs.getInt("member_id");
                int room_id = rs.getInt("room_id");
                int room_amount = rs.getInt("room_amount");
                int total_price = rs.getInt("total_price");
                Date checkin = rs.getDate("checkin_date");
                Date checkout = rs.getDate("checkout_date");
                String customer = rs.getString("customer");
                int paid_status = rs.getInt("paid_status");
                Date created = rs.getDate("created");
                int hotel_id = rs.getInt("hotel_id");
                String room_name = rs.getString("room_name");
                double room_price = rs.getDouble("room_price");
                String room_image = rs.getString("room_image");
                
                /** 將每一筆商品資料產生一名新Order物件 */
                o = new Order(order_id, member_id, room_id, room_amount, total_price, checkin, checkout, customer, paid_status, created, hotel_id, room_name, room_price, room_image);
                /** 取出該項商品之資料並封裝至 JSONsonArray 內 */
                    
                data = o.getData();
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
        response.put("sql", execute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", data);

        return response;
    }
    
    
    public JSONObject deleteByID(int id) {
        /** 記錄實際執行之SQL指令 */
        String execute_sql = "";
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
            String sql = "DELETE FROM `missa`.`tblorder` WHERE `order_id` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, id);
            /** 執行刪除之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();

            /** 紀錄真實執行的SQL指令，並印出 **/
            execute_sql = pres.toString();
            System.out.println(execute_sql);
            
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
        response.put("sql", execute_sql);
        response.put("row", row);
        response.put("time", duration);

        return response;
    }
}
