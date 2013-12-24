/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ex2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author gil
 */
public class MyServlet extends HttpServlet {

    protected final String HEADER_HTML_FILEPATH = "htmls/header.htm";
    protected final String FOOTER_HTML_FILEPATH = "htmls/footer.htm";
    protected final String HEADER_LOGOUT_FILEPATH = "htmls/logout.htm";
    /**
     * 
     * @param filePath to read from
     * @return
     * @throws FileNotFoundException 
     */
    protected String getFileContent(String filePath) throws FileNotFoundException
    {

        String relPath = getServletContext().getRealPath("/");
        Scanner in = new Scanner(new FileReader( relPath + filePath));
        StringBuilder buff = new StringBuilder();
        String lineSeparator = System.getProperty("line.separator");        
        while (in.hasNextLine())
            buff.append(in.nextLine()).append(lineSeparator);
        in.close();

        return buff.toString();
    }
}