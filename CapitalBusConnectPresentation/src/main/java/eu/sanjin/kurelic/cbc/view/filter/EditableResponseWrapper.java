package eu.sanjin.kurelic.cbc.view.filter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.PrintWriter;
import java.io.StringWriter;

public class EditableResponseWrapper extends HttpServletResponseWrapper {

    private PrintWriter writer;
    private StringWriter output;

    EditableResponseWrapper(HttpServletResponse response) {
        super(response);
        output = new StringWriter();
        writer = new PrintWriter(output);
    }

    @Override
    public PrintWriter getWriter() {
        return writer;
    }

    String getOutput() {
        return output.toString();
    }
}
