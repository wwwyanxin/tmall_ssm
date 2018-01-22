package xin.wyan.tmall.util;

import java.util.HashMap;
import java.util.Map;

public enum ImageType {
    single("type_single"),
    detail("type_detail");

    private final String type;

    private static final Map<String, ImageType> stringToEnum=new HashMap<>();

    static {
        for (ImageType imageType : ImageType.values()) {
            stringToEnum.put(imageType.getType(), imageType);
        }
    }

    ImageType(String type) {
        this.type=type;
    }

    public String getType() {
        return type;
    }

    public static ImageType fromType(String type) {
        return stringToEnum.get(type);
    }
}
