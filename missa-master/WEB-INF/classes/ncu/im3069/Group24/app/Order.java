package ncu.im3069.Group24.app;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.sql.Date;

import org.json.*;

import ncu.im3069.Group24.util.Arith;

public class Order {
	
	/** order_id，訂單編號 */
	private int order_id;
	
	/** member_id，會員編號 */
	private int member_id;
	
	/** room_id，房間編號 */
	private int room_id;
	
	/** room_amount，房間數量 */
	private int room_amount;
	
	/** total_price，訂單總金額 */
	private int total_price;
	
	/** checkin，入住日期 */
	private Date checkin;
	
	/** checkout，退房日期 */
	private Date checkout;
	
	/** customer，客戶名稱 */
	private String customer;
	
	/** paid_status，付款狀態 */
	private int paid_status;
	
	/** created，訂單成立時間 */
	private Date created;
	
	/** hotel_id，店家編號 */
	private int hotel_id;
	
	/** room_name，房間名稱 */
	private String room_name;
	
	/** room_price，房間價格 */
	private double room_price;
	
	/** room_image，房間圖片網址 */
	private String room_image;
	
	
	/** oh，OrderHelper 之物件與 Order 相關之資料庫方法（Sigleton） */
    private OrderHelper oh = OrderHelper.getHelper();

	
	
	

    /**
     * 實例化（Instantiates）一個新的（new）Order 物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立訂單資料時，產生一個新的訂單
     *
     * @param member_id 會員編號
     * @param room_id 房間編號
     * @param room_amount 房間數量
     * @param total_price 訂單總金額
     * @param checkin 入住日期
     * @param checkout 退房日期
     * @param customer 客戶名稱
     * @param paid_status 付款狀態
     * @param created 訂單新增時間
     * @param hotel_id 店家編號
     * @param room_name 房間名稱
     * @param room_price 房間價格
     * @param room_image 房間圖片網址
     */
    public Order(int member_id, int room_id, int room_amount, int total_price, Date checkin, Date checkout, String customer, int paid_status, Date created, int hotel_id, String room_name, double room_price, String room_image) {
        this.member_id = member_id;
        this.room_id = room_id;
        this.room_amount = room_amount;
        this.total_price = total_price;
        this.checkin = checkin;
        this.checkout = checkout;
        this.customer = customer;
        this.paid_status = paid_status;
        this.created = created;
        this.hotel_id = hotel_id;
        this.room_name = room_name;
        this.room_price = room_price;
        this.room_image = room_image;
    }

    /**
     * 實例化（Instantiates）一個新的（new）Order 物件<br>
     * 採用多載（overload）方法進行，此建構子用於查詢訂單資料時，產生一個新的訂單
     *
     * @param order_id 會員編號
     * @param member_id 會員編號
     * @param room_id 房間編號
     * @param room_amount 房間數量
     * @param total_price 訂單總金額
     * @param checkin 入住日期
     * @param checkout 退房日期
     * @param customer 客戶名稱
     * @param paid_status 付款狀態
     * @param created 訂單新增時間
     * @param hotel_id 店家編號
     * @param room_name 房間名稱
     * @param room_price 房間價格
     * @param room_image 房間圖片網址
     */
    public Order(int order_id, int member_id, int room_id, int room_amount, int total_price, Date checkin, Date checkout, String customer, int paid_status, Date created, int hotel_id, String room_name, double room_price, String room_image) {
        this.order_id = order_id;
    	this.member_id = member_id;
        this.room_id = room_id;
        this.room_amount = room_amount;
        this.total_price = total_price;
        this.checkin = checkin;
        this.checkout = checkout;
        this.customer = customer;
        this.paid_status = paid_status;
        this.created = created;
        this.hotel_id = hotel_id;
        this.room_name = room_name;
        this.room_price = room_price;
        this.room_image = room_image;
    }
	
    /**
    * 取得訂單編號
    * 
    * @return the order_id 回傳訂單的編號
    */
	public int getOrderID() {
		return order_id;
	}
	
	/**
    * 取得該訂單的會員編號
    *
    * @return the member_id 回傳訂單會員的編號
    */
	public int getMemberID() {
		return member_id;
	}
	
	/**
    * 取得房間的編號
    *
    * @return the room_id 回傳房間的編號
    */
	public int getRoomID() {
		return room_id;
	}
	
	/**
    * 取得訂購房間數量
    *
    * @return the room_amount 回傳訂購房間數量
    */
	public int getRoomAmount() {
		return room_amount;
	}
	
	
	/**
    * 取得訂單總金額
    *
    * @return the total_price 回傳訂單總金額
    */
	public int getTotalPrice() {
		return total_price;
	}
	
	/**
    * 取得訂單上的入住日期
    *
    * @return the checkin 回傳訂單上的入住日期
    */
	public Date getCheckin() {
		return checkin;
	}
	
	/**
    * 取得訂單上的退房日期
    *
    * @return the checkout 回傳訂單上的退房日期
    */
	public Date getCheckout() {
		return checkout;
	}
	
	
	/**
    * 取得訂單上的住客名稱
    *
    * @return the customer 回傳訂單上的住客名稱
    */
	public String getCustomer() {
		return customer;
	}
	
	
	/**
    * 取得訂單上的付款狀態
    *
    * @return the paid_status 回傳訂單上的付款狀態
    */
	public int getPaidStatus() {
		return paid_status;
	}
	
	/**
    * 取得訂單成立的時間
    *
    * @return the created 回傳訂單成立的時間
    */
	public Date getCreated() {
		return created;
	}
	
	/**
    * 取得訂單中的房間編號
    *
    * @return the hotel_id 回傳訂單中的房間編號
    */
	public int getHotelID() {
		return hotel_id;
	}
	
	/**
	* 取得訂單中的房間名稱
	*
	* @return the room_name 回傳訂單中的房間名稱
	*/
	public String getRoomName() {
		return room_name;
	}
			
	/**
	* 取得訂單中的房間價格
	*
	* @return the room_price 回傳訂單中的房間價格
	*/
	public double getRoomPrice() {
		return room_price;
	}
	
	/**
	* 取得訂單中的房間圖片網址
	*
	* @return the room_image 回傳訂單中的房間圖片網址
	*/
	public String getRoomImage() {
		return room_image;
	}
	
    
	/**
    * 取得訂單基本資料
    *
    * @return the data 取得該訂單之所有資料並封裝於JSONObject物件內
    */
    public JSONObject getData() {
    	/** 透過JSONObject將該房間所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("order_id", getOrderID());
        jso.put("member_id", getMemberID());
        jso.put("room_id", getRoomID());
        jso.put("room_amount", getRoomAmount());
        jso.put("total_price", getTotalPrice());
        jso.put("checkin", getCheckin());
        jso.put("checkout", getCheckout());
        jso.put("customer", getCustomer());
        jso.put("paid_status", getPaidStatus());
		jso.put("created",getCreated());
		jso.put("hotel_id",getHotelID());
		jso.put("room_name",getRoomName());
		jso.put("room_price",getRoomPrice());
		jso.put("room_image",getRoomImage());
		
        return jso;
    }
    
}
