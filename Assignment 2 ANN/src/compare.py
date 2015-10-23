f1 = open("test_outputs.txt")
f2 = open("test_targets.txt")
lines1 = f1.readlines()
lines2 = f2.readlines()
f1.close()
f2.close()
amount1 = 0
amount2 = 0
for i in range(len(lines1)):
	if lines1[i]!=lines2[i]:
		amount1+=1
print "For the test layer: \n"
print "Amount of errors: %d \n" %(amount1)
print "Error percentage: (%.2f%%) \n" %(amount1*100./len(lines1))
import os


f3 = open("validation_outputs.txt")
f4 = open("validation_targets.txt")
lines3 = f3.readlines()
lines4 = f4.readlines()
f3.close()
f4.close()
amount = 0
for i in range(len(lines3)):
	if lines3[i]!=lines4[i]:
		amount2+=1
print "For the validation layer: \n"
print "Amount of errors: %d \n" %(amount2)
print "Error percentage: (%.2f%%) \n" %(amount2*100./len(lines3))
import os


f6 = open("parameters.txt")
lines6 = f6.readlines()
f6.close()

f7 = open("results.txt","a")
f7.write ("################################################################\n")
f7.write ("For the test layer: \n")
f7.write ("Amount of errors: %d \n" %(amount1))
f7.write ("Error percentage: (%.2f%%) \n" %(amount1*100./len(lines1)))

f7.write ("For the validation layer: \n")
f7.write ("Amount of errors: %d \n" %(amount2))
f7.write ("Error percentage: (%.2f%%) \n" %(amount2*100./len(lines3)))

f7.write("Parameters: ")
f7.write("%s\n" %(lines6))
f7.close()

os.system("pause")
