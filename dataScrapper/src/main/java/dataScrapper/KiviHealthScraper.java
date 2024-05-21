package dataScrapper;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;


public class KiviHealthScraper {
    private static final String URL = "https://kivihealth.com/jaipur/doctors";
    private static final String[] COLUMN_HEADERS = {"Doctor Profile", "Doctor Name", "Doctor Education", "Doctor Fees", "Doctor Specialization"};

    public static void main(String[] args) {
        try {
            Document document = Jsoup.connect(URL).get();
            Elements doctors = document.select(".doctor-profile-selector"); // To Fetch the Doctor Profile Image

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("DoctorsData");

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < COLUMN_HEADERS.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(COLUMN_HEADERS[i]);
            }

            int rowNum = 1;
            for (Element doctor : doctors) {
                String profile = doctor.select(".profile-class").attr("src"); // Doctor Image Fetching
                String name = doctor.select(".name-class").text(); // Adjust the class name
                String education = doctor.select(".education-class").text(); // Adjust the class name
                String fees = doctor.select(".fees-class").text(); // Adjust the class name
                String specialization = doctor.select(".specialization-class").text(); // Adjust the class name

                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(profile);
                row.createCell(1).setCellValue(name);
                row.createCell(2).setCellValue(education);
                row.createCell(3).setCellValue(fees);
                row.createCell(4).setCellValue(specialization);
            }

            // Resize all columns to fit the content size
            for (int i = 0; i < COLUMN_HEADERS.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the output to a file
            FileOutputStream fileOut = new FileOutputStream("doctors.xlsx");
            workbook.write(fileOut);
            fileOut.close();

            // Closing the workbook
            workbook.close();

            System.out.println("Data successfully scraped and written to doctors.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

