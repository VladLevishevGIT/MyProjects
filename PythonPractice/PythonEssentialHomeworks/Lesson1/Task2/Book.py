from Review import Review

class Book:
    def __init__(self, author, title, year, genre, reviewList=[]):
        self.author = author
        self.title = title
        self.year = year
        self.genre = genre
        self.reviewsList = reviewList
    
    def __eq__(self, other):
        if not isinstance(other, Book):
            return False
        else: 
            return self.author == other.author and self.title == other.title and self.year == other.year and self.genre == other.genre

    def __repr__(self):
        book = "Book(author:{0!r},title:{1!r},year{2!r},genre{3!r})".format(self.author, self.title, self.year, self.genre)
        reviews ='\n'.join(map(str, reviewsList)
        return reviews + book

    def __str__(self):
        return "Author is {0}, Title is: {1}, Year is {2}, Genre is {3}".format(self.author, self.title, self.year, self.genre)




  




