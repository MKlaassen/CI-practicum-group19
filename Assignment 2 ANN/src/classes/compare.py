f1 = open("19_classes.txt")
f2 = open("solution.txt")
lines1 = f1.readline()
lines2 = f2.readlines()
values1 = lines1.split(', ')

for j in range(784):
        print values1[j]
        print lines2[j].rstrip()

f1.close()
f2.close()
amount1 = 0

for i in range(784):
	if values1[i]!=lines2[i].rstrip():
		amount1+=1
print "For the output layer: \n"
print "Amount of errors: %d \n" %(amount1)
print "Error percentage: (%.2f%%) \n" %(amount1*100./784)
import os

os.system("pause")
