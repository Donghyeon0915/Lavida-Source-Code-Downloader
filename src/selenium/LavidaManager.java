/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selenium;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author Donghyeon <20183188>
 */
public class LavidaManager {

    public static void lavidaLogin(WebDriver driver, String base_url, String userid, String password) {
        driver.get(base_url);

        WebElement id = driver.findElement(By.xpath("//*[@id=\"user_id\"]"));
        id.sendKeys(userid);

        WebElement pw = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        pw.sendKeys(password);
        pw.sendKeys("\n");

    }

    public static ArrayList<String> getProblemLink(WebDriver driver) {

        //푼 문제 리스트를 가져옴 (a 태그)
        // solved = 문제 리스트가 저장되어있는 리스트
        WebElement solvedList = driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/div[2]/table[2]")).findElement(By.xpath("/html/body/div[3]/div/div[2]/div[2]/table[2]/tbody/tr/td"));
        List<WebElement> solved = solvedList.findElements(By.tagName("a"));

        ArrayList<String> links = new ArrayList<>();
        solved.forEach(webElement -> {
            links.add(webElement.getAttribute("href"));
        });

        return links;
    }

    public static ArrayList<WebElement> getAcceptedProblem(List<WebElement> resultTr) {
        ArrayList<WebElement> acceptedList = new ArrayList<>();

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
        return acceptedList;
    }
}
