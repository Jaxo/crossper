package com.crossper.utils;


import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.CharacterCodingException;
import javax.imageio.ImageIO;
import org.apache.commons.lang.StringUtils;

/**
 * This class generates QR code from given data
 *
 */
public class QRCodeGenerator  {
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    private String __name;
    private URI __location;
    private OutputStream __stream;
    protected FORMAT __format = FORMAT.PNG;
    protected int height;
    protected int width;

    /**
     * Format enumeration
     */
    protected enum FORMAT {

        PNG("png"),
        JPEG("jpeg");
        private String ext;

        FORMAT(String ext) {
            this.ext = ext;
        }

        public String ext() {
            return this.ext;
        }
    };

    public void setRoot(URI root) {
        this.__location = root;
    }

    public void setRoot(String root) {
        this.__location = URI.create(root);
    }

    public void setStream(OutputStream stream) {
        this.__stream = stream;
    }

    public void setRoot(File root) {
        this.__location = root.toURI();
    }

    public void setName(String name) {
        this.__name = name;
    }

    public void setSize(int h, int w) {
        this.height = h;
        this.width = w;
    }

    /** convert bitmatrix to image **/
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
    int width = matrix.getWidth();
    int height = matrix.getHeight();
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
          for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
          }
        }
        return image;
    }

    /** write image to file **/
    public static void writeToFile(BitMatrix matrix, String format, File file)
          throws IOException {
    BufferedImage image = toBufferedImage(matrix);
    if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
      }

    /** write to stream **/
    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream)
          throws IOException {
    BufferedImage image = toBufferedImage(matrix);
    if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

    /**
     * This method returns URI to the generated code
     *
     * @return
     */
    public URI generateCode(String data) throws IOException, WriterException, CharacterCodingException {
        URI uri = null;

        if (__location == null && __stream == null) {
            throw new IllegalArgumentException("No location given to store code");
        }
        if (data == null) {
            throw new IllegalArgumentException("No data to generate code");
        }

        // get a byte matrix for the data
        BitMatrix bitMatrix = generateMatrix(data);

        if (__location != null && StringUtils.isNotEmpty(__location.getPath())) {
            String filePath = __location.getPath() + File.separator + __name + "." + __format.ext();
            File file = new File(filePath);
            uri = file.toURI();
            writeToFile(bitMatrix, __format.toString(), file);
            System.out.println("printing to " + file.getAbsolutePath());
        } else {
            writeToStream(bitMatrix, __format.toString(), __stream);
            if (__stream != null) {
                __stream.flush();
            }
        }
        return uri;
    }

    public BitMatrix generateMatrix(String data) throws CharacterCodingException, UnsupportedEncodingException {
        BitMatrix matrix = null;
        int h = (height == 0 ? 100 : height);
        int w = (width == 0 ? 100 : width);
        com.google.zxing.Writer writer = new QRCodeWriter();
        Charset charset = Charset.forName("ISO-8859-1");
        CharsetEncoder encoder = charset.newEncoder();
        byte[] b = null;

        // Convert a string to ISO-8859-1 bytes in a ByteBuffer
        ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(data));
        b = bbuf.array();
        data = new String(b, "ISO-8859-1");
        try {
            matrix = writer.encode(data,
                    com.google.zxing.BarcodeFormat.QR_CODE, w, h);
        } catch (com.google.zxing.WriterException e) {
            System.out.println(e.getMessage());
        }
        return matrix;
    }
}
