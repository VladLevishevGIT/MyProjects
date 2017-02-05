# -*- coding: utf-8 -*-

class Temperature(object):
    def __init__(self, celsius=0):
        self.celsius = celsius

    @property
    def fahrenheit(self):
        return self.celsius * 1.8 + 32

    @fahrenheit.setter
    def fahrenheit(self, value):
        self.celsius = (value - 32) / 1.8

    def __str__(self):
        return 'Celsius: {:g}\nFahrenheit: {:g}\n'.format(
            self.celsius, self.fahrenheit)


def main():
    temperature = Temperature(24)
    print(temperature)

    temperature.celsius = 10
    print(temperature)

    temperature.fahrenheit = 100
    print(temperature)


if __name__ == '__main__':
    main()