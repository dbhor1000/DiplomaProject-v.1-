package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.AuthService;


@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdController {

    //private final AdService adService;
    //
    @GetMapping
    public ResponseEntity<Ads> adInfo() {
            return ResponseEntity.ok().build();
    }

    //

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> newAd(@RequestParam("properties") Ad ad, @RequestParam("image") MultipartFile picture) {
        return ResponseEntity.ok().build();
    }

    //

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAd> adInfoById(@PathVariable Integer id) {
        return ResponseEntity.ok().build();
    }

    //

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdById(@PathVariable Integer id) {
        return ResponseEntity.ok().build();
    }

    //

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchAdById(@PathVariable Integer id, @RequestBody CreateOrUpdateAd createOrUpdateAd) {
        return ResponseEntity.ok().build();
    }

    //

    @GetMapping("/me")
    public ResponseEntity<?> showAuthorizedUserAd() {
        return ResponseEntity.ok().build();
    }

    //

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> patchAdPictureById(@PathVariable Integer id, @RequestParam("image") MultipartFile linkedPicture) {
        return ResponseEntity.ok().build();
    }











}
