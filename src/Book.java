public class Book {
    private final String isbn;
    private final String title;
    private final int yob; //year of publication
    private float price; //demo books dont have price but most
                       //of the other existing and possible future types do


    public Book(String isbn,String title,int yob,float price){
        this.isbn=isbn;
        this.title=title;
        this.yob=yob;
        this.price=price;
    }

    public String getIsbn(){return isbn;}
    public String getTitle(){return title;}
    public int getYob(){return yob;}
    public float getPrice(){return price;}

    //Based on my understanding, only price can change
    public void setPrice(int price){this.price=price;}

}
