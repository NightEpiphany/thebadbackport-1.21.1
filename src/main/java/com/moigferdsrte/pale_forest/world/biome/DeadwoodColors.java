package com.moigferdsrte.pale_forest.world.biome;

import java.awt.*;

public class DeadwoodColors {
    // 颜色映射表（需通过资源包加载）
    private static int[] colorMap = new int[65536];
    public static final int DEFAULT = -10732494;
    // 基础棕黄色调参数
    private static final int BASE_HUE = 30;   // 棕黄色相 (0-360)
    private static final float BASE_SATURATION = 0.7f; // 基础饱和度
    private static final float BASE_BRIGHTNESS = 0.4f; // 基础亮度

    public static void setColorMap(int[] pixels) {
        colorMap = pixels;
    }

    static int getColor(double temperature, double downfall, int[] colormap, int fallback) {
        downfall *= temperature;
        int i = (int)((1.0 - temperature) * 255.0);
        int j = (int)((1.0 - downfall) * 255.0);
        int k = j << 8 | i;
        return k >= colormap.length ? fallback : colormap[k];
    }

    /**
     * 主颜色计算方法（基于群系温湿度）
     */
    public static int getColor(double temperature, double humidity, float brightnessFactor) {
        humidity *= temperature;
        int i = (int)((1.0 - temperature) * 255.0);
        int j = (int)((1.0 - humidity) * 255.0);
        int k = j << 8 | i;

        // 转换为HSB调整亮度
        return DeadwoodColors.getColor(temperature, humidity, colorMap, -10732494);

    }

    /**
     * 通过HSB模型调整亮度
     * @param rgb 原始颜色值
     * @param factor 亮度系数 (0.0-2.0)
     * @return 调整后的RGB颜色
     */
    private static int adjustBrightness(int rgb, float factor) {
        // 分解RGB分量
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        // 转换为HSB
        float[] hsb = new float[3];
        Color.RGBtoHSB(r, g, b, hsb);

        // 调整亮度（限制在0-1范围）
        hsb[2] = Math.min(1.0f, hsb[2] * factor);

        // 转回RGB
        return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
    }

    /**
     * 动态生成默认颜色（替代硬编码）
     */
    public static int getDefaultColor() {
        return Color.HSBtoRGB(
                BASE_HUE / 360f,
                BASE_SATURATION * 0.9f,
                BASE_BRIGHTNESS * 0.8f
        );
    }

    // 特殊变种颜色（可配合不同枯木类型）
    public static int getDesertDeadwoodColor() {
        return Color.HSBtoRGB(
                (BASE_HUE + 5) / 360f, // 色相略微偏橙
                BASE_SATURATION * 0.6f, // 降低饱和度
                BASE_BRIGHTNESS * 1.1f   // 提高亮度
        );
    }

    public static int getSwampDeadwoodColor() {
        return Color.HSBtoRGB(
                (BASE_HUE - 8) / 360f, // 色相偏绿
                BASE_SATURATION * 1.2f,
                BASE_BRIGHTNESS * 0.7f
        );
    }

    public static int getMountainDeadwoodColor() {
        return Color.HSBtoRGB(
                BASE_HUE / 360f,
                BASE_SATURATION * 0.8f,
                BASE_BRIGHTNESS * 0.9f
        );
    }

    // 初始化默认颜色映射
    static {
        generateDefaultColorMap();
    }

    /**
     * 生成默认颜色映射（无资源包时使用）
     */
    private static void generateDefaultColorMap() {
        colorMap = new int[65536];
        for (int i = 0; i < 256; ++i) {
            for (int j = 0; j < 256; ++j) {
                float temp = 1.0f - i / 255.0f;
                float humid = 1.0f - (float)Math.pow(j / 255.0f, 1.5);

                float hue = BASE_HUE + temp * 15.0f; // 温度影响色相偏移 (±15度)
                float sat = BASE_SATURATION * (0.8f + humid * 0.2f);
                float bri = BASE_BRIGHTNESS * (0.7f + temp * 0.3f);

                colorMap[j << 8 | i] = Color.HSBtoRGB(
                        hue / 360f,
                        Math.min(sat, 1.0f),
                        Math.min(bri, 1.0f)
                );
            }
        }
    }
}
