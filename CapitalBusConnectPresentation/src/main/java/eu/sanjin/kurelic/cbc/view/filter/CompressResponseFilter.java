package eu.sanjin.kurelic.cbc.view.filter;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import eu.sanjin.kurelic.cbc.view.configuration.SpringConfiguration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

// Classic servlet filter for compressing HTML output
//@WebFilter(urlPatterns = "/*")
public class CompressResponseFilter implements Filter {

    private static final String HTML_ELEMENTS_WITHOUT_SURROUNDING_SPACES = "a,div";
    private HtmlCompressor htmlCompressor;

    @Override
    public void init(FilterConfig filterConfig) {
        htmlCompressor = new HtmlCompressor();
        htmlCompressor.setRemoveIntertagSpaces(true);
        htmlCompressor.setPreserveLineBreaks(false);
        htmlCompressor.setRemoveSurroundingSpaces(HTML_ELEMENTS_WITHOUT_SURROUNDING_SPACES);
        htmlCompressor.setCompressCss(false);
        htmlCompressor.setCompressJavaScript(false);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        var request = (HttpServletRequest) req;
        var response = new EditableResponseWrapper((HttpServletResponse) res);
        // Skip resource url
        var url = request.getRequestURL().toString();
        // Do stuff
        chain.doFilter(request, response);
        // Trim HTML
        String html = response.getOutput();
        if(!url.contains(SpringConfiguration.RESOURCES_LOCATION)) {
            html = new String(htmlCompressor.compress(html).getBytes(), StandardCharsets.UTF_8);
        }
        // Output HTML
        PrintWriter out = new PrintWriter(response.getOutputStream());
        out.write(html);
        out.flush();
    }

    @Override
    public void destroy() {
    }
}