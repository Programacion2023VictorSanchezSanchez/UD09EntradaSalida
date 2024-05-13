package T09Tarea2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.List;

class Post {
    private long id;
    private String title;
    private String body;
    private long userId;

    public long getid() { return id; }
    public void setid(long value) { this.id = value; }

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }

    public String getBody() { return body; }
    public void setBody(String value) { this.body = value; }

    public long getUserId() { return userId; }
    public void setUserId(long value) { this.userId = value; }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", userId=" + userId +
                "}\n";
    }

    public static void main(String[] args) {
        String url="https://jsonplaceholder.typicode.com/posts";
        List<Post> posts;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            posts = objectMapper.readValue(new URL(url), new TypeReference<List<Post>>() {});
            System.out.println(posts);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

