package com.example.projectcpe.CSV.Utility;

import java.io.File;
import java.io.IOException;

public interface FileCallback {
    void onSuccess(File file) throws IOException;
    void onFail(String err);
}
