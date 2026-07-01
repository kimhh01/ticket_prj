package kr.admin.mypage;

public class AdminMyPageService {

    private AdminMyPageDAO dao;

    public AdminMyPageService() {
        dao = new AdminMyPageDAO();
    }

    public AdminMyPageDTO searchAdminInfo(int adminCode) {
        return dao.selectAdminInfo(adminCode);
    }

    public boolean modifyAdminInfo(AdminMyPageDTO admin) {

        int result = 0;

        if (admin.getAdminPassword() == null
                || admin.getAdminPassword().trim().isEmpty()) {

            result = dao.updateAdminInfo(admin);

        } else {

            result = dao.updateAdminInfoWithPassword(admin);
        }

        return result > 0;
    }
}