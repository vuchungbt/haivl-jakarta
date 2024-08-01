package net.blwsmartware.controller.home.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import net.blwsmartware.model.PostModel;
import net.blwsmartware.model.UserModel;
import net.blwsmartware.service.IPostService;
import net.blwsmartware.service.IUserService;
import net.blwsmartware.util.HttpUtil;
import net.blwsmartware.util.JWTUtil;
import org.jboss.logging.annotations.Pos;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/api-post")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 15    // 15 MB
)
public class PostAPI extends HttpServlet {


    @Inject
    private IPostService postService;
    @Inject
    private IUserService userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json");

        Map<String, Object> result = new HashMap<>();
        try {
            Long idUser = JWTUtil.getIdUserFromToken(JWTUtil.getToken(request));
            UserModel userModel = userService.findByID(idUser);

//            Part filePart = request.getPart("image");
//
//            PostModel postModel = HttpUtil.of(request.getReader()).toModel(PostModel.class);
            Map<String,Object> formData = new HashMap<>();
            Part filePart = null;
            for(Part part : request.getParts()){
                if(part.getContentType() == null){
                    String fieldName = part.getName();
                    String fieldValue = new BufferedReader(new InputStreamReader(part.getInputStream()))
                            .lines().collect(Collectors.joining("\n"));
                    formData.put(fieldName,fieldValue);
                }else{
                    if(part.getName().equals("image")){
                        filePart = part;
                    }
                }
            }
            PostModel postModel = new ObjectMapper().convertValue(formData, PostModel.class);


            if (filePart != null && filePart.getSize() > 0) {
                String originalFileName = filePart.getSubmittedFileName();
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
                String baseName = originalFileName.substring(0,originalFileName.lastIndexOf('.'));

                // Đường dẫn thư mục lưu trữ file
                String uploadDir = getServletContext().getRealPath("/") + "uploads";
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();
                }

                // Lưu file vào thư mục đích
                String fileName = originalFileName;
                File file = new File(uploadDir + File.separator + fileName);
                int counter = 1;
                while(file.exists()){
                    fileName = baseName + "(" + counter + ")" + fileExtension;
                    file = new File(uploadDir + File.separator + fileName);
                    counter ++;
                }

                String filePath = uploadDir + File.separator + fileName;
                filePart.write(filePath);

                // Thiết lập tên file cho thuộc tính image
                postModel.setSource(fileName);
            }

            postModel.setCreatedBy(userModel.getName());
            postModel.setAuthId(userModel.getId());
            postModel = postService.save(postModel);
            System.out.println(postModel);
            result.put("status","success");
            result.put("postModdel",postModel);
        } catch (Exception e){
            result.put("status", "error");
        }
        objectMapper.writeValue(response.getOutputStream(), result);

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json");
        Map<String, Object> result = new HashMap<>();
        try {
            Long idUser = JWTUtil.getIdUserFromToken(JWTUtil.getToken(request));
            UserModel userModel = userService.findByID(idUser);

            PostModel postModel = HttpUtil.of(request.getReader()).toModel(PostModel.class);
            postModel.setModifiedBy(userModel.getName());
            postModel = postService.update(postModel);
            System.out.println(postModel);
            result.put("postModdel",postModel);
            result.put("status","success");
        }
        catch (Exception e){
            result.put("status", "error");
        }
        objectMapper.writeValue(response.getOutputStream(), result);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> result = new HashMap<>();
        try {
            Long idUser = JWTUtil.getIdUserFromToken(JWTUtil.getToken(request));
            PostModel postModel = HttpUtil.of(request.getReader()).toModel(PostModel.class);
            postService.delete(postModel.getId());

        }catch (Exception e){
            result.put("status", "error");
        }
        objectMapper.writeValue(response.getOutputStream(), result);
    }
}
