package request;
import java.io.Serializable;
import java.util.*;

public class PostSubmission implements Serializable {
	private String author;
	private String tweet;
    private Date timestamp;
    public PostSubmission( String author,String tweet,Date timestamp){
        this.author=author;
        this.tweet=tweet;
        this.timestamp=timestamp;
	}

	public String getAuthor() {
        return author;
    }

    public String getTweet() {
        return tweet;
    }

    public Date getTimestamp() {
        return timestamp;
    }

}
