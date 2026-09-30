package com.veggiebasket.release_manager_backend.dto;

import com.veggiebasket.release_manager_backend.entity.Release;

public class ReleaseResponse {

    private Long id;
    private String version;
    private Integer versionCode;
    private String releaseDate;
    private String apkUrl;
    private String apkSize;
    private String platform;
    private String releaseNotes;
    private Boolean published;
    private Boolean forceUpdate;

    public ReleaseResponse() {
    }

    public ReleaseResponse(Release release) {
        this.id = release.getId();
        this.version = release.getVersion();
        this.versionCode = release.getVersionCode();
        this.releaseDate = release.getReleaseDate() != null
                ? release.getReleaseDate().toString()
                : null;
        this.apkUrl = release.getApkUrl();
        this.apkSize = release.getApkSize();
        this.platform = release.getPlatform();
        this.releaseNotes = release.getReleaseNotes();
        this.published = release.getPublished();
        this.forceUpdate = release.getForceUpdate();
    }

    public Long getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public String getApkSize() {
        return apkSize;
    }

    public String getPlatform() {
        return platform;
    }

    public String getReleaseNotes() {
        return releaseNotes;
    }

    public Boolean getPublished() {
        return published;
    }

    public Boolean getForceUpdate() {
        return forceUpdate;
    }
}