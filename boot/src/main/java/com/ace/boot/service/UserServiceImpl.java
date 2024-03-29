package com.ace.boot.service;

import com.ace.boot.entity.User;
import com.ace.boot.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.management.JMRuntimeException;
import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    @Transactional
    public boolean registerUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setPhoto(changeMultipartFileToBlob(user.getFile()));
            userRepository.save(user);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public boolean deleteUser(Long id) {
        try {
            userRepository.delete(userRepository.findById(id).orElseThrow(()->new RuntimeException("not found user with such id")));
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> searchMethod(Long id, String name,String email,String role) throws SQLException, IOException {
        List<Specification<User>> specifications = new ArrayList<>();
        if(id!=null){
            specifications.add((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(
                            root.get("id"),id));
        }
        if(StringUtils.hasLength(name)){
            specifications.add((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("name")),"%".concat(name.toLowerCase().concat("%"))));
        }
        if(StringUtils.hasLength(email)){
            specifications.add((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(
                            criteriaBuilder.lower(root.get("email")),email.toLowerCase()));
        }
        if(StringUtils.hasLength(role)){
            specifications.add((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(
                            criteriaBuilder.lower(root.get("role")),role.toLowerCase()));
        }
        Specification<User> combinedSpecification = Specification.where(null);
        for(var s : specifications){
            combinedSpecification = combinedSpecification.and(s);
        }
        return addPhotoString(userRepository.findAll(combinedSpecification));
    }

    @Override
    public List<User> getAllUser() {
        try {
            return addPhotoString(userRepository.findAll());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findById(Long id) {
        var user = userRepository.findById(id).orElseThrow(()->new RuntimeException("not found"));
        var list =new ArrayList<User>();
        list.add(user);
        return list;
    }

    @Override
    public List<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean login(String email, String password) {
        try {
            userRepository.findByEmail(email).get(0).getPassword().equals(password);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public Blob changeMultipartFileToBlob(MultipartFile file) throws SQLException, IOException {
        return   new SerialBlob(file.getInputStream().readAllBytes());
    }
    public MultipartFile changeBlobToMultipartfile(Blob blob) throws SQLException, IOException {
        var inputStream = blob.getBinaryStream();
        var bytes = FileCopyUtils.copyToByteArray(inputStream);
        var fileName = "image.png"; // replace with actual file name
        var inputStream2 = new ByteArrayInputStream(bytes);
        return new MockMultipartFile(fileName, fileName, "image/png", inputStream2);
    }

    public String getEncodedPhotoString(MultipartFile file) throws IOException {
        return Base64.getEncoder().encodeToString(file.getInputStream().readNBytes((int) file.getSize()));
    }
    public String decodePassword(String password){
        return passwordEncoder.encode(password);
    }
    public boolean updateUser(User user){
        try {
            var found = findById(user.getId()).get(0);
            if(!StringUtils.hasLength(user.getPassword())){
                user.setPassword(found.getPassword());
            }
            if(  user.getFile() == null){
                user.setFile(found.getFile());
                user.setPhoto(found.getPhoto());
            }else {
                user.setPhoto(changeMultipartFileToBlob(user.getFile()));
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public List<User> addPhotoString(List<User> users) throws SQLException, IOException {
        for(var u :users){
            u.setPhotoString(getEncodedPhotoString(changeBlobToMultipartfile(u.getPhoto())));
//            u.getFile().getInputStream().close();
        }
        return users;
    }
    public String generateRandomCode(){
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public void sendEmail(String email,String code){
        var simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("OTP code to reset your password");
        simpleMailMessage.setText("your OTP code is " + code);
        javaMailSender.send(simpleMailMessage);
    }


}
