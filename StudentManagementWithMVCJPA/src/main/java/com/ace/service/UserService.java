package com.ace.service;

import com.ace.model.UserModel;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface UserService {
    public boolean userLogin(UserModel userModel);
    public boolean registerUser(UserModel user) throws SQLException, IOException;
    public boolean updateUser(UserModel user) throws SQLException, IOException;
    public boolean deleteUser(int id);
    public List<UserModel> showAllUser();
    public List<UserModel> findByName(String name);
    public UserModel findByEmail(String email);
    public UserModel findById(int id) throws SQLException, IOException;
    public void getPdf(HttpServletResponse httpServletResponse) throws IOException, JRException;
    public void getExcel(HttpServletResponse httpServletResponse) throws JRException, IOException;
}
