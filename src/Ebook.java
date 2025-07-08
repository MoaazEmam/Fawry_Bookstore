import java.util.Date;

public class Ebook extends Book implements Emailable{
    private String filetype;

    public Ebook(String isbn, String title, int yob,float price,String filetype){
        super(isbn,title,yob,price);
        this.filetype=filetype;
    }

    public String getFiletype(){return filetype;}
    public void setFiletype(String filetype){this.filetype=filetype;}
}
