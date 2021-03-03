package ncu.im3069.Group24.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

import ncu.im3069.Group24.app.Hotel;
import ncu.im3069.Group24.app.HotelHelper;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/hotel.do")
public class HotelController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private HotelHelper hh =  HotelHelper.getHelper();

    public HotelController() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        System.out.println("Class HotelController method doGet");
		JsonReader jsr = new JsonReader(request);
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
        String id = jsr.getParameter("id");
        System.err.println(id);

        JSONObject resp = new JSONObject();
      	JSONObject countrycity = hh.getCountryCity();
        /** 判斷該字串是否存在，若存在代表要取回購物車內產品之資料，否則代表要取回全部資料庫內產品之資料 */
        if (!id.isEmpty()) {



        	if(id.equalsIgnoreCase("0")) {
        		resp = countrycity;
        		System.err.println(resp.toString());

        	}else {
        		/** 透過HotelHelper物件的getByID()方法自資料庫取回該名會員之資料，回傳之資料為JSONObject物件 */
        		JSONObject query = hh.getById(id);
          
        		/** 新建一個JSONObject用於將回傳之資料進行封裝 */
        		System.err.println(countrycity.toString());
        		resp.put("status", "200");
        		resp.put("message", "店家資料取得成功");
        		resp.put("response", query);
        		resp.put("countrycity", countrycity );
   
        	}
        	/*

        	*/
        }
        else {
          System.out.println("will execute hh.getAll");
          JSONObject query = hh.getAll();

          resp.put("status", "200");
          resp.put("message", "所有店家資料取得成功");
          resp.put("response", query);
        }

        System.out.println("finish HotelController method doGet");
		/** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
	}

	   /**
     * 處理Http Method請求POST方法（新增資料）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        Hotel h = null;
        
        /** 取出經解析到JSONObject之Request參數 */
        String email = jso.getString("email");
        String password = jso.getString("password");
        String name = jso.getString("name");
        String image = jso.getString("image");
        String description = jso.getString("description");
        String phone = jso.getString("phone");
        String country = jso.getString("country");
        String city = jso.getString("city");
        String address = jso.getString("address");
        
        /** 建立一個新的店家物件 */
        try {
			h = new Hotel(email, password, name, image, description, phone, country, city, address);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
        if(email.isEmpty() || password.isEmpty() || name.isEmpty()|| image.isEmpty()|| description.isEmpty()
        		|| phone.isEmpty()|| country.isEmpty()|| city.isEmpty()|| address.isEmpty()) {
           /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }
        /** 透過MemberHelper物件的checkDuplicate()檢查該會員電子郵件信箱是否有重複 */
        else if (!hh.checkDuplicate(h)) {
            /** 透過MemberHelper物件的create()方法新建一個會員至資料庫 */
            JSONObject data = hh.create(h);
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "成功! 註冊店家資料...");
            resp.put("response", data);
            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
        else {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'新增帳號失敗，此E-Mail帳號重複！\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }
    }
    

    /**
     * 處理Http Method請求DELETE方法（刪除）
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
        
        /** 透過MemberHelper物件的deleteByID()方法至資料庫刪除該名會員，回傳之資料為JSONObject物件 */
        JSONObject query = hh.deleteByID(id);
        
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "店家移除成功！");
        resp.put("response", query);

        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
    }

    /**
     * 處理Http Method請求PUT方法（更新）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        int id = jso.getInt("id");
        String email = jso.getString("email");
        String password = jso.getString("password");
        String name = jso.getString("name");
        String image = jso.getString("image");
        String description = jso.getString("description");
        String phone = jso.getString("phone");
        String country = jso.getString("country");
        String city = jso.getString("city");
        String address = jso.getString("address");

        /** 透過傳入之參數，新建一個以這些參數之會員Hotel物件 */
        Hotel h = null;
		try {
			h = new Hotel(id, email, password, name, image, description, phone, country, city, address);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        /** 透過Member物件的update()方法至資料庫更新該名會員資料，回傳之資料為JSONObject物件 */
        JSONObject data = h.update();
        
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "成功! 更新店家資料...");
        resp.put("response", data);
        
        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
    }


}
