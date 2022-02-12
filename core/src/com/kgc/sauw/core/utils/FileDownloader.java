package com.kgc.sauw.core.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.files.FileHandle;

import java.io.InputStream;
import java.io.OutputStream;

public class FileDownloader {
    public interface ProgressListener {
        void update(int progress);

        void done();
    }

    public static void download(String url, FileHandle downloadTo, ProgressListener progressListener) {
        HttpRequest request = new HttpRequest(HttpMethods.GET);
        request.setTimeOut(2500);
        request.setUrl(url);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                long length = Long.parseLong(httpResponse.getHeader("Content-Length"));

                byte[] bytes = new byte[1024];
                int count = -1;
                long read = 0;

                try (InputStream is = httpResponse.getResultAsStream(); OutputStream os = downloadTo.write(false)) {
                    while ((count = is.read(bytes, 0, bytes.length)) != -1) {
                        os.write(bytes, 0, count);
                        read += count;

                        final int progress = ((int) (((double) read / (double) length) * 100));

                        Gdx.app.postRunnable(() -> {
                            if (progress == 100) {
                                progressListener.done();
                            }
                            progressListener.update(progress);
                        });
                    }
                } catch (Exception e) {
                    Gdx.app.error("error", "Downloading error", e);
                }
            }

            @Override
            public void failed(Throwable t) {

            }

            @Override
            public void cancelled() {

            }
        });
    }
}
