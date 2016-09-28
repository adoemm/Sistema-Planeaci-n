<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>

<%@page import="javax.imageio.IIOImage"%>
<%@page import="javax.imageio.ImageWriteParam"%>
<%@page import="javax.imageio.ImageWriter"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.awt.Color"%>
<%@page import="java.awt.Graphics2D"%>
<%@page import="java.awt.geom.Rectangle2D"%>
<%@page import="java.awt.geom.AffineTransform"%>
<%@page import="java.awt.font.FontRenderContext"%>
<%@page import="java.awt.Font"%>
<%@page import="org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="org.krysalis.barcode4j.tools.UnitConv"%>
<%@page import="org.krysalis.barcode4j.impl.code128.Code128Bean"%>
<%
    //SystemSettings.ignition();
//request.setCharacterEncoding(PageParameters.getPatameter("charset").toString());
    //response.setCharacterEncoding(PageParameters.getPatameter("charset").toString());
    session = request.getSession(true);
    String mime = "image/jpg";
    response.setContentType(mime);
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    
    
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.addHeader("Cache-control", "max-age=0");
    

    try {
        String msg = "";
        if (request.getParameter("stringToBarcode") == null) {
            msg = "1020020000046653115939333266";
        } else {
            msg = request.getParameter("stringToBarcode");
        }
        //String[] paramArr = new String[]{"Hola", "Mundo"};
        String[] paramArr = new String[]{};
        Code128Bean bean = new Code128Bean();

        final int dpi = 200;
        bean.setModuleWidth(UnitConv.in2mm(8.0f / dpi)); //makes a dot/module exactly eight pixels
        bean.doQuietZone(false);
        boolean antiAlias = false;
        int orientation = 0;
        int padding = 2;
        float imageQuality = 0.95f;
        int width = 250;
        int height = 150;
        int fontSize = 60; //pixels
        int lineHeight = (int) (fontSize * 1.2);
        Font font = new Font("Arial", Font.PLAIN, fontSize);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////        
        //Set up the canvas provider to create a monochrome bitmap
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(dpi, BufferedImage.TYPE_BYTE_BINARY, antiAlias, orientation);
        //Generate the barcode
        
        bean.setFontSize(7);
        bean.generateBarcode(canvas, msg);
        
        //Signal end of generation
        canvas.finish();
        BufferedImage symbol = canvas.getBufferedImage();
        width = symbol.getWidth();
        height = symbol.getHeight();
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        FontRenderContext frc = new FontRenderContext(new AffineTransform(), antiAlias, true);
        for (int i = 0; i < paramArr.length; i++) {
            String line = paramArr[i];
            Rectangle2D bounds = font.getStringBounds(line, frc);
            width = (int) Math.ceil(Math.max(width, bounds.getWidth()));
            System.out.println("width: " + width);
            height += lineHeight;
            System.out.println("height " + height);
        }

        //Add padding
        padding = 2;
        width += 2 * padding;
        height += 3 * padding;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////        


        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        g.setBackground(Color.white);
        g.setColor(Color.black);
        g.clearRect(0, 0, width, height);
        g.setFont(font);

        //Draw an oval
        //Color backgroundColor = Color.CYAN;
        //g.setColor(backgroundColor);
        //g.fillRect(0, 0, width, height);
        //g.setColor(Color.BLACK);
        //g.drawLine(0, 0, 100, 100);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                       
        //Place the barcode symbol 
        AffineTransform symbolPlacement = new AffineTransform();
        symbolPlacement.translate(padding, padding);
        g.drawRenderedImage(symbol, symbolPlacement);

        int y = padding + symbol.getHeight() + padding;
        for (int i = 0; i < paramArr.length; i++) {
            String line = paramArr[i];
            y += lineHeight;
            g.drawString(line, padding, y);
        }

        //Write the image as a jpg
        Iterator iter = ImageIO.getImageWritersByFormatName("JPG");
        if (iter.hasNext()) {
            ImageWriter writer = (ImageWriter) iter.next();
            ImageWriteParam iwp = writer.getDefaultWriteParam();
            iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            iwp.setCompressionQuality(imageQuality);
            writer.setOutput(ImageIO.createImageOutputStream(response.getOutputStream()));
            IIOImage imageIO = new IIOImage(bufferedImage, null, null);
            writer.write(null, imageIO, iwp);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } else {
            throw new RuntimeException("no encoder found for jsp");
        }
        g.dispose();
    } catch (Exception oe) {
        throw new RuntimeException("Unable to build image", oe);
    }

%>