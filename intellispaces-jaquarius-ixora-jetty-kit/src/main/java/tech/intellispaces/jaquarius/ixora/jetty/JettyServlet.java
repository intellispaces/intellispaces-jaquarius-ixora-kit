package tech.intellispaces.jaquarius.ixora.jetty;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tech.intellispaces.commons.base.collection.ArraysFunctions;
import tech.intellispaces.jaquarius.ixora.http.HttpMethods;
import tech.intellispaces.jaquarius.ixora.http.HttpPortExchangeChannel;
import tech.intellispaces.jaquarius.ixora.http.HttpRequestHandle;
import tech.intellispaces.jaquarius.ixora.http.HttpRequests;
import tech.intellispaces.jaquarius.ixora.http.UnmovableHttpResponseHandle;
import tech.intellispaces.jaquarius.object.reference.MovableObjectHandle;

import java.io.IOException;

class JettyServlet extends HttpServlet {
  private MovableObjectHandle<?> logicalPort;
  private Class<? extends HttpPortExchangeChannel> exchangeChannel;

  void init(MovableObjectHandle<?> logicalPort, Class<? extends HttpPortExchangeChannel> exchangeChannel) {
    this.logicalPort = logicalPort;
    this.exchangeChannel = exchangeChannel;
  }

  @Override
  protected void doGet(
      HttpServletRequest servletRequest, HttpServletResponse servletResponse
  ) throws IOException {
    HttpRequestHandle request = buildRequest(servletRequest);
    UnmovableHttpResponseHandle response = logicalPort.mapOfMovingThru(exchangeChannel, request);
    populateServletResponse(servletResponse, response);
  }

  private HttpRequestHandle buildRequest(HttpServletRequest req) {
    String url = req.getRequestURL().toString();
    String query = req.getQueryString();
    String uri = (query == null ? url : url + '?' + query);
    return HttpRequests.get(
      HttpMethods.get(req.getMethod()),
      uri
    );
  }

  private void populateServletResponse(
      HttpServletResponse servletResponse, UnmovableHttpResponseHandle response
  ) throws IOException {
    if (response.status().isOkStatus()) {
      servletResponse.setStatus(HttpServletResponse.SC_OK);
    } else {
      throw new RuntimeException();
    }

    byte[] body = ArraysFunctions.toByteArray(response.bodyStream().readAll().nativeList());
    servletResponse.getOutputStream().write(body);
  }
}
