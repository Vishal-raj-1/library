# Book and Author Management System

### Task to performed

0) Created a db with following structure in mongodb:
1) `Book(id, copiesAvailable, authorId, genre)` & `Author(id, name, address(house no, city,state)`
2) Create a GET api to get all the books from db
3) Create a GET api to get all the books from db whose genre matches the query param input
4) Create a GET api to get all the books from db whose genre matches the query param input and copiesAvailable is more than input provided in the path param.
5) Create a POST api to save book detail in db (including validations of not blank, not null etc)
6) Create a POST api to save author detail in db (including validations of not blank, not null etc)
7) Create a GET api to fetch all books whose author is in one of the input provided in the query params
8) Create a GET api to fetch all authors whose name matches the regular expression provided in the input query params.

### Model

Let's create Book Model with `id` as primary key. `Book(id, copiesAvailable, authorId, genre)`

```java
@Document
public class Book {
    @Id
    private String id;
    private String genre;
    private String authorId;
    private int copiesAvailable;
}
```

Now for creating the Author Model, we need  `Address` (house no, city, state), so let's create one address class. **NOTE**: It's not a collection, it is used just for author details.


```java
public class Address {
    private String houseNo;
    private String city;
    private String state;
}
```

Now, we can create Author Model using Address.

```java
@Document
public class Author {
    @Id
    private String id;
    private String name;
    private Address address;
    private List<String> bookList;
}
```

### Repository

Create `BookRepository` and `AuthorRepository` which extends the `MongoRepository`. Also add additional methods, as per the needs.

BookRepository
```java
public interface BookRepository extends MongoRepository<Book, String> {

    @Query("{ genre : ?0 }")
    List<Book> findByGenre(String genre);

    @Query("{ genre : ?0 , copiesAvailable : { $gt : ?1 } }")
    List<Book> findByGenreAndCopiesAvailableGreaterThan(String genre, int copiesAvailable);

    List<Book> findByAuthorIdIn(List<String> Author);
}
```

AuthorRepository
```java
public interface AuthorRepository extends MongoRepository<Author, String> {

    @Query("{ name : ?0 }")
    Author findByName(String authorName);

    @Query("{ name: { $regex : ?0 } }")
    List<Author> findByNameRegex(String nameRegex);
}
```

### Services

Create `BookService` and `AuthorService`. 

BookService
```java
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }

    public List<Book> getBooksByGenreAndCopiesAvailable(String genre, int copiesAvailable) {
        return bookRepository.findByGenreAndCopiesAvailableGreaterThan(genre, copiesAvailable);
    }

    public Book getBookById(String id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book saveBook(Book book, String authorName) {
        if( book!=null && book.getCopiesAvailable()>=0 && !authorName.isEmpty() && book.getGenre()!=null ) {
            Author author = authorRepository.findByName(authorName);
            if(author == null){
                author = new Author();
                author.setName(authorName);

                String authorId = new ObjectId().toHexString();
                author.setId(authorId);

                authorRepository.save(author);
                book.setAuthorId(authorId);
            } else {
                book.setAuthorId(author.getId());
            }

            Book savedBook = bookRepository.save(book);
            author.getBookList().add(savedBook.getId());
            authorRepository.save(author);

            return savedBook;
        } else{
            throw new IllegalArgumentException("Invalid/Incomplete details given");
        }
    }

    public List<Book> getBooksByAuthorNames(List<String> authors) {
        List<Book> books = new ArrayList<>();

        for (String authorName : authors) {
            Author author = authorRepository.findByName(authorName);
            List<String> bookIds = author.getBookList();

            for(String bookId: bookIds) {
                Optional<Book> book = bookRepository.findById(bookId);

                book.ifPresent(books::add);
            }
        }

        return books;
    }
}
```

AuthorService
```java
@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(String id) {
        return authorRepository.findById(id).orElse(null);
    }

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public List<Author> getAuthorsByNameRegex(String nameRegex) {
        return authorRepository.findByNameRegex(nameRegex);
    }
}
```
### Controllers

Create `BookControllers` and `AuthorController`

BookController
```java
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable String bookId){
        Book book = bookService.getBookById(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/byGenre")
    public ResponseEntity<List<Book>> getBooksByGenre(@RequestParam String genre) {
        List<Book> books = bookService.getBooksByGenre(genre);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/byGenreAndCopiesAvailable")
    public ResponseEntity<List<Book>> getBooksByGenreAndCopiesAvailable(
            @RequestParam String genre,
            @RequestParam int copiesAvailable) {
        List<Book> books = bookService.getBooksByGenreAndCopiesAvailable(genre, copiesAvailable);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody BookAuthorDTO request) {
        String authorName = request.getAuthorName();
        Book book = request.getBook();

        Book savedBook = bookService.saveBook(book, authorName);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @GetMapping("/byAuthors")
    public ResponseEntity<List<Book>> getBooksByAuthorIds(@RequestBody List<String> authors) {
        List<Book> books = bookService.getBooksByAuthorNames(authors);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
```

AuthorController
```java
@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/{authorId}")
    public ResponseEntity<Author> getAuthorById(@PathVariable String authorId) {
        Author authors = authorService.getAuthorById(authorId);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("/byNameRegex")
    public ResponseEntity<List<Author>> getAllAuthorsByNameRegex(@RequestParam String nameRegex) {
        List<Author> authors = authorService.getAuthorsByNameRegex(nameRegex);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Author> saveAuthor(@RequestBody Author author) {
        Author savedAuthor = authorService.saveAuthor(author);
        return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
    }
}
```
