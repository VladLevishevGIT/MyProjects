# -*- coding: utf-8 -*-

"""
Задание 2
Создайте класс, описывающий отзыв к книге. Добавьте в класс книги
поле – список отзывов. Сделайте так, что при выводе книги на экран
при помощи функции print также будут выводиться отзывы к ней.
"""


class Book(object):
    def __init__(self, author, title, publication_year, genre, comments=[]):
        self.author = author
        self.title = title
        self.publication_year = publication_year
        self.genre = genre
        self.comments = comments

    def __eq__(self, other):
        if not isinstance(other, Book):
            return False
        return self.author == other.author and \
               self.title == other.title and \
               self.publication_year == other.publication_year and \
               self.genre == other.genre

    def __as_string(self, format_string):
        return format_string.format(
            self.author,
            self.title,
            self.publication_year,
            self.genre,
            self.comments
        )

    def __str__(self):
        book = self.__as_string('"{1}" by {0} (published in {2}, genre: {3})'
                                '\nComments:\n')
        comments = '\n'.join(map(str, self.comments)) or 'No comments.'
        return book + comments

    def __repr__(self):
        return self.__as_string("Book({0!r}, {1!r}, {2!r}, {3!r}, {4!r})")


class Comment(object):
    def __init__(self, mark, text):
        self.mark = mark
        self.text = text

    def __repr__(self):
        return 'Comment({!r}, {!r})'.format(self.mark, self.text)

    def __str__(self):
        return 'Mark: {}\nReview: {}'.format(self.mark, self.text)


def main():
    orwell1984 = Book('George Orwell', '1984', 1949, 'dystopia')
    learning_python = Book('Mark Lutz', 'Learning Python', 2013, 'tutorial')

    orwell1984.comments = [
        Comment(5, 'Awesome book, changed my perception of the life'),
        Comment(4, "Not bad, but Huxley's scenario seems more realistic to me"),
    ]

    print(orwell1984)
    print()
    print(learning_python)
    print()

    print(repr(orwell1984))
    print(repr(learning_python))


if __name__ == '__main__':
    main()
