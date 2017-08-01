package lm.macro;

import org.apache.commons.lang3.RandomUtils;

import java.text.NumberFormat;

public class Test {
    public static void main(String[] args) {
        double 빨간거 = 0.0023;

        double 빨간거깐총금액 = 0;
        int 로테이션횟수 = 500;

        double 제일돈많이쓴금액 = 0;
        double temp = 0;

        for (int j = 0; j < 로테이션횟수; j++) {
            double k = 1;
            double 빨간거깐금액 = 0;
            double 유물까기횟수 = 0;

            while (k > 빨간거) {
                k = RandomUtils.nextDouble(0, 1);
                빨간거깐금액 += 3000;
                유물까기횟수++;
            }

            temp = 빨간거깐금액;

            if (temp > 제일돈많이쓴금액) {
                제일돈많이쓴금액 = temp;
            }

            빨간거깐총금액 += 빨간거깐금액;
            System.out.println(String.format("유물까기 횟수 : %f, 사용금액 : %f원", 유물까기횟수, 빨간거깐금액));
        }

        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        float 금액 = (float) (빨간거깐총금액 / 로테이션횟수);

        System.out.println("평균 유물에서 빨간거 깔수 있는금액 : " + formatter.format(금액) + "원");
        System.out.println("제일 돈 많이쓴금액 : " + 제일돈많이쓴금액);
    }

    public static String formatDecimal(float number) {
        float epsilon = 0.004f; // 4 tenths of a cent
        if (Math.abs(Math.round(number) - number) < epsilon) {
            return String.format("%10.0f", number); // sdb
        } else {
            return String.format("%10.2f", number); // dj_segfault
        }
    }
}

