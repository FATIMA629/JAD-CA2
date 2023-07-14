package Ratings;

public class Rating {
	private int ratingId;
    private int bookId;
    private int userId;
    private double rating;
    private int helpful;
    private String comment;

    public Rating(int bookId, int userId, double rating, int helpful, String comment) {
    	this.ratingId = ratingId;
        this.bookId = bookId;
        this.userId = userId;
        this.rating = rating;
        this.helpful = helpful;
        this.comment = comment;
    }
    
 // Constructors
    public Rating() {
    }
       // Getters and Setters
    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }
    
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getHelpful() {
        return helpful;
    }

    public void setHelpful(int helpful) {
        this.helpful = helpful;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
