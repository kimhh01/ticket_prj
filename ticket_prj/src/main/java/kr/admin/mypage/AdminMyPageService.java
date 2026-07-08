package kr.admin.mypage;

import kr.admin.common.AES256Util;
import kr.admin.common.PasswordHashUtil;

public class AdminMyPageService {

    private AdminMyPageDAO dao;

    public AdminMyPageService() {
        dao = new AdminMyPageDAO();
    }

    public AdminMyPageDTO searchAdminInfo(int adminCode) {

        AdminMyPageDTO admin = dao.selectAdminInfo(adminCode);

        if (admin == null) {
            return null;
        }

        admin.setAdminName(AES256Util.decrypt(admin.getAdminName()));
        admin.setAdminEmail(AES256Util.decrypt(admin.getAdminEmail()));
        admin.setAdminTel(AES256Util.decrypt(admin.getAdminTel()));

        admin.setAdminPassword(null);

        return admin;
    }

    public boolean modifyAdminInfo(AdminMyPageDTO admin) {

        if (admin == null) {
            return false;
        }

        AdminMyPageDTO encryptedAdmin = new AdminMyPageDTO();

        encryptedAdmin.setAdminCode(admin.getAdminCode());
        encryptedAdmin.setAdminName(AES256Util.encrypt(admin.getAdminName()));
        encryptedAdmin.setAdminEmail(AES256Util.encrypt(admin.getAdminEmail()));
        encryptedAdmin.setAdminTel(AES256Util.encrypt(admin.getAdminTel()));

        int result = 0;

        if (admin.getAdminPassword() == null
                || admin.getAdminPassword().trim().isEmpty()) {

            result = dao.updateAdminInfo(encryptedAdmin);

        } else {

            String hashedPassword =
                    PasswordHashUtil.hashPassword(admin.getAdminPassword());

            encryptedAdmin.setAdminPassword(hashedPassword);

            result = dao.updateAdminInfoWithPassword(encryptedAdmin);
        }

        return result > 0;
    }
}