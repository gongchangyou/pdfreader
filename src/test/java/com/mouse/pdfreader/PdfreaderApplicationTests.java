package com.mouse.pdfreader;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@SpringBootTest
class PdfreaderApplicationTests {

    @Test
    void listFile() {
        String path = "/Users/gongchangyou/Downloads/税单";		//要遍历的路径
        File file = new File(path);		//获取其file对象
        File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中
        for(File f:fs){					//遍历File[]数组
            if(!f.isDirectory()) {        //若非目录(即文件)，则打印
//                System.out.println(f);
                contextLoads(f);
            }
        }

    }
    @SneakyThrows
    void contextLoads(File file) {
        try {
            PDDocument document = Loader.loadPDF(file);
        if (!document.isEncrypted()) {
            PDFTextStripper stripper = new PDFTextStripper();
            var lastNo = "";
            var goodOffset = 1;

            for(int i = 1; i<=document.getNumberOfPages(); i ++){
                stripper.setSortByPosition(true);
                stripper.setStartPage(i);
                stripper.setEndPage(i);

                String text = stripper.getText(document);
//                System.out.println("Text:" + text);


                //捕获海关编号
                String pattern = "海关编号.*?(\\d+)";
                // 创建 Pattern 对象
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(text);
                if (m.find( )) {
                    val currentNo =  m.group(1);
                    if (lastNo.equals(currentNo)) {//无需重置 $good_offset

                    } else {
                        goodOffset = 1;
                        System.out.println("海关编号 \t'" + currentNo + "\t" + file.getName()+ "\t page " + i);
                    }
                    lastNo = currentNo;
                } else {
                    goodOffset = 1;
                    log.error("filename {} page {} has no 海关编号", file.getName(), i);
                }

                //捕获商品
                String goodsPatternStr = "商品 商品名称.*?区）(.*)税费征收情况";
                // 创建 Pattern 对象
                Pattern goodsPattern = Pattern.compile(goodsPatternStr, Pattern.DOTALL);
                Matcher goodsMatch = goodsPattern.matcher(text);
                if (goodsMatch.find()) {
                    val goodsStr = goodsMatch.group(1).trim();
                    val goodsList = goodsStr.split("\n");

                    for (val goods : goodsList){
                        //捕获商品
                        String linePatternStr = "^" + goodOffset + ".*";
                        // 创建 Pattern 对象
                        Pattern linePattern = Pattern.compile(linePatternStr);
                        Matcher lineMatch = linePattern.matcher(goods);
                        if (lineMatch.find()){
                            System.out.println();
                            System.out.print(goods.replaceAll("\n", ""));
                            goodOffset++;
                        } else {
                            System.out.print(goods.replaceAll("\n", ""));
                        }
                    }
                    System.out.println("\n");
                } else {
                    log.error("filename {} page {} has no goods", file.getName(), i);
                }

            }

        }
        document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
