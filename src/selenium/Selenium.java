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

    WebElement link;
    String language;

    public AcceptedProblem(WebElement link, String language) {
        this.link = link;
        this.language = language;
    }

    public WebElement getLink() {
        return link;
    }

    public void setLink(WebElement link) {
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
    private WebDriver driver;
    private final WebDriverWait wait;
    //Properties
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "C:\\Users\\cyber\\Downloads\\Selenium\\chromedriver.exe";
    StringBuilder subUrl;
    Robot robot;

    public static void main(String[] args) throws AWTException, InterruptedException {
        // TODO code application logic here

        Selenium selTest = new Selenium();
        selTest.crawl();
    }

    private String base_url;

    public Selenium() throws AWTException {

        //System Property SetUp
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        
        //Driver SetUp
        driver = new ChromeDriver();
        base_url = "https://lavida.us/index.php";
        robot = new Robot();
        wait = new WebDriverWait(driver, 10);
    }

    public void lavidaLogin(){
        driver.get(base_url);

            WebElement id = driver.findElement(By.xpath("//*[@id=\"user_id\"]"));
            id.sendKeys("20183188");

            WebElement pw = driver.findElement(By.xpath("//*[@id=\"password\"]"));
            pw.sendKeys("blue795132486");
            pw.sendKeys("\n");

    }
    
    public void crawl() throws InterruptedException {

        try {
            //get page (= 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다)
            lavidaLogin();
            
            //내 정보 페이지로 이동
            WebElement myPage = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/ul[2]/li[1]/a"));
            myPage.click();

            //푼 문제 리스트를 가져옴 (a 태그)
            WebElement solvedList = driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/div[2]/table[2]")).findElement(By.xpath("/html/body/div[3]/div/div[2]/div[2]/table[2]/tbody/tr/td"));
            List<WebElement> solved = solvedList.findElements(By.tagName("a"));

            System.out.println("solved list 개수 : " + solved.size());

            ArrayList<String> links = new ArrayList<>();
            solved.forEach(webElement -> {
                links.add(webElement.getAttribute("href"));
            });

            /*
            * solved = 문제 리스트가 저장되어있는 리스트
             */
            links.forEach(link -> {
                ProblemInfo pbInfo = new ProblemInfo();

                //문제 페이지로 이동
                String href = link;
                pbInfo.setProblemNum(href.substring(href.length() - 4));
                driver.get(href);

                //Thread_Sleep(2000);

                //By.xpath("/html/body/div[4]/div/div[1]/div[1]/h2")
                //String problemName = driver.findElement(By.cssSelector("body > div.container > div > div.panel.panel-default > div.panel-heading > h2")).getText();
                WebElement problemPage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.container > div > div.panel.panel-default > div.panel-heading > h2")));
                String problemName = problemPage.getText();
                        
                pbInfo.setProblemName(problemName.substring(0, problemName.length() - 26));

                //My Submission으로 이동
                subUrl = new StringBuilder("https://lavida.us/status.php?user_id=20183188&problem_id=" + pbInfo.getProblemNum());
                driver.get(subUrl.toString());
                
                // 채점 결과 테이블 찾기
                //driver.findElement(By.xpath("/html/body/div[3]/div/table"));
                WebElement resultTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.container > div > table")));
                        
                //채점 결과 테이블의 tr 태그를 가져옴
                List<WebElement> resultTr = resultTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));

                // accepted인 결과 가져옴
                ArrayList<WebElement> acceptedList = new ArrayList<>();

                // 채점 결과 테이블 중에서 Accepted인 항목만 acceptedList에 넣음
                for (int i = 0; i < resultTr.size(); i++) {
                    //채점 결과 테이블의 tr에서 td 항목만 가져옴
                    List<WebElement> resultTd = resultTr.get(i).findElements(By.tagName("td"));

                    //td중에서 채점 결과(Result)만 가져옴
                    String resultStr = resultTd.get(3).getText();

                    if (resultStr.equals("Accepted")) {
                        //Accepted인 경우 소스코드 태그를 가져옴 (a)
                        acceptedList.add(resultTd.get(6).findElement(By.tagName("a")));
                    }
                }

                //소스코드 링크 클릭
                //acceptedList엔 여러개의 요소가 들어있을 수 있음 (여러 언어로 문제를 맞췄을 경우)
                //그러므로 accpetedList.get(i)로 반복문 돌려야함
                //같은 언어로 여러번 풀었을 경우 중복을 제거해주어야한다.
                
                int acceptedListSize = acceptedList.size();

                for (int i = 0; i < acceptedListSize; i++) {

                    acceptedList.get(i).click();
                    String language = acceptedList.get(i).getText();
                    pbInfo.setLanguage(language);

                    /*
                  * 소스코드 복사
                     */
                    robot.mouseMove(400, 500);
                    
                    WebElement sourceArea = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.container > div > div")));
                    
                    releaseLeftMouse();
                    ctrl_A();
                    ctrl_C();

                    saveSourceCode(getClipBoard(), pbInfo);
                }
                
                closeTab();
            });
        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            driver.close();
        }

    }

    public void closeTab() {
        Set<String> handlesSet = driver.getWindowHandles();
        List<String> handlesList = new ArrayList<>(handlesSet);
        for (int i = 1; i < handlesList.size(); i++) {
            driver.switchTo().window(handlesList.get(i));
            driver.close();
        }
        
        driver.switchTo().window(handlesList.get(0));
    }

    public void Thread_Sleep(int sec) {
        try {
            Thread.sleep(sec);
        } catch (InterruptedException ex) {
            Logger.getLogger(Selenium.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveSourceCode(String sourceCode, ProblemInfo pbinfo) {
        String[] puncts = {"\\", "/", ":", "*", "?", "<", ">", "|", "\""};
        String folder = pbinfo.getProblemName().trim().replaceAll("\\p{Punct}", "");
        String fileExtension = pbinfo.getLanguage().matches("^(C\\+\\+)\\d*$") ? "cpp" : pbinfo.getLanguage();
        String fileName = pbinfo.getProblemName().substring(6).trim().replaceAll("\\p{Punct}", "");;
        
        String path = "C:\\Users\\cyber\\Downloads\\Lavida Online Judge\\" + folder;
        File file = new File(path);

        if (!file.exists()) {
            try {
                //파일 이름엔 특수문자가 들어갈 수 없음(예외처리 필요)
                // \ / : * ? " < > | 는 폴더, 파일 이름으로 쓸 수 없음
                file.mkdir();
            } catch (Exception e) {
                System.out.print("폴더 생성 실패");
            }
        }

        file = new File(path + "\\" + fileName + "." + fileExtension.toLowerCase());
        if (file.exists()) {
            int fileCnt = 1;
            while (file.exists()) {
                file = new File(path + "\\" + fileName + "(" + fileCnt + ")" + "." + fileExtension.toLowerCase());
                fileCnt++;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            writer.write(sourceCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getClipBoard() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(clipboard);
        String pasteString = "";

        if (contents != null) {

            try {
                pasteString = (String) (contents.getTransferData(
                        DataFlavor.stringFlavor));

                return pasteString;
            } catch (UnsupportedFlavorException | IOException e) {
                System.out.println("클립보드 에러");
            }

        }
        return pasteString;
    }

    public void releaseLeftMouse() {
        robot.delay(500);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
    }

    public void ctrl_A() {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_A);

        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_A);
        robot.delay(100);
    }

    public void ctrl_C() {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_C);

        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_C);
        robot.delay(100);
    }

}
