import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;

import javax.swing.JPanel;

class JPanelReversed extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public void paint(Graphics g) {

        BufferedImage im = new BufferedImage(this.getWidth(), this.getHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);
        // Paint normally but on the image
        super.paint(im.getGraphics());

        // Reverse the image
        AffineTransform tx = AffineTransform.getScaleInstance(-1, -1);
        tx.translate(-im.getWidth(), -im.getHeight());
        AffineTransformOp op = new AffineTransformOp(tx,
                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        im = op.filter(im, null);

       // Draw the reversed image on the screen
        g.drawImage(im, 0, 0, null);

    }
	
    private static BufferedImage createInverted(BufferedImage image)
    {
        if (image.getType() != BufferedImage.TYPE_INT_ARGB)
        {
            image = convertToARGB(image);
        }
        LookupTable lookup = new LookupTable(0, 4)
        {
            @Override
            public int[] lookupPixel(int[] src, int[] dest)
            {
                dest[0] = (int)(255-src[0]);
                dest[1] = (int)(255-src[1]);
                dest[2] = (int)(255-src[2]);
                return dest;
            }
        };
        LookupOp op = new LookupOp(lookup, new RenderingHints(null));
        return op.filter(image, null);
    }
    
    private static BufferedImage convertToARGB(BufferedImage image)
    {
        BufferedImage newImage = new BufferedImage(
            image.getWidth(), image.getHeight(),
            BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }    

}