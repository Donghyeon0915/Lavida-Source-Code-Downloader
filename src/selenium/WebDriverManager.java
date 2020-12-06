/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selenium;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author Donghyeon <20183188>
 */
public class WebDriverManager {
    
    /*
    * 새 탭을 띄울 때마다 모든 Handler를 받아와서 항상 마지막 Handler로 driver switch
     */
    public static void changeTab(WebDriver driver) {
        Set<String> handlesSet = driver.getWindowHandles();
        List<String> handlesList = new ArrayList<>(handlesSet);

        driver.switchTo().window(handlesList.get(handlesList.size() - 1));
    }

    public static void closeTab(WebDriver driver) {
        Set<String> handlesSet = driver.getWindowHandles();
        List<String> handlesList = new ArrayList<>(handlesSet);

        for (int i = 1; i < handlesList.size(); i++) {
            driver.switchTo().window(handlesList.get(i));
            driver.close();
        }

        driver.switchTo().window(handlesList.get(0));
    }
}
