package ncu.im3069.Group24.app;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.json.*;
public class Hotel {

    /** id，店家編號 */
    private int hotel_id;

    /** name，店家名稱 */
    private String hotel_name;
    
    /** email，店家信箱 */
    private String hotel_email;
    
    /** password，店家密碼 */
    private String hotel_password;
    
    /** image，店家圖片 */
    private String hotel_image;
    
    /** description，店家描述 */
    private String hotel_description;
    
    /** phone，店家電話 */
    private String hotel_phone;
    
    /** modifiedTimes，店家更新日期 */
    private Date hotel_modified;
    
    /** createdTimes，店家建立日期 */
    private Date hotel_created;
    
    /** country，店家國家編號 */
    private int hotel_country_id;
    /** country，店家國家名稱 */
    private String hotel_country_name;
    
    /** city，店家城市編號 */
    private int hotel_city_id;
    /** city，店家城市名稱 */
    private String hotel_city_name;
    
    /** address，店家詳細地址 */
    private String hotel_address;
    
    /** lowest_price，該旅館最低房間價格 */
    private Double hotel_lowest_price;
   
    /** hh，ProductHelper之物件與Product相關之資料庫方法（Sigleton） */
    private HotelHelper hh =  HotelHelper.getHelper();
    
    
/*********************Constructor***********************/
    
    /**
     * 實例化（Instantiates）一個新的（new）HotelProduct物件<br>
     * 採用多載（overload）方法進行，此建構子用於檢查店家是否存在時，產生一個新的店家
     *
     * 
     * @param hotel_id 店家編號
     * @throws ParseException 
     */
    public Hotel(int id) throws ParseException{
     this.hotel_id = id;
    }
    
    
    /**
     * 實例化（Instantiates）一個新的（new）HotelProduct物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立店家資料時，產生一名新的店家
     *
     * 
     * @param email 店家電子信箱
     * @param password 店家密碼
     * @param name 店家名稱
     * @param image 店家圖片
     * @param description 店家描述
     * @param phone 店家電話
     * @param country_id 店家國家編號
     * @param city_id 店家城市編號
     * @param address 店家地址
     * @throws ParseException 
     */
    public Hotel(String email, String password, String name, 
    		String image, String description, String phone, 
    		int countryid, int cityid, String address) throws ParseException{
        
    	this.hotel_email = email;
        this.hotel_password = password;
        this.hotel_name = name;
        this.hotel_image = image;
        this.hotel_description = description;
        this.hotel_phone = phone;
        this.hotel_address = address;
        this.hotel_country_id = countryid;
        this.hotel_city_id = cityid;
        getCountryCity(countryid, cityid);
        update();
    }
    
    /**
     * 實例化（Instantiates）一個新的（new）HotelProduct物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立店家資料時，產生一名新的店家
     *
     * 
     * @param email 店家電子信箱
     * @param password 店家密碼
     * @param name 店家名稱
     * @param image 店家圖片
     * @param description 店家描述
     * @param phone 店家電話
     * @param country_name 店家國家名稱
     * @param city_name 店家城市名稱
     * @param address 店家地址
     * @throws ParseException 
     */
    public Hotel(String email, String password, String name, 
    		String image, String description, String phone, 
    		String countryname, String cityname, String address) throws ParseException{
        
    	this.hotel_email = email;
        this.hotel_password = password;
        this.hotel_name = name;
        this.hotel_image = image;
        this.hotel_description = description;
        this.hotel_phone = phone;
        this.hotel_address = address;
        this.hotel_country_name = countryname;
        this.hotel_city_name = cityname;
        getCountryCity(countryname, cityname);
        update();
    }
    

    /**
     * 實例化（Instantiates）一個新的（new）Product物件<br>
     * 採用多載（overload）方法進行，此建構子用於更新店家資料時，產生一名店家。
     * 
     * @param name 店家名稱
     * @param email 店家電子信箱
     * @param password 店家密碼
     * @param image 店家圖片
     * @param description 店家描述
     * @param phone 店家電話
     * @param country_id 店家國家編號
     * @param city_id 店家城市編號
     * @param address 店家地址
     */
    public Hotel(int id, String email, String password, String name,
    		String image, String description, String phone, int countryid, 
    		int cityid, String address, Date modified, Date created)  throws ParseException{
    	this.hotel_id = id;
    	this.hotel_email = email;
        this.hotel_password = password;
        this.hotel_name = name;
        this.hotel_image = image;
        this.hotel_description = description;
        this.hotel_phone = phone;
        this.hotel_country_id = countryid;
        this.hotel_city_id = cityid;
        this.hotel_address = address;
        this.hotel_modified = modified;
        this.hotel_created = created;
        getLowestPrice(id);
        getCountryCity(countryid, cityid);

    }
    
