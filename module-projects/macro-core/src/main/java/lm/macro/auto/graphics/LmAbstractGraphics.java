package lm.macro.auto.graphics;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class LmAbstractGraphics {
    protected List<LmGraphic> graphics = new ArrayList<>();

    public void add(String name, String imageName) {
        graphics.add(create(name, imageName));
    }

    public LmGraphic create(String name, String imageName) {
        return new LmGraphic(name, imageName);
    }

    public LmGraphic getByName(String name) throws Exception {
        for (LmGraphic graphic : graphics) {
            String name1 = StringUtils.deleteWhitespace(name);
            String name2 = StringUtils.deleteWhitespace(graphic.getName());

            if (name1.equals(name2)) {
                return graphic;
            }
        }

        throw new Exception(String.format("%s 이미지를 찾을수 없습니다.", name));
    }

    public List<LmGraphic> getGraphics() {
        return graphics;
    }
}
