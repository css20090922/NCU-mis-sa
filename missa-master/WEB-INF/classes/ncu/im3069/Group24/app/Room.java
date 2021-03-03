package ncu.im3069.Group24.app;

import org.json.*;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class Room
 * Room類別（class）具有房間所需要之屬性與方法，並且儲存與房間相關之商業判斷邏輯<br>
 * </p>
 * 
 */

public class Room {
    
    /** id，房間編號 */
    private int room_id;
    
    /** hotel_id，房間所屬的旅館*/
    private int hotel_id;
    
    /** name，房間名字 */
    private String name;
    
    /** price，房間價格 */
    private double price;
    
    /** img，房間圖片連結 */
    private String img;
    
    /** rh，RoomHelper之物件與Room相關之資料庫方法（Sigleton） */
    private RoomHelper rh =  RoomHelper.getHelper();
    
    
    /**
     * 實例化（Instantiates）一個新的（new）Room物件<br>
     * 採用多載（overload）方法進行，此建構子用於新增房間資料時，產生一個房間物件
     * 
     * @param id 房間編號
     * @param name 房間名字
     * @param price 房間價格
     * @param img 房間圖片連結
     */
    public Room(int hotel_id, String name, double price, String img) {
        this.hotel_id = hotel_id;
        this.name = name;
        this.price = price;
        this.img = img;
    }
    
    /**
     * 實例化（Instantiates）一個新的（new）Room物件<br>
     * 採用多載（overload）方法進行，此建構子用於查詢房間資料或更新房間資料時，將每一筆資料新增為一個房間物件
     *
     * @param room_id 房間編號
     * @param hotel_id 旅館編號
     * @param name 房間名字
     * @param price 房間價格
     * @param img 房間圖片連結
     */
    public Room(int room_id, int hotel_id, String name, double price, String img) {
        this.room_id = room_id;
        this.hotel_id = hotel_id;
        this.name = name;
        this.price = price;
        this.img = img;
    }
    
    /**
     * 取得房間之編號
     *
     * @return the room_id 回傳房間編號
     */
    public int getRoomID() {
        return this.room_id;
    }
    
    /**
     * 取得旅館之編號
     *
     * @return the hotel_id 回傳旅館編號
     */
    public int getHotelID() {
        return this.hotel_id;
    }


    /**
     * 取得房間之名字
     *
     * @return the name 回傳房間名字
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * 取得房間之價格
     *
     * @return the price 回傳房間價格
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * 取得房間之圖片連結
     *
     * @return the img 回傳房間圖片連結
     */
    public String getImg() {
        return this.img;
    }
    
    /**
     
    
    /**
     * 更新房間資料
     *
     * @return the JSON object 回傳SQL更新之結果與相關封裝之資料
     */
    public JSONObject update() {
        /** 新建一個JSONObject用以儲存更新後之資料 */
        JSONObject data = new JSONObject();
        
        /** 檢查該名房間是否已經在資料庫 */
        if(this.room_id != 0) {
            /** 透過RoomHelper物件，更新目前之房間資料置資料庫中 */
            data = rh.update(this);
        }
        
        return data;
    }
    
    /**
     * 取得該名房間所有資料
     *
     * @return the data 取得該房間之所有資料並封裝於JSONObject物件內
     */
    public JSONObject getData() {
        /** 透過JSONObject將該房間所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("room_id", getRoomID());
        jso.put("hotel_id", getHotelID());
        jso.put("room_name", getName());
        jso.put("room_price", getPrice());
        jso.put("room_img", getImg());
        
        return jso;
    }
    
    
    
}