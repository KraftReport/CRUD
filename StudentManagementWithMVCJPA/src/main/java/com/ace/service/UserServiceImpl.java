package com.ace.service;

import com.ace.model.UserModel;
import com.ace.entity.Role;
import com.ace.entity.User;
import com.ace.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean userLogin(UserModel userModel) {
        try {
            System.err.println(userModel.getEmail());
            System.err.println(userRepository.findByEmail(userModel.getEmail()));
            System.err.println(userModel.getPassword());
            System.err.println(userRepository.findByEmail(userModel.getEmail()).getPassword());
            System.err.println(userRepository.getAllUser());
            if(findByEmail(userModel.getEmail()) != null && findByEmail(userModel.getEmail()).getPassword().equals(userModel.getPassword())){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public boolean registerUser(UserModel user) throws SQLException, IOException {
           try {
               userRepository.addUser(changeUserDTOToUser(user));
               return true;
           }catch (Exception e){
               e.printStackTrace();
               return false;
           }
    }

    @Override
    public boolean updateUser(UserModel user) throws SQLException, IOException {
        try {
            userRepository.updateUser(changeUserDTOToUser(user));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUser(int id) {
        try {
            userRepository.deleteUser(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<UserModel> showAllUser() {
        try {
            List<User> users = userRepository.getAllUser();
            List<UserModel> userModels = new ArrayList<>();
            for(int i=0 ;i<users.size(); i++){
                userModels.add(changeUserToUserDTO(users.get(i)));
            }
            return userModels;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UserModel> findByName(String name) {
        try {
            List<UserModel> userModels =  showAllUser();
            List<UserModel> foundList = new ArrayList<>();
            for(int i = 0; i< userModels.size(); i++){
                if(userModels.get(i).getName().equals(name)){
                    foundList.add(userModels.get(i));
                }
            }
            return foundList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserModel findByEmail(String email) {
        try {
            List<UserModel> list = showAllUser();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getEmail().equals(email)) {
                    return list.get(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public UserModel findById(int id) throws SQLException, IOException {
        return changeUserToUserDTO(userRepository.findById(id));
    }

    @Override
    public void getPdf(HttpServletResponse httpServletResponse) throws IOException, JRException {
        var print = getPrint();
        var out = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(print,out);
        out.flush();
        out.close();
    }

    @Override
    public void getExcel(HttpServletResponse httpServletResponse) throws JRException, IOException {
        var print = getPrint();
        var exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(print));
        var out = httpServletResponse.getOutputStream();
        var outPut = new SimpleOutputStreamExporterOutput(out);
        exporter.setExporterOutput(outPut);
        var config = new SimpleXlsReportConfiguration();
        exporter.setConfiguration(config);
        exporter.exportReport();
        out.flush();
        out.close();
    }

    private User changeUserDTOToUser(UserModel userModel) throws IOException, SQLException {
        var user = new User();
        user.setId(userModel.getId());
        user.setName(userModel.getName());
        user.setEmail(userModel.getEmail());
        user.setPassword(userModel.getPassword());
        user.setRole(Role.valueOf(userModel.getRole()));
        user.setPhoto(new SerialBlob(userModel.getPhoto().getInputStream().readAllBytes()));
        return user;
    }

    private UserModel changeUserToUserDTO(User user) throws SQLException, IOException {
        var userDTO = new UserModel();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(String.valueOf(user.getRole()));
        userDTO.setPhoto(new MockMultipartFile("photofile",new ByteArrayInputStream(user .getPhoto()
                                                                                                .getBytes(1L, (int) user.getPhoto()
                                                                                                                              .length()))));
        userDTO.setPhotoString(Base64.getEncoder().encodeToString(userDTO.getPhoto().getBytes()));
        return  userDTO;
    }

    private JasperPrint getPrint() throws JRException, FileNotFoundException {
        var user = userRepository.getAllUser();
        var file = ResourceUtils.getFile("classpath:user.jrxml");
        var report = JasperCompileManager.compileReport(file.getAbsolutePath());
        var source = new JRBeanCollectionDataSource(user);
        Map<String,Object> map = new HashMap<>();
        map.put("created by","myo set paing");
        var print = JasperFillManager.fillReport(report,map,source);
        return print;
    }
}
