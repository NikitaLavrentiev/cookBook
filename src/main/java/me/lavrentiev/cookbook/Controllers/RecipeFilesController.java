package me.lavrentiev.cookbook.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/files/recipe")
@Tag(name = "API по работе с рецептами", description = "сохранение.удаление файлов с рецептами")
public class RecipeFilesController {

    private final FileRecipeService fileRecipeService;
    private final RecipeService recipeService;


    public RecipeFilesController(FileRecipeService recipeService, RecipeService recipeService1) {
        this.fileRecipeService = recipeService;
        this.recipeService = recipeService1;
    }


    @Operation(
            summary = "Скачать рецепты",
            description = "Файл скачается в формате .json"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Файл успешно скачался."
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "Файл для скачивания не существует"
                    )
            }
    )
    @GetMapping(value = "/export")
    public ResponseEntity<InputStreamResource> downloadDataFile() throws FileNotFoundException {
       File file = fileRecipeService.getDataFile();

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
    @Operation(
            summary = "Закачать файл на сервер",
            description = "Подходят только файлы формата .json с рецептами"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "База рецептов успешно обновлена"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Файл не принят на сервер, либо ошибка сервера. Обратитесь к администратору"
            )
    })
    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadDataFile(@RequestParam MultipartFile file) {
        fileRecipeService.deleteDataFile();
        File dataFile = fileRecipeService.getDataFile();

        try (FileOutputStream fos=new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        }  catch (IOException e) {
        e.printStackTrace();
    }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
}

    @Operation(
            summary = "Скачать список рецептов в .TXT",
            description = "Файл скачается в формате .txt"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Файл успешно скачался."
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "Файл для скачивания не существует"
                    )
            }
    )
    @GetMapping(value = "/export/txt")
    public ResponseEntity<InputStreamResource> downloadTxtFile() {
        try {
            File file = recipeService.recipesToTxt();
            InputStreamResource resource = new InputStreamResource(new FileInputStream((file)));

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"recipes.txt\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }
}
