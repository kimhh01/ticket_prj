package kr.admin.coupon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import kr.admin.common.AdminDBConnection;

public class CouponManagementDAO {

    /*
     * 쿠폰 목록 조회
     */
    public List<CouponManagementDTO> selectCouponList() {

        List<CouponManagementDTO> list =
                new ArrayList<>();

        StringBuilder sql =
                new StringBuilder();

        sql.append(" SELECT c.coupon_code, ");
        sql.append("        c.coupon_type, ");
        sql.append("        c.coupon_discount_rate, ");
        sql.append("        c.coupon_name, ");
        sql.append("        c.coupon_desc, ");
        sql.append("        TO_CHAR(c.start_date, 'YYYY-MM-DD') AS start_date, ");
        sql.append("        TO_CHAR(c.end_date, 'YYYY-MM-DD') AS end_date, ");
        sql.append("        NVL(cc.issued_count, 0) AS issued_count, ");
        sql.append("        NVL(cc.usable_count, 0) AS usable_count, ");
        sql.append("        NVL(cc.used_count, 0) AS used_count ");
        sql.append("   FROM coupon c ");
        sql.append("   LEFT JOIN ( ");
        sql.append("        SELECT coupon_code, ");
        sql.append("               COUNT(*) AS issued_count, ");
        sql.append("               SUM(CASE WHEN coupon_state = '사용가능' THEN 1 ELSE 0 END) AS usable_count, ");
        sql.append("               SUM(CASE WHEN coupon_state = '사용완료' THEN 1 ELSE 0 END) AS used_count ");
        sql.append("          FROM custody_coupon ");
        sql.append("         GROUP BY coupon_code ");
        sql.append("   ) cc ");
        sql.append("     ON c.coupon_code = cc.coupon_code ");
        sql.append("  ORDER BY c.start_date DESC, c.coupon_code ");

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql.toString());

