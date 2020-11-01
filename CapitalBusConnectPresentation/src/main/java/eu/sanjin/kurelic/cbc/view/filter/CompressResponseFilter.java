package eu.sanjin.kurelic.cbc.view.filter;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import eu.sanjin.kurelic.cbc.view.configuration.SpringConfiguration;
import eu.sanjin.kurelic.cbc.view.controller.web.CommerceController;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

// Classic servlet filter for compressing HTML output
@WebFilter(urlPatterns = "/*")
public class CompressResponseFilter implements Filter {

  private static final String SLASH = "/";
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

  private boolean isUrlCompressible(String url) {
    var ticketImage = CommerceController.TICKET_IMAGE_URL;
    ticketImage = ticketImage.substring(0, ticketImage.lastIndexOf(SLASH));
    return !(url.contains(SpringConfiguration.RESOURCES_LOCATION) || url.contains(ticketImage));
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
    throws IOException, ServletException {
    var request = (HttpServletRequest) req;
    var response = new EditableResponseWrapper((HttpServletResponse) res);
    // Do stuff
    chain.doFilter(request, response);
    // Trim HTML
    String html = response.getOutput();
    if (isUrlCompressible(request.getRequestURL().toString())) {
      html = htmlCompressor.compress(html);
    }
    // Output HTML - notice auto flush is not needed
    PrintWriter out = new PrintWriter(response.getOutputStream(), false, StandardCharsets.UTF_8);
    out.write(html);
    out.flush();
  }

  @Override
  public void destroy() {
  }
}