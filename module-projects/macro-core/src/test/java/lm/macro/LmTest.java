package lm.macro;

import lm.macro.sns.service.KakaoSnsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LmTestConfig.class})
public class LmTest {
    @Resource
    private KakaoSnsService kakaoSnsService;

    @Test
    public void test() throws Exception {

    }
}