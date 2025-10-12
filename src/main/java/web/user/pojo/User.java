package web.user.pojo;

import java.sql.Date;
import java.sql.Timestamp;

import core.pojo.Core;

public class User  extends Core{

	private static final long serialVersionUID = 1L;
	// === 欄位對應 ===
    private Integer userId;           // 使用者ID (PRIMARY KEY)
    private Integer cntCode;          // 縣市代碼ID (FK → country.cnt_code)
    private Integer distCode;         // 鄉鎮區代碼ID (FK → district.dist_code)
    private String detailAddress;     // 詳細地址
    private String email;             // 帳號 (UNIQUE)
    private String password;          // 密碼
    private String name;              // 姓名
    private String resetCode;         // 6位數認證碼
    private String phone;             // 電話號碼
    private String avatarUrl;         // 頭像URL
    private Boolean isBanned;         // 黑名單狀態
    private Date birthday;            // 生日
    private String gender;            // 性別 (M/F)
    private Timestamp createdAt;      // 建立時間
    
    

    // === Getter / Setter ===

    public User() {
	
	}
    
	public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCntCode() {
        return cntCode;
    }
    public void setCntCode(Integer cntCode) {
        this.cntCode = cntCode;
    }

    public Integer getDistCode() {
        return distCode;
    }
    public void setDistCode(Integer distCode) {
        this.distCode = distCode;
    }

    public String getDetailAddress() {
        return detailAddress;
    }
    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getResetCode() {
        return resetCode;
    }
    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Boolean getIsBanned() {
        return isBanned;
    }
    public void setIsBanned(Boolean isBanned) {
        this.isBanned = isBanned;
    }

    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // === toString() ===
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", cntCode=" + cntCode +
                ", distCode=" + distCode +
                ", detailAddress='" + detailAddress + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", resetCode='" + resetCode + '\'' +
                ", phone='" + phone + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", isBanned=" + isBanned +
                ", birthday=" + birthday +
                ", gender='" + gender + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}