package ncu.im3069.Group24.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.*;

import ncu.im3069.Group24.app.Member;
import ncu.im3069.tools.JsonReader;

//TODO: Auto-generated Javadoc
/**
* <p>
* The Class ProductDetailController<br>
* ProductDetailController類別（class）主要用於處理ProductDetail結帳相關之Http請求（Request），繼承HttpServlet
* </p>
* 
* @author IPLab
* @version 1.0.0
* @since 1.0.0
*/

@WebServlet("/api/productDetail.do")
public class ProductDetailController extends HttpServlet{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    private JSONObject jso_post = null;
    
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
        jso_post = jso;
        
        /** 取出經解析到JSONObject之Request參數 */
        String hotel_id = jso.getString("hotel_id");
        String hotel_name = jso.getString("hotel_name");
        String room_id = jso.getString("room_id");
        String room_name = jso.getString("room_name");
        int room_amount = jso.getInt("room_amount");
        String room_price = jso.getString("room_price");
        String checkin_date = jso.getString("checkin_date");
        String checkout_date = jso.getString("checkout_date");

            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "成功傳遞結帳資訊");
            resp.put("response", "data");
            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
   
    }
    

    /**
     * 處理Http Method請求GET方法（取得資料）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
        //String id = jsr.getParameter("id");
        

            /** 透過MemberHelper物件之getAll()方法取回所有會員之資料，回傳之資料為JSONObject物件 */
            //JSONObject query = mh.getAll();
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "結帳資料取得成功");
            resp.put("response", jso_post);
    
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);

 

}
}
