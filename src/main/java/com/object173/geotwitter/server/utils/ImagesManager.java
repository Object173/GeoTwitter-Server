package com.object173.geotwitter.server.utils;

import com.object173.geotwitter.server.contract.ResourcesContract;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImagesManager {

    private static final int SMALL_WIDTH = 300;
    private static final int MEDIUM_WIDTH = 600;
    private static final int LARGE_WIDTH = 1200;

    public static byte[] createMiniAvatar(final byte[] bytes) {
        if(bytes == null) {
            return null;
        }

        final InputStream in = new ByteArrayInputStream(bytes);
        try {
            final BufferedImage avatar = ImageIO.read(in);
            final BufferedImage avatarMini = new BufferedImage(SMALL_WIDTH, SMALL_WIDTH,
                    BufferedImage.TYPE_INT_ARGB);
            final Graphics2D g = avatarMini.createGraphics();
            g.drawImage(avatar, 0, 0, SMALL_WIDTH, SMALL_WIDTH, null);
            g.dispose();

            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(avatarMini, ResourcesContract.IMAGE_FORMAT, baos);

            return baos.toByteArray();
        } catch (IOException e) {
            System.out.println(e.toString());
            return null;
        }
    }
}
