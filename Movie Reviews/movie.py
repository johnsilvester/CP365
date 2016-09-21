import matplotlib.pyplot as plt
import math
import numpy as np
from random import randint

np.random.seed(42)
def loadDataset(filename="u.data"):
    my_data = np.genfromtxt(filename, skip_header=0)
        
    userID = my_data[:,0]
    movieID = my_data[:,1]
    rating = my_data[:,2]
        
    return userID, movieID, rating

def createDataSet(userID, movieID, rating):
    mainDic = {}
    for i in range(len(movieID)):
        if movieID[i] in mainDic:
            mainDic[(movieID[i])].update({(userID[i]): rating[i]})
        else:
            mainDic[(movieID[i])] = {(userID[i]): rating[i]}
    return mainDic

class model:
    
    def __init__(self, centroid, mainDic, userID, centroidsArr, clusterArr):
        self.centroid = centroid ##number of clusters
        self.mainDic = mainDic ##dictionary of all movies
        self.userID = userID
        self.centroidsArr = centroidsArr ##array of centroid dictionaries
        self.clusterArr = clusterArr ##array of cluster objects
    


    def makeRandomCentroid(self):
        for i in range(self.centroid):
            i = {}
            for j in range(len(self.userID)):
                    i[userID[j]]=randint(0,5)
            self.centroidsArr.append(i)

    def makeClusters(self):
        clusters = []
        for i in range(self.centroid):
            empty_dic = {}
            current_cluster = cluster(self.centroidsArr[i],empty_dic)
            self.clusterArr.append(current_cluster)

    def evaluateCluster(self):

        for key, movie in self.mainDic.iteritems():
            bestCluster = self.clusterArr[0]
            bestCost = 10000
            for cluster in self.clusterArr:
                currentCost = self.movieDistance(cluster.centroid,movie)
                if( currentCost < bestCost ):
                    bestCost = currentCost
                    bestCluster = cluster
            bestCluster.addToDictionary( key, movie )

    def movieDistance(self, movie_one,movie_two):
        cost = 0
        for key, value in movie_one.iteritems():
            if key in movie_two:
                cost += (movie_one[key]-movie_two[key])**2
        return cost
    
    def printModel(self, i):
        for cluster in self.clusterArr:
            val = "Movie Cluster: "
            for key, value in cluster.clusterDic.iteritems():
                val += str(key) + ", "
            print "Epoch #: " + str(i)
            print val

    def initialize(self):
    
        self.makeRandomCentroid()
        self.makeClusters()
        self.evaluateCluster()
    
    ##Recalculates each centroid and recomputes the clusters accordingly
    def train(self, epochs):
        for i in range(epochs):
            newCentroidsArr = []
            for j in range(len(self.clusterArr)):
                newCentroidsArr.append(self.clusterArr[j].recalcCentroid())
            self.centroidsArr = newCentroidsArr
            self.clusterArr = []
            self.makeClusters()
            self.evaluateCluster()
            self.printModel(i)

class cluster:
    def __init__(self, centroid, clusterDic):
		self.centroid = centroid
		self.clusterDic = clusterDic
    
    def addToDictionary(self,key, movie):
		self.clusterDic[key] = movie

    def recalcCentroid(self):
        updateCentroid = {}
        for user, rating in self.centroid.iteritems():
            cost=0
            validUsers = 0
            for movieID, movieDic in self.clusterDic.iteritems():
                if user in movieDic:
                    cost += movieDic[user]
                    validUsers += 1
            if cost != 0: updateRating = cost/validUsers
            else: 
                updateRating = 0
                updateCentroid[user] = updateRating
        return updateCentroid


if __name__ == '__main__':
    
    userID, movieID, rating =loadDataset()

    mainDic = createDataSet(userID,movieID,rating)
	
    emptyCentroidsArr = []
    
    emptyClusterArr = []
    
    movieRatingsModel = model(8, mainDic, userID, emptyCentroidsArr, emptyClusterArr)
    
    movieRatingsModel.initialize()
    
    movieRatingsModel.train(10)


