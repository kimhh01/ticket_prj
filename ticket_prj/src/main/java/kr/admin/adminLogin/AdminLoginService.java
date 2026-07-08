package kr.admin.adminLogin;

import kr.admin.common.AES256Util;
import kr.admin.common.PasswordHashUtil;

public class AdminLoginService {

    private AdminLoginDAO dao;

    public AdminLoginService() {
        dao = new AdminLoginDAO();
    }

    // 로그인
    public AdminInfoDTO loginAdmin(AdminDTO adminDTO) {

        if (adminDTO == null
                || isEmpty(adminDTO.getId())
                || isEmpty(adminDTO.getPassword())) {
            return null;
        }

        AdminInfoDTO admin = dao.selectAdminById(adminDTO.getId());

        if (admin == null) {
            return null;
        }

        boolean passwordFlag = checkPasswordAndUpgradeIfNeeded(
                admin.getAdminCode(),
                adminDTO.getPassword(),
                admin.getPassword()
        );

        if (!passwordFlag) {
            return null;
        }

        decryptAdminInfo(admin);
        admin.setPassword(null);

        return admin;
    }

    public void logoutAdmin() {
        dao.clearAdminSession();
    }

    // 마이페이지 진입 전 비밀번호 확인
    public boolean checkPasswordForMyPage(int adminCode, String currentPw) {

        if (isEmpty(currentPw)) {
            return false;
        }

        String storedPassword = dao.selectPasswordByAdminCode(adminCode);

        return checkPasswordAndUpgradeIfNeeded(adminCode, currentPw, storedPassword);
    }

    private boolean checkPasswordAndUpgradeIfNeeded(int adminCode, String inputPassword, String storedPassword) {

        if (!checkPassword(inputPassword, storedPassword)) {
            return false;
        }

        // 기존 평문 또는 SHA-1 비밀번호로 로그인에 성공한 경우 PBKDF2로 자동 전환합니다.
        if (!PasswordHashUtil.isPbkdf2Hash(storedPassword)) {
            String upgradedPasswordHash = PasswordHashUtil.hashPassword(inputPassword);
            dao.updatePassword(adminCode, upgradedPasswordHash);
        }

        return true;
    }

    private boolean checkPassword(String inputPassword, String storedPassword) {

        if (inputPassword == null || storedPassword == null) {
            return false;
        }

        if (PasswordHashUtil.isPbkdf2Hash(storedPassword)) {
            return PasswordHashUtil.matches(inputPassword, storedPassword);
        }

        if (PasswordHashUtil.matchesLegacySha1(inputPassword, storedPassword)) {
            return true;
        }

        // 기존 평문 비밀번호 지원: 로그인/비밀번호 확인 성공 시 PBKDF2로 자동 전환됩니다.
        return storedPassword.equals(inputPassword);
    }

    private void decryptAdminInfo(AdminInfoDTO admin) {

        admin.setAdminName(AES256Util.decrypt(admin.getAdminName()));
        admin.setAdminEmail(AES256Util.decrypt(admin.getAdminEmail()));
        admin.setAdminTel(AES256Util.decrypt(admin.getAdminTel()));
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
