package ncu.im3069.Group24.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import ncu.im3069.Group24.app.HotelHelper;
import ncu.im3069.Group24.app.MemberHelper;
import ncu.im3069.tools.JsonReader;



@WebServlet("/api/login.do")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/** mh，MemberHelper之物件與Member相關之資料庫方法（Sigleton） */
    private MemberHelper mh =  MemberHelper.getHelper();
    private HotelHelper hh = HotelHelper.getHelper();
   
	
	 /**
	  *  登入
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws UnsupportedEncodingException
     */

		@Override
   		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonReader jsr = new JsonReader(request);
		JSONObject jso = jsr.getObject();
		System.out.println(jso.getString("user"));
		 /**
		  *    	會員登入
		  */
		if(jso.getString("user").contentEquals("member")) {
			/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        	String message = null;
        	JSONObject  resp = new JSONObject();
        	String email = jso.getString("Email");
            String password = jso.getString("Password");
        	String status;
        	
        	/** 透過MemberHelper物件的getLogin()方法至資料庫查詢會員是否存在 */
            JSONObject data = mh.getLogin(email, password);
            if(data == null) {
            	status = "405";
            	message = "帳號或密碼錯誤";
            }else {
            	status = "200";
            	/**把email種進cookie*/
            	Cookie loginName= new Cookie("memberEmail",email);
            	loginName.setPath("/");//這裡是之根目錄下所有的目錄都可以共享Cookie
            	loginName.setMaxAge(3600);
                response.addCookie(loginName);//新增Cookie
                System.out.println("member:"+email+" login");
            }
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            resp.put("status", status);
            resp.put("response", data);
            resp.put("message", message);
            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
		}else {
			 /**
			  *    	店家登入
			  */
			String message = null;
        	JSONObject  resp = new JSONObject();
        	String email = jso.getString("Email");
            String password = jso.getString("Password");
        	String status;
        	
        	/** 透過HotelHelper物件的getLogin()方法至資料庫查詢店家是否存在 */
            JSONObject data = hh.getLogin(email, password);
            if(data == null) {
            	status = "405";
            	message = "帳號或密碼錯誤";
            }else {
            	status = "200";
            	/**把email種進cookie*/
            	Cookie loginName= new Cookie("hotelEmail",email);
            	loginName.setPath("/");//這裡是之根目錄下所有的目錄都可以共享Cookie
            	loginName.setMaxAge(3600);
                response.addCookie(loginName);//新增Cookie
                System.out.println("hotel:"+email+" login");
            }
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            resp.put("status", status);
            resp.put("response", data);
            resp.put("message", message);
            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
		}
			
		
		
		
		
		}
		 /**
		 * 	登出
	     *
	     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
	     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
	     * @throws UnsupportedEncodingException
	     */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(req);

        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
        
        String user = jsr.getParameter("user");
		System.out.println(user);
		if(user.equals("member")) {
			Cookie[] cookies = req.getCookies();//這裡是取出Cookie
			Cookie cookie = new Cookie("memberEmail", null);
			cookie.setMaxAge(0);
			cookie.setPath("/");
			resp.addCookie(cookie);
			
			System.out.println(user+" logout");
		}else {
			Cookie[] cookies = req.getCookies();//這裡是取出Cookie
			Cookie cookie = new Cookie("hotelEmail", null);
			cookie.setMaxAge(0);
			cookie.setPath("/");
			resp.addCookie(cookie);
		}
		
		
	}
}
