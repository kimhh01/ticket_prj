package kr.co.util;

import java.io.File;

import javax.servlet.ServletContext;

public class UploadPathUtil {

    private static final String IMAGE_ROOT_PARAM = "upload.image.root";

    private UploadPathUtil() {
    }

    /**
     * web.xml에 설정한 이미지 루트 경로 반환
     * 예: C:/dev/workspace/ticket_copy_prj/src/main/webapp/images
     */
    public static String getImageRoot(ServletContext context) {

        String imageRoot =
                context.getInitParameter(IMAGE_ROOT_PARAM);

        if (imageRoot == null || imageRoot.trim().isEmpty()) {
            throw new IllegalStateException(
                    "web.xml에 upload.image.root 설정이 없습니다.");
        }

        return imageRoot.trim().replace("\\", "/");
    }

    /**
     * 카테고리별 실제 저장 폴더 반환
     * 예: images/event, images/stadium
     */
    public static File getImageUploadDir(ServletContext context,
                                         String category) {

        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("category가 비어 있습니다.");
        }

        return new File(getImageRoot(context), category);
    }

    /**
     * DB에 /upload/카테고리/파일명 형태로 저장하고 싶을 때 사용
     */
    public static String getDbImagePath(String category,
                                        String fileName) {

        return "/upload/" + category + "/" + fileName;
    }

    /**
     * DB에 /upload/stadium/파일명.png 형태로 저장된 값에서 파일명만 추출
     */
    public static String getFileName(String dbPath) {

        if (dbPath == null || dbPath.trim().isEmpty()) {
            return "";
        }

        String value = dbPath.replace("\\", "/");

        return value.substring(value.lastIndexOf("/") + 1);
    }
}