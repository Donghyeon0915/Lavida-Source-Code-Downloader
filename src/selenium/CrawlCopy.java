/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selenium;

import java.awt.Robot;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author Donghyeon <20183188>
 */
public class CrawlCopy {

    private WebDriver driver;
    private WebDriverWait wait;
    //Properties
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "C:\\Users\\cyber\\Downloads\\Selenium\\chromedriver.exe";
    StringBuilder subUrl;
    Robot robot;

//    public void crawl() {
//
//        try {
//            //get page (= 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다)
//            driver.get(base_url);
//
//            WebElement id = driver.findElement(By.xpath("//*[@id=\"user_id\"]"));
//            id.sendKeys("20183188");
//
//            WebElement pw = driver.findElement(By.xpath("//*[@id=\"password\"]"));
//            pw.sendKeys("blue795132486");
//            pw.sendKeys("\n");
//
//            //내 정보 페이지로 이동
//            WebElement myPage = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/ul[2]/li[1]/a"));
//            myPage.click();
//
//            //푼 문제 리스트를 가져옴 (a 태그)
//            WebElement solvedList = driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/div[2]/table[2]")).findElement(By.xpath("/html/body/div[3]/div/div[2]/div[2]/table[2]/tbody/tr/td"));
//            List<WebElement> solved = solvedList.findElements(By.tagName("a"));
//
//            System.out.print("solved list 개수 : " + solved.size());
//
//            ArrayList<String> links = new ArrayList<>();
//            for (WebElement webElement : solved) {
//                links.add(webElement.getAttribute("href"));
//            }
//
//            /*
//            * solved = 문제 리스트가 저장되어있는 리스트
//             */
//            solved.forEach(link -> {
//                ProblemInfo pbInfo = new ProblemInfo();
//
//                //문제 페이지로 이동
//                String href = link.getAttribute("href");
//                System.out.print(href);
//                pbInfo.setProblemNum(href.substring(href.length() - 4));
//                driver.get(href);
//
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(Selenium.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//                String problemName = driver.findElement(By.xpath("/html/body/div[4]/div/div[1]/div[1]/h2")).getText();
//                pbInfo.setProblemName(problemName.substring(0, problemName.length() - 26));
//
//                //System.out.println(problemName.substring(0, problemName.length() - 26));
//                //My Submission으로 이동
//                subUrl = new StringBuilder("https://lavida.us/status.php?user_id=20183188&problem_id=" + pbInfo.getProblemNum());
//                driver.get(subUrl.toString());
//
//                // 채점 결과 테이블 찾기
//                WebElement resultTable = driver.findElement(By.xpath("/html/body/div[3]/div/table"));
//
//                //채점 결과 테이블의 tr 태그를 가져옴
//                List<WebElement> resultTr = resultTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
//
//                // accepted인 결과 가져옴
//                ArrayList<WebElement> acceptedList = new ArrayList<>();
//
//                // 채점 결과 테이블 중에서 Accepted인 항목만 acceptedList에 넣음
//                for (int i = 0; i < resultTr.size(); i++) {
//                    //채점 결과 테이블의 tr에서 td 항목만 가져옴
//                    List<WebElement> resultTd = resultTr.get(i).findElements(By.tagName("td"));
//
//                    //td중에서 채점 결과(Result)만 가져옴
//                    String resultStr = resultTd.get(3).getText();
//
//                    if (resultStr.equals("Accepted")) {
//                        //Accepted인 경우 소스코드 태그를 가져옴 (a)
//                        acceptedList.add(resultTd.get(6).findElement(By.tagName("a")));
//                    }
//                }
//
//                //소스코드 링크 클릭
//                //acceptedList엔 여러개의 요소가 들어있을 수 있음 (여러 언어로 문제를 맞췄을 경우)
//                //그러므로 accpetedList.get(i)로 반복문 돌려야함
//                //같은 언어로 여러번 풀었을 경우 중복을 제거해주어야한다.
////            ArrayList<AcceptedProblem> saveList = new ArrayList<>();
////            for (WebElement accepted : acceptedList) {
////                saveList.add(new AcceptedProblem(accepted, accepted.getText()));
////            }
//                int acceptedListSize = acceptedList.size();
//
//                for (int i = 0; i < acceptedListSize; i++) {
//
//                    acceptedList.get(i).click();
//                    String language = acceptedList.get(i).getText();
//                    pbInfo.setLanguage(language);
//
//                    /*
//                  * 소스코드 복사
//                     */
//                    robot.mouseMove(400, 500);
//                    robot.delay(3000);
//
//                    releaseLeftMouse();
//                    ctrl_A();
//                    ctrl_C();
//
//                    saveSourceCode(getClipBoard(), pbInfo);
//                }
//
//            });
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        } finally {
//
//            //driver.close();
//        }
//
//    }
}
