package ncu.im3069.Group24.app;

import org.json.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class Member
 * Member類別（class）具有會員所需要之屬性與方法，並且儲存與會員相關之商業判斷邏輯<br>
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class Member {
	 /** id，會員編號 */
    private int id;
    
    /** name，會員名 */
    private String firstName;
    
    /** name，會員姓 */
    private String lastName;
    
    /** email，會員電子郵件信箱 */
    private String email;
    
    /** password，會員密碼 */
    private String password;
    
    /** modified_times，更新時間 */
    private Date modifiedTimes;
    
    /** created_times，創建時間 */
    private Date createdTimes;
    
    /** birthday，會員的生日*/
    private Date birthday;
    
    /** mh，MemberHelper之物件與Member相關之資料庫方法（Singleton） */
    private MemberHelper mh =  MemberHelper.getHelper();
    
    /**
     * 實例化（Instantiates）一個新的（new）Member物件
     * 採用多載（overload）方法進行，此建構子用於建立會員資料時，產生一名新的會員
     *
     * @param email 會員電子信箱
     * @param password 會員密碼
     * @param fname 會員名
     * @param lname 會員姓
     * @param birthday 會員生日
     * @throws ParseException 
     */
    public Member(String email, String password, String fname, String lname, Date birthday) throws ParseException {
        this.email = email;
        this.password = password;
        this.firstName = fname;
        this.lastName = lname;
        this.birthday = birthday;
        
    }

    /**
     * 實例化（Instantiates）一個新的（new）Member物件<br>
     * 採用多載（overload）方法進行，此建構子用於更新會員資料時，產生一名會員
     * 
     * @param id 會員編號
     * @param email 會員電子信箱
     * @param password 會員密碼
     * @param name 會員姓名
     * @throws ParseException 
     */
    public Member(int id, String email, String password, String fname, String lname, Date birthday) throws ParseException {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = fname;
        this.lastName = lname;
        getTimesStatus(birthday);
        update();
    }
    
    /**
     * 實例化（Instantiates）一個新的（new）Member物件<br>
     * 採用多載（overload）方法進行，此建構子用於查詢會員資料時，將每一筆資料新增為一個會員物件
     *
     * @param id 會員編號
     * @param email 會員電子信箱
     * @param password 會員密碼
     * @param fname 會員名
     * @param lname 會員姓
     * @param orderID 會員之訂單號碼
     * @throws ParseException 
     */
    public Member(int id, String email, String password, String fname, String lname) throws ParseException {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = fname;
        this.lastName = lname;
        getTimesStatus();
    }
    
    /**
     * 取得會員之編號
     *
     * @return the id 回傳會員編號
     */
    public int getID() {
        return this.id;
    }

    /**
     * 取得會員之電子郵件信箱
     *
     * @return the email 回傳會員電子郵件信箱
     */
    public String getEmail() {
        return this.email;
    }
    
    /**
    * 取得會員之名
    *
    * @return the firstName 回傳會員的名
    */
    public String getFirstName() {
		return this.firstName;
	}

    /**
     * 取得會員之姓
     *
     * @return the lastName 回傳會員的姓
     */
	public String getLastName() {
		return this.lastName;
	}
    

    /**
     * 取得會員之密碼
     *
     * @return the password 回傳會員密碼
     */
    public String getPassword() {
        return this.password;
    }
    
    /**
     * 取得會員資料之更新時間
     *
     * @return the modifiedTimes 回傳會員更新時間
     */
	public Date getModifiedTimes() {
		return this.modifiedTimes;
	}

	 /**
     * 取得會員資料之創建時間
     *
     * @return the createdTimes 回傳會員創建時間
     */
	public Date getCreatedTimes() {
		return this.createdTimes;
	}
	

    /**
     * 取得會員資料之生日
     *
     * @return the birthday 回傳會員組別
     */
	public Date getBirthday() {
		return this.birthday;
	}

	  /**

	/**
     * 更新會員資料
     *
     * @return the JSON object 回傳SQL更新之結果與相關封裝之資料
     */
    public JSONObject update() {
        /** 新建一個JSONObject用以儲存更新後之資料 */
        JSONObject data = new JSONObject();
        /** 取得更新資料時間 */
        
        
        /** 檢查該名會員是否已經在資料庫 */
        if(this.id != 0) {
            /** 透過MemberHelper物件，更新目前之會員資料置資料庫中 */
            data = mh.update(this);
        }
        
        return data;
    }
    
    /**
     * 取得該名會員所有資料
     *
     * @return the data 取得該名會員之所有資料並封裝於JSONObject物件內
     */
    public JSONObject getData() {
    	SimpleDateFormat birthdaysdf  = new SimpleDateFormat("yyyy-MM-dd");
        /** 透過JSONObject將該名會員所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("id", getID());
        jso.put("firstname", getFirstName());
        jso.put("lastname", getLastName());
        jso.put("email", getEmail());
        jso.put("password", getPassword());
        jso.put("modifiedTimes", getModifiedTimes());
        jso.put("createdTimes", getCreatedTimes());
        jso.put("birthday", birthdaysdf.format(getBirthday()));

        return jso;
    }
    
    /**
     * 取得資料庫內之更新資料時間分鐘數與會員組別
     * 用於查詢和建立
     * @throws ParseException 
     *
     */
    private void getTimesStatus() throws ParseException {
        /** 透過MemberHelper物件，取得儲存於資料庫的更新時間、創建時間、會員組別與會員生日 */
        JSONObject data = mh.getTimesStatus(this);
        
        /** 將資料庫所儲存該名會員之相關資料指派至Member物件之屬性 */
        String modifiedTimes = data.getString("modifiedTimes");
        String createdTimes = data.getString("createdTimes");
        String birthday = data.getString("birthday");
        /**設定日期時間格式*/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        SimpleDateFormat birthdaysdf  = new SimpleDateFormat("yyyy-MM-dd");
        
        this.modifiedTimes = sdf.parse(modifiedTimes);
        this.createdTimes = sdf.parse(createdTimes);
        this.birthday = birthdaysdf.parse(birthday);
    }
    
    /** 此方法專用於更新*/
    private void getTimesStatus(Date birthday) throws ParseException {
        /** 透過MemberHelper物件，取得儲存於資料庫的更新時間、創建時間、會員組別與會員生日 */
        JSONObject data = mh.getTimesStatus(this);
        
        /** 將資料庫所儲存該名會員之相關資料指派至Member物件之屬性 */
        String createdTimes = data.getString("createdTimes");
        
        
        /**設定日期時間格式*/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        this.modifiedTimes = Timestamp.valueOf(LocalDateTime.now().plusHours(8));
        this.createdTimes = sdf.parse(createdTimes);
        this.birthday = birthday;
        
    }
}