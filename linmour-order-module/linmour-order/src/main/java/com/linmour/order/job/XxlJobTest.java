package com.linmour.order.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @ClassName： XxlJobTest
 * @Description: XxlJobTest
 * @author：
 * @date： 2022年12月07日 12:58
 * @version： 1.0
 */
@Slf4j
@Component
public class XxlJobTest {

    @XxlJob("xxlJobTest")
    public void xxlJobTest(String date) {
        String command = "python D:\\soft\\datax\\bin\\datax.py D:\\soft\\datax\\job\\linmour_order.json";
        try {
            Process process = Runtime.getRuntime().exec(command);

            // 获取命令输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 等待命令执行完成
            int exitCode = process.waitFor();
            System.out.println("命令执行完成，退出码：" + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}