    /**
     * 實例化（Instantiates）一個新的（new）Hotel物件<br>
     * 採用多載（overload）方法進行，此建構子用於更新店家資料時，產生一名店家。
     * 
     * @param name 店家名稱
     * @param email 店家電子信箱
     * @param password 店家密碼
     * @param image 店家圖片
     * @param description 店家描述
     * @param phone 店家電話
     * @param country_name 店家國家名稱
     * @param city_name 店家城市名稱
     * @param address 店家地址
     */
    public Hotel(int id, String email, String password, String name,
    		String image, String description, String phone, String countryname, 
    		String cityname, String address, Date modified, Date created)  throws ParseException{
    	this.hotel_id = id;
    	this.hotel_email = email;
        this.hotel_password = password;
        this.hotel_name = name;
        this.hotel_image = image;
        this.hotel_description = description;
        this.hotel_phone = phone;
        this.hotel_country_name = countryname;
        this.hotel_city_name = cityname;
        this.hotel_address = address;
        this.hotel_modified = modified;
        this.hotel_created = created;
        getLowestPrice(id);
        getCountryCity(countryname, cityname);
        
    }
    
    /**
     * 實例化（Instantiates）一個新的（new）Hotel物件<br>
     * 採用多載（overload）方法進行，此建構子用於更新店家資料時，產生一名店家。
     * 
     * @param name 店家名稱
     * @param email 店家電子信箱
     * @param password 店家密碼
     * @param image 店家圖片
     * @param description 店家描述
     * @param phone 店家電話
     * @param country_name 店家國家名稱
     * @param city_name 店家城市名稱
     * @param address 店家地址
     */
    public Hotel(int id, String email, String password, String name,
    		String image, String description, String phone, String countryname, 
    		String cityname, String address)  throws ParseException{
    	this.hotel_id = id;
    	this.hotel_email = email;
        this.hotel_password = password;
        this.hotel_name = name;
        this.hotel_image = image;
        this.hotel_description = description;
        this.hotel_phone = phone;
        this.hotel_country_name = countryname;
        this.hotel_city_name = cityname;
        this.hotel_address = address;
        getLowestPrice(id);
        getCountryCity(countryname, cityname);
        
    }
    
    /**
     * 實例化（Instantiates）一個新的（new）Hotel物件<br>
     * 採用多載（overload）方法進行，此建構子用於檢視或查詢店家資料時，產生一名店家。
     * 
     * @param name 店家名稱
     * @param email 店家電子信箱
     * @param image 店家圖片
     * @param description 店家描述
     * @param phone 店家電話
     * @param country_name 店家國家名稱
     * @param city_name 店家城市名稱
     * @param address 店家地址
     */
    public Hotel(int id, String email, String name,
    		String image, String description, String phone, Date modified, Date created,
    		String countryname, String cityname, String address, Double price){
    	this.hotel_id = id;
    	this.hotel_email = email;
        this.hotel_name = name;
        this.hotel_image = image;
        this.hotel_description = description;
        this.hotel_phone = phone;
        this.hotel_country_name = countryname;
        this.hotel_city_name = cityname;
        this.hotel_address = address;
        this.hotel_lowest_price = price;
        this.hotel_modified = modified;
        this.hotel_created = created;
        getCountryCity(countryname, cityname);
        
        
    }
    
    /**
     * 實例化（Instantiates）一個新的（new）Hotel物件<br>
     * 採用多載（overload）方法進行，此建構子用於檢視或查詢店家資料時，產生一名店家。
     * 
     * @param name 店家名稱
     * @param email 店家電子信箱
     * @param password 店家密碼
     * @param image 店家圖片
     * @param description 店家描述
     * @param phone 店家電話
     * @param country_name 店家國家名稱
     * @param city_name 店家城市名稱
     * @param address 店家地址
     */
    public Hotel(int id, String email, String password, String name,
    		String image, String description, String phone, 
    		int country_id, int city_id, String address){
    	this.hotel_id = id;
    	this.hotel_email = email;
    	this.hotel_password = password;
        this.hotel_name = name;
        this.hotel_image = image;
        this.hotel_description = description;
        this.hotel_phone = phone;
        this.hotel_country_id = country_id;
        this.hotel_city_id = city_id;
        this.hotel_address = address;
        getCountryCity(country_id, city_id);
        
        
    }
    /*********************get methods***********************/
    /**
     * 取得店家編號
     *
     * @return int 回傳店家編號
     */
	public int getID() {
		return this.hotel_id;
	}

    /**
     * 取得店家名稱
     *
     * @return String 回傳店家名稱
     */
	public String getName() {
		return this.hotel_name;
	}
	
    /**
     * 取得店家信箱
     *
     * @return String 回傳店家信箱
     */
	public String getEmail() {
		return this.hotel_email;
	}
	
    /**
     * 取得店家密碼
     *
     * @return String 回傳店家密碼
     */
	public String getPassword() {
		return this.hotel_password;
	}
	
    /**
     * 取得店家圖片
     *
     * @return String 回傳店家圖片
     */
	public String getImage() {
		return this.hotel_image;
	}
	
    /**
     * 取得店家描述
     *
     * @return String 回傳店家描述
     */
	public String getDescribe() {
		return this.hotel_description;
	}
	
