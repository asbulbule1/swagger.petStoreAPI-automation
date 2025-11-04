package pojo;

import java.util.List;

public class Pet {
    private long id;
    private String name;
    private String status;
    private Category category;
    private List<String> photoUrls;
    private List<Tag> tags;

    public static class Category {
        public long id;
        public String name;
    }

    public static class Tag {
        public long id;
        public String name;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public java.util.List<String> getPhotoUrls() { return photoUrls; }
    public void setPhotoUrls(java.util.List<String> photoUrls) { this.photoUrls = photoUrls; }

    public java.util.List<Tag> getTags() { return tags; }
    public void setTags(java.util.List<Tag> tags) { this.tags = tags; }
}
