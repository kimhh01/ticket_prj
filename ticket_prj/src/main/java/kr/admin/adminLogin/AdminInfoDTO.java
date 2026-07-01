package kr.admin.adminLogin;

public class AdminInfoDTO extends AdminDTO {

    private String adminName;
    private String adminEmail;
    private String adminTel;

    public AdminInfoDTO() {
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminTel() {
        return adminTel;
    }

    public void setAdminTel(String adminTel) {
        this.adminTel = adminTel;
    }

    @Override
    public String toString() {
        return "AdminInfoDTO [adminCode=" + getAdminCode()
                + ", id=" + getId()
                + ", adminName=" + adminName
                + ", adminEmail=" + adminEmail
                + ", adminTel=" + adminTel + "]";
    }
}