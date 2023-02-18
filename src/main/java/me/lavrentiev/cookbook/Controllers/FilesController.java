package me.lavrentiev.cookbook.Controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import me.lavrentiev.cookbook.service.FileRecipeService;
import me.lavrentiev.cookbook.service.RecipeService;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/files")
@Tag(name = "API по работе с ингредиентами", description = "CRUD - операции для ингредиентов")
public class FilesController {

    private final FileRecipeService recipeService;

    public FilesController(FileRecipeService fileRecipeService) {
        this.recipeService = fileRecipeService;
    }

    @GetMapping(value = "/recipe/export/")
    public ResponseEntity<InputStreamResource> downloadDataFile() throws FileNotFoundException {
       File file = recipeService.getDataFile();

        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream((file)));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; file name=\"RecipeLog.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/recipe/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadDataFile(@RequestParam MultipartFile file) {
        recipeService.deleteDataFile();
        File dataFile = recipeService.getDataFile();

        try (FileOutputStream fos=new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        }  catch (IOException e) {
        e.printStackTrace();
    }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
}
}
