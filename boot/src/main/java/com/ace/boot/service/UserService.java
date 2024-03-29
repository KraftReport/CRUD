package com.ace.boot.service;

import com.ace.boot.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Service
public interface UserService {
    boolean registerUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(Long id);
    List<User> searchMethod(Long id,String name,String email,String role) throws SQLException, IOException;
    List<User> getAllUser();
    List<User> findById(Long id);
    List<User> findByEmail(String email);
    boolean login(String email, String password);
    String getEncodedPhotoString(MultipartFile multipartFile) throws IOException;
    MultipartFile changeBlobToMultipartfile(Blob blob) throws SQLException, IOException;
     Blob changeMultipartFileToBlob(MultipartFile file) throws IOException, SQLException;
    List<User> addPhotoString(List<User> users) throws SQLException, IOException;
    void sendEmail(String email,String code);
    String generateRandomCode();

}


