package komersa.controller;

import komersa.dto.mapper.ImagesDtoMapper;
import komersa.dto.request.ImagesDtoRequest;
import komersa.dto.response.ImagesDtoResponse;
import komersa.model.Images;
import komersa.service.ImagesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static komersa.utils.TokenManager.verifyToken;

@RestController
@RequestMapping("/api/images")
public class ImagesController {
    private final ImagesService imagesService;

    public ImagesController(ImagesService imagesService) {
        this.imagesService = imagesService;
    }

    @PostMapping
    @Operation(summary = "Create an images", description = "Create new images")
    @ApiResponse(responseCode = "201", description = "Images saved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Invalid foreign key that is not found")
    public ResponseEntity<ImagesDtoResponse> createImages(@RequestHeader(required = false, value = "Authorization") String token, @Valid @RequestBody ImagesDtoRequest imagesDtoRequest) {
        verifyToken(token);
        Images images = ImagesDtoMapper.toModel(imagesDtoRequest);
        images = imagesService.create(images);
        return new ResponseEntity<>(ImagesDtoMapper.toResponse(images), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Images", description = "Get Images By Id")
    @ApiResponse(responseCode = "200", description = "Images Get successfully")
    @ApiResponse(responseCode = "404", description = "Images with such an Id not found")
    public ResponseEntity<ImagesDtoResponse> getImagesById(@PathVariable("id") Long id) {
        Images images = imagesService.getById(id);
        return new ResponseEntity<>(ImagesDtoMapper.toResponse(images), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get All Images", description = "Get All Images")
    @ApiResponse(responseCode = "200", description = "Images Get All successfully")
    @ApiResponse(responseCode = "404", description = "No records with Images have been found")
    public ResponseEntity<Page<ImagesDtoResponse>> getAllImages(Pageable pageable) {
        Page<Images> imagesPage = imagesService.getAll(pageable);
        return new ResponseEntity<>(imagesPage.map(ImagesDtoMapper::toResponse), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an images", description = "Update an images by Id and new Images")
    @ApiResponse(responseCode = "201", description = "Images updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Images with such an Id not found or invalid foreign key that is not found")
    public ResponseEntity<ImagesDtoResponse> updateImages(@RequestHeader(required = false, value = "Authorization") String token, @PathVariable("id") Long id, @Valid @RequestBody ImagesDtoRequest imagesDtoRequest) {
        verifyToken(token);
        Images images = ImagesDtoMapper.toModel(imagesDtoRequest);
        images = imagesService.updateById(id, images);
        return new ResponseEntity<>(ImagesDtoMapper.toResponse(images), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an images", description = "Delete an images by id")
    @ApiResponse(responseCode = "204", description = "Images deleted successfully")
    public ResponseEntity<Boolean> deleteImages(@RequestHeader(required = false, value = "Authorization") String token, @PathVariable("id") Long id) {
        verifyToken(token);
        return new ResponseEntity<>(imagesService.deleteById(id), HttpStatus.NO_CONTENT);
    }
}