package com.kgc.sauw;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.badlogic.gdx.files.FileHandle;
import com.kgc.sauw.core.utils.APKFileOpener;

public class APKOpener implements APKFileOpener {
    private Context context;

    public APKOpener(Context context) {
        this.context = context;
    }

    @Override
    public void open(FileHandle file) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        install.setDataAndType(Uri.fromFile(file.file()), "application/vnd.android.package-archive");
        context.startActivity(install);
    }
}
