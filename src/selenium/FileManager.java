/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selenium;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Donghyeon <20183188>
 */
public class FileManager {

    public static void saveSourceCode(String sourceCode, ProblemInfo pbinfo) {
        // \ / : * ? " < > | 는 폴더, 파일 이름으로 쓸 수 없음
        String folder = pbinfo.getProblemName().trim().replaceAll("[\\/:*?\"<>|]", "");
        String fileExtension = pbinfo.getLanguage().matches("^(C\\+\\+)\\d*$") ? "cpp" : pbinfo.getLanguage();
        String fileName = pbinfo.getProblemName().substring(6).trim().replaceAll("\\p{Punct}", "");
        String path = "C:\\Users\\cyber\\Documents\\Lavida Online Judge\\" + folder;

        File file = new File(path);
        makeDirectory(file);

        file = setFileName(path, fileName, fileExtension);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(sourceCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void makeDirectory(File file) {
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                System.out.print("폴더 생성 실패");
            }
        }
    }

    public static File setFileName(String path, String fileName, String fileExtension) {
        File file = new File(path + "\\" + fileName + "." + fileExtension.toLowerCase());
        if (file.exists()) {
            int fileCnt = 2;
            while (file.exists()) {
                file = new File(path + "\\" + fileName + "(" + fileCnt + ")" + "." + fileExtension.toLowerCase());
                fileCnt++;
            }
        }
        return file;
    }

}
