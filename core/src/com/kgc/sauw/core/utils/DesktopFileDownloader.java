package com.kgc.sauw.core.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.files.FileHandle;

import java.io.InputStream;
import java.io.OutputStream;

public class DesktopFileDownloader implements FileDownloader {
    @Override
    public void download(String url, FileHandle downloadTo, ProgressListener progressListener) {
        Net.HttpRequest request = new Net.HttpRequest("GET");
        request.setUrl(url);
        request.setContent(null);

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
                            } else {
                                progressListener.update(progress);
                            }
                        });
                    }
                } catch (Exception e) {
                    Gdx.app.error("error", "Downloading error", e);
                }
            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.error("error", "failed", t);
                progressListener.failed(t);
            }

            @Override
            public void cancelled() {
            }
        });
    }
}
