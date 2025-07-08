import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class BookStore {
    private ConcurrentHashMap<String, Book> Books = new ConcurrentHashMap<>();
    private Scanner scan = new Scanner(System.in);

    public void startBookstore(){
        System.out.println("\tWelcome to our Bookstore");
        System.out.println("\tHow can we help you today?");
        int choice=0;
        do {
            System.out.println("1. Add a new book");
            System.out.println("2. Add an existing book");
            System.out.println("3. Remove outdate books");
            System.out.println("4. View available books");
            System.out.println("5. Buy a book");
            System.out.println("0. Exit");
            choice=scan.nextInt();
            scan.nextLine();
            switch(choice){
                case 1->addNewbook();
                case 2->addExistingbook();
                case 3->returnBooks();
                case 4->listBooks();
                case 5->buyBook();
                case 0->System.out.println("\tThank you for visiting our store....Bye");
                default->System.out.println("Invalid choice, try again please");
            }
        }while(choice!=0);
    }
    private Book addPaperBook(String isbn, String title, int yob) {
        System.out.println("Please enter price: ");
        float price = scan.nextFloat();
        scan.nextLine();
        System.out.println("Please enter stock: ");
        int stock = scan.nextInt();
        scan.nextLine();
        return new PaperBook(isbn, title, yob, price, stock);
    }

    private Book addEbook(String isbn, String title, int yob) {
        System.out.println("Please enter price: ");
        float price = scan.nextFloat();
        scan.nextLine();
        System.out.println("Please enter filetype");
        String filetype = scan.nextLine();
        return new Ebook(isbn, title, yob, price, filetype);
    }

    private Book addDemoBook(String isbn, String title, int yob) {
        return new DemoBook(isbn, title, yob);
    }

    private void addNewbook() {
        System.out.println("What is the type of book");
        System.out.println("1. Paper Book");
        System.out.println("2. Ebook");
        System.out.println("3. Demo Book");
        System.out.println("0. Back to main menu");
        int choice = scan.nextInt();
        scan.nextLine();
        System.out.println("Please enter ISBN: ");
        String isbn = scan.nextLine();
        System.out.println("Please enter title: ");
        String title = scan.nextLine();
        System.out.println("Please enter year of publication: ");
        int yob = scan.nextInt();
        scan.nextLine();
        if(yob<=0 || yob>2025) {
            System.out.println("Invalid year, please try again\n");
            return;
        }

        Book newBook = null;
        try {
            switch (choice) {
                case 1 -> newBook = addPaperBook(isbn, title, yob);
                case 2 -> newBook = addEbook(isbn, title, yob);
                case 3 -> newBook = addDemoBook(isbn, title, yob);
                case 0-> {return;}
                default->System.out.println("Invalid choice, try again please");
            }
            if (!newBook.equals(null) && !Books.containsKey(isbn))
                Books.put(newBook.getIsbn(), newBook);
            else System.out.println("Book already exists");
        } catch (Exception e) {
            System.err.printf("An error happened while creating book: %s\n", e.getMessage());
        }
    }
    private void addExistingbook(){
        System.out.println("Please enter ISBN: ");
        String isbn=scan.nextLine();

        if(!Books.containsKey(isbn)){
            System.err.printf("ISBN: %s does not exist\n",isbn);
        } else {
            try{
                Book existingBook=Books.get(isbn);
                System.out.println("Please enter amount to add");
                int amount=scan.nextInt();
                scan.nextLine();
                if(existingBook instanceof PaperBook){
                    ((PaperBook) existingBook).addStock(amount);
                } else {
                    System.out.println("This book is not a physical book, cannot add stock");
                }
            } catch(Exception e){
                System.err.printf("An error happened while adding the book: %s\n",e.getMessage());
            }
        }
    }
    private void returnBooks(){
        try{
                System.out.println("Please enter year of expiry: "); //books do not expire but idk how to word it correctly
                int yoe = scan.nextInt();
                scan.nextLine();
                for (Book book:Books.values()) {
                    if (yoe >= book.getYob()) {
                        Books.remove(book.getIsbn());
                        System.out.printf("Book with ISBN %s removed\n", book.getIsbn());
                    }
                }
        } catch(Exception e){
            System.err.printf("An error happened while removing the book: %s\n",e.getMessage());
        }
    }
    private void listBooks(){
        if(!Books.isEmpty()) {
            for (Book book : Books.values()) {
                System.out.printf("ISBN: %s\tTitle: %s\tPrice: %.2f\tYear of publication: %d\n", book.getIsbn(), book.getTitle(),book.getPrice(),book.getYob());
            }
        } else System.out.println("No books currently available");
    }
    private void buyBook(){
        System.out.println("Please enter ISBN: ");
        String isbn=scan.nextLine();
        try {
            Book book=Books.get(isbn);
            if(!book.equals(null)) {
                System.out.println("Please enter quantity you want to buy");
                int quantity = scan.nextInt();
                scan.nextLine();
                System.out.println("Please enter your email address");
                String email=scan.nextLine();
                System.out.println("Please enter your physical address");
                String address=scan.nextLine();
                if(book instanceof Shippable){
                    if(quantity>((PaperBook) book).getStock()){
                        System.out.printf("Not enough quantity of %s, available stock is %d\n",book.getTitle(),((PaperBook) book).getStock());
                    } else {
                        ShippingService.ship();
                        System.out.printf("Purchase of a quantity: %d of book %s successful\n",quantity,book.getTitle());
                        System.out.printf("Total paid: %.2f\n",quantity*book.getPrice());
                    }
                } else if(book instanceof Emailable){
                    MailService.mail();
                    System.out.printf("Purchase of a quantity: %d of book %s successful\n",quantity,book.getTitle());
                    System.out.printf("Total paid: %.2f\n",quantity*book.getPrice());
                } else if(book instanceof DemoBook){
                    System.out.println("Sorry, this book is not for sale");
                }
            }
        } catch(Exception e){
            System.err.printf("An error happened while removing the book: %s\n",e.getMessage());
        }
    }
}