    /**
     * 取得店家電話
     *
     * @return String 回傳店家電話
     */
	public String getPhone() {
		return this.hotel_phone;
	}
	
    /**
     * 取得店家國家編號
     *
     * @return int 回傳店家國家編號
     */
	public int getCountryId() {
		return this.hotel_country_id;
	}
	
    /**
     * 取得店家國家名稱
     *
     * @return String 回傳店家國家名稱
     */
	public String getCountryName() {
		return this.hotel_country_name;
	}
	
    /**
     * 取得店家城市編號
     *
     * @return int 回傳店家城市編號
     */
	public int getCityId() {
		return this.hotel_city_id;
	}
	
    /**
     * 取得店家城市名稱
     *
     * @return String 回傳店家城市名稱
     */
	public String getCityName() {
		return this.hotel_city_name;
	}
	
    /**
     * 取得店家詳細地址
     *
     * @return String 回傳店家詳細地址
     */
	public String getAddress() {
		return this.hotel_address;
	}

    /**
     * 取得店家最低房間價格
     *
     * @return double 回傳店家最低房間價格
     */
	public double getHotelLowestPrice() {
		return this.hotel_lowest_price;
	}
	
	 /**
     * 取得店家最低房間價格
     *
     * @return double 回傳店家最低房間價格
     */
	public Date getModified() {
		return this.hotel_modified;
	}
	
	 /**
     * 取得店家最低房間價格
     *
     * @return double 回傳店家最低房間價格
     */
	public Date getCreated() {
		return this.hotel_created;
	}
	
	
    /**甇斗瘜��閰ｇ����澈�����楊��誑���振蝺刻�������澈鋆∪���振頝���*/
    private void getCountryCity(int city_id, int country_id)  {
        /** ��otelHelper�隞塚���摮鞈�澈���振��振隞亙��振���� */
        String country_name = hh.getCountryName(country_id);
        String city_name = hh.getCityName(country_id);
        
        /** 撠��澈���摮府���振銋�����晷�Hotel�隞嗡�惇�� */
        this.hotel_country_name = country_name;
        this.hotel_city_name = city_name;
    }
    
    /**甇斗瘜�����遣蝡���垢����振�����鞈�澈�����楊��誑���振蝺刻��*/
    private void getCountryCity(String country_name,String city_name)  {
        /** ��otelHelper�隞塚���摮鞈�澈�������撱箸����振蝯 */
        int country_id = hh.getByCountryId(country_name);
        int city_id = hh.getByCityId(city_name);
       
        
        this.hotel_country_id = country_id;
        this.hotel_city_id = city_id;
    }
    
    private void getLowestPrice(int hotel_id) {
    	double lowest_price = hh.getLowestPrice(hotel_id);
    	
    	this.hotel_lowest_price = lowest_price;
    }
    
    /*********************update***********************/
    /**
     * 更新店家資料
     *
     * @return the JSON object 回傳SQL更新之結果與相關封裝之資料
     */
    public JSONObject update() {
        /** 新建一個JSONObject用以儲存更新後之資料 */
        JSONObject data = new JSONObject();
        
        /** 檢查該名店家是否已經在資料庫 */
        if(this.hotel_id != 0) {
            /** 透過ProductHelper物件，更新目前之店家資料置資料庫中 */
            data = hh.update(this);
        }
        
        return data;
    }
	
	/*********************封裝JsonObject***********************/
    /**
     * 取得店家所有資訊(店家管理用)
     *
     * @return JSONObject 回傳店家資訊
     */
	public JSONObject getData() {
        /** 透過JSONObject將該項店家所需之資料全部進行封裝*/
        JSONObject jso = new JSONObject();
        jso.put("id", getID());
        jso.put("name", getName());
        jso.put("email", getEmail());
        jso.put("password", getPassword());
        jso.put("image", getImage());
        jso.put("description", getDescribe());
        jso.put("phone", getPhone());
        jso.put("country_id", getCountryId());
        jso.put("city_id", getCityId());
        jso.put("countryname", getCountryName());
        jso.put("cityname", getCityName());
        jso.put("lowestprice", getHotelLowestPrice());
        jso.put("address", getAddress());
        jso.put("image", getImage());
        jso.put("modifiedTimes", getModified());
        jso.put("createdTimes",getCreated());

        return jso;
    }


    /**
     * 取得店家檢視資訊(搜尋店家用)
     *
     * @return JSONObject 回傳店家資訊
     */
	public JSONObject getSearchData() {
        /** 透過JSONObject將該項店家所需之資料全部進行封裝*/
        JSONObject jso = new JSONObject();
        jso.put("id", getID());
        jso.put("email", getEmail());
        jso.put("name", getName());
        jso.put("lowestprice", getHotelLowestPrice());
        jso.put("image", getImage());
        jso.put("phone", getPhone());
        jso.put("description", getDescribe());
        jso.put("countryname", getCountryName());
        jso.put("cityname", getCityName());
        jso.put("address", getAddress());
        jso.put("modifiedTimes", getModified());
        jso.put("createdTimes", getCreated());
        
        return jso;
    }
}
