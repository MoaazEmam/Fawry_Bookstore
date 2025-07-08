public class PaperBook extends Book implements Shippable{
    private int stock;

    public PaperBook(String isbn, String title, int yob, float price, int stock){
        super(isbn,title,yob,price);
        this.stock=stock;
    }

    public int getStock(){return stock;}
    public void setStock(int stock){this.stock=stock;}

    public void reduceStock(int quantity){
        if (quantity>stock){
            throw new IllegalArgumentException("Insufficient stock");
        } else {
            stock-=quantity;
        }
    }
    public void addStock(int amount){
        stock+=amount;
    }
}
