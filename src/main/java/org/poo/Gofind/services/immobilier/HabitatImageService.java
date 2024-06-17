package org.poo.Gofind.services.immobilier;

import org.poo.Gofind.dto.immobilier.HabitatImageDTO;
import org.poo.Gofind.models.immobilier.Habitat;
import org.poo.Gofind.models.immobilier.HabitatImage;
import org.poo.Gofind.repositories.immobilier.HabitatImageRepository;
import org.poo.Gofind.repositories.immobilier.HabitatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class HabitatImageService {

    @Autowired
    private HabitatRepository habitatRepository;

    @Autowired
    private HabitatImageRepository habitatImageRepository;

    //enregistrement d'une image
    public HabitatImageDTO uploadImage(Long habitatId, MultipartFile file) throws IOException {
        Optional<Habitat> optionalHabitat = habitatRepository.findById(habitatId);
        if (optionalHabitat.isPresent()) {
            Habitat habitat = optionalHabitat.get();
            HabitatImage habitatImage = HabitatImage.builder()
                .habitat(habitat)
                .imageData(file.getBytes())
                .build();
            habitatImage = habitatImageRepository.save(habitatImage);
            return HabitatImageDTO.builder()
                .imageId(habitatImage.getImageId())
                .habitatId(habitat.getId())
                .imageData(habitatImage.getImageData())
                .build();
        } else {
            throw new IOException("Habitat not found");
        }
    }

    //recuperation des images
    public Iterable<HabitatImageDTO> getImages(Long habitatId) {
        Iterable<HabitatImage> images = habitatImageRepository.findByHabitatId(habitatId);
        return StreamSupport.stream(images.spliterator(), false)
            .map(image -> HabitatImageDTO.builder()
                .imageId(image.getImageId())
                .habitatId(image.getHabitat().getId())
                .imageData(image.getImageData())
                .build())
            .collect(Collectors.toList());
    }

    //suppression d'une image
    public void deleteImage(Long imageId) {
        habitatImageRepository.deleteById(imageId);
    }

    //modification d'une image a partir de son id
    public HabitatImageDTO updateImage(Long imageId, MultipartFile file) throws IOException {
        Optional<HabitatImage> optionalHabitatImage = habitatImageRepository.findById(imageId);
        if (optionalHabitatImage.isPresent()) {
            HabitatImage habitatImage = optionalHabitatImage.get();
            habitatImage.setImageData(file.getBytes());
            habitatImage = habitatImageRepository.save(habitatImage);
            return HabitatImageDTO.builder()
                .imageId(habitatImage.getImageId())
                .habitatId(habitatImage.getHabitat().getId())
                .imageData(habitatImage.getImageData())
                .build();
        } else {
            throw new IOException("Image not found");
        }
    }
}
