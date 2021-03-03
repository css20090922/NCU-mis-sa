package ncu.im3069.Group24.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

import ncu.im3069.Group24.app.Order;
import ncu.im3069.Group24.app.OrderHelper;
import ncu.im3069.Group24.app.RoomHelper;
import ncu.im3069.tools.JsonReader;

import javax.servlet.annotation.WebServlet;

@WebServlet("/api/order.do")
public class OrderController extends HttpServlet {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

    /** oh，OrderHelper 之物件與 order 相關之資料庫方法（Sigleton） */
	private OrderHelper oh =  OrderHelper.getHelper();

    public OrderController() {
        super();
    }

    /**
     * 處理 Http Method 請求 GET 方法（取得資料）
     *
     * @param request Servlet 請求之 HttpServletRequest 之 Request 物件（前端到後端）
     * @param response Servlet 回傳之 HttpServletResponse 之 Response 物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /** 透過 JsonReader 類別將 Request 之 JSON 格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        
        /** 取出經解析到 JsonReader 之 Request 參數 */
        String id = jsr.getParameter("order_id");

        /** 判斷該字串是否存在，若存在代表要取回個別訂單之資料，否則代表要取回全部資料庫內訂單之資料 */
        if (!id.isEmpty()) {
        	int order_id = Integer.parseInt(id);
        	
        	/** 透過 orderHelper 物件的 getByID() 方法自資料庫取回該筆訂單之資料，回傳之資料為 JSONObject 物件 */
        	JSONObject query = oh.getById(order_id);
        	
        	/** 新建一個 JSONObject 用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
        	resp.put("status", "200");
        	resp.put("message", "單筆訂單資料取得成功");
        	resp.put("response", query);
        	
        	/** 透過 JsonReader 物件回傳到前端（以 JSONObject 方式） */
            jsr.response(resp, response);
           
        }
        else {
        	/** 透過 orderHelper 物件之 getAll() 方法取回所有訂單之資料，回傳之資料為 JSONObject 物件 */
        	JSONObject query = oh.getAll();
        	
        	/** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            
            resp.put("status", "200");
            resp.put("message", "所有訂單資料取得成功");
            resp.put("response", query);
            
            /** 透過 JsonReader 物件回傳到前端（以 JSONObject 方式） */
            jsr.response(resp, response);
            
        }
	}
	
	/**
     * 處理Http Method請求DELETE方法（刪除訂單）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
	
	public void doDelete(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
	        JsonReader jsr = new JsonReader(request);
	        JSONObject jso = jsr.getObject();
	        
	        /** 取出經解析到JSONObject之Request參數 */
	        int id = jso.getInt("id");
	        
	        /** 透過OrderHelper物件的deleteByID()方法至資料庫刪除該名會員，回傳之資料為JSONObject物件 */
	        JSONObject query = oh.deleteByID(id);
	        
	        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
	        JSONObject resp = new JSONObject();
	        resp.put("status", "200");
	        resp.put("message", "訂單移除成功！");
	        resp.put("response", query);

	        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
	        jsr.response(resp, response);
	    }

    /**
     * 處理 Http Method 請求 POST 方法（新增訂單資料）
     *
     * @param request Servlet 請求之 HttpServletRequest 之 Request 物件（前端到後端）
     * @param response Servlet 回傳之 HttpServletResponse 之 Response 物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    /** 透過 JsonReader 類別將 Request 之 JSON 格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到 JSONObject 之 Request 參數 */
        int member_id = jso.getInt("member_id");
        String str_room_id = jso.getString("room_id");
        String str_room_amount = jso.getString("room_amount");
        int total_price = jso.getInt("total_price");
        String str_checkin = jso.getString("checkin");
        String str_checkout = jso.getString("checkout");
        String customer = jso.getString("customer");
        String str_paid_status = jso.getString("paid_status");
        String str_created = jso.getString("created");
        String str_hotel_id = jso.getString("hotel_id");
        String room_name = jso.getString("room_name");
        String str_room_price = jso.getString("room_price");
        String room_img = jso.getString("room_img");
        
        
        int room_id = Integer.parseInt(str_room_id);
        int room_amount = Integer.parseInt(str_room_amount);
        Date checkin = java.sql.Date.valueOf(str_checkin);
        Date checkout = java.sql.Date.valueOf(str_checkout);
        int paid_status = Integer.parseInt(str_paid_status);
        Date created = java.sql.Date.valueOf(str_created);
        int hotel_id = Integer.parseInt(str_hotel_id);
        double room_price = Double.parseDouble(str_room_price); 
        
        
        /** 建立一個新的訂單物件 */
	    Order o = new Order(member_id, room_id, room_amount, total_price, checkin, checkout, customer, paid_status, created, hotel_id, room_name, room_price, room_img);
	    /** 透過 OrderHelper物件的 create()方法新建一筆訂單至資料庫 */
	    JSONObject data = oh.create(o);
	        
		/** 新建一個 JSONObject 用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "訂單新增成功！");
        resp.put("response",data);

        /** 透過 JsonReader 物件回傳到前端（以 JSONObject 方式） */
        jsr.response(resp, response);
	}
	
}

