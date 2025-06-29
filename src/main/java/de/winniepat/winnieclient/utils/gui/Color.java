package de.winniepat.winnieclient.utils.gui;

import org.apache.commons.lang3.StringUtils;
import org.joml.Vector3f;

public class Color {
    private int r;
    private int g;
    private int b;
    private int alpha = 255;

    private float hue;
    private float saturation;
    private float luminance;

    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;

        HSVFromRGB(r, g, b);
    }

    public Color(int r, int g, int b, int alpha) {
        this.r = r;
        this.g = g;
        this.b = b;

        HSVFromRGB(r, g, b);

        this.alpha = alpha;
    }

    public Color(float r, float g, float b, float alpha) {
        this.r = (int) (r * 255f);
        this.g = (int) (g * 255f);
        this.b = (int) (b * 255f);
        this.alpha = (int) (alpha * 255f);
    }

    public Color getAsSolid() {
        return new Color(r, g, b, 255);
    }

    public static Color interpolate(Color color1, Color color2, float factor) {
        int r = (int) (color1.r + (color2.r - color1.r) * factor);
        int g = (int) (color1.g + (color2.g - color1.g) * factor);
        int b = (int) (color1.b + (color2.b - color1.b) * factor);
        int alpha = (int) (color1.alpha + (color2.alpha - color1.alpha) * factor);
        return new Color(r, g, b, alpha);
    }

    private void HSVFromRGB(int r, int g, int b) {
        float rPrime = r / 255.0f;
        float gPrime = g / 255.0f;
        float bPrime = b / 255.0f;

        float cMax = Math.max(rPrime, Math.max(gPrime, bPrime));
        float cMin = Math.min(rPrime, Math.min(gPrime, bPrime));

        float delta = cMax - cMin;

        if (delta == 0.0f) {
            hue = 0.0f;
        } else {
            if (cMax == rPrime) {
                hue = (60.0f * (((gPrime - bPrime) / delta) % 6));
            } else if (cMax == gPrime) {
                hue = (60.0f * (((bPrime - rPrime) / delta) + 2));
            } else if (cMax == bPrime) {
                hue = (60.0f * (((rPrime - gPrime) / delta) + 2));
            }
        }

        if (cMax == 0.0f)
            saturation = 0.0f;
        else
            saturation = delta / cMax;

        luminance = cMax;
    }

    public Color(float hue, float saturation, float luminance) {
        this.setHSV(hue, saturation, luminance);
    }

    public float getHue() {
        return hue;
    }

    public float getSaturation() {
        return saturation;
    }

    public float getLuminance() {
        return luminance;
    }

    public void setHSV(float hue, float saturation, float luminance) {
        this.hue = hue;
        this.saturation = saturation;
        this.luminance = luminance;
        Color vec = hsv2rgb(hue, saturation, luminance);
        if (vec != null) {
            this.r = vec.r;
            this.g = vec.g;
            this.b = vec.b;
        }
    }

    public void setHue(float hue) {
        this.hue = hue;
        Color vec = hsv2rgb(this.hue, this.saturation, this.luminance);
        if (vec != null) {
            this.r = vec.r;
            this.g = vec.g;
            this.b = vec.b;
        }
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
        Color vec = hsv2rgb(this.hue, this.saturation, this.luminance);
        if (vec != null) {
            this.r = vec.r;
            this.g = vec.g;
            this.b = vec.b;
        }
    }

    public void setLuminance(float luminance) {
        this.luminance = luminance;
        Color vec = hsv2rgb(this.hue, this.saturation, this.luminance);
        if (vec != null) {
            this.r = vec.r;
            this.g = vec.g;
            this.b = vec.b;
        }
    }

    public void setRGB(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void setRGBA(int r, int g, int b, int alpha) {
        this.r = r;
        this.g = b;
        this.b = b;
        this.alpha = alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public String getColorAsString() {
        String rs = Integer.toString((int) (r));
        String gs = Integer.toString((int) (g));
        String bs = Integer.toString((int) (b));
        return rs + gs + bs;
    }

    public int getColorAsInt() {
        int Alpha = ((this.alpha) << 24) & 0xFF000000;
        int R = ((this.r) << 16) & 0x00FF0000;
        int G = ((this.g) << 8) & 0x0000FF00;
        int B = (this.b) & 0x000000FF;
        return Alpha | R | G | B;
    }

    public String getColorAsHex() {
        return String.format("#%06X", this.getColorAsInt());
    }

    public float getRed() {
        return ((float) this.r) / 255.0f;
    }

    public float getGreen() {
        return ((float) this.g) / 255.0f;
    }

    public float getBlue() {
        return ((float) this.b) / 255.0f;
    }

    public float getAlpha() {
        return ((float) this.alpha) / 255.0f;
    }

    public Color add(Color color) {
        return new Color(this.r + color.r, this.g + color.g, this.b + color.b);
    }

    public Color add(float r, float g, float b) {
        return new Color((int) Math.min(255, this.r + r), (int) Math.min(255, this.g + g),
                (int) Math.min(255, this.b + b));
    }

    public static String rgbToString(int r, int g, int b) {
        String rs = Integer.toString((int) (r));
        String gs = Integer.toString((int) (g));
        String bs = Integer.toString((int) (b));
        return rs + gs + bs;
    }

    public static int rgbToInt(int r, int g, int b) {
        String rs = Integer.toString((int) (r));
        String gs = Integer.toString((int) (g));
        String bs = Integer.toString((int) (b));
        return Integer.parseInt(rs + gs + bs);
    }

    public static int convertRGBToHex(int r, int g, int b) {
        String strr = StringUtils.leftPad(Integer.toHexString(r), 2, '0');
        String strg = StringUtils.leftPad(Integer.toHexString(g), 2, '0');
        String strb = StringUtils.leftPad(Integer.toHexString(b), 2, '0');
        String string = strr + strg + strb;
        return Integer.parseInt(string, 16);
    }

    public static Color convertHextoRGB(String hexColor) {
        hexColor = hexColor.replace("#", "");
        if (hexColor.length() == 6) {
            int r = Integer.valueOf(hexColor.substring(0, 2), 16);
            int g = Integer.valueOf(hexColor.substring(2, 4), 16);
            int b = Integer.valueOf(hexColor.substring(4, 6), 16);
            return new Color(r, g, b);
        } else if (hexColor.length() == 8) {
            int alpha = Integer.valueOf(hexColor.substring(0, 2), 16);
            int r = Integer.valueOf(hexColor.substring(2, 4), 16);
            int g = Integer.valueOf(hexColor.substring(4, 6), 16);
            int b = Integer.valueOf(hexColor.substring(6, 8), 16);
            return new Color(r, g, b, alpha);
        } else {
            throw new IllegalArgumentException("Invalid hex color format. Expected 6 or 8 characters.");
        }
    }

    public static Color hsv2rgb(float hue, float saturation, float luminance) {
        float h = (hue / 60);
        float chroma = luminance * saturation;
        float x = chroma * (1 - Math.abs((h % 2) - 1));

        Vector3f rgbVec;
        if (h >= 0 && h <= 1) {
            rgbVec = new Vector3f(chroma, x, 0);
        } else if (h >= 1 && h <= 2) {
            rgbVec = new Vector3f(x, chroma, 0);
        } else if (h >= 2 && h <= 3) {
            rgbVec = new Vector3f(0, chroma, x);
        } else if (h >= 3 && h <= 4) {
            rgbVec = new Vector3f(0, x, chroma);
        } else if (h >= 4 && h <= 5) {
            rgbVec = new Vector3f(x, 0, chroma);
        } else if (h >= 5 && h <= 6) {
            rgbVec = new Vector3f(chroma, 0, x);
        } else {
            rgbVec = null;
        }

        if (rgbVec != null) {
            float m = luminance - chroma;
            return new Color((int) (255.0f * (rgbVec.x + m)), (int) (255.0f * (rgbVec.y + m)),
                    (int) (255.0f * (rgbVec.z + m)));
        }
        return null;
    }
}