package com.veggiebasket.release_manager_backend.service;

import com.veggiebasket.release_manager_backend.dto.ReleaseRequest;
import com.veggiebasket.release_manager_backend.dto.ReleaseResponse;
import com.veggiebasket.release_manager_backend.entity.Release;
import com.veggiebasket.release_manager_backend.repository.ReleaseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReleaseService {

    private final ReleaseRepository releaseRepository;

    public ReleaseService(ReleaseRepository releaseRepository) {
        this.releaseRepository = releaseRepository;
    }

    public List<ReleaseResponse> getAllReleases() {
        return releaseRepository
                .findAllByOrderByVersionCodeDesc()
                .stream()
                .map(ReleaseResponse::new)
                .toList();
    }

    public ReleaseResponse getLatestPublishedRelease() {
        Release release = releaseRepository
                .findFirstByPublishedTrueOrderByVersionCodeDesc()
                .orElseThrow(() -> new RuntimeException("No published release found"));

        return new ReleaseResponse(release);
    }

    public ReleaseResponse createRelease(ReleaseRequest request) {

        if (releaseRepository.findByVersion(request.getVersion()).isPresent()) {
            throw new RuntimeException("Version already exists");
        }

        Release release = new Release();

        mapRequestToEntity(request, release);

        Release savedRelease = releaseRepository.save(release);

        return new ReleaseResponse(savedRelease);
    }

    public ReleaseResponse updateRelease(Long id, ReleaseRequest request) {

        Release release = releaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Release not found"));

        releaseRepository.findByVersion(request.getVersion())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new RuntimeException("Version already exists");
                    }
                });

        mapRequestToEntity(request, release);

        Release updatedRelease = releaseRepository.save(release);

        return new ReleaseResponse(updatedRelease);
    }

    public void deleteRelease(Long id) {

        if (!releaseRepository.existsById(id)) {
            throw new RuntimeException("Release not found");
        }

        releaseRepository.deleteById(id);
    }

    public ReleaseResponse publishRelease(Long id) {

        Release selectedRelease = releaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Release not found"));

        List<Release> publishedReleases =
                releaseRepository.findByPublishedTrueOrderByVersionCodeDesc();

        for (Release release : publishedReleases) {
            release.setPublished(false);
        }

        selectedRelease.setPublished(true);

        releaseRepository.saveAll(publishedReleases);

        Release savedRelease = releaseRepository.save(selectedRelease);

        return new ReleaseResponse(savedRelease);
    }

    private void mapRequestToEntity(
            ReleaseRequest request,
            Release release
    ) {
        release.setVersion(request.getVersion());
        release.setVersionCode(request.getVersionCode());
        release.setReleaseDate(
                LocalDate.parse(request.getReleaseDate())
        );
        release.setApkUrl(request.getApkUrl());
        release.setApkSize(request.getApkSize());

        release.setPlatform(
                request.getPlatform() == null ||
                request.getPlatform().isBlank()
                        ? "Android"
                        : request.getPlatform()
        );

        release.setReleaseNotes(request.getReleaseNotes());

        release.setPublished(
                request.getPublished() != null
                        ? request.getPublished()
                        : false
        );

        release.setForceUpdate(
                request.getForceUpdate() != null
                        ? request.getForceUpdate()
                        : false
        );
    }
}