            ResultSet rs =
                    pstmt.executeQuery()
        ) {

            while (rs.next()) {

                CouponManagementDTO dto =
                        new CouponManagementDTO();

                dto.setCouponCode(
                        rs.getString("coupon_code"));
                
                dto.setCouponType(
                		rs.getString("coupon_type"));

                dto.setCouponDiscountRate(
                        rs.getInt("coupon_discount_rate"));

                dto.setCouponName(
                        rs.getString("coupon_name"));

                dto.setCouponDesc(
                        rs.getString("coupon_desc"));

                dto.setStartDate(
                        rs.getString("start_date"));

                dto.setEndDate(
                        rs.getString("end_date"));

                dto.setIssuedCount(
                        rs.getInt("issued_count"));

                dto.setUsableCount(
                        rs.getInt("usable_count"));

                dto.setUsedCount(
                        rs.getInt("used_count"));

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /*
     * 쿠폰 상세 조회
     */
    public CouponManagementDTO selectCouponDetail(String couponCode) {

        CouponManagementDTO dto =
                null;

        StringBuilder sql =
                new StringBuilder();

        sql.append(" SELECT c.coupon_code, ");
        sql.append("        c.coupon_type, ");
        sql.append("        c.coupon_discount_rate, ");
        sql.append("        c.coupon_name, ");
        sql.append("        c.coupon_desc, ");
        sql.append("        TO_CHAR(c.start_date, 'YYYY-MM-DD') AS start_date, ");
        sql.append("        TO_CHAR(c.end_date, 'YYYY-MM-DD') AS end_date, ");
        sql.append("        NVL(cc.issued_count, 0) AS issued_count, ");
        sql.append("        NVL(cc.usable_count, 0) AS usable_count, ");
        sql.append("        NVL(cc.used_count, 0) AS used_count ");
        sql.append("   FROM coupon c ");
        sql.append("   LEFT JOIN ( ");
        sql.append("        SELECT coupon_code, ");
        sql.append("               COUNT(*) AS issued_count, ");
        sql.append("               SUM(CASE WHEN coupon_state = '사용가능' THEN 1 ELSE 0 END) AS usable_count, ");
        sql.append("               SUM(CASE WHEN coupon_state = '사용완료' THEN 1 ELSE 0 END) AS used_count ");
        sql.append("          FROM custody_coupon ");
        sql.append("         GROUP BY coupon_code ");
        sql.append("   ) cc ");
        sql.append("     ON c.coupon_code = cc.coupon_code ");
        sql.append("  WHERE c.coupon_code = ? ");

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql.toString())
        ) {

            pstmt.setString(1, couponCode);

            try (
                ResultSet rs =
                        pstmt.executeQuery()
            ) {

                if (rs.next()) {

                    dto =
                            new CouponManagementDTO();

                    dto.setCouponCode(
                            rs.getString("coupon_code"));
                    
                    dto.setCouponType(
                    		rs.getString("coupon_type"));

                    dto.setCouponDiscountRate(
                            rs.getInt("coupon_discount_rate"));

                    dto.setCouponName(
                            rs.getString("coupon_name"));

                    dto.setCouponDesc(
                            rs.getString("coupon_desc"));

                    dto.setStartDate(
                            rs.getString("start_date"));

                    dto.setEndDate(
                            rs.getString("end_date"));

                    dto.setIssuedCount(
                            rs.getInt("issued_count"));

                    dto.setUsableCount(
                            rs.getInt("usable_count"));

                    dto.setUsedCount(
                            rs.getInt("used_count"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }

    /*
     * 쿠폰 등록
     * couponCode가 비어 있으면 CP001, CP002 형식으로 자동 생성
     */
    public String insertCoupon(CouponManagementDTO coupon) {

        String couponCode =
                coupon.getCouponCode();

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection()
        ) {

            if (couponCode == null ||
                couponCode.trim().isEmpty()) {

                couponCode =
                        generateNextCouponCode(con);
            }

            StringBuilder sql =
                    new StringBuilder();

            sql.append(" INSERT INTO coupon ( ");
            sql.append("        coupon_code, ");
            sql.append("        coupon_type, ");
            sql.append("        coupon_discount_rate, ");
            sql.append("        coupon_name, ");
            sql.append("        coupon_desc, ");
            sql.append("        start_date, ");
            sql.append("        end_date ");
            sql.append(" ) VALUES ( ");
            sql.append("        ?, ");
            sql.append("        ?, ");
            sql.append("        ?, ");
            sql.append("        ?, ");
            sql.append("        ?, ");
            sql.append("        TO_DATE(?, 'YYYY-MM-DD'), ");
            sql.append("        TO_DATE(?, 'YYYY-MM-DD') ");
            sql.append(" ) ");

            try (
                PreparedStatement pstmt =
                        con.prepareStatement(sql.toString())
            ) {

            	pstmt.setString(1, couponCode);
            	pstmt.setString(2, coupon.getCouponType());
            	pstmt.setInt(3, coupon.getCouponDiscountRate());
            	pstmt.setString(4, coupon.getCouponName());
            	pstmt.setString(5, coupon.getCouponDesc());
            	pstmt.setString(6, coupon.getStartDate());
            	pstmt.setString(7, coupon.getEndDate());
            	
                int result =
                        pstmt.executeUpdate();

                if (result > 0) {
                    return couponCode;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
     * 쿠폰 수정
     */
    public boolean updateCoupon(CouponManagementDTO coupon) {

        boolean resultFlag =
                false;

        StringBuilder sql =
                new StringBuilder();

        sql.append(" UPDATE coupon ");
        sql.append("    SET coupon_type = ?, ");
        sql.append("        coupon_discount_rate = ?, ");
        sql.append("        coupon_name = ?, ");
        sql.append("        coupon_desc = ?, ");
        sql.append("        start_date = TO_DATE(?, 'YYYY-MM-DD'), ");
        sql.append("        end_date = TO_DATE(?, 'YYYY-MM-DD') ");
        sql.append("  WHERE coupon_code = ? ");

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql.toString())
        ) {

        	pstmt.setString(1, coupon.getCouponType());
        	pstmt.setInt(2, coupon.getCouponDiscountRate());
        	pstmt.setString(3, coupon.getCouponName());
        	pstmt.setString(4, coupon.getCouponDesc());
        	pstmt.setString(5, coupon.getStartDate());
        	pstmt.setString(6, coupon.getEndDate());
        	pstmt.setString(7, coupon.getCouponCode());

            int result =
                    pstmt.executeUpdate();

            resultFlag =
                    result > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultFlag;
    }

    /*
     * 쿠폰 삭제
     * 이미 사용자에게 지급된 쿠폰이 있으면 삭제하지 않음
     */
    public boolean deleteCoupon(String couponCode) {

        boolean resultFlag =
                false;

        if (selectCustodyCouponCount(couponCode) > 0) {
            System.out.println("사용자 보유 쿠폰이 있어서 삭제 불가 : " + couponCode);
            return false;
        }

        String sql =
                " DELETE FROM coupon WHERE coupon_code = ? ";

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql)
        ) {

            pstmt.setString(1, couponCode);

            int result =
                    pstmt.executeUpdate();

            resultFlag =
                    result > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultFlag;
    }

    /*
     * 사용자 보유 쿠폰 목록 조회
     */
    public List<CustodyCouponDTO> selectCustodyCouponList(String couponCode) {

        List<CustodyCouponDTO> list =
                new ArrayList<>();

        StringBuilder sql =
                new StringBuilder();

        sql.append(" SELECT cc.coupon_code, ");
        sql.append("        cc.member_id, ");
        sql.append("        TO_CHAR(cc.get_date, 'YYYY-MM-DD HH24:MI:SS') AS get_date, ");
        sql.append("        cc.coupon_state, ");
        sql.append("        c.coupon_name, ");
        sql.append("        c.coupon_discount_rate ");
        sql.append("   FROM custody_coupon cc ");
        sql.append("   JOIN coupon c ");
        sql.append("     ON cc.coupon_code = c.coupon_code ");
        sql.append("  WHERE cc.coupon_code = ? ");
        sql.append("  ORDER BY cc.get_date DESC, cc.member_id ");

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql.toString())
        ) {

            pstmt.setString(1, couponCode);

            try (
                ResultSet rs =
                        pstmt.executeQuery()
            ) {

                while (rs.next()) {

                    CustodyCouponDTO dto =
                            new CustodyCouponDTO();

                    dto.setCouponCode(
                            rs.getString("coupon_code"));

                    dto.setMemberId(
                            rs.getString("member_id"));

                    dto.setGetDate(
                            rs.getString("get_date"));

                    dto.setCouponState(
                            rs.getString("coupon_state"));

                    dto.setCouponName(
                            rs.getString("coupon_name"));

                    dto.setCouponDiscountRate(
                            rs.getInt("coupon_discount_rate"));

                    list.add(dto);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /*
     * 특정 회원에게 쿠폰 지급
     */
    public boolean insertCustodyCoupon(String couponCode,
                                       String memberId) {

        boolean resultFlag =
                false;

        StringBuilder sql =
                new StringBuilder();

        sql.append(" INSERT INTO custody_coupon ( ");
        sql.append("        coupon_code, ");
        sql.append("        member_id, ");
        sql.append("        get_date, ");
        sql.append("        coupon_state ");
        sql.append(" ) VALUES ( ");
        sql.append("        ?, ");
        sql.append("        ?, ");
        sql.append("        SYSDATE, ");
        sql.append("        '사용가능' ");
        sql.append(" ) ");

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql.toString())
        ) {

            pstmt.setString(1, couponCode);
            pstmt.setString(2, memberId);

            int result =
                    pstmt.executeUpdate();

            resultFlag =
                    result > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultFlag;
    }

    /*
     * 사용자 보유 쿠폰 상태 변경
     */
    public boolean updateCustodyCouponState(String couponCode,
                                            String memberId,
                                            String couponState) {

        boolean resultFlag =
                false;

        StringBuilder sql =
                new StringBuilder();

        sql.append(" UPDATE custody_coupon ");
        sql.append("    SET coupon_state = ? ");
        sql.append("  WHERE coupon_code = ? ");
        sql.append("    AND member_id = ? ");

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql.toString())
        ) {

            pstmt.setString(1, couponState);
            pstmt.setString(2, couponCode);
            pstmt.setString(3, memberId);

            int result =
                    pstmt.executeUpdate();

            resultFlag =
                    result > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultFlag;
    }

    /*
     * 사용자 보유 쿠폰 삭제
     */
    public boolean deleteCustodyCoupon(String couponCode,
                                       String memberId) {

        boolean resultFlag =
                false;

        StringBuilder sql =
                new StringBuilder();

        sql.append(" DELETE FROM custody_coupon ");
        sql.append("  WHERE coupon_code = ? ");
        sql.append("    AND member_id = ? ");

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql.toString())
        ) {

            pstmt.setString(1, couponCode);
            pstmt.setString(2, memberId);

            int result =
                    pstmt.executeUpdate();

            resultFlag =
                    result > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultFlag;
    }

    /*
     * 쿠폰 코드 중복 확인
     */
    public boolean existsCouponCode(String couponCode) {

        boolean existsFlag =
                false;

        String sql =
                " SELECT COUNT(*) AS cnt FROM coupon WHERE coupon_code = ? ";

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql)
        ) {

            pstmt.setString(1, couponCode);

            try (
                ResultSet rs =
                        pstmt.executeQuery()
            ) {

                if (rs.next()) {
                    existsFlag =
                            rs.getInt("cnt") > 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return existsFlag;
    }

    /*
     * 특정 사용자가 이미 해당 쿠폰을 가지고 있는지 확인
     */
    public boolean existsCustodyCoupon(String couponCode,
                                       String memberId) {

        boolean existsFlag =
                false;

        StringBuilder sql =
                new StringBuilder();

        sql.append(" SELECT COUNT(*) AS cnt ");
        sql.append("   FROM custody_coupon ");
        sql.append("  WHERE coupon_code = ? ");
        sql.append("    AND member_id = ? ");

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql.toString())
        ) {

            pstmt.setString(1, couponCode);
            pstmt.setString(2, memberId);

            try (
                ResultSet rs =
                        pstmt.executeQuery()
            ) {

                if (rs.next()) {
                    existsFlag =
                            rs.getInt("cnt") > 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return existsFlag;
    }

    /*
     * 사용자 보유 쿠폰 개수
     */
    public int selectCustodyCouponCount(String couponCode) {

        int count =
                0;

        String sql =
                " SELECT COUNT(*) AS cnt FROM custody_coupon WHERE coupon_code = ? ";

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql)
        ) {

            pstmt.setString(1, couponCode);

            try (
                ResultSet rs =
                        pstmt.executeQuery()
            ) {

                if (rs.next()) {
                    count =
                            rs.getInt("cnt");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    /*
     * CP001, CP002 형식의 다음 쿠폰 코드 생성
     */
    private String generateNextCouponCode(Connection con)
            throws Exception {

        StringBuilder sql =
                new StringBuilder();

        sql.append(" SELECT NVL(MAX(TO_NUMBER(SUBSTR(coupon_code, 3))), 0) + 1 AS next_no ");
        sql.append("   FROM coupon ");
        sql.append("  WHERE REGEXP_LIKE(coupon_code, '^CP[0-9]+$') ");

        try (
            PreparedStatement pstmt =
                    con.prepareStatement(sql.toString());

            ResultSet rs =
                    pstmt.executeQuery()
        ) {

            if (rs.next()) {

                int nextNo =
                        rs.getInt("next_no");

                return String.format("CP%03d", nextNo);
            }
        }

        return "CP001";
    }
}