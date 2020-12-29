/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selenium;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author Donghyeon <20183188>
 */
class AcceptedProblem {

    String link;
    String language;

    public AcceptedProblem(String link, String language) {
        this.link = link;
        this.language = language;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}

class ProblemInfo {

    String problemName;
    String problemNum;
    String language;

    public ProblemInfo() {
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public void setProblemNum(String problemNum) {
        this.problemNum = problemNum;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ProblemInfo(String problemName, String problemNum, String language) {
        this.problemName = problemName;
        this.problemNum = problemNum;
        this.language = language;
    }

    public String getProblemName() {
        return problemName;
    }

    public String getProblemNum() {
        return problemNum;
    }

    public String getLanguage() {
        return language;
    }

}

public class Selenium {

    /**
     * @param args the command line arguments
     */
    private final WebDriver driver;
    private final WebDriverWait wait;
    //Properties
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "src\\MyLibrary\\chromedriver.exe";
    StringBuilder subUrl;
    Robot robot;

    public static void main(String[] args) throws AWTException, InterruptedException {
        // TODO code application logic here

        Selenium selTest = new Selenium();
        selTest.crawl();
    }

    private final String base_url;

    public Selenium() throws AWTException {

        //System Property SetUp
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        //Driver SetUp
        driver = new ChromeDriver();
        base_url = "https://lavida.us/";
        robot = new Robot();
        wait = new WebDriverWait(driver, 10);
    }



    public void crawl() throws InterruptedException {

        try {
            //get page (= 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다)
            LavidaManager.lavidaLogin(driver, base_url, "id", "password");

            //내 정보 페이지로 이동
            WebElement myPage = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/ul[2]/li[1]/a"));
            myPage.click();

            // Accepted받은 문제의 링크를 가져옴
            ArrayList<String> links = LavidaManager.getProblemLink(driver);

            links.forEach(link -> {
                ProblemInfo pbInfo = new ProblemInfo();

                //문제 페이지로 이동
                String href = link;
                pbInfo.setProblemNum(href.substring(href.length() - 4));
                driver.get(href);

                // 문제 페이지에서 제목 가져옴
                WebElement problemPage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.container > div > div.panel.panel-default > div.panel-heading > h2")));
                String problemName = problemPage.getText();

                pbInfo.setProblemName(problemName.substring(0, problemName.length() - 26));

                //My Submission으로 이동
                subUrl = new StringBuilder("https://lavida.us/status.php?user_id=20183188&problem_id=" + pbInfo.getProblemNum());
                driver.get(subUrl.toString());

                // 채점 결과 테이블 찾기
                WebElement resultTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.container > div > table")));

                //채점 결과 테이블의 tr 태그를 가져옴
                List<WebElement> resultTr = resultTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));

                // Accepted인 문제만 가져옴
                ArrayList<WebElement> acceptedList = LavidaManager.getAcceptedProblem(resultTr);
                int acceptedListSize = acceptedList.size();
                String mainHanlder = driver.getWindowHandle();

                // 클릭해서 넘어가는 순간 driver 바꿔주고 for문 마지막에서 driver.switchTo로 처음 탭으로 변경
                // 소스코드 가져와서 파일로 생성
                for (int i = 0; i < acceptedListSize; i++) {
                    pbInfo.setLanguage(acceptedList.get(i).getText());
                    acceptedList.get(i).click();

                    WebDriverManager.changeTab(driver);
                    
                    //소스 코드 가져오기
                    WebElement sourceArea = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.container > div > div")));
                    String sourceCode = sourceArea.findElement(By.cssSelector("textarea")).getAttribute("textContent");

                    //소스 코드 저장
                    FileManager.saveSourceCode(sourceCode, pbInfo);
                    
                    driver.switchTo().window(mainHanlder);
                }

                WebDriverManager.closeTab(driver);
            });

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            driver.close();
        }

    }



}
