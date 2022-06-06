package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Converter implements TextGraphicsConverter{

    private double maxRatio;
    private int maxWidth;
    private int maxHeight;
    private TextColorSchema schema;

    public Converter(TextColorSchema textColorSchema) {
        this.schema = textColorSchema;
    }

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        ModifiedImage modifiedImage = new ModifiedImage();

        checkUrlImageRatio(img);

        resizeUrlImageWidthAndHeight(img, modifiedImage);

        final int newWidth = modifiedImage.getNewImageWidth();
        final int newHeight = modifiedImage.getNewImageHeight();

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);

        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);

        Graphics2D graphics = bwImg.createGraphics();

        graphics.drawImage(scaledImage, 0, 0, null);

        ImageIO.write(bwImg, "png", new File("out.png"));

        WritableRaster bwRaster = bwImg.getRaster();

        return buildSymbolImage(bwRaster);
    }

    private void checkUrlImageRatio(BufferedImage img) throws BadImageSizeException {
        double urlImageRatio = (double) img.getWidth() / img.getHeight();

        if (urlImageRatio > maxRatio && urlImageRatio == 0) {
            throw new BadImageSizeException(urlImageRatio, maxRatio);
        }
    }

    private void resizeUrlImageWidthAndHeight(BufferedImage img, ModifiedImage modifiedImage) {
        if (img.getWidth() > getMaxWidth() || img.getHeight() > getMaxHeight()) {
            double res1 = (double) img.getWidth() / getMaxWidth() ;
            double res2 = (double) img.getHeight() / getMaxHeight();
            double resultFinal = Math.max(res1, res2);
            modifiedImage.setNewImageWidth((int) (img.getWidth() / resultFinal));
            modifiedImage.setNewImageHeight((int) (img.getHeight() / resultFinal));
        } else {
            modifiedImage.setNewImageWidth(img.getWidth());
            modifiedImage.setNewImageHeight(img.getHeight());
        }
    }

    private String buildSymbolImage(WritableRaster bwRaster) {
        StringBuilder symbolImage = new StringBuilder();

        for (int h = 0; h < bwRaster.getHeight(); h++) {
            for (int w = 0; w < bwRaster.getWidth(); w++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char symbol = this.schema.convert(color);
                symbolImage.append(symbol).append(symbol);
            }
            symbolImage.append("\n");
        }
        return symbolImage.toString();
    }

    private int getMaxWidth() {
        return maxWidth;
    }

    private int getMaxHeight() {
        return maxHeight;
    }

    @Override
    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    @Override
    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }
}
