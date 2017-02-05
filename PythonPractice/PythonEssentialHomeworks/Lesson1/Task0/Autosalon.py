from Car import Car

class Autosalon():

    def __init__(self):
        car1 = Car("Mazda", "Red", 500)
        car2 = Car("Opel", "Grey", 600)
        car3 = Car("Kia", "Black", 400)
        self.carsList = [car1, car2, car3]

    def buyCar(self, item):
        index = 0
        for car in self.carsList:
            if car.brend.__eq__(item):
                print ("{} is your new car".format(item))
                self.carsList.remove(car)
                for car in self.carsList:
                    print ("{}".format(car.brend))
                index = 1
                break
        if index == 0: print("No such brend to buy")
     


