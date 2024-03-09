package com.ace.controller;

import com.ace.entity.Student;
import com.ace.repository.StudentRepository;
import com.ace.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.hibernate.type.descriptor.java.ObjectJavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class ReportController {
    @Autowired
    private StudentRepository studentRepository;
    @GetMapping("/report")
    public String report(@RequestParam("export") String export, ModelMap modelMap,
                         HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        var context = httpServletRequest.getServletContext();
        var path = context.getRealPath("/WEB-INF/report.jrxml");
//        JRBeanCollectionDataSource dataSource = null;
//        JasperReport jasperReport;
        JasperPrint jasperPrint;
        var students = studentRepository.getAllStudents();
        var parameter = new HashMap<String , Object>().put("title","student report");
        try {
            System.err.println("one");
            var dataSource = new JRBeanCollectionDataSource(students);
            System.err.println("two");
            var report = JasperCompileManager.compileReport(path);
            System.err.println("three");
            var print = JasperFillManager.fillReport(report, (Map<String, Object>) parameter,dataSource);
            if (export.equals("excel")) {
                System.err.println("four");
                httpServletResponse.setContentType("application/vnd.ms-excel");
                System.err.println("five");
                httpServletResponse.setHeader("Content-Disposition", "attachment; filename=book.xls");
                System.err.println("six");
                var exporterXLS = new JRXlsExporter();
                System.err.println("seven");
                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
                System.err.println("eight");
                exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,httpServletResponse.getOutputStream());
                System.err.println("nine");
                exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
                System.err.println("ten");
                exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                System.err.println("eleven");
                exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                System.err.println("twelve");
                exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                System.err.println("thirteen");
                System.err.println(exporterXLS);
                exporterXLS.exportReport();
                System.err.println("fourteen");
                return "studentManage";
            } else {
                httpServletResponse.setContentType("application/pdf");
                httpServletResponse.setHeader("Content-Disposition", "attachment; filename=student.pdf");
                var exporterPdf = new JRPdfExporter();
                exporterPdf.setParameter(JRPdfExporterParameter.JASPER_PRINT,print);
                exporterPdf.setParameter(JRPdfExporterParameter.OUTPUT_STREAM,httpServletResponse.getWriter());
                exporterPdf.exportReport();
                System.err.println(exporterPdf);
                return "studentManage";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "studentManage";
    }
}
