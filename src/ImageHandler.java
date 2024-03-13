import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ImageHandler {

    /**
     * Rotates an image by a specified number of degrees.
     *
     * @param image  The original image to rotate.
     * @param degrees The angle in degrees.
     * @return A new BufferedImage instance representing the rotated image.
     */
    public static BufferedImage rotateImage(BufferedImage image, double degrees) {
        double radians = Math.toRadians(degrees);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int newWidth = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
        int newHeight = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);

        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImage.createGraphics();

        AffineTransform at = new AffineTransform();
        at.translate((newWidth - image.getWidth()) / 2, (newHeight - image.getHeight()) / 2);
        at.rotate(radians, image.getWidth() / 2, image.getHeight() / 2);
        g2d.setTransform(at);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }

    /**
     * Flips an image horizontally or vertically.
     *
     * @param image The original image to flip.
     * @param horizontal Flip horizontally if true, vertically otherwise.
     * @return A new BufferedImage instance representing the flipped image.
     */
    public static BufferedImage flipImage(BufferedImage image, boolean horizontal) {
        AffineTransform at = new AffineTransform();
        if (horizontal) {
            at.scale(-1, 1);
            at.translate(-image.getWidth(null), 0);
        } else {
            at.scale(1, -1);
            at.translate(0, -image.getHeight(null));
        }

        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(image, null);
    }

    /**
     * Scales an image to the specified width and height.
     *
     * @param image  The original image to scale.
     * @param width  The target width.
     * @param height The target height.
     * @return A new BufferedImage instance representing the scaled image.
     */
    public static BufferedImage scaleImage(BufferedImage image, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();

        return scaledImage;
    }
}