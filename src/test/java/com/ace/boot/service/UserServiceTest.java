package com.ace.boot.service;

import com.ace.boot.entity.Role;
import com.ace.boot.entity.User;
import com.ace.boot.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JavaMailSender javaMailSender;
    User user = new User();
    List<User> users = new ArrayList<>();
    @Captor
    ArgumentCaptor<Specification<User>> captor;
    @Mock
    private JasperCompileManager jasperCompileManager;
    @Mock
    private JasperExportManager jasperExportManager;
    @Mock
    private JRXlsExporter jrXlsExporter;
    HttpServletResponse response;
    @BeforeEach
    void setUp() throws SQLException, JRException {
        openMocks(this);
        user.setId(1L);
        user.setName("test");
        user.setEmail("test@gmail.com");
        user.setPassword("testpassword");
        user.setAddress("testaddress");
        user.setRole(Role.ADMIN);
        user.setFile(new MockMultipartFile("test.txt", "Hello, World!".getBytes()));
        user.setPhoto(new SerialBlob(new byte[0]));
        users.add(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findAll(captor.capture())).thenReturn(users);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(users);
        doNothing().when(userRepository).delete(user);
        response =  mock(HttpServletResponse.class);
    }

    @Test
    void registerUser() throws SQLException, IOException {
        assertTrue(userService.registerUser(user));
        assertFalse(userService.registerUser(null));
    }

    @Test
    void updateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        assertTrue(userService.updateUser(user));
        assertFalse(userService.updateUser(null));
    }

    @Test
    void deleteUser() {
        assertTrue(userService.deleteUser(1L));
        assertFalse(userService.deleteUser(2L));
    }

    @Test
    void searchMethod() throws SQLException, IOException {
        assertNotNull(userService.searchMethod(1L,"test","test@gmail.com","ADMIN"));
        assertEquals("test", userService.searchMethod(1L, null, null, null).get(0).getName());
        assertNotNull(userService.searchMethod(1L,null,null,null));
        assertNotNull(userService.searchMethod(1L,"test",null,null));
        assertNotNull(userService.searchMethod(1L,"test","test@gmail.com",null));
    }

    @Test
    void getAllUser() {
        assertNotNull(userService.getAllUser());
    }

    @Test
    void findById() {
        assertNotNull(userService.findById(1L));
        assertThrows(RuntimeException.class,()->userService.findById(2L));
    }

    @Test
    void findByEmail() {
        assertNotNull(userService.findByEmail("test@gmail.com"));
    }

    @Test
    void login() {
        assertTrue(userService.login("test@gmail.com","testpassword"));
        assertFalse(userService.login("aa@gmail.com","aa"));
    }

    @Test
    void getEncodedPhotoString() throws IOException {
        assertNotNull(userService.getEncodedPhotoString(user.getFile()));
    }

    @Test
    void changeBlobToMultipartfile() throws SQLException, IOException {
        assertNotNull(userService.changeBlobToMultipartfile(user.getPhoto()));
    }

    @Test
    void changeMultipartFileToBlob() throws SQLException, IOException {
        assertNotNull(userService.changeMultipartFileToBlob(user.getFile()));
    }

    @Test
    void addPhotoString() throws SQLException, IOException {
        assertNotNull(userService.addPhotoString(users));
    }

    @Test
    void sendEmail(){
        var code = userService.generateRandomCode();
        userService.sendEmail("test@gmail.com",code);
    }

    @Test
    void generateRandomCode() {
        assertNotNull(userService.generateRandomCode());
    }


}