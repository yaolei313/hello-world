package com.yao.app.springmvc.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class JSONPResponseWrapper extends HttpServletResponseWrapper {
    private ServletOutputStream output = null;
    private PrintWriter writer = null;
    private ByteArrayOutputStream buffer = null;
    //private int httpStatus;

    public JSONPResponseWrapper(HttpServletResponse resp) throws IOException {
        super(resp);
        buffer = new ByteArrayOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(buffer, getCharacterEncoding()));
        output = new WrappedOutputStream(buffer);
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return writer;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return output;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (writer != null)
            writer.flush();
        if (output != null)
            output.flush();
    }

    @Override
    public void reset() {
        buffer.reset();
    }

    public byte[] getResponseData() throws IOException {
        flushBuffer();
        return buffer.toByteArray();
    }

   /* @Override
    public void sendError(int sc) throws IOException {
        httpStatus = sc;
        super.sendError(sc);
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        httpStatus = sc;
        super.sendError(sc, msg);
    }

    @Override
    public void setStatus(int sc) {
        httpStatus = sc;
        super.setStatus(sc);
    }

    public int getStatus() {
        return httpStatus;
    }*/

    class WrappedOutputStream extends ServletOutputStream {
        private ByteArrayOutputStream buffer;

        public WrappedOutputStream(ByteArrayOutputStream buffer) {
            this.buffer = buffer;
        }

        public void write(int b) throws IOException {
            buffer.write(b);
        }

        public byte[] toByteArray() {
            return buffer.toByteArray();
        }
    }
}
