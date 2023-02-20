package me.lavrentiev.cookbook.Controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import me.lavrentiev.cookbook.service.FileIngredientService;
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
@RequestMapping("/files/ingredient")
@Tag(name = "API по работе с ингредиентами", description = "сохранение.удаление файлов с ингредиентами")
public class IngredientFilesController {
    private final FileIngredientService ingredientService;

    public IngredientFilesController(FileIngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping(value = "/export")
    public ResponseEntity<InputStreamResource> downloadDataFile() throws FileNotFoundException {
        File file = ingredientService.getDataFile();

        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream((file)));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; file name=\"IngredientLog.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadDataFile(@RequestParam MultipartFile file) {
        ingredientService.deleteDataFile();
        File dataFile = ingredientService.getDataFile();

        try (FileOutputStream fos=new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
