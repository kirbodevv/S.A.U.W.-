package com.kgc.sauw;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;
import com.badlogic.gdx.files.FileHandle;
import com.kgc.sauw.core.utils.APKFileOpener;

public class APKOpener implements APKFileOpener {
    private Context context;

    public APKOpener(Context context) {
        this.context = context;
    }

    @Override
    public void open(FileHandle file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri uri = FileProvider.getUriForFile(context,
                    context.getApplicationContext().getPackageName() + ".provider", file.file());
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            context.startActivity(intent);
        } else {
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            install.setDataAndType(Uri.fromFile(file.file()), "application/vnd.android.package-archive");
            context.startActivity(install);
        }
    }
}
