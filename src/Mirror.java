import java.awt.image.BufferedImage;



class Mirror {
    public BufferedImage image;
    public int rotation;

    public Mirror(BufferedImage image) {
        this.image = image;
        this.rotation = 0;
    }

    public void rotate() {
        rotation = (rotation + 1) % 4;
        ImageHandler.rotateImage(image, 90);
    }

}