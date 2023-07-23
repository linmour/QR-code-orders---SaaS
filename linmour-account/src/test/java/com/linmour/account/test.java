package com.linmour.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RunWith(SpringRunner.class)
public class test {
    @Test
    public void a() {
        String input = "本本-人站人->2AN人-男，民族汉-中节>REes-此月-有=人--->百-AU必主二一可上aa市海口公民身份号码”350181254501281852-ERRRRRREEEPEETCTEECT-由rrTERRYRARRSS区村外NE玫TS-|-5RENSAAANINEENSE-Ts、AIERRERRASARENSRRE-";
        String target = "身份号码";
        int startIndex = input.indexOf(target);
        if (startIndex != -1) {
            String result = input.substring(startIndex + target.length(), startIndex + target.length() + 20)
                    .replaceAll("[^\\da-zA-ZXx]", "");
            System.out.println(result);
        }

    }
    @Test
    public void b(){
        String input = "本本-人站人->2AN人-男，民族汉-中节>REes-此月-有=人--->百-AU必主二一可上aa市海口公民身份号码”350181254501281852-ERRRRRREEEPEETCTEECT-由rrERRYRARRSS区村外NE玫TS-|-5RENSAAANINEENSE-Ts、AIERRERRASARENSRRE-";
        Pattern pattern = Pattern.compile("\\b\\d{17}[\\dXx]\\b");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String result = matcher.group(0);
            System.out.println(result);
        }
    }
}
