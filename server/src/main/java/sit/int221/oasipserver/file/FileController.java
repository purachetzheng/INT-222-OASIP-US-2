package sit.int221.oasipserver.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@RestController
@RequestMapping("/api/file")
@CrossOrigin("*")
public class FileController {

    @Autowired
    FilesStorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<FileResponseMessage> uploadFiles(@RequestParam("files") MultipartFile[] files) throws IOException{
        String message = "";
        try {
            List<String> fileNames = new ArrayList<>();

            Arrays.asList(files).stream().forEach(file -> {
                try {
                    storageService.save(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                fileNames.add(file.getOriginalFilename());
            });

            message = "Uploaded the files successfully: " + fileNames;
            return ResponseEntity.status(HttpStatus.OK).body(new FileResponseMessage(message));
        } catch (Exception e) {
            message = "Fail to upload files!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileResponseMessage(message));
        }
    }

    @GetMapping("")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FileController.class, "getFile", path.getFileName().toString()).build().toString();
            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename,
                                     @RequestParam(required = false) Boolean nameOnly) throws IOException {
        File file = storageService.load(filename).orElseThrow(); //File Object ที่หา
        Path path = Paths.get(file.getAbsolutePath()); //Path File ที่หา
        Resource resource = new UrlResource(path.toUri()); //สร้าง Link จาก Path ที่เราหา
//        long bytes = Files.size(path);
//        ResponseEntity.BodyBuilder bodyBuilder =  ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"" +
//                        "size=\"" + bytes + "\"");
        ResponseEntity.BodyBuilder bodyBuilderDownload =  ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");
        if (nameOnly == null || !nameOnly) {
            return bodyBuilderDownload.body(resource);
        } else {
//            return bodyBuilder.body(file.getName());
            return bodyBuilderDownload.body(file.getName());
        }

    }



    @DeleteMapping("/{filename:.+}")
    public ResponseEntity<?> deleteFile(@PathVariable String filename) {
        storageService.delete(filename);
        return ResponseEntity.ok().body(new FileResponseMessage(filename + " successfully deleted"));
    }
}
