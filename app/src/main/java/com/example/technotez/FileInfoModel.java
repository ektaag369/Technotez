package com.example.technotez;

public class FileInfoModel {
    String PdfUrl, Semester,Subject,Title,UUID;
    public boolean isVisible;

    public FileInfoModel() {
    }

    public FileInfoModel(String pdfUrl, String semester, String subject, String title, String UUID, boolean isVisible) {
        PdfUrl = pdfUrl;
        Semester = semester;
        Subject = subject;
        Title = title;
        this.UUID = UUID;
        this.isVisible = isVisible;
    }

    public String getPdfUrl() {
        return PdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        PdfUrl = pdfUrl;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
