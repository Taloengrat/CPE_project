package com.example.projectcpe.CSV.Utility;

import java.io.File;

public interface FileCallback {
    void onSuccess(File file);
    void onFail(String err);
}
