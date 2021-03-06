package data;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Blog implements Readable, Writable{
    List<Post> tweets;

    public Blog(){
        tweets = new LinkedList<>();
        try{
            populateFromDisk();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally {
        	//TODO
        }
    }

    public void addPost(Post newPost) {
    	tweets.add(newPost);
    }
    
    private void populateFromDisk() throws IOException {
      
        	  try {
        	    FileInputStream fis = new  FileInputStream("new.txt");
        	    ObjectInputStream ois = new ObjectInputStream(fis);
        	    Object obj = ois.readObject();
        	    tweets = (LinkedList<Post>) obj;
        	  } 
        	  catch (Exception e) {
        	    System.out.println(e);
        	  } 
        	  
        	
    }


//returns latest tweet
    public Post readOne(){
        return ((LinkedList<Post>) tweets).getLast();

    }


//returns all tweets
    public List<Post> readAll() throws IOException {
    	
        List<Post> allTweets = new LinkedList<Post>();

        for(Iterator<Post> it = tweets.iterator(); it.hasNext();){
            allTweets.add(it.next());
        }

        if(readOne()!=null){
            return allTweets; //return of list right?
        }else{
            return null;
        }
        
    }

//returns only E from the same author
   public List<Post> readOwnPost() throws IOException{
    List<Post> ownTweets = new LinkedList<Post>();
/*
        for(ListIterator<AbstractPost> it = tweets.ListIterator(); it.hasNext();){
            AbstractPost post = it.next();

            //if(post.getAuthor() == this.author???)
            ownTweets.add(post);
        }

        if(ownTweets.readOne()!=null){*/
            return ownTweets; //return of list right?
       /* }else{
            return null;
        }
        
    }*/


    }

//save() rewrites the whole file, erasing previous data
   public void save() throws IOException{
		  try {
		    FileOutputStream fos = new FileOutputStream ("new.txt");
		    ObjectOutputStream oos = new ObjectOutputStream(fos);
		    oos.writeObject(tweets);
		    fos.close();
		  } 
		  catch (Exception e) {
		    System.out.println(e);   
		  }
 }

}
