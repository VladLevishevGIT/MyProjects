# -*- coding: utf-8 -*-

"""
Задание 1
Создайте класс, описывающий книгу. Он должен содержать информацию об авторе,
названии, годе издания и жанре. Создайте несколько разных книг. Определите
для него операции проверки на равенство и неравенство, методы __repr__ и __str__.
"""

import copy


class Book(object):
    def __init__(self, author, title, publication_year, genre):
        self.author = author
        self.title = title
        self.publication_year = publication_year
        self.genre = genre

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
            self.genre
        )

    def __str__(self):
        return self.__as_string('"{1}" by {0} (published in {2}, genre: {3})')

    def __repr__(self):
        return self.__as_string("Book({0!r}, {1!r}, {2!r}, {3!r})")


def main():
    orwell1984 = Book('George Orwell', '1984', 1949, 'dystopia')
    orwell_copy = copy.copy(orwell1984)  # копирование объекта
    learning_python = Book('Mark Lutz', 'Learning Python', 2013, 'tutorial')

    print(orwell1984)
    print(learning_python)

    print(repr(orwell1984))
    print(repr(learning_python))

    print(orwell1984 == orwell_copy)
    print(orwell1984 != orwell_copy)
    print(orwell1984 == learning_python)
    print(orwell1984 != learning_python)
    print(learning_python == learning_python)
    print(learning_python != learning_python)


if __name__ == '__main__':
    main()