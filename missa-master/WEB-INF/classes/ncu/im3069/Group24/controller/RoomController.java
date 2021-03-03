package ncu.im3069.Group24.controller;

import org.json.*;
import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ncu.im3069.Group24.app.Hotel;
import ncu.im3069.Group24.app.Room;
import ncu.im3069.Group24.app.RoomHelper;
import ncu.im3069.tools.JsonReader;

/**
 * Servlet implementation class RoomController
 */
@WebServlet("/api/room.do")
public class RoomController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private RoomHelper rh =  RoomHelper.getHelper();
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * 處理Http Method請求GET方法（取得資料）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
        String id = jsr.getParameter("room_id");
        
        /** 判斷該字串是否存在，若存在代表要取回個別房間之資料，否則代表要取回全部資料庫內房間之資料 */
        if (id.isEmpty()) {
            /** 透過RoomHelper物件之getAll()方法取回所有房間之資料，回傳之資料為JSONObject物件 */
            JSONObject query = rh.getAll();
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "所有房間資料取得成功");
            resp.put("response", query);
    
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
        else {
        	int room_id = Integer.parseInt(id);
            
        	/** 透過RoomHelper物件的getByID()方法自資料庫取回該房間之資料，回傳之資料為JSONObject物件 */
            JSONObject query = rh.getByID(room_id);
            System.out.print("room_id");
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "房間資料取得成功");
            resp.put("response", query);
    
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
	}

	/**
	 * 處理Http Method請求POST方法（新增資料）
	 * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        int hid = jso.getInt("hotel_id");
        String name = jso.getString("room_name");
        double price = jso.getDouble("room_price");
        String img = jso.getString("room_image");

        String str_price = Double.toString(price);
        String str_hid = Integer.toString(hid);
        
        /** 建立一個新的房間物件 */
        Room r = new Room(hid, name, price, img);
        
        Hotel h = null;
        try {
			h = new Hotel(hid);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
        if(str_hid.isEmpty() || name.isEmpty() || str_price.isEmpty() || img.isEmpty()) {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }
        /** 透過RoomHelper物件的checkExist()檢查該店家編號是否存在 */
        else if (!rh.checkExist(h)){ 
            /** 以字串組出JSON格式之資料 */
        	String resp = "{\"status\": \'400\', \"message\": \'新增房間失敗，此店家編號不存在！\', \'response\': \'\'}";
        	/** 透過JsonReader物件回傳到前端（以字串方式） */
        	jsr.response(resp, response);
        }
        
        else{
        	/** 透過RoomHelper物件的create()方法新建一個房間至資料庫 */
            JSONObject data = rh.create(r);
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "成功! 新增房間資料...");
            resp.put("response", data);
            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
        
	}

	/**
     * 處理Http Method請求PUT方法（更新）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        int rid = jso.getInt("room_id");
        int hid = jso.getInt("hotel_id");
        String name = jso.getString("room_name");
        double price = jso.getDouble("room_price");
        String img = jso.getString("room_image");
        
        /** 透過傳入之參數，新建一個以這些參數之房間Room物件 */
        Room r = new Room(rid, hid, name, price, img);
        
        /** 透過Room物件的update()方法至資料庫更新該房間資料，回傳之資料為JSONObject物件 */
        JSONObject data = r.update();
        
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "成功! 更新房間資料...");
        resp.put("response", data);
        
        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
	}

	/**
     * 處理Http Method請求DELETE方法（刪除）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        int id = jso.getInt("room_id");
        
        /** 透過RoomHelper物件的deleteByID()方法至資料庫刪除該房間，回傳之資料為JSONObject物件 */
        JSONObject query = rh.deleteByID(id);
        
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "房間移除成功！");
        resp.put("response", query);

        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
	}

}
