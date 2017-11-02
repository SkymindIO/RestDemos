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

public class DeployIris extends HttpServlet {
    public DeployIris() {
    }

    private String form = "<form method=\"post\">" +
            "Param 1<input type=\"text\" name=\"1\"><br>" +
            "Param 2<input type=\"text\" name=\"2\"><br>" +
            "Param 3<input type=\"text\" name=\"3\"><br>" +
            "Param 4<input type=\"text\" name=\"4\"><br>" +
            "<input type=\"submit\">" +
            "</form>";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");



        String title = "Simple REST wrapper for Iris Model";
        String docType =
                "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

        out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor = \"#f0f0f0\">\n" +
                "<h1 align = \"center\">" + title + "</h1>\n" +
                "<p>Enter Parameters</p>" +
                form +
                "</body>" +
                "</html>" );

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        INDArray myArray = Nd4j.zeros(1, 4);
        //myArray.putScalar(0, 0, 4.6D);
        //myArray.putScalar(0, 1, 3.6D);
        //myArray.putScalar(0, 2, 1.0D);
        //myArray.putScalar(0, 3, 0.2D);

        //int foo = Integer.parseInt("1234");
        String value1 = request.getParameter("1");
        String value2 = request.getParameter("2");
        String value3 = request.getParameter("3");
        String value4 = request.getParameter("4");

        myArray.putScalar(0, 0, Double.parseDouble(value1));
        myArray.putScalar(0, 1, Double.parseDouble(value2));
        myArray.putScalar(0, 2, Double.parseDouble(value3));
        myArray.putScalar(0, 3, Double.parseDouble(value4));

        MultiLayerNetwork model = ModelSerializer.restoreMultiLayerNetwork((new ClassPathResource("trained_iris_model.zip")).getFile());
        INDArray output = model.output(myArray);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Iris Classifier</title></head>");
        out.println("<body>");
        out.println("<p>Given the measurements</p>");
        out.println(value1);
        out.println(value2);
        out.println(value3);
        out.println(value4);
        out.println("</br>");
        out.println("<p>Here is the prediction</p>");
        out.println(output);
        out.println("<p>Try Another ?</p>");
        out.println(form);
        out.println("</body></html>");
        out.close();
    }
}
