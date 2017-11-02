package io.skymind.rest;

import org.datavec.api.util.ClassPathResource;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class DeployAbelone extends HttpServlet {
    public DeployAbelone() {
    }

    private String form = "<form method=\"post\">" +
            "Param 1<input type=\"text\" name=\"1\" size=\"45\"><br>" +
                        "<input type=\"submit\">" +
            "</form>";

    private String datasample = "Age 10 - 1,0.72,0.55,0.18,1.52,0.637,0.325,0.435\n<br/>" +
            "Age 9 - 2,0.475,0.34,0.105,0.4535,0.203,0.08,0.1465\n<br/>" +
            "Age 9 - 2,0.53,0.405,0.13,0.6615,0.2945,0.1395,0.19\n<br/>" +
            "Age 3 - 2,0.205,0.155,0.045,0.0495,0.0235,0.011,0.014<br/>";


    private String welcomeMessage = "Abelone age predictor, enter your data as comma delimited text";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");



        String title = "Abelone Inference";
        String docType =
                "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

        out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor = \"#f0f0f0\">\n" +
                "<h1 align = \"center\">" + title + "</h1>\n" +
                "<p>" + welcomeMessage + "</p>" +
                "<p>" +  "Here is some sample data" +  "</p>" +
                datasample +
                form +
                "</body>" +
                "</html>" );

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        // Get the input from the form text field
        String value1 = request.getParameter("1");

        // Create array of doubles from the string
        String [] items = value1.split(",");

        // take array of strings and create array of doubles

        double[] doubleArray = new double[items.length];
        for (int i = 0; i < items.length; i++) {
            String doubleAsString = items[i];
            doubleArray[i] = Double.parseDouble(doubleAsString);
        }

        //INDArray myArray = Nd4j.create(items);
        //INDArray myArray = Nd4j.create(new double[]{});
        //INDArray myArray = Nd4j.create(new double[]{1, 0.72, 0.55, 0.18, 1.52, 0.637, 0.325, 0.435});

        INDArray myArray = Nd4j.create(doubleArray);
        MultiLayerNetwork model = ModelSerializer.restoreMultiLayerNetwork((new ClassPathResource("trained_abelone_model.zip")).getFile());
        INDArray output = model.output(myArray);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Abelone Age Predictor</title></head>");
        out.println("<body>");
        out.println("<p>Given the measurements</p>");
        out.println("As a String");
        out.print(value1);
        out.println("<p>As an Array</p>");
        out.println(Arrays.toString(items));
        //out.println(value2);
        //out.println(value3);
        //out.println(value4);
        out.println("</br>");
        out.println("<p>Here is the prediction</p>");
        out.println(output);
        out.println("<p>Try Another ?</p>");
        out.println(form);
        out.print("<p>" +  "Here is some sample data" +  "</p>" +
                datasample) ;

        out.println("</body></html>");
        out.close();
    }
}
