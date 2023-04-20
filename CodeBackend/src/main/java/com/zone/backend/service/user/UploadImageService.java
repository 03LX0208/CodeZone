package com.zone.backend.service.user;

import java.io.File;
import java.util.Map;

public interface UploadImageService {
    Map<String, String> uploadImage(File file);
}
