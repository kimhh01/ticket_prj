package userEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.user.common.UserDBConnection;

public class CouponDAO {

    // 쿠폰 목록 조회
    public List<CouponDTO> selectCoupon() {

        List<CouponDTO> list = new ArrayList<>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        UserDBConnection db = UserDBConnection.getInstance();

        try {

            con = db.getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT ");
            sql.append("COUPON_CODE, ");
            sql.append("COUPON_DISCOUNT_RATE, ");
            sql.append("COUPON_NAME, ");
            sql.append("COUPON_DESC, ");
            sql.append("START_DATE, ");
            sql.append("END_DATE, ");
            sql.append("COUPON_TYPE ");
            sql.append("FROM COUPON ");
            sql.append("ORDER BY COUPON_CODE DESC");

            stmt = con.prepareStatement(sql.toString());

            rs = stmt.executeQuery();

            while(rs.next()){

                CouponDTO dto = new CouponDTO();

                dto.setCouponCode(rs.getString("COUPON_CODE"));
                dto.setCouponDiscountRate(rs.getInt("COUPON_DISCOUNT_RATE"));
                dto.setCouponName(rs.getString("COUPON_NAME"));
                dto.setCouponDesc(rs.getString("COUPON_DESC"));
                dto.setStartDate(rs.getDate("START_DATE"));
                dto.setEndDate(rs.getDate("END_DATE"));
                dto.setCouponType(rs.getString("COUPON_TYPE"));

                list.add(dto);
            }

        } catch(SQLException e) {

            e.printStackTrace();

        } finally {

            try {
                db.close(rs, stmt, con);
            } catch(SQLException e) {
                e.printStackTrace();
            }

        }

        return list;
    }  // 쿠폰 목록 조회
    
 // 이미 받은 쿠폰인지 확인
    public boolean hasCoupon(String memberId, String couponCode) {

        boolean flag = false;

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        UserDBConnection db = UserDBConnection.getInstance();

        try {

            con = db.getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT COUNT(*) CNT ");
            sql.append("FROM CUSTODY_COUPON ");
            sql.append("WHERE MEMBER_ID=? ");
            sql.append("AND COUPON_CODE=? ");

            stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, memberId);
            stmt.setString(2, couponCode);

            rs = stmt.executeQuery();

            if(rs.next()) {
                flag = rs.getInt("CNT") > 0;
            }

        } catch(SQLException e) {

            e.printStackTrace();

        } finally {

            try {
                db.close(rs, stmt, con);
            } catch(SQLException e) {
                e.printStackTrace();
            }

        }

        return flag;
    } // 이미 받은 쿠폰인지 확인

 // 쿠폰 발급
    public int insertCoupon(String memberId, String couponCode) {

        int result = 0;

        Connection con = null;
        PreparedStatement stmt = null;

        UserDBConnection db = UserDBConnection.getInstance();

        try {

            con = db.getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("INSERT INTO CUSTODY_COUPON (");
            sql.append("COUPON_CODE, ");
            sql.append("MEMBER_ID, ");
            sql.append("GET_DATE, ");
            sql.append("COUPON_STATE) ");
            sql.append("VALUES (?, ?, SYSDATE, ?)");

            stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, couponCode);
            stmt.setString(2, memberId);
            stmt.setString(3, "미사용");

            result = stmt.executeUpdate();

        } catch(SQLException e) {

            e.printStackTrace();

        } finally {

            db.close(stmt, con);

        }

        return result;
    } // 쿠폰 발급
    
}//end