package lm.macro.auto.common;

import lm.macro.auto.utils.LmPropUtils;
import org.apache.commons.configuration2.ImmutableConfiguration;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LmCommon {
    public static final String VERSION = "0.81";

    private static final String DIR_PATH = getDirPath();

    public static final String SOURCE_PATH = DIR_PATH + "/src/sources";

    public static final ImmutableConfiguration prop = LmPropUtils.load(SOURCE_PATH + "/config/macro.properties");

    public static final String WEB_SERVER_URL = "http://linm.ideapeople.co.kr";

    public static final int MACRO_SCHEDULER_TIME = 1000;

    public static final long REFRESH_SLEEP = 300;

    public static final int TELEPORT_FIND_MAX_COUNT = 15;

    public static final int FIND_WAREHOUSE_MAX_COUNT = 5;

    public static final String ADB_PATH = LmCommon.SOURCE_PATH + "/adb/adb.exe";

    public static final String IMAGE_PATH = LmCommon.SOURCE_PATH + "/images/";

    public static final String 빨간물약 = "빨간물약";
    public static final String 주홍물약 = "주홍물약";
    public static final String 초록물약 = "초록물약";
    public static final String 강화초록물약 = "강화초록물약";
    public static final String 파란물약 = "파란물약";
    public static final String 변신주문서 = "변신주문서";
    public static final String 인형소환주문서 = "인형소환주문서";
    public static final String 와퍼 = "와퍼";
    public static final String 은화살 = "은화살";
    public static final String 해독제 = "해독제";
    public static final String 순간이동주문서 = "순간이동주문서";
    public static final String 귀환주문서 = "귀환주문서";
    public static final String 혈맹귀환주문서 = "혈맹귀환주문서";
    public static final String 정령옥 = "정령옥";
    public static final String 마력의돌 = "마력의돌";
    public static final String 숫돌 = "숫돌";
    public static final String 에바의축복 = "에바의축복";
    public static final String 용해제 = "용해제";
    public static final String 영리한칠면조 = "영리한칠면조";
    public static final String 날쌘연어찜 = "날쌘연어찜";
    public static final String 진주 = "진주";
    public static final String 힘센한우스테이크 = "힘센한우스테이크";
    public static final String 철괴 = "철괴";
    public static final String 용기의물약 = "용기의물약";
    public static final String 드레곤의다이아몬드 = "드레곤의다이아몬드";

    public static final String 상점마을 = "48 우드벡 마을";

    public static final String 가방무게 = "가방무게";

    public static final String 가죽 = "가죽";
    public static final String 보석 = "보석";
    public static final String 천 = "천";

    public static final String 글루딘마을 = "글루딘 마을";
    public static final String 켄트마을 = "켄트 마을";
    public static final String 은기사마을 = "은기사 마을";
    public static final String 우드벡마을 = "우드벡 마을";
    public static final String 기란마을 = "기란 마을";
    public static final String 하이네마을 = "하이네 마을";
    public static final String 화전민마을 = "화전민 마을";
    public static final String 웰던마을 = "웰던 마을";
    public static final String 오렌마을 = "오렌 마을";
    public static final String 아덴마을 = "아덴 마을";
    public static final String 요정숲마을 = "요정 숲 마을";
    public static final String 아지트 = "아지트";

    public static final String 조이스틱_상 = "상";
    public static final String 조이스틱_우상 = "우상";
    public static final String 조이스틱_우 = "우";
    public static final String 조이스틱_우하 = "우하";
    public static final String 조이스틱_하 = "하";
    public static final String 조이스틱_좌하 = "좌하";
    public static final String 조이스틱_좌 = "좌";
    public static final String 조이스틱_좌상 = "좌상";


    public static List<String> 조이스틱좌표() {
        List<String> list = new ArrayList<>();
        list.add(조이스틱_상);
        list.add(조이스틱_우상);
        list.add(조이스틱_우);
        list.add(조이스틱_우하);
        list.add(조이스틱_하);
        list.add(조이스틱_좌하);
        list.add(조이스틱_좌);
        list.add(조이스틱_좌상);

        return list;
    }

    private static String getDirPath() {
        Path currentRelativePath = Paths.get("");
        return currentRelativePath.toAbsolutePath().toString();
    }
}