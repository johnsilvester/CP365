import random
from random import randint
import string
import math, operator
from PIL import Image, ImageDraw
import numpy as np
from scipy.spatial import distance


#target = "to be or not to be that is the question".upper()
target = Image.open("goal.jpg")
rgb_target = target.convert('RGB')
data = np.asarray( target, dtype="int32" )
goalWidth, goalHeight = target.size
class GA:
    def __init__(self, pop_size):
        self.pop = []
        self.pop_size = pop_size


#####Trouble here#####################
    def fitness(self, solution):

        #rgb_solution = solution.convert('RGB')
        rgb_solution = solution
    #Getting the fitness to work well can be tricky.  I recommend taking both images and then randomly choosing a bunch of sample (x,y) locations to compare. 
    # So if you have two images named targetImage and generatedImage,
     #then you might compare them with Euclidean distance 
        total_diff = 0
        for i in range(20):#do this 100 times:
            rand_x = randint(0,target.width-1)
            rand_y = randint(0,target.height-1)
            a = np.array(rgb_target.getpixel((rand_x, rand_y)))
            b = np.array(rgb_solution.getpixel((rand_x, rand_y)))

            diff = distance.euclidean(a,b)
           
            total_diff += diff

        average_diff = total_diff / 100
        return 1.0/average_diff

    def generateRandomSolution(self):
        length = 50
        img = Image.new('RGB', target.size)
        drw = ImageDraw.Draw(img, 'RGBA')
        for i in range(length):
            drw.rectangle([randint(0,length)-25,randint(0,length)-25,randint(0,goalWidth)-25,randint(0,goalWidth)-25],fill = (randint(0,255),randint(0,255),randint(0,255))) 
            #drw.polygon([(randint(0,goalWidth), randint(0,goalHeight)), (randint(0,goalWidth), randint(0,goalHeight)), (randint(0,goalWidth), randint(0,goalHeight))], (randint(0,255), randint(0,255), randint(0,255), randint(0,255)))
        del drw
        return img

    def generateInitPopulation(self):
        for i in range(self.pop_size):
            self.pop.append(self.generateRandomSolution())
        self.generatePopulationFitness()


    def generatePopulationFitness(self):
        self.pop_fitnesses = []
        for solution in self.pop:
            self.pop_fitnesses.append(self.fitness(solution))

    def pickFitParent(self):
        total_fitness = sum(self.pop_fitnesses)
        #print total_fitness
        r = total_fitness*random.random()
        
        ind = -1
        while r > 0:
            ind += 1
            r -= self.pop_fitnesses[ind]
        return self.pop[ind]

    def crossover(self, img1, img2):
        draw = ImageDraw.Draw(img1)
        x, y = img2.size
        nImg2 = img2.crop((0, 0, y, x/2))
        img1.paste(nImg2, (0,0,y,x/2))
        del draw
        return img1

    def mutate(self, child, mutation_rate=.005):
        length = 10
        for i in range(length):
            if random.random() < mutation_rate:
                drw = ImageDraw.Draw(child, 'RGB')
                #print child
                drw.rectangle([randint(0,goalWidth),randint(0,goalWidth),randint(0,goalWidth),randint(0,goalWidth)],fill = (randint(0,255),randint(0,255),randint(0,255)))
                #drw.polygon([(randint(0,goalWidth), randint(0,goalHeight)), (randint(0,goalWidth), randint(0,goalHeight)), (randint(0,goalWidth), randint(0,goalHeight))], (randint(0,255), randint(0,255), randint(0,255), randint(0,255)))
                
                #return drw
        print "out"
        return child

        #morphing algorithm setup##
          

    def getBestSolution(self):
        max_ind = self.pop_fitnesses.index(max(self.pop_fitnesses))
        return self.pop[max_ind]

    def generateNewPopulation(self):
        new_pop = []
        for i in range(self.pop_size):
            p1 = self.pickFitParent()
            p2 = self.pickFitParent()
            child = self.crossover(p1, p2)
            child = self.mutate(child)
            new_pop.append(child)
        self.pop = new_pop

    def evolve(self, number_epochs):
        self.generateInitPopulation()
        for i in range(number_epochs):
            self.generateNewPopulation()
            self.generatePopulationFitness()
            if i % 100 == 0:
                b = self.getBestSolution()
                b.save("out/ga{}.jpg".format(i), "JPEG")
                print i, b, max(self.pop_fitnesses)

if __name__=="__main__":

    #im = cv2.imread("goal.jpg")
    #print data
    #print target.BytesIO()
    ga = GA(10)
    ga.evolve(10000)


