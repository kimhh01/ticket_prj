package kr.co.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/upload/*")
public class CommonImageViewServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("===== CommonImageViewServlet 호출 =====");
        System.out.println("requestURI = " + request.getRequestURI());
        System.out.println("pathInfo = " + request.getPathInfo());

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || "/".equals(pathInfo)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String path = pathInfo.replace("\\", "/");

        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        String[] pathArr = path.split("/");

        if (pathArr.length != 2) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String category = pathArr[0];
        String fileName = pathArr[1];

        if (!isAllowedCategory(category) || !isSafeFileName(fileName)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        File rootDir =
                UploadPathUtil.getImageUploadDir(
                        getServletContext(),
                        category);

        File file =
                new File(rootDir, fileName);

        String rootPath =
                rootDir.getCanonicalPath();

        String filePath =
                file.getCanonicalPath();

        System.out.println("category = " + category);
        System.out.println("fileName = " + fileName);
        System.out.println("rootPath = " + rootPath);
        System.out.println("filePath = " + filePath);
        System.out.println("file.exists() = " + file.exists());
        System.out.println("file.isFile() = " + file.isFile());

        if (!filePath.startsWith(rootPath + File.separator)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (!file.exists() || !file.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String mimeType =
                getServletContext().getMimeType(file.getName());

        if (mimeType == null) {
            String lowerName = file.getName().toLowerCase();

            if (lowerName.endsWith(".png")) {
                mimeType = "image/png";
            } else if (lowerName.endsWith(".jpg") ||
                       lowerName.endsWith(".jpeg")) {
                mimeType = "image/jpeg";
            } else if (lowerName.endsWith(".webp")) {
                mimeType = "image/webp";
            } else if (lowerName.endsWith(".svg")) {
                mimeType = "image/svg+xml";
            }
        }

        if (mimeType == null || !mimeType.startsWith("image/")) {
            response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            return;
        }

        response.setContentType(mimeType);
        response.setContentLengthLong(file.length());
        response.setHeader("Cache-Control", "public, max-age=86400");

        Files.copy(file.toPath(), response.getOutputStream());
    }

    private boolean isAllowedCategory(String category) {
        return "event".equals(category)
            || "stadium".equals(category)
            || "notice".equals(category)
            || "team_logo".equals(category)
            || "notice".equals(category);
    }

    private boolean isSafeFileName(String fileName) {

        if (fileName == null || fileName.trim().isEmpty()) {
            return false;
        }

        if (fileName.contains("..") ||
            fileName.contains("/") ||
            fileName.contains("\\")) {
            return false;
        }

        return fileName.matches("^[A-Za-z0-9._-]+$");
    }
}