/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JFrame;
import org.apache.commons.io.FileUtils;
import views.Inbox;

/**
 *
 * @author DELL
 */
public class Util {

    static Point mouseDownCompCoords;

    public static void drag(final JFrame f) {
        f.addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
                mouseDownCompCoords = null;
            }

            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
            }

            public void mouseExited(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseClicked(MouseEvent e) {
            }
        });

        f.addMouseMotionListener(new MouseMotionListener() {
            public void mouseMoved(MouseEvent e) {
            }

            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                f.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            }
        });
    }

    public static int[] getScreenSize() {
        int widthHeight[] = new int[2];
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        widthHeight[0] = screenSize.width;
        widthHeight[1] = screenSize.height;
        return widthHeight;
    }

    public static String fill() {
        String logs = "";
        StringBuffer sb = new StringBuffer("");
        File file = new File(Utils.SystemProperties.getErrorLogFolder().getAbsolutePath() + File.separator + "activity.log");
        try {
            String newLine = System.getProperty("line.separator");
            Scanner fileScanner = new Scanner(file);
            int fileSize = 0;
            fileSize = org.apache.commons.io.FileUtils.readLines(file).size();
            String lineScan;
            for (int i = 0; i < fileSize; i++) {
                lineScan = FileUtils.readLines(file).get(i) + newLine;
                sb.append(lineScan);
            }
            logs = sb.toString();
            //System.out.println(logs);
            fileScanner.close();

        } catch (IOException ex) {
            Logger.getLogger(Inbox.class.getName()).log(Level.SEVERE, null, ex);

        }
        return logs;

    }

    public static void main(String[] args) {
        Util.fill();
    }

    public static String DateMysqlFormat(Date date) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String DateTimeMysqlFormat(Date date) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(date);
    }

    public static Date dateFromMysqlFormat(String date) {
        Date inputDate;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            inputDate = dateFormat.parse(date);
        } catch (ParseException ex) {
            inputDate = null;
        }
        return inputDate;
    }

    public static java.sql.Date utilDateToSqlDate(Date utilDate) {
        return new java.sql.Date(utilDate.getTime());
    }

    public static void logError(String className, String message) {
        FileHandler fh;
        Logger logger = Logger.getLogger(className);
        try {
            logger.setLevel(Level.ALL);
            fh = new FileHandler(Utils.SystemProperties.getErrorLogFolder().getAbsolutePath() + File.separator + "system.log", true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.info("Class Name :" + className + "\t" + message);
            fh.flush();
            fh.close();

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    

